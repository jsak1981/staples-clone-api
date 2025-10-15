package com.ecommerce.staples_clone.controller;

import com.ecommerce.staples_clone.service.ExcelService;
import com.ecommerce.staples_clone.util.AppConstants;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

  private static final Logger log = LoggerFactory.getLogger(ExcelController.class);
  private final ExcelService excelService;

  public ExcelController(ExcelService excelService) {
    this.excelService = excelService;
  }

  @PostMapping("/products/import")
  public ResponseEntity<String> importProducts(@RequestParam("file") MultipartFile file) {
    log.info("Received request to import products from file: {}", file.getOriginalFilename());
    excelService.importProductsFromExcel(file);
    return ResponseEntity.ok("Successfully imported products from Excel file.");
  }

  @GetMapping("/products/export")
  public ResponseEntity<Resource> exportProducts() {
    log.info("Received request to export products to Excel.");
    String timestamp = new SimpleDateFormat(("yyyyMMdd_HHmmss")).format(new Date());
    String fileName = "products_" + timestamp + ".xls";

    ByteArrayInputStream in = excelService.exportProductsToExcel();
    InputStreamResource resource = new InputStreamResource(in);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
        .contentType(MediaType.parseMediaType(AppConstants.MimeTypes.OPENXML_SPREADSHEET))
        .body(resource);
  }
}
