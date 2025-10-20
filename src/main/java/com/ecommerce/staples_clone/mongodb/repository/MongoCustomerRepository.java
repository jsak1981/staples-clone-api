package com.ecommerce.staples_clone.mongodb.repository;

import com.ecommerce.staples_clone.mongodb.model.MongoCustomer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoCustomerRepository extends MongoRepository<MongoCustomer, String> {
	// Spring Data will automatically create the query for this method based on its name.
	// It will find a customer by the 'customerId' field.
	Optional<MongoCustomer> findByCustomerId(String customerId);
}
