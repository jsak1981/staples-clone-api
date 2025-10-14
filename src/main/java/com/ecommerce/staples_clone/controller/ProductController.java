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
    Product product = productService.getProductById(id);
    return ResponseEntity.ok(convertToDto(product));
  }

  @PostMapping
  public ResponseEntity<ProductResponseDTO> createProduct(
      @RequestBody ProductRequestDTO productDto) {
    log.info("Received request to create a new product");
    log.debug("Product payload: {}", productDto);

    Product newProduct = productService.createProduct(productDto);
    return new ResponseEntity<>(convertToDto(newProduct), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> updateProduct(
      @PathVariable Long id, @RequestBody ProductRequestDTO productDto) {
    log.info("Received request to update product with id: {}", id);
    log.debug("Update payload: {}", productDto);

    Product newProduct = productService.updateProduct(id, productDto);
    return new ResponseEntity<>(convertToDto(newProduct), HttpStatus.CREATED);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> patchProduct(
      @PathVariable Long id, @RequestBody Map<String, Object> updates) {
    log.info("Received PATCH request to partially update product id: {}", id);

    Product updatedProduct = productService.patchProduct(id, updates);
    return ResponseEntity.ok(convertToDto(updatedProduct));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    log.info("Received request to delete product with id: {}", id);

    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
  public ResponseEntity<Void> headProduct(@PathVariable Long id) {
    log.info("Received HEAD request for product id: {}", id);
    Product existingProduct = productService.getProductById(id);
    return ResponseEntity.ok().build();
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
