package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.dto.ProductRequestDTO;
import com.ecommerce.staples_clone.exception.ResourceNotFoundException;
import com.ecommerce.staples_clone.model.Product;
import com.ecommerce.staples_clone.model.ProductCategory;
import com.ecommerce.staples_clone.repository.ProductCategoryRepository;
import com.ecommerce.staples_clone.repository.ProductRepository;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ProductService {
  private static final Logger log = LoggerFactory.getLogger(ProductService.class);
  private final ProductRepository productRepository;
  private final ProductCategoryRepository categoryRepository;

  @Autowired
  public ProductService(ProductRepository p, ProductCategoryRepository c) {
    this.productRepository = p;
    this.categoryRepository = c;
  }

  @Transactional(readOnly = true)
  public List<Product> getAllProducts() {
    log.debug("Fetching all products from database");
    return productRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Product getProductById(Long id) {
    log.debug("Querying database for product with id: {}", id);
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
  }

  @Transactional
  public Product createProduct(ProductRequestDTO dto) {
    log.debug("Creating new Product");
    Product product = mapDtoToEntity(dto, new Product());
    return productRepository.save(product);
  }

  @Transactional
  public Product updateProduct(Long id, ProductRequestDTO productDto) {
    log.debug("Attempting to update product with id: {}", id);
    Product product = getProductById(id);
    return mapDtoToEntity(productDto, product);
  }

  @Transactional
  public Product patchProduct(Long id, Map<String, Object> updates) {
    log.debug("Attempting to patch paroduct with id: {}", id);

    Product existingProduct = getProductById(id);
    updates.forEach(
        (key, value) -> {
          try {
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field == null) throw new NoSuchFieldException();
            ReflectionUtils.makeAccessible(field);
            if (field.getType().equals(BigDecimal.class) && value instanceof Number) {
              ReflectionUtils.setField(field, existingProduct, new BigDecimal(value.toString()));
            } else {
              ReflectionUtils.setField(field, existingProduct, value);
            }
          } catch (NoSuchFieldException ex) {
            log.error("Field not found for patching: {}", key, ex);
            throw new IllegalArgumentException("Invalid field for patching: " + key);
          }
        });
    return productRepository.save(existingProduct);
  }

  @Transactional
  public void deleteProduct(Long id) {
    log.debug("Deleting product with id: {}", id);
    if (!productRepository.existsById(id)) {
      throw new ResourceAccessException("Product not found with id: " + id);
    }
    productRepository.deleteById(id);
  }

  private Product mapDtoToEntity(ProductRequestDTO dto, Product product) {
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setSku(dto.getSku());
    product.setPrice(dto.getPrice());
    product.setStockQuantity(dto.getStockQuantity());

    if (dto.getCategoryId() != null) {
      ProductCategory category =
          categoryRepository
              .findById(dto.getCategoryId())
              .orElseThrow(
                  () -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
      product.setCategory(category);
    }
    return product;
  }
}
