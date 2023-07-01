package in.stonecolddev.maliketh.cms.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import in.stonecolddev.maliketh.cms.api.entry.Entry;
import in.stonecolddev.maliketh.cms.api.page.PageFieldData;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;
@Profile({"local", "local-docker", "unit-test", "it-test", "dev", "prod"})
@ConfigurationProperties(prefix = "cache")
public record CacheConfiguration(
  Integer entryCacheExpireAfterWrite,
  Integer entryCountCacheExpire,
  Integer cacheMaxSize) {

  @Bean
  public Cache<String, Integer> entryCountCache() {
    return Caffeine.newBuilder()
             .expireAfterWrite(entryCacheExpireAfterWrite(), TimeUnit.MINUTES)
             .maximumSize(cacheMaxSize())
             .build();
  }

  @Bean
  public Cache<String, Entry> entryCache() {
    return Caffeine.newBuilder()
             .expireAfterWrite(entryCacheExpireAfterWrite(), TimeUnit.MINUTES)
             .maximumSize(cacheMaxSize())
             .build();
  }

  @Bean
  public Cache<String, PageFieldData> pageFieldCache() {
    return Caffeine.newBuilder()
             .expireAfterWrite(entryCacheExpireAfterWrite(), TimeUnit.MINUTES)
             .maximumSize(cacheMaxSize())
             .build();
  }
}
