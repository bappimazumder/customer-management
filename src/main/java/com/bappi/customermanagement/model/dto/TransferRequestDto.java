package com.bappi.customermanagement.model.dto;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    @NonNull
    private String senderAccountNo;
    @NonNull
    private String receiverAccountNo;
    @NonNull
    private Double amount;
}
