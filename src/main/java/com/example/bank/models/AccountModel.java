package com.example.bank.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountModel {

    private String id;

    private String accountNumber;

    private Double balance;

    private String accountType;

    private String status;

    private Date createdAt;
}
