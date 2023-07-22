package in.stonecolddev.maliketh.cms.cache;

import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class EntryCountCache extends DefaultCache<String, Integer> {
  public EntryCountCache() {
    super(Comparator.reverseOrder());
  }
}