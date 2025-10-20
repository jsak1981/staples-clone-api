package com.ecommerce.staples_clone.mongodb.repository;

import com.ecommerce.staples_clone.mongodb.model.MongoProduct;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoProductRepository extends MongoRepository<MongoProduct, String> {

  Optional<MongoProduct> findBySku(String sku);
}
