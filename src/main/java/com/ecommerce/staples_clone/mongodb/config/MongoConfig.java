package com.ecommerce.staples_clone.mongodb.config;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import org.bson.types.Decimal128;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConfig {

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    return new MongoCustomConversions(
        Arrays.asList(
            // --- Time Converters ---
            new OffsetDateTimeWriteConverter(),
            new OffsetDateTimeReadConverter(),
            new DateToOffsetDateTimeConverter(),
            // --- BigDecimal Converters (for Price) ---
            new BigDecimalWriteConverter(),
            new Decimal128ReadConverter()));
  }

  static class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, String> {
    @Override
    public String convert(OffsetDateTime source) {
      return source.format(DateTimeFormatter.ISO_DATE_TIME);
    }
  }

  static class OffsetDateTimeReadConverter implements Converter<String, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(String source) {
      // convert and return.
      return OffsetDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
    }
  }

  static class DateToOffsetDateTimeConverter implements Converter<Date, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(Date source) {
      // convert and return.
      return source.toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
  }

  static class BigDecimalWriteConverter implements Converter<BigDecimal, Decimal128> {
    @Override
    public Decimal128 convert(BigDecimal source) {
      return new Decimal128(source);
    }
  }

  static class Decimal128ReadConverter implements Converter<Decimal128, BigDecimal> {
    @Override
    public BigDecimal convert(Decimal128 source) {
      return source.bigDecimalValue();
    }
  }
}
