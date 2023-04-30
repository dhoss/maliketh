package in.stonecolddev.maliketh.cms.api.entry;

import com.github.slugify.Slugify;
import in.stonecolddev.maliketh.cms.api.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EntryService {
  private final Logger log = LoggerFactory.getLogger(EntryService.class);

  private final EntryRepository entryRepository;

  private final EntryTypeRepository entryTypeRepository;

  private final UserRepository userRepository;

  private final CategoryRepository categoryRepository;

  private final Slugify slug;

  public EntryService(
    EntryRepository entryRepository,
    EntryTypeRepository entryTypeRepository,
    UserRepository userRepository,
    CategoryRepository categoryRepository
  ) {
    this.entryRepository = entryRepository;
    this.entryTypeRepository = entryTypeRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;

    this.slug = Slugify.builder().build();
  }

  public Set<Entry> all(Integer page) {
    // TODO: move page size to config
    var pageSize = 50;
    // TODO: cache this
    var totalRecords = entryRepository.count();

    var offset = (page - 1) * pageSize;
    if (offset >= totalRecords) {
      offset = totalRecords - pageSize;
    }

    return entryRepository.all(offset, pageSize);
  }

  public Entry create(Entry entry) {
    log.info("Creating new entry: {}", entry);
    entryRepository.insert(
      entry.title(),
      entry.body(),
      slug.slugify(entry.title()),
      userRepository.findByUserName(entry.author().userName())
        .map(User::id)
        .orElseThrow(),
      1,
      entryTypeRepository.findByName(entry.type().name())
        .map(Type::id)
        .orElseThrow(),
      entry.tags().toArray(String[]::new),
      categoryRepository.findBySlug(entry.category().name())
        .map(Category::id)
        .orElseThrow(),
      entry.published());
    return entry;
  }

  public Entry findOne(Integer year, Integer month, Integer day, String slug) {
    return entryRepository.findOne(year, month, day, slug).orElseThrow();
  }

  public Set<Entry> findForYear(Integer year) {
    return entryRepository.findForYear(year);
  }

  public Set<Entry> findForMonth(Integer year, Integer month) {
    return entryRepository.findForMonth(year, month);
  }

  public Set<Entry> findForDay(Integer year, Integer month, Integer day) {
    return entryRepository.findForDay(year, month, day);
  }

  // TODO: move this to category service
  public Set<Entry> findCategory(String slug) {
    return categoryRepository.categoryEntries(slug);//.orElseThrow();
  }
}