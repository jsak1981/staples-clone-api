package com.ecommerce.staples_clone.batch;

import com.ecommerce.staples_clone.dto.ProductCsvDTO;
import com.ecommerce.staples_clone.exception.ResourceNotFoundException;
import com.ecommerce.staples_clone.model.Product;
import com.ecommerce.staples_clone.model.ProductCategory;
import com.ecommerce.staples_clone.repository.ProductCategoryRepository;
import com.ecommerce.staples_clone.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductItemProcessor implements ItemProcessor<ProductCsvDTO, Product> {

  private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);
  private final ProductRepository productRepository;
  private final ProductCategoryRepository categoryRepository;

  public ProductItemProcessor(ProductRepository p, ProductCategoryRepository c) {
    this.productRepository = p;
    this.categoryRepository = c;
  }

  @Override
  public Product process(ProductCsvDTO item) throws Exception {
    log.debug("Processing item with SKU: {}", item.getSku());

    Product product = productRepository.findBySku(item.getSku()).orElse(new Product());

    product.setName(item.getName());
    product.setDescription(item.getDescription());
    product.setSku(item.getSku());
    product.setPrice(item.getPrice());
    product.setStockQuantity(item.getStockQuantity());
    ProductCategory category =
        categoryRepository
            .findById(item.getCategoryId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Category not found for ID: " + item.getCategoryId()));
    product.setCategory(category);

    return product;
  }
}
