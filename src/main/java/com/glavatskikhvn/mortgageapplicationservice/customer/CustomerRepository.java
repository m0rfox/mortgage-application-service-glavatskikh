package com.glavatskikhvn.mortgageapplicationservice.customer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, String> {

    Customer findByFirstnameAndSecondNameAndPatronymicAndPassportNumber(String firstname, String secondName,
                                                                String patronymic, String passportNumber);
}
