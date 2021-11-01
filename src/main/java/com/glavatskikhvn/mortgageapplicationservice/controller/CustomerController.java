package com.glavatskikhvn.mortgageapplicationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.openapitools.client.api.MortgageCalculatorApi;
import org.openapitools.client.model.MortgageCalculateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.glavatskikhvn.mortgageapplicationservice.customer.Customer;
import com.glavatskikhvn.mortgageapplicationservice.customer.CustomerRepository;
import com.glavatskikhvn.mortgageapplicationservice.customer.CustomerWithoutId;
import com.glavatskikhvn.mortgageapplicationservice.customer.Status;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Tag(name = "Customer", description = "Customer API")
@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Operation(
            operationId = "getCustomer",
            summary = "Find application by ID",
            description = "Return single customer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = {@Content}
                    )
            }
    )
    @GetMapping("/customer/{id}")
    public ResponseEntity getByID(@PathVariable String id) {
        Optional<Customer> userOpt;
        userOpt = customerRepository.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.of(userOpt);
        }
        return ResponseEntity.badRequest().
                body(Collections.singletonMap("error", "Customer not found"));
    }

    @Operation(
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = CustomerWithoutId.class))
                            }
                    )
            }
    )

    @PostMapping
    ResponseEntity<?> createCustomer(@RequestBody CustomerWithoutId customer) {
        Customer customerWithId = customer.getCustomer(customer);
        if (!isExpected(customer)) {
            if (!customerWithId.fieldNotZero()) {
                return ResponseEntity.badRequest().
                        body(Collections.singletonMap("error", "one of the fields is empty"));
            }
            MortgageCalculatorApi mortgageCalculatorApi = new MortgageCalculatorApi();
            MortgageCalculateParams mortgageCalculateParams = new MortgageCalculateParams();
            mortgageCalculateParams.setCreditAmount(BigDecimal.valueOf(customerWithId.getMortgageAmount()));
            mortgageCalculateParams.setDurationInMonths(customerWithId.getMortgagePeriod());
            BigDecimal monthlyPayment = mortgageCalculatorApi.calculate(mortgageCalculateParams).getMonthlyPayment();
            if (!customerWithId.fieldNotNull()) {
                return ResponseEntity.badRequest().
                        body(Collections.singletonMap("error", "one of the fields is empty"));
            }
            if (customer.satisfactorySalary()) {
                customerWithId.setStatus(Status.APPROVED);
                customerWithId.setMonthlyPayment(monthlyPayment);
                customerRepository.save(customerWithId);
            } else {
                customerWithId.setStatus(Status.DENIED);
                customerRepository.save(customerWithId);
            }
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/customer/{id}").
                    build(Collections.singletonMap("id", customerWithId.getId()))).body(customerWithId);
        } else {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    boolean isExpected(CustomerWithoutId customerWithoutId) {
        if (customerRepository.findByFirstnameAndSecondNameAndPatronymicAndPassportNumber(customerWithoutId.getFirstname(),
                customerWithoutId.getSecondName(), customerWithoutId.getSecondName(),
                customerWithoutId.getPassportNumber()) == null) {
            return false;
        } else {
            return true;

        }
    }
}

