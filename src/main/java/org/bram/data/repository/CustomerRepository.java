package org.bram.data.repository;

import org.bram.data.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

        Optional<Customer> findByEmail(String email);
}
