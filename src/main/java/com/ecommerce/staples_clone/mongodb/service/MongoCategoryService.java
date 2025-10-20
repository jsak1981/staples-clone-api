package com.ecommerce.staples_clone.mongodb.service;

import com.ecommerce.staples_clone.exception.ResourceNotFoundException;
import com.ecommerce.staples_clone.mongodb.model.MongoCategory;
import com.ecommerce.staples_clone.mongodb.repository.MongoCategoryRepository;
import java.util.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoCategoryService {

  private static final Logger log = LoggerFactory.getLogger(MongoCategoryService.class);
  private final MongoCategoryRepository categoryRepository;

  @Autowired
  public MongoCategoryService(MongoCategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<MongoCategory> getAllCategories() {
    log.info("Fetching all categories from MongoDB.");
    return categoryRepository.findAll();
  }

  public MongoCategory getCategoryByCategoryId(String categoryId) {
    log.debug("Querying MongoDB for category with categoryId: {}", categoryId);
    return categoryRepository
        .findByCategoryId(categoryId)
        .orElseThrow(
            () -> {
              log.warn("Failed to find category with categoryId: {}", categoryId);
              return new ResourceNotFoundException(
                  "Category not found with categoryId: " + categoryId);
            });
  }

  public MongoCategory createCategory(MongoCategory category) {
    log.debug("Attempting to create a new category in MongoDB with name: {}", category.getName());
    // Generate a unique business key.
    category.setCategoryId("CAT-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase());
    MongoCategory savedCategory = categoryRepository.save(category);
    log.info(
        "Successfully created new category with categoryId: {}", savedCategory.getCategoryId());
    return savedCategory;
  }

  public MongoCategory updateCategory(String categoryId, MongoCategory categoryDetails) {
    log.debug("Attempting to update category with categoryId: {}", categoryId);
    MongoCategory existingCategory = getCategoryByCategoryId(categoryId); // Find-or-throw

    // Update the mutable fields
    existingCategory.setName(categoryDetails.getName());
    existingCategory.setDescription(categoryDetails.getDescription());

    MongoCategory updatedCategory = categoryRepository.save(existingCategory);
    log.info("Successfully updated category with categoryId: {}", updatedCategory.getCategoryId());
    return updatedCategory;
  }

  public void deleteCategory(String categoryId) {
    log.debug("Attempting to delete category with categoryId: {}", categoryId);
    MongoCategory category = getCategoryByCategoryId(categoryId); // Find-or-throw
    categoryRepository.delete(category);
    log.info("Successfully deleted category with categoryId: {}", categoryId);
  }
}
