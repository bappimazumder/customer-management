package com.bappi.customermanagement.repository;

import com.bappi.customermanagement.model.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount,Long> {

    CustomerAccount findByAccountNumber(String accountNumber);
    long countByAccountNumber(String accountNumber);

}
