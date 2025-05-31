package com.example.bank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Long amount;

    private String description;

    private String status;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Accounts fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Accounts toAccount;
}
