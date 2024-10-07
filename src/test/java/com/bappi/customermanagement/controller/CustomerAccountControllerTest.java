package com.bappi.customermanagement.controller;

import com.bappi.customermanagement.Utils.error.APIErrorCode;
import com.bappi.customermanagement.Utils.error.CustomException;
import com.bappi.customermanagement.model.dto.CustomerAccountResponseDto;
import com.bappi.customermanagement.model.dto.TransferRequestDto;
import com.bappi.customermanagement.model.dto.TransferResponseDto;
import com.bappi.customermanagement.service.CustomerAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Timestamp;

import static com.bappi.customermanagement.config.ApiPath.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerAccountController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    CustomerAccountService service;
    @Mock
    private ObjectMapper objectMapper;

    CustomerAccountResponseDto customerAccount;

    TransferRequestDto tranferRequestDto;

    @BeforeEach
    public void setup(){
         customerAccount = CustomerAccountResponseDto.builder()
                                                    .accountNumber("1111111111")
                                                    .fullName("David")
                                                    .dob("2023-05-23")
                                                    .accountType("Savings Account")
                                                    .accountStatus(Boolean.TRUE)
                                                    .balance(10000.0)
                                                    .lastTransactionDate(new Timestamp(System.currentTimeMillis()))
                                                    .build();
        tranferRequestDto = new TransferRequestDto("1111","2222",2000.0);
        objectMapper = new ObjectMapper();

    }

    @Test
    @Order(1)
    void getAccountDetailsTest() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("accountNumber", customerAccount.getAccountNumber());
        when(service.getByAccountNo(customerAccount.getAccountNumber())).thenReturn(customerAccount);
        // action
        ResultActions response = mockMvc.perform(get(API_BASE_PATH+API_ACCOUNT+API_GET_ACC_DETAILS)
                .params(requestParams));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.accountNumber", is(customerAccount.getAccountNumber())))
                .andExpect(jsonPath("$.fullName", is(customerAccount.getFullName())))
                .andExpect(jsonPath("$.balance", is(customerAccount.getBalance())));


    }

    //Post Controller
    @Test
    @Order(2)
    public void testTransferAmount_Success() throws Exception{
        TransferResponseDto responseDto = new TransferResponseDto("Transfer Successfully",null);
        when(service.transferAmount(any(TransferRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post(API_BASE_PATH+API_ACCOUNT+API_POST_TRANSFER_AMOUNT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tranferRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Transfer Successfully"));
    }

    @Test
    @Order(3)
    void testTransferAmount_Failure() throws Exception {
        TransferResponseDto responseDto = new TransferResponseDto("Invalid sender or receiver account no", APIErrorCode.WRONG_INFORMATION_PROVIDED);
        when(service.transferAmount(any(TransferRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post(API_BASE_PATH+API_ACCOUNT+API_POST_TRANSFER_AMOUNT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tranferRequestDto))) // Serialize to JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Invalid sender or receiver account no"));
    }

    @Test
    @Order(4)
    void testTransferAmount_Exception() throws Exception {
        when(service.transferAmount(any(TransferRequestDto.class))).thenThrow(new CustomException(APIErrorCode.INTERNAL_SERVER_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(post(API_BASE_PATH+API_ACCOUNT+API_POST_TRANSFER_AMOUNT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tranferRequestDto))) // Serialize to JSON
                .andExpect(status().isInternalServerError());

    }

}
