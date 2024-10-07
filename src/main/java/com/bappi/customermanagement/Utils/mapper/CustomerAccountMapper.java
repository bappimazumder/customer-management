package com.bappi.customermanagement.Utils.mapper;

import com.bappi.customermanagement.model.dto.CustomerAccountResponseDto;
import com.bappi.customermanagement.model.entity.CustomerAccount;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerAccountMapper {
    CustomerAccountResponseDto map(CustomerAccount obj);
}
