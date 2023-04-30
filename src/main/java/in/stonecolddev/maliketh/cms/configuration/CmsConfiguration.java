package in.stonecolddev.maliketh.cms.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties(prefix = "cms")
@ConstructorBinding
@Profile({"local", "unit-test", "it-test", "dev", "prod"})
public record CmsConfiguration(
  Integer pageSize
) {}