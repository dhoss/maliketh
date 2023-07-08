package in.stonecolddev.maliketh.cms.api.page;


import in.stonecolddev.maliketh.cms.cache.PageFieldCache;
import org.springframework.stereotype.Component;

@Component
public class PageService {

  private final PageFieldCache pageFieldCache;

  private final PageFieldRepository pageFieldRepository;

  public PageService(
    PageFieldCache pageFieldCache,
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