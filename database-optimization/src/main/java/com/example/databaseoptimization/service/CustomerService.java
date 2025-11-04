package com.example.databaseoptimization.service;

import com.example.databaseoptimization.model.Customer;
import com.example.databaseoptimization.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    @Transactional
    public Optional<Customer> update(Long id, Customer updated) {
        return repository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            return repository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}