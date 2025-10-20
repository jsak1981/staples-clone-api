package com.ecommerce.staples_clone.mongodb.config;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
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
            new OffsetDateTimeWriteConverter(),
            new OffsetDateTimeReadConverter(),
            new DateToOffsetDateTimeConverter()));
  }

  static class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, String> {
    @Override
    public String convert(OffsetDateTime source) {
      return source.format(DateTimeFormatter.ISO_DATE_TIME);
    }
    /*@Override
    public <U> Converter<OffsetDateTime, U> andThen(Converter<? super String, ? extends U> after) {
     return Converter.super.andThen(after);
    }*/
  }

  static class OffsetDateTimeReadConverter implements Converter<String, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(String source) {
      return OffsetDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
    }
  }

  static class DateToOffsetDateTimeConverter implements Converter<Date, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(Date source) {
      return source.toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
  }
}
