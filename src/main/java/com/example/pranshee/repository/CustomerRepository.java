package com.example.pranshee.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.pranshee.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

   Optional<Customer> findByEmail(String email);

}
