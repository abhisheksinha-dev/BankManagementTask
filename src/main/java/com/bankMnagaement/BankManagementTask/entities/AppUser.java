package com.bankMnagaement.BankManagementTask.entities;

import com.bankMnagaement.BankManagementTask.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleType role;

    @OneToOne(mappedBy = "accountHolder")
    private BankAccount bankAccount;
}
