package in.stonecolddev.maliketh.cms.cache;

import in.stonecolddev.maliketh.cms.api.entry.Entry;
import org.springframework.stereotype.Component;

@Component
public class EntryCache extends DefaultCache<String, Entry> {}