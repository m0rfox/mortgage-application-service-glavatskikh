package com.glavatskikhvn.mortgageapplicationservice.customer;

import lombok.Data;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerWithoutId {

    private String firstname;
    private String lastName;
    private String patronymic;
    private String passportNumber;
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
    private Customer.Sex sex;
    private int salary;
    private int mortgageAmount;
    private int mortgagePeriod;

    public CustomerWithoutId(String firstname, String lastName,
                             String patronymic, String passportNumber,
                             LocalDate birthdate, Customer.Sex sex,
                             int salary, int mortgageAmount, int mortgagePeriod) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.passportNumber = passportNumber;
        this.birthdate = birthdate;
        this.sex = sex;
        this.salary = salary;
        this.mortgageAmount = mortgageAmount;
        this.mortgagePeriod = mortgagePeriod;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public Customer getCustomer(CustomerWithoutId customer){
        return new Customer(generateId(), firstname, lastName,patronymic, passportNumber,
        birthdate, sex, salary, mortgageAmount, mortgagePeriod);
    }
}
