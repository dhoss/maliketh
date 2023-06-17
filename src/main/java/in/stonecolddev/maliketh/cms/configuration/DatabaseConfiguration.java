package in.stonecolddev.maliketh.cms.configuration;

import com.zaxxer.hikari.HikariDataSource;
import in.stonecolddev.maliketh.cms.api.page.PageField;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile({"local", "local-docker", "unit-test", "it-test", "dev", "prod"})
public class DatabaseConfiguration {

  @Bean
  public NamedParameterJdbcOperations operations() {
    return new NamedParameterJdbcTemplate(dataSource());
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport() {
    NamedParameterJdbcDaoSupport support = new NamedParameterJdbcDaoSupport();
    support.setDataSource(dataSource());

    return support;
  }

  @Bean
  @ConfigurationProperties("spring.datasource.hikari")
  public HikariDataSource dataSource() {
    return DataSourceBuilder.create().type(HikariDataSource.class).build();
  }

  @Bean
  public JdbcCustomConversions jdbcCustomConversions() {
    final List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(PageField.EntityWritingConverter.INSTANCE);
    converters.add(PageField.EntityReadingConverter.INSTANCE);
    return new JdbcCustomConversions(converters);
  }
}