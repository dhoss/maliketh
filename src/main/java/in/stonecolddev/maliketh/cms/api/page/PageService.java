package in.stonecolddev.maliketh.cms.api.page;


import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class PageService {

  private final Cache<String, PageFieldData> pageFieldCache;

  private final PageFieldRepository pageFieldRepository;

  public PageService(
    Cache<String, PageFieldData> pageFieldCache,
    PageFieldRepository pageFieldRepository) {

    this.pageFieldCache = pageFieldCache;
    this.pageFieldRepository = pageFieldRepository;
  }

  public PageFieldData pageFields(String page) {
    return pageFieldCache.get("pageFields", k -> pageFieldRepository.pageFields(page));
  }

  public PageFieldData siteFields() {
    return pageFieldCache.get("siteFields", k -> pageFieldRepository.siteFields());
  }
}