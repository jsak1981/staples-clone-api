package com.ecommerce.staples_clone.mongodb.repository;

import com.ecommerce.staples_clone.mongodb.model.MongoCategory;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoCategoryRepository extends MongoRepository<MongoCategory, String> {

	Optional<MongoCategory> findByCategoryId(String categoryId);
}
