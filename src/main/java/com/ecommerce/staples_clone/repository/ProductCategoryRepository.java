package com.ecommerce.staples_clone.repository;

import com.ecommerce.staples_clone.model.Product;
import com.ecommerce.staples_clone.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {}
