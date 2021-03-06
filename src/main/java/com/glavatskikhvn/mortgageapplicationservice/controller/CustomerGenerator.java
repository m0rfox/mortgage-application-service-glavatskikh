package com.glavatskikhvn.mortgageapplicationservice.controller;

import com.glavatskikhvn.mortgageapplicationservice.customer.Customer;
import com.glavatskikhvn.mortgageapplicationservice.customer.Sex;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerGenerator {

    private String secondName;
    private String firstname;
    private String patronymic;
    private String passportNumber;
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private BigDecimal salary;
    private BigDecimal mortgageAmount;
    private int mortgagePeriod;

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public Customer getCustomer(CustomerGenerator customer){
        return new Customer(generateId(), secondName, firstname,  patronymic, passportNumber,
                birthdate, sex, salary, mortgageAmount, mortgagePeriod);
    }

}
