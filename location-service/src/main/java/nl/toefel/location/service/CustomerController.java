package nl.toefel.location.service;

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

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer/{id}")
    public Optional<Customer> customer(@PathVariable Long id) {
        Optional<Customer> x = customerRepository.findById(id);
        return x;
    }

    @PostMapping("/customer")
    public ResponseEntity<?> customer(@RequestBody Customer customer) {
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.ok(saved);
    }
}