package com.bankMnagaement.BankManagementTask.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int countOf2000Notes;
    private int countOf500Notes;
    private int countOf100Notes;

    private double totalMoney;
}
