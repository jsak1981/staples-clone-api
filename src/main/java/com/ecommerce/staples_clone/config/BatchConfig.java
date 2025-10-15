package com.ecommerce.staples_clone.config;

import com.ecommerce.staples_clone.batch.ProductItemProcessor;
import com.ecommerce.staples_clone.dto.ProductCsvDTO;
import com.ecommerce.staples_clone.model.Product;
import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Autowired private JobBuilderFactory jobBuilderFactory;
  @Autowired private StepBuilderFactory stepBuilderFactory;
  @Autowired private EntityManagerFactory entityManagerFactory;
  @Autowired private ProductItemProcessor productItemProcessor;

  @Bean
  public FlatFileItemReader<ProductCsvDTO> reader() {
    FlatFileItemReaderBuilder<ProductCsvDTO> ItemReader = new FlatFileItemReaderBuilder<>();

    ItemReader.name("productItemReader");
    ItemReader.resource(new ClassPathResource("data/products.csv"));
    ItemReader.linesToSkip(1);
    ItemReader.delimited()
        .names("name", "description", "sku", "price", "stockQuantity", "categoryId");
    ItemReader.fieldSetMapper(
        new BeanWrapperFieldSetMapper<>() {
          {
            setTargetType(ProductCsvDTO.class);
          }
        });
    return ItemReader.build();
  }

  @Bean
  public JpaItemWriter<Product> writer() {
    JpaItemWriterBuilder<Product> builder = new JpaItemWriterBuilder<>();

    builder.entityManagerFactory(entityManagerFactory);
    return builder.build();
  }

  @Bean
  public Step productImportStep(JpaItemWriter<Product> itemWriter) {
    SimpleStepBuilder<ProductCsvDTO, Product> stepBuilder =
        stepBuilderFactory
            .get("productImportStep")
            .<ProductCsvDTO, Product>chunk(100)
            .reader(reader())
            .processor(productItemProcessor)
            .writer(itemWriter);

    return stepBuilder.build();
  }

  @Bean(name = "importProductJob")
  public Job importProductJob(Step pImportStep) {
    FlowJobBuilder jobBuilder =
        jobBuilderFactory
            .get("importProductJob")
            .incrementer(new RunIdIncrementer())
            .flow(pImportStep)
            .end();

    return jobBuilder.build();
  }
}
