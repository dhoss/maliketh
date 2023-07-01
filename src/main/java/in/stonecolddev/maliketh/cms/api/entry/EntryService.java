package in.stonecolddev.maliketh.cms.api.entry;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.slugify.Slugify;
import in.stonecolddev.maliketh.cms.api.user.UserRepository;
import in.stonecolddev.maliketh.cms.configuration.CmsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EntryService {
  private final Logger log = LoggerFactory.getLogger(EntryService.class);

  private final EntryRepository entryRepository;

  private final EntryTypeRepository entryTypeRepository;

  private final UserRepository userRepository;

  private final CategoryRepository categoryRepository;

  private final Slugify slug;

  private final CmsConfiguration cmsConfiguration;

  private final Cache<String, Entry> entryCache;

  private final Cache<String, Integer> entryCountCache;


  public EntryService(
    EntryRepository entryRepository,
    EntryTypeRepository entryTypeRepository,
    UserRepository userRepository,
    CategoryRepository categoryRepository,
    CmsConfiguration cmsConfiguration,
    Cache<String, Entry> entryCache,
    Cache<String, Integer> entryCountCache
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

  public Set<Entry> all(Integer page) {
    var pageSize = cmsConfiguration.pageSize();
    var totalRecords = entryCountCache.get("entryCount", k -> entryRepository.count());
    var offset = calculateOffset(pageSize, totalRecords).apply(page);

    return new HashSet<>(
      entryCache.getAll(
          entrySlugs(offset, pageSize),
          // TODO: entryCache.getAll is effectively throwing away first argument
          //   consider adjusting `entryRepository.all(...)` to take a list of slugs to retrieve
        (s) -> entryRepository.all(offset, pageSize)
                 .stream()
                 .collect(Collectors.toMap(Entry::slug, Function.identity())))
        .values());
  }

  private Set<String> entrySlugs(Integer offset, Integer pageSize) {
    var e = entryCache.asMap()
        .keySet()
        .stream()
        .skip(offset)
        .limit(pageSize)
        .collect(Collectors.toSet());

    if (e.isEmpty()) {
      e = entryRepository.entrySlugs(offset, pageSize);
    }

    return e;
  }

  private static Function<Integer, Integer> calculateOffset(
    Integer pageSize, Integer totalRecords) {

    return (Integer p) -> {
      var o = (p - 1) * pageSize;
      if (o >= totalRecords) {
        o = totalRecords - pageSize;
      }
      return o;
    };

  }

  public Entry create(Entry entry) {
    log.info("Creating new entry: {}", entry);
    // TODO: implement cache write through
    //   https://github.com/ben-manes/caffeine/wiki/Writer
    //   LoadingCache<Key, Graph> graphs = Caffeine.newBuilder()
    //  .writer(new CacheWriter<Key, Graph>() {
    //    @Override public void write(Key key, Graph graph) {
    //      // write to storage or secondary cache
    //    }
    //    @Override public void delete(Key key, Graph graph, RemovalCause cause) {
    //      // delete from storage or secondary cache
    //    }
    //  })
    //  .build(key -> createExpensiveGraph(key));

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

    entryCache.invalidateAll();

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