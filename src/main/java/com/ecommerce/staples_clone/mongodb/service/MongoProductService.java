package com.ecommerce.staples_clone.mongodb.service;

import com.ecommerce.staples_clone.exception.ResourceNotFoundException;
import com.ecommerce.staples_clone.mongodb.model.MongoProduct;
import com.ecommerce.staples_clone.mongodb.repository.MongoProductRepository;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoProductService {
  private static final Logger log = LoggerFactory.getLogger(MongoProductService.class);
  private final MongoProductRepository productRepository;

  @Autowired
  public MongoProductService(MongoProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  // find all products
  // find product by productid
  // create new product
  // update existing product
  // delete the product
  public List<MongoProduct> getAllProducts() {
    log.info("Fetching all products from MongoDB.");
    return productRepository.findAll();
  }

  public MongoProduct getProductByProductId(String productId) {
    log.debug("Querying MongoDB for product with productId: {}", productId);
    return productRepository
        .findById(productId)
        .orElseThrow(
            () -> new ResourceNotFoundException("Product not found with productId: " + productId));
  }

  public MongoProduct getProductBySku(String sku) {
    log.debug("Querying MongoDB for product with sku: {}", sku);
    return productRepository
        .findBySku(sku)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with sku: " + sku));
  }

  public MongoProduct createProduct(MongoProduct product) {
    log.debug("Attempting to create a new product in MongoDB with SKU: {}", product.getSku());
    productRepository
        .findBySku(product.getSku())
        .ifPresent(
            p -> {
              throw new IllegalStateException(
                  "Product with SKU " + p.getSku() + " already exists.");
            });

    product.setId("PROD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    // product.setLastUpdatedDate(OffsetDateTime.now());

    MongoProduct savedProduct = productRepository.save(product);
    log.info("Successfully created new product with productId: {}", savedProduct.getId());
    return savedProduct;
  }

  public MongoProduct updateProduct(String productId, MongoProduct productDetails) {
    log.debug("Attempting to update product with productId: {}", productId);
    MongoProduct existingProduct = getProductByProductId(productId);

    existingProduct.setName(productDetails.getName());
    existingProduct.setDescription(productDetails.getDescription());
    existingProduct.setPrice(productDetails.getPrice());
    existingProduct.setStock(productDetails.getStock());
    // existingProduct.setCategoryId(productDetails.getCategoryId());
    // existingProduct.setLastUpdatedDate(OffsetDateTime.now());
    existingProduct.setOnSale(productDetails.isOnSale());
    existingProduct.setTags(productDetails.getTags());
    existingProduct.setSpecifications(productDetails.getSpecifications());
    existingProduct.setReviews(productDetails.getReviews());

    MongoProduct updatedProduct = productRepository.save(existingProduct);
    log.info("Successfully updated product with productId: {}", updatedProduct.getId());
    return updatedProduct;
  }

  public void deleteProduct(String productId) {
    log.debug("Attempting to delete product with productId: {}", productId);
    MongoProduct product = getProductByProductId(productId);
    productRepository.delete(product);
    log.info("Successfully deleted product with productId: {}", productId);
  }
}
