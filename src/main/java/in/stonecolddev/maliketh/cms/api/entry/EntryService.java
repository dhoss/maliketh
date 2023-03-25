package in.stonecolddev.maliketh.cms.api.entry;

import com.github.slugify.Slugify;
import in.stonecolddev.maliketh.cms.api.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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

  // TODO: pagination
  public Set<Entry> all() {
    return entryRepository.all();
  }

  public Entry create(CreateEntryRequest createEntryRequest) {
    log.info("Creating new entry: {}", createEntryRequest);
    return entryRepository.save(
      EntryBuilder.builder()
        .title(createEntryRequest.title())
        .slug(slug.slugify(createEntryRequest.title()))
       // .authorId(
       //   userRepository.findByUserName(createEntryRequest.author())
       //                        .map(User::id)
       //                        .orElseThrow())
       // .typeId(
       //   entryTypeRepository.findByName(createEntryRequest.type())
       //     .map(Type::id)
       //     .orElseThrow())
        .body(createEntryRequest.body())
        .tags(createEntryRequest.tags())
       // .categoryId(
       //   categoryRepository.findByName(createEntryRequest.category())
       //     .map(Category::id)
       //     .orElseThrow())
        .published(createEntryRequest.published())
        .created(OffsetDateTime.now())
        .build());
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
}