package in.stonecolddev.maliketh.cms.api.entry;

import in.stonecolddev.maliketh.cms.api.user.EntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EntryService {
  private final Logger log = LoggerFactory.getLogger(EntryService.class);

  private final EntryRepository entryRepository;

  public EntryService(EntryRepository entryRepository) {
    this.entryRepository = entryRepository;
  }

  public Set<Entry> all() {
    return entryRepository.all();
  }
}