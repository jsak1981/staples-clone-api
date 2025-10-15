package com.ecommerce.staples_clone.repository;

import com.ecommerce.staples_clone.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findBySku(String sku);
}
