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
import com.glavatskikhvn.mortgageapplicationservice.customer.Status;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                                    @Content(schema = @Schema(implementation = CustomerGenerator.class))
                            }
                    )
            }
    )

    @PostMapping
    ResponseEntity<?> createCustomer(@RequestBody CustomerGenerator newCustomer) {
        Customer customer = newCustomer.getCustomer(newCustomer);
        if (!isExpected(newCustomer)) {
            if (!customer.fieldNotZero()) {
                return ResponseEntity.badRequest().
                        body(Collections.singletonMap("error", "one of the fields is empty"));
            }
            MortgageCalculatorApi mortgageCalculatorApi = new MortgageCalculatorApi();
            MortgageCalculateParams mortgageCalculateParams = new MortgageCalculateParams();
            mortgageCalculateParams.setCreditAmount(customer.getMortgageAmount());
            mortgageCalculateParams.setDurationInMonths(customer.getMortgagePeriod());
            BigDecimal monthlyPayment = mortgageCalculatorApi.calculate(mortgageCalculateParams).getMonthlyPayment();
            if (!customer.fieldNotNull()) {
                return ResponseEntity.badRequest().
                        body(Collections.singletonMap("error", "one of the fields is empty"));
            }
            if (customer.getSalary().divide(monthlyPayment, 0, RoundingMode.DOWN).compareTo(new BigDecimal(2)) == 1) {
                customer.setStatus(Status.APPROVED);
                customer.setMonthlyPayment(monthlyPayment);
                customerRepository.save(customer);
            } else {
                customer.setStatus(Status.DENIED);
                customerRepository.save(customer);
                return ResponseEntity.badRequest().
                        body(Collections.singletonMap("error", "low income level, increase the mortgage period or decrease mortgage amount"));
            }
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/customer/{id}").
                    build(Collections.singletonMap("id", customer.getId()))).body(customer);
        } else {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
    }

    boolean isExpected(CustomerGenerator customerGenerator) {
        if (customerRepository.findByFirstnameAndSecondNameAndPatronymicAndPassportNumber(
                customerGenerator.getFirstname(),
                customerGenerator.getSecondName(), customerGenerator.getSecondName(),
                customerGenerator.getPassportNumber()) == null) {
            return false;
        } else {
            return true;

        }
    }
}

