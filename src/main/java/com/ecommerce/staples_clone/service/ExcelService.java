package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.exception.ResourceNotFoundException;
import com.ecommerce.staples_clone.model.Product;
import com.ecommerce.staples_clone.model.ProductCategory;
import com.ecommerce.staples_clone.repository.ProductCategoryRepository;
import com.ecommerce.staples_clone.repository.ProductRepository;
import com.ecommerce.staples_clone.util.AppConstants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelService {
  private static final Logger log = LoggerFactory.getLogger(ExcelService.class);
  private final ProductRepository productRepository;
  private final ProductCategoryRepository categoryRepository;

  public ExcelService(ProductRepository p, ProductCategoryRepository pc) {
    this.productRepository = p;
    this.categoryRepository = pc;
  }

  public void importProductsFromExcel(MultipartFile file) {
    log.info("Starting Excel import process for file: {}", file.getOriginalFilename());
    if (!isExcelFile(file)) {
      throw new IllegalArgumentException("Invalid file format. Please upload excel file");
    }

    try (InputStream is = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(is)) {
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> rows = sheet.iterator();

      List<Product> productToSave = new ArrayList<>();
      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        int assignRowNumber = rowNumber;
        Product product = new Product();
        product.setName(currentRow.getCell(0).getStringCellValue());
        product.setDescription(currentRow.getCell(1).getStringCellValue());
        product.setSku(currentRow.getCell(2).getStringCellValue());
        product.setPrice(new BigDecimal(currentRow.getCell(3).getNumericCellValue()));
        product.setStockQuantity((int) currentRow.getCell(4).getNumericCellValue());

        Integer categoryId = (int) currentRow.getCell(5).getNumericCellValue();
        ProductCategory category =
            categoryRepository
                .findById(categoryId)
                .orElseThrow(
                    () ->
                        new ResourceNotFoundException(
                            "Category not found with id: "
                                + categoryId
                                + " in row "
                                + (assignRowNumber + 1)));
        product.setCategory(category);

        productToSave.add(product);
        rowNumber++;
      }

      productRepository.saveAll(productToSave);
      log.info("Successfully imported {} products from Excel file.", productToSave.size());
    } catch (IOException ex) {
      log.error("Failed to parse Excel file", ex);
      throw new RuntimeException("Failed to parse Excel file: " + ex.getMessage());
    }
  }

  public ByteArrayInputStream exportProductsToExcel() {
    log.info("Starting Excel export process for products.");
    List<Product> products = productRepository.findAll();
    String[] headers = {
      "Name", "Description", "SKU", "Price", "Stock Quantity", "Category ID", "Category Name"
    };
    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Products");

      Row headerRow = sheet.createRow(0);
      for (int col = 0; col < headers.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(headers[col]);
      }

      int rowIdx = 1;
      for (Product product : products) {
        Row row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(product.getName());
        row.createCell(1).setCellValue(product.getDescription());
        row.createCell(2).setCellValue(product.getSku());
        row.createCell(3).setCellValue(product.getPrice().doubleValue());
        row.createCell(4).setCellValue(product.getStockQuantity());

        if (product.getCategory() != null) {
          row.createCell(5).setCellValue(product.getCategory().getCategoryId());
          row.createCell(6).setCellValue(product.getCategory().getCategoryName());
        }
      }
      workbook.write(out);
      log.info("Successfully created Excel file with {} products.", products.size());
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException ex) {
      log.error("Failed to export data to Excel file", ex);
      throw new RuntimeException("Failed to export data to Excel file: " + ex.getMessage());
    }
  }

  private boolean isExcelFile(MultipartFile file) {
    String contentType = file.getContentType();

    return contentType != null
        && (contentType.equals(AppConstants.MimeTypes.MS_EXCEL)
            || contentType.equals(AppConstants.MimeTypes.OPENXML_SPREADSHEET));
  }
}
