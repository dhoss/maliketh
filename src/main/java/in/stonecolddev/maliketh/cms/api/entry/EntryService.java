package in.stonecolddev.maliketh.cms.api.entry;

import com.github.slugify.Slugify;
import in.stonecolddev.maliketh.cms.api.user.CategoryRepository;
import in.stonecolddev.maliketh.cms.api.user.EntryRepository;
import in.stonecolddev.maliketh.cms.api.user.EntryTypeRepository;
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

  // TODO: pagination
  public Set<Entry> all() {
    return entryRepository.all();
  }

  public Entry create(CreateEntryRequest createEntryRequest) {

    log.debug("**** RETRIEVING AUTHOR ID");
    var authorId = userRepository.findByUserName(createEntryRequest.author())
                         .map(User::id)
                         .orElseThrow();
    log.debug("**** AUTHOR ID :{}", authorId);
    log.info("Creating new entry: {}", createEntryRequest);
    return entryRepository.save(
      EntryBuilder.builder()
        .title(createEntryRequest.title())
        .slug(slug.slugify(createEntryRequest.title()))
        .authorId(
          authorId
        )
        .typeId(
          entryTypeRepository.findByName(createEntryRequest.type())
            .map(Type::id)
            .orElseThrow())
        .body(createEntryRequest.body())
        .tags(createEntryRequest.tags())
        .categoryId(categoryRepository.findByName(createEntryRequest.category()).map(Category::id).orElseThrow())
        .build());
  }
}