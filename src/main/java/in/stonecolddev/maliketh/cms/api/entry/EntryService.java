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

  // TODO: switch from page to last seen
  public List<Entry> all(Integer lastSeen) {
    var pageSize = cmsConfiguration.pageSize();
    var totalRecords = entryCountCache.get("entryCount", k -> entryRepository.count());
    var pointer = (lastSeen == 0) ? totalRecords : lastSeen; //calculateOffset(pageSize, totalRecords).apply(lastSeen);

    // page 1  -> totalRecords - pageSize
    // page 2 -> lastSeen - pageSize

    log.debug("**** LAST SEEN {}", lastSeen);
    log.debug("**** OFFSET {}", pointer);
    log.debug("**** TOTAL RECORDS {}", totalRecords);
    log.debug("**** PAGE SIZE {}", pageSize);

    log.debug("**** ENTRIES FROM CACHE:");

    List<Entry> entries = entryCache.getAll(
      () -> {
        var m = new HashMap<String, Entry>();
        List<Entry> all = entryRepository.all(pointer, pageSize);
        log.debug("**** ALL {}", all);
        for (var e : all) {
          log.debug("**** ENTRY {}", e);
          m.put(e.slug(), e);
        }
        return m;
      }).values().stream().sorted(Comparator.comparing(Entry::published).reversed()).toList();


    log.debug("**** ENTRIES FROM DB:");
    log.debug("{}", entries);

    return
      entries;
  }

 // private List<String> entrySlugs() {
 //   var e = new ArrayList<>(entryCache.asMap().keySet());

 //   if (e.isEmpty()) {
 //     e.addAll(entryRepository.entrySlugs());
 //   }

 //   return e;
 // }

 // private static Function<Integer, Integer> calculateOffset(
 //   Integer pageSize, Integer totalRecords) {

 //   return (Integer p) -> {
 //     if (p == 1) {
 //       return totalRecords;
 //     }

 //     var o = //(p - 1) * pageSize;
 //     if (o >= totalRecords) {
 //       o = totalRecords - pageSize;
 //     }
 //     return o;
 //   };

 // }

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