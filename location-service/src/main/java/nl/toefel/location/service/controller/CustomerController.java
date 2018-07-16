package nl.toefel.location.service.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import nl.toefel.location.service.access.CustomerRepository;
import nl.toefel.location.service.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@ApiModel("Customers")
@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @ApiOperation(notes = "Retieves a customer by it's ID", value = "Retrieves a customer by it's ID")
    @GetMapping("/customer/{id}")
    public Optional<Customer> createCustomer(@PathVariable Long id) {
        Optional<Customer> x = customerRepository.findById(id);
        return x;
    }

    @ApiOperation(notes = "Creates a new customer", value = "Creates a new customer in the system and returns the full entity with it's internal ID on success")
    @PostMapping("/customer")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.ok(saved);
    }
}