package com.bappi.customermanagement.Utils.error;

import com.bappi.customermanagement.Utils.error.APIErrorCode;
import com.bappi.customermanagement.Utils.error.IAPIErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{
    private IAPIErrorCode code;
    private final HttpStatus httpStatus;
    private final String data;

    public CustomException(IAPIErrorCode errorCode, HttpStatus httpStatus) {
        this.code = errorCode;
        this.httpStatus = httpStatus;
        this.data = null;
    }

    public CustomException(IAPIErrorCode errorCode, HttpStatus httpStatus, String data) {
        this.code = errorCode;
        this.httpStatus = httpStatus;
        this.data = data;
    }

}
