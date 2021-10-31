package com.glavatskikhvn.mortgageapplicationservice.customer;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Table
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "passportNumber")
    private String passportNumber;
    @Column(name = "birthdate")
    private LocalDate birthdate;
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Column(name = "salary")
    private int salary;
    @Column(name = "mortgageAmount")
    private int mortgageAmount;
    @Column(name = "mortgagePeriod")
    private int mortgagePeriod;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;


    public Customer() {
    }

    public Customer(String firstname, String lastName, String patronymic,
                    String passportNumber, LocalDate birthdate,
                    Sex sex, int salary, int mortgageAmount, int mortgagePeriod) {
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

    public Customer(String id, String firstname, String lastName, String patronymic,
                    String passportNumber, LocalDate birthdate,
                    Sex sex, int salary, int mortgageAmount, int mortgagePeriod, Status status) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.passportNumber = passportNumber;
        this.birthdate = birthdate;
        this.sex = sex;
        this.salary = salary;
        this.mortgageAmount = mortgageAmount;
        this.mortgagePeriod = mortgagePeriod;
        this.status = status;
    }

    public Customer(String id, String firstname, String lastName, String patronymic,
                    String passportNumber, LocalDate birthdate,
                    Sex sex, int salary, int mortgageAmount, int mortgagePeriod) {
        this.id = id;
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


    public boolean fieldNoZero() {
        return this.salary != 0 &&
            this.mortgageAmount != 0 &&
            this.mortgagePeriod != 0;
    }
    public boolean fieldNoNull() {
        return this.firstname != null &&
                this.lastName != null &&
                this.patronymic != null &&
                this.passportNumber != null &&
                this.birthdate != null &&
                this.sex != null;
    }



}
