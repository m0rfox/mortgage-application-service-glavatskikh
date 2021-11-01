package com.glavatskikhvn.mortgageapplicationservice.controller;

import com.glavatskikhvn.mortgageapplicationservice.customer.Customer;
import com.glavatskikhvn.mortgageapplicationservice.customer.Sex;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerGenerator {

    private String firstname;
    private String secondName;
    private String patronymic;
    private String passportNumber;
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private int salary;
    private int mortgageAmount;
    private int mortgagePeriod;

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public Customer getCustomer(CustomerGenerator customer){
        return new Customer(generateId(), firstname, secondName, patronymic, passportNumber,
                birthdate, sex, salary, mortgageAmount, mortgagePeriod);
    }

}
