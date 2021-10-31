package com.glavatskikhvn.mortgageapplicationservice.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, String> {
    Customer findByFirstnameAndLastNameAndPatronymicAndPassportNumber(String firstname, String lastName,
                                                                      String patronymic, String passportNumber);
}

