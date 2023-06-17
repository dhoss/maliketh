package in.stonecolddev.maliketh.cms.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import in.stonecolddev.maliketh.cms.api.page.PageFieldData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;
@Profile({"local", "local-docker", "unit-test", "it-test", "dev", "prod"})
@ConfigurationProperties(prefix = "cache")
public record CacheConfiguration(
  Integer cacheExpireAfterWrite,
  Integer cacheMaxSize) {
  @Bean
  public Cache<String, PageFieldData> pageFieldCache() {
    return Caffeine.newBuilder()
             .expireAfterWrite(cacheExpireAfterWrite(), TimeUnit.MINUTES)
             .maximumSize(cacheMaxSize())
             .build();
  }
}
