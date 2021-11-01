package com.glavatskikhvn.mortgageapplicationservice.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Customer {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "SECOND_NAME")
    private String secondName;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "PATRONYMIC")
    private String patronymic;
    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;
    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;
    @Column(name = "SEX")
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Column(name = "SALARY")
    private BigDecimal salary;
    @Column(name = "MORTGAGE_AMOUNT")
    private BigDecimal mortgageAmount;
    @Column(name = "MORTGAGE_PERIOD")
    private int mortgagePeriod;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "MONTHLY_PAYMENT")
    private BigDecimal monthlyPayment;

    public Customer() {
    }

    public Customer(String id, String secondName, String firstname, String patronymic,
                    String passportNumber, LocalDate birthdate, Sex sex,
                    BigDecimal salary, BigDecimal mortgageAmount, int mortgagePeriod) {
        this.id = id;
        this.secondName = secondName;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.passportNumber = passportNumber;
        this.birthdate = birthdate;
        this.sex = sex;
        this.salary = salary;
        this.mortgageAmount = mortgageAmount;
        this.mortgagePeriod = mortgagePeriod;
    }

    public boolean fieldNotZero() {
        return this.salary.compareTo(BigDecimal.ZERO) !=0 &&
                this.mortgageAmount.compareTo(BigDecimal.ZERO) !=0 &&
                this.mortgagePeriod != 0;
    }
    public boolean fieldNotNull() {
        return this.secondName != null &&
                this.firstname != null &&
                this.patronymic != null &&
                this.passportNumber != null &&
                this.birthdate != null &&
                this.sex != null;
    }
}