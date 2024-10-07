package com.bappi.customermanagement.service;

import com.bappi.customermanagement.Utils.error.APIErrorCode;
import com.bappi.customermanagement.Utils.error.CustomException;
import com.bappi.customermanagement.Utils.mapper.CustomerAccountMapper;
import com.bappi.customermanagement.model.dto.CustomerAccountResponseDto;
import com.bappi.customermanagement.model.dto.TransferRequestDto;
import com.bappi.customermanagement.model.dto.TransferResponseDto;
import com.bappi.customermanagement.model.entity.CustomerAccount;
import com.bappi.customermanagement.repository.CustomerAccountRepository;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Slf4j
@Service
public class CustomerAccountService {

    private final CustomerAccountRepository repository;
    private final CustomerAccountMapper objectMapper;

    @Autowired
    public CustomerAccountService(CustomerAccountRepository repository) {
        this.repository = repository;
        this.objectMapper = Mappers.getMapper(CustomerAccountMapper.class);;
    }

    public CustomerAccountResponseDto getByAccountNo(String accountNo) {
        log.debug("Get Customer account details by account no {} " , accountNo);

        CustomerAccount accountDetails = repository.findByAccountNumber(accountNo);
        if(accountDetails == null){
            throw new CustomException(APIErrorCode.WRONG_INFORMATION_PROVIDED,HttpStatus.BAD_REQUEST);
        }
        return objectMapper.map(accountDetails);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public TransferResponseDto transferAmount(TransferRequestDto dto) {
        TransferResponseDto response = new TransferResponseDto();
        CustomerAccount senderAccount = repository.findByAccountNumber(dto.getSenderAccountNo());
        CustomerAccount receiverAccount = repository.findByAccountNumber(dto.getReceiverAccountNo());


        if(dto.getAmount() < 1){
            log.error("Amount is less than 1");
            response.setResponseMessage("Amount is less than 1.0");
            response.setErrorCode(APIErrorCode.INVALID_REQUEST);
            return response;
        }

        if (senderAccount == null  || receiverAccount == null){
            log.error("Invalid sender or receiver account no");
            response.setResponseMessage("Invalid sender or receiver account no");
            response.setErrorCode(APIErrorCode.WRONG_INFORMATION_PROVIDED);
            return response;
        }

        if (!senderAccount.getAccountStatus() || !receiverAccount.getAccountStatus()){
            log.error("Invalid sender or receiver account status");
            response.setResponseMessage("Invalid sender or receiver account status");
            response.setErrorCode(APIErrorCode.INVALID_REQUEST);
            return response;
        }


        if (senderAccount.getBalance() < dto.getAmount()){
            log.error("Sender account has exceeded the current amount");
            response.setResponseMessage("Sender account has exceeded the current amount");
            response.setErrorCode(APIErrorCode.INVALID_REQUEST);
            return response;
        }

        try {
            // Sender Account Update
            senderAccount.setBalance(senderAccount.getBalance() - dto.getAmount());
            senderAccount.setLastTransactionDate(new Timestamp(System.currentTimeMillis()));
            repository.save(senderAccount);

            // Receiver Account Update
            receiverAccount.setBalance(receiverAccount.getBalance()+dto.getAmount());
            receiverAccount.setLastTransactionDate(new Timestamp(System.currentTimeMillis()));
            repository.save(receiverAccount);

        }catch(Exception exception){
            log.error("Transfer Save Failed" + exception.getMessage());
            throw new CustomException(APIErrorCode.INTERNAL_SERVER_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setResponseMessage("Transfer Successfully");
        response.setErrorCode(null);
        return response;
    }
}
