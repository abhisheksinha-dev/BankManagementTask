package com.bankMnagaement.BankManagementTask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id")
    private AppUser accountHolder;

    private String accountHolderName;

    private double balance;

    @OneToMany(mappedBy = "bankAccount",cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
