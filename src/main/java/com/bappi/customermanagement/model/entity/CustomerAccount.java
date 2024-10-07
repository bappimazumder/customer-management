package com.bappi.customermanagement.model.entity;

import com.bappi.customermanagement.config.CustomerAccountDBConstant;
import com.bappi.customermanagement.model.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = CustomerAccountDBConstant.CUSTOMER_ACC_TABLE)
public class CustomerAccount implements Serializable {

    @Id
    @Column(name = CustomerAccountDBConstant.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = CustomerAccountDBConstant.ACCOUNT_NUMBER,unique=true)
    private String accountNumber;

    @Column(name = CustomerAccountDBConstant.FULL_NAME)
    private String fullName;

    @Column(name = CustomerAccountDBConstant.DATE_OF_BIRTH)
    private String dob;

    @Column(name = CustomerAccountDBConstant.ACCOUNT_TYPE)
    // @Enumerated(EnumType.STRING)
    private String accountType;

    @NonNull
    @Column(name = CustomerAccountDBConstant.ACCOUNT_STATUS)
    private Boolean accountStatus;

    @Column(name = CustomerAccountDBConstant.BALANCE)
    private Double balance;

    @Column(name = CustomerAccountDBConstant.LAST_TRANSACTION_DATE)
    private Timestamp lastTransactionDate;

}
