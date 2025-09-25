package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.dto.ProductRequestDTO;
import com.ecommerce.staples_clone.model.Product;
import com.ecommerce.staples_clone.model.ProductCategory;
import com.ecommerce.staples_clone.repository.ProductCategoryRepository;
import com.ecommerce.staples_clone.repository.ProductRepository;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  public Optional<Product> getProductById(Long id) {
    log.debug("Querying database for product with id: {}", id);
    if (!productRepository.existsById(id)) {
      throw new ResourceAccessException("Product not found with id: " + id);
    }
    return productRepository.findById(id);
  }

  @Transactional
  public Product createProduct(ProductRequestDTO dto) {
    log.debug("Creating new Product");
    Product product = mapDtoToEntity(dto, new Product());
    return productRepository.save(product);
  }

  @Transactional
  public Optional<Product> updateProduct(Long id, ProductRequestDTO dto) {
    log.debug("Attempting to update product with id: {}", id);
    return productRepository
        .findById(id)
        .map(
            existingProduct -> {
              Product updatedProduct = mapDtoToEntity(dto, existingProduct);
              return productRepository.save(updatedProduct);
            });
  }

  @Transactional
  public Optional<Product> patchProduct(Long id, Map<String, Object> updates) {
    log.debug("Attempting to patch paroduct with id: {}", id);

    return productRepository
        .findById(id)
        .map(
            product -> {
              updates.forEach(
                  (key, value) -> {
                    try {
                      Field field = Product.class.getDeclaredField(key);
                      field.setAccessible(true);

                      if (field.getType().equals(Integer.class)
                          || field.getType().equals(int.class)) {
                        field.set(product, ((Number) value).intValue());
                      } else {
                        field.set(product, value);
                      }

                    } catch (NoSuchFieldException | IllegalAccessException ex) {
                      log.error("Field not found or could not be accessed: {}", key, ex);
                      throw new RuntimeException("Invalid field for patching: " + key);
                    }
                  });
              return productRepository.save(product);
            });
  }

  @Transactional
  public Boolean deleteProduct(Long id) {
    log.debug("Deleting product with id: {}", id);
    if (!productRepository.existsById(id)) {
      throw new ResourceAccessException("Product not found with id: " + id);
    }
    productRepository.deleteById(id);
    return true;
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
