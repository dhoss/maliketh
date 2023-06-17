package in.stonecolddev.maliketh.cms.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import java.time.ZoneOffset;

@ConfigurationProperties(prefix = "cms")
@Profile({"local", "unit-test", "it-test", "dev", "prod"})
public record CmsConfiguration(
  Integer pageSize,
  ZoneOffset timeZone
) {}