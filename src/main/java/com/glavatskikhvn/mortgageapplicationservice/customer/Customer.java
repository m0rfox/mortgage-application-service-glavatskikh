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
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "SECOND_NAME")
    private String secondName;
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
    private int salary;
    @Column(name = "MORTGAGE_AMOUNT")
    private int mortgageAmount;
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

    public Customer(String id, String firstname, String secondName, String patronymic,
                    String passportNumber, LocalDate birthdate, Sex sex,
                    int salary, int mortgageAmount, int mortgagePeriod) {
        this.id = id;
        this.firstname = firstname;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.passportNumber = passportNumber;
        this.birthdate = birthdate;
        this.sex = sex;
        this.salary = salary;
        this.mortgageAmount = mortgageAmount;
        this.mortgagePeriod = mortgagePeriod;
    }

    public boolean fieldNotZero() {
        return this.salary != 0 &&
                this.mortgageAmount != 0 &&
                this.mortgagePeriod != 0;
    }
    public boolean fieldNotNull() {
        return this.firstname != null &&
                this.secondName != null &&
                this.patronymic != null &&
                this.passportNumber != null &&
                this.birthdate != null &&
                this.sex != null;
    }
}