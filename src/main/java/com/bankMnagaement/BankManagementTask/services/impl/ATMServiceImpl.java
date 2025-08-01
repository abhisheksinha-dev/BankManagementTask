package com.bankMnagaement.BankManagementTask.services.impl;

import com.bankMnagaement.BankManagementTask.dtos.ATMDto;
import com.bankMnagaement.BankManagementTask.entities.ATM;
import com.bankMnagaement.BankManagementTask.entities.AppUser;
import com.bankMnagaement.BankManagementTask.entities.BankAccount;
import com.bankMnagaement.BankManagementTask.exception.InvalidIdException;
import com.bankMnagaement.BankManagementTask.exception.ResourceNotFoundException;
import com.bankMnagaement.BankManagementTask.repositories.ATMRepository;
import com.bankMnagaement.BankManagementTask.repositories.AppUserRepository;
import com.bankMnagaement.BankManagementTask.repositories.BankAccountRepository;
import com.bankMnagaement.BankManagementTask.services.ATMService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ATMServiceImpl implements ATMService {

    private final ATMRepository atmRepository;
    private final ModelMapper modelMapper;
    private final AppUserRepository appUserRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public String createAtm(ATMDto dto) {
        ATM atm = new ATM();
        atm.setCountOf100Notes(dto.getCountOf100Notes());
        atm.setCountOf500Notes(dto.getCountOf500Notes());
        atm.setCountOf2000Notes(dto.getCountOf2000Notes());
        double totalMoney =
                dto.getCountOf100Notes() * 100 +
                        dto.getCountOf500Notes() * 500 +
                        dto.getCountOf2000Notes() * 2000;

        atm.setTotalMoney(totalMoney);
        atmRepository.save(atm);
        return "ATM created with Total Money : "+ totalMoney;
    }

    @Override
    @Transactional
    public String depositMoney(ATMDto atmDto, String id) {

        ATM atm = atmRepository.findById(id).orElseThrow(()-> new InvalidIdException("ID IS WRONG"));

            atm.setCountOf100Notes(atmDto.getCountOf100Notes() + atm.getCountOf100Notes());
            atm.setCountOf500Notes(atmDto.getCountOf500Notes() + atm.getCountOf500Notes());
            atm.setCountOf2000Notes(atmDto.getCountOf2000Notes() + atm.getCountOf2000Notes());

            double totalDepositedAmount =
                                atmDto.getCountOf100Notes() * 100 +
                                atmDto.getCountOf500Notes() * 500 +
                                atmDto.getCountOf2000Notes() * 2000;

            double totalMoney =
                                atm.getCountOf100Notes() * 100 +
                                atm.getCountOf500Notes() * 500 +
                                atm.getCountOf2000Notes() * 2000;

        atm.setTotalMoney(totalMoney);

        return "total deposited amount : " + totalDepositedAmount + "and total ATM balance : "+ totalMoney;
    }

    @Override
    @Transactional
    public String withdrawMoney(double amount,String atmId) {

        ATM atm = atmRepository.findById(atmId).orElseThrow(() -> new InvalidIdException("ID IS WRONG"));

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser currentUser = appUserRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("No User"));

        BankAccount userAccount = currentUser.getBankAccount();
        if (userAccount.getBalance() < amount){
            return "you don't have sufficient balance to withdraw";
        }

        if (amount > atm.getTotalMoney())
            return "INSUFFICIENT FUNDS";

        StringBuilder message = new StringBuilder();

        double remaining = amount;

//        if (remaining >= 2000 && atm.getCountOf2000Notes()>0)
//            return ifAmountGreaterThan2000(amount);
//
//        if (amount > 500 && amount < atm.getTotalMoney())
//            return ifAmountGreaterThan500(amount);
//
//        if (amount > 100 && amount < atm.getTotalMoney())
//            return ifAmountGreaterThan100(amount);
//
//        return "we don't have notes less than 100";
//    }
//
//    private String ifAmountGreaterThan100(double amount){
//        int total100Notes = (int) (amount/100);
//        double extraAmount = amount - (total100Notes * 100);
//        return "Total 100 notes withdrawn : "+total100Notes +" remaining amount : "+extraAmount;
//    }
//
//    private String  ifAmountGreaterThan500(double amount){
//        int total500Notes = (int) (amount/500);
//        double extraAmount = amount - (total500Notes * 500);
//        if(extraAmount > 100)
//            return ifAmountGreaterThan100(extraAmount);
//
//        return "Total 500 notes withdrawn : "+total500Notes;
//    }
//
//    private String ifAmountGreaterThan2000(double amount){
//        int total2000Notes = (int) (amount/2000);
//        double extraAmount = amount - (total2000Notes * 2000);
//        if (extraAmount > 500)
//            return ifAmountGreaterThan500(extraAmount);
//        else if (extraAmount > 100)
//            return ifAmountGreaterThan100(extraAmount);
//
//        return "Total 2000 notes withdrawn : "+total2000Notes;
//    }

        if (remaining >= 2000 && atm.getCountOf2000Notes() > 0) {
            int notes = Math.min((int) (remaining / 2000), atm.getCountOf2000Notes());
            remaining -= notes * 2000;
            atm.setCountOf2000Notes(atm.getCountOf2000Notes() - notes);
            message.append("2000 x ").append(notes).append("\n");
        }

        if (remaining >= 500 && atm.getCountOf500Notes() > 0) {
            int notes = Math.min((int) (remaining / 500), atm.getCountOf500Notes());
            remaining -= notes * 500;
            atm.setCountOf500Notes(atm.getCountOf500Notes() - notes);
            message.append("500 x ").append(notes).append("\n");
        }

        if (remaining >= 100 && atm.getCountOf100Notes() > 0) {
            int notes = Math.min((int) (remaining / 100), atm.getCountOf100Notes());
            remaining -= notes * 100;
            atm.setCountOf100Notes(atm.getCountOf100Notes() - notes);
            message.append("100 x ").append(notes).append("\n");
        }

        if (remaining > 0) {
            return "ATM don't has smaller notes";
        }

        double totalMoney =
                        atm.getCountOf2000Notes() * 2000 +
                        atm.getCountOf500Notes() * 500 +
                        atm.getCountOf100Notes() * 100;

        atm.setTotalMoney(totalMoney);

        userAccount.setBalance(userAccount.getBalance() - amount);

        bankAccountRepository.save(userAccount);
        atmRepository.save(atm);

        return "Withdrawn Rs. "+ amount + "Successful ! breakdown:\n" + message.toString();
    }

    @Override
    public String checkTotalMoney(String atmId) {
        ATM atm = atmRepository.findById(atmId).orElseThrow(() -> new InvalidIdException("ID IS WRONG"));
        return "Total Money Remaining in ATM = "+ atm.getTotalMoney();
    }

}
