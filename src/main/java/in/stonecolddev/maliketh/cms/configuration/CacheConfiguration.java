package in.stonecolddev.maliketh.cms.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
@Profile({"local", "local-docker", "unit-test", "it-test", "dev", "prod"})
@ConfigurationProperties(prefix = "cache")
public record CacheConfiguration(
  Integer entryCacheExpireAfterWrite,
  Integer entryCountCacheExpire,
  Integer cacheMaxSize) {}