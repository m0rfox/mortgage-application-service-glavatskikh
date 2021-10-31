package com.glavatskikhvn.mortgageapplicationservice.customer;

import lombok.Data;
import org.openapitools.client.api.MortgageCalculatorApi;
import org.openapitools.client.model.MortgageCalculateParams;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
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
    private Sex sex;
    private int salary;
    private int mortgageAmount;
    private int mortgagePeriod;

    public CustomerWithoutId(String firstname, String lastName,
                             String patronymic, String passportNumber,
                             LocalDate birthdate, Sex sex,
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
        return new Customer(generateId(), firstname, lastName, patronymic, passportNumber,
        birthdate, sex, salary, mortgageAmount, mortgagePeriod);
    }

    private final MortgageCalculatorApi mortgageCalculatorApi = new MortgageCalculatorApi();
    private final MortgageCalculateParams mortgageCalculateParams = new MortgageCalculateParams();
    private final BigDecimal monthlyPayment = mortgageCalculatorApi.calculate(mortgageCalculateParams).getMonthlyPayment();
    public boolean satisfactorySalary(){
        return this.salary/monthlyPayment.doubleValue() >= 2;
    }
}
