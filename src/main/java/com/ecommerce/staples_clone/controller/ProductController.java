package com.ecommerce.staples_clone.controller;

import com.ecommerce.staples_clone.dto.ProductRequestDTO;
import com.ecommerce.staples_clone.dto.ProductResponseDTO;
import com.ecommerce.staples_clone.model.Product;
import com.ecommerce.staples_clone.service.ProductService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private static final Logger log = LoggerFactory.getLogger(ProductController.class);
  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
    log.info("Received GET request for all the products");
    List<Product> products = productService.getAllProducts();
    return ResponseEntity.ok(
        products.stream().map(this::convertToDto).collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
    log.info("Received request to get product by id: {}", id);

    return productService
        .getProductById(id)
        .map(this::convertToDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<ProductResponseDTO> createProduct(
      @RequestBody ProductRequestDTO productDto) {
    log.info("Received request to create a new product");
    log.debug("Product payload: {}", productDto);
    Product newProduct = productService.createProduct(productDto);
    log.info("Successfully created product with id: {}", newProduct.getProductId());
    return new ResponseEntity<>(convertToDto(newProduct), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> updateProduct(
      @PathVariable Long id, @RequestBody ProductRequestDTO productDto) {
    log.info("Received request to update product with id: {}", id);
    log.debug("Update payload: {}", productDto);

    return productService
        .updateProduct(id, productDto)
        .map(this::convertToDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> patchProduct(
      @PathVariable Long id, @RequestBody Map<String, Object> updates) {
    log.info("Received PATCH request to partially update product id: {}", id);
    return productService
        .patchProduct(id, updates)
        .map(this::convertToDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    log.info("Received request to delete product with id: {}", id);
    if (productService.deleteProduct(id)) {
      log.info("Successfully deleted product with id: {}", id);
      return ResponseEntity.noContent().build();
    } else {
      log.warn("Product not found with id: {}, cannot delete", id);
      return ResponseEntity.notFound().build();
    }
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
  public ResponseEntity<Void> headProduct(@PathVariable Long id) {
    log.info("Received HEAD request for product id: {}", id);
    boolean exists = productService.getProductById(id).isPresent();
    if (exists) {
      log.info("Product exists for HEAD request, id: {}", id);
      return ResponseEntity.ok().build();
    } else {
      log.warn("Product not found for HEAD request, id: {}", id);
      return ResponseEntity.notFound().build();
    }
  }

  @RequestMapping(method = RequestMethod.OPTIONS)
  public ResponseEntity<Void> options() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Allow", "GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS");
    return new ResponseEntity<>(null, headers, HttpStatus.OK);
  }

  private ProductResponseDTO convertToDto(Product product) {
    ProductResponseDTO dto = new ProductResponseDTO();
    dto.setProductId(product.getProductId());
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setSku(product.getSku());
    dto.setPrice(product.getPrice());
    dto.setStockQuantity(product.getStockQuantity());

    if (product.getCategory() != null) {
      dto.setCategoryName(product.getCategory().getCategoryName());
    }
    return dto;
  }
}
