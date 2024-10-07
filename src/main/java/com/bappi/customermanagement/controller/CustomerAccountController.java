package com.bappi.customermanagement.controller;

import com.bappi.customermanagement.Utils.error.APIErrorCode;
import com.bappi.customermanagement.Utils.error.CustomException;
import com.bappi.customermanagement.config.ApiPath;
import com.bappi.customermanagement.model.dto.CustomerAccountResponseDto;
import com.bappi.customermanagement.model.dto.TransferRequestDto;
import com.bappi.customermanagement.model.dto.TransferResponseDto;
import com.bappi.customermanagement.service.CustomerAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.API_ACCOUNT)
public class CustomerAccountController {

    private final CustomerAccountService service;

    @Autowired
    public CustomerAccountController(CustomerAccountService customerAccountService) {
        this.service = customerAccountService;
    }


    @GetMapping(value = ApiPath.API_GET_ACC_DETAILS)
    public ResponseEntity<CustomerAccountResponseDto> getDetails(@RequestParam(value = "accountNumber") String accountNumber){

        if(accountNumber.equals("null") || accountNumber.isEmpty()){
            throw new CustomException(APIErrorCode.INVALID_REQUEST,HttpStatus.BAD_REQUEST);
        }
        CustomerAccountResponseDto dto =  service.getByAccountNo(accountNumber);
        log.info("Account details to return: " + dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = ApiPath.API_POST_TRANSFER_AMOUNT)
    public ResponseEntity<TransferResponseDto> transferAmount(@RequestBody TransferRequestDto transferRequestDto){
        log.info("Transfer Amount{} ", transferRequestDto.toString());
        TransferResponseDto response = service.transferAmount(transferRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
