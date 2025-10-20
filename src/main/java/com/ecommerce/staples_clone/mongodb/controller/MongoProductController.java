package com.ecommerce.staples_clone.mongodb.controller;

import com.ecommerce.staples_clone.mongodb.model.MongoProduct;
import com.ecommerce.staples_clone.mongodb.service.MongoProductService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mongo/products")
public class MongoProductController {

  private static final Logger log = LoggerFactory.getLogger(MongoProductController.class);
  private final MongoProductService productService;

  @Autowired
  public MongoProductController(MongoProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<MongoProduct>> getAllProducts() {
    log.info("Received request to get all MongoDB products.");
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{productId}")
  public ResponseEntity<MongoProduct> getProductById(@PathVariable String productId) {
    log.info("Received request to get MongoDB product by productId: {}", productId);
    return ResponseEntity.ok(productService.getProductByProductId(productId));
  }

  @GetMapping("/sku/{sku}")
  public ResponseEntity<MongoProduct> getProductBySku(@PathVariable String sku) {
    log.info("Received request to get MongoDB product by sku: {}", sku);
    return ResponseEntity.ok(productService.getProductBySku(sku));
  }

  @PostMapping
  public ResponseEntity<MongoProduct> createProduct(@Valid @RequestBody MongoProduct product) {
    log.info("Received request to create a new MongoDB product.");
    MongoProduct createdProduct = productService.createProduct(product);
    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<MongoProduct> updateProduct(
      @PathVariable String productId, @Valid @RequestBody MongoProduct product) {
    log.info("Received request to update MongoDB product with productId: {}", productId);
    MongoProduct updatedProduct = productService.updateProduct(productId, product);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
    log.info("Received request to delete MongoDB product with productId: {}", productId);
    productService.deleteProduct(productId);
    return ResponseEntity.noContent().build();
  }
}
