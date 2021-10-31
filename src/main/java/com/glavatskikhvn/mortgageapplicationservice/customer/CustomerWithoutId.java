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

    public Customer getCustomer(CustomerWithoutId customer){
        return new Customer(generateId(), firstname, secondName, patronymic, passportNumber,
                birthdate, sex, salary, mortgageAmount, mortgagePeriod);
    }


    private final MortgageCalculatorApi mortgageCalculatorApi = new MortgageCalculatorApi();
    private final MortgageCalculateParams mortgageCalculateParams = new MortgageCalculateParams();
    private final BigDecimal monthlyPayment = mortgageCalculatorApi.calculate(mortgageCalculateParams).getMonthlyPayment();
    public boolean satisfactorySalary(){
        return this.salary/monthlyPayment.doubleValue() >= 2;
    }
}
