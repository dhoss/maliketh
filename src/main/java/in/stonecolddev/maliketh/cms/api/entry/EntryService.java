package in.stonecolddev.maliketh.cms.api.entry;

import com.github.slugify.Slugify;
import in.stonecolddev.maliketh.cms.api.user.UserRepository;
import in.stonecolddev.maliketh.cms.cache.EntryCache;
import in.stonecolddev.maliketh.cms.cache.EntryCountCache;
import in.stonecolddev.maliketh.cms.configuration.CmsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class EntryService {
  private final Logger log = LoggerFactory.getLogger(EntryService.class);

  private final EntryRepository entryRepository;

  private final EntryTypeRepository entryTypeRepository;

  private final UserRepository userRepository;

  private final CategoryRepository categoryRepository;

  private final Slugify slug;

  private final CmsConfiguration cmsConfiguration;

  private final EntryCache entryCache;

  private final EntryCountCache entryCountCache;


  public EntryService(
    EntryRepository entryRepository,
    EntryTypeRepository entryTypeRepository,
    UserRepository userRepository,
    CategoryRepository categoryRepository,
    CmsConfiguration cmsConfiguration,
    EntryCache entryCache,
    EntryCountCache entryCountCache
  ) {
    this.entryRepository = entryRepository;
    this.entryTypeRepository = entryTypeRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
    this.cmsConfiguration = cmsConfiguration;
    this.entryCache = entryCache;
    this.entryCountCache = entryCountCache;

    this.slug = Slugify.builder().build();
  }

  public List<Entry> all(Integer lastSeen) {
    // TODO: get pagination with caching working
    return
      entryCache.getAll(
        () -> {
          var m = new HashMap<String, Entry>();
          for (var e : entryRepository.all(
            (lastSeen == 0) ? entryCountCache.get("entryCount", k -> entryRepository.count())
                            : lastSeen, cmsConfiguration.pageSize())) {
            m.put(e.slug(), e);
          }
          return m;
        }).values()
          .stream()
          .sorted(Comparator.comparing(Entry::published).reversed())
          .toList();
  }

  public Entry create(Entry entry) {
    log.info("Creating new entry: {}", entry);
    // TODO: implement cache write through


    var entryTitleSlug = slug.slugify(entry.title());
    entryRepository.insert(
      entry.title(),
      entry.body(),
      entryTitleSlug,
      userRepository.findByUserName(entry.author().userName())
        .map(User::id)
        .orElseThrow(() -> new RuntimeException("No such user %s".formatted(entry.author().userName()))),
      1, // TODO: implement versioning for entries
      entryTypeRepository.findByName(entry.type().name())
        .map(Type::id)
        .orElseThrow(() -> new RuntimeException("No entry type of %s found".formatted(entry.type().name()))),
      entry.tags().toArray(String[]::new),
      categoryRepository.findBySlug(entry.category().slug())
        .map(Category::id)
        .orElseThrow(() -> new RuntimeException("No category named %s found".formatted(entry.category().slug()))),
      entry.published());

    entryCache.put(entryTitleSlug, entry);

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
    return categoryRepository.categoryEntries(slug);
  }
}