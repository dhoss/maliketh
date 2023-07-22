package in.stonecolddev.maliketh.cms.cache;

import in.stonecolddev.maliketh.cms.api.entry.Entry;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Comparator;

@Component
public class EntryCache extends DefaultCache<OffsetDateTime, Entry> {
  public EntryCache() {
    super(Comparator.reverseOrder());
  }
}