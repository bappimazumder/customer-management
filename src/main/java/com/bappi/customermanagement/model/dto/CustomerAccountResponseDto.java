package com.bappi.customermanagement.model.dto;

import com.bappi.customermanagement.model.enums.AccountType;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CustomerAccountResponseDto {
    private String accountNumber;
    private String fullName;
    private String dob;
    private String accountType;
    private Boolean accountStatus;
    private Double balance;
    private Timestamp lastTransactionDate;
}
