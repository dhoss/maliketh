package in.stonecolddev.maliketh.cms.cache;

import in.stonecolddev.maliketh.cms.api.page.PageFieldData;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class PageFieldCache extends DefaultCache<String, PageFieldData> {
  public PageFieldCache() {
    super(Comparator.reverseOrder());
  }
}