package com.ecommerce.staples_clone.mongodb.controller;

import com.ecommerce.staples_clone.mongodb.model.MongoCategory;
import com.ecommerce.staples_clone.mongodb.service.MongoCategoryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mongo/categories")
public class MongoCategoryController {

  private static final Logger log = LoggerFactory.getLogger(MongoCategoryController.class);
  private final MongoCategoryService categoryService;

  @Autowired
  public MongoCategoryController(MongoCategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<MongoCategory>> getAllCategories() {
    log.info("Received request to get all MongoDB categories.");
    return ResponseEntity.ok(categoryService.getAllCategories());
  }

  @GetMapping("/{categoryId}")
  public ResponseEntity<MongoCategory> getCategoryById(@PathVariable String categoryId) {
    log.info("Received request to get MongoDB category by categoryId: {}", categoryId);
    return ResponseEntity.ok(categoryService.getCategoryByCategoryId(categoryId));
  }

  @PostMapping
  public ResponseEntity<MongoCategory> createCategory(@RequestBody MongoCategory category) {
    log.info("Received request to create a new MongoDB category.");
    MongoCategory createdCategory = categoryService.createCategory(category);
    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

  @PutMapping("/{categoryId}")
  public ResponseEntity<MongoCategory> updateCategory(
      @PathVariable String categoryId, @RequestBody MongoCategory category) {
    log.info("Received request to update MongoDB category with categoryId: {}", categoryId);
    MongoCategory updatedCategory = categoryService.updateCategory(categoryId, category);
    return ResponseEntity.ok(updatedCategory);
  }

  @DeleteMapping("/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
    log.info("Received request to delete MongoDB category with categoryId: {}", categoryId);
    categoryService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }
}
