package in.stonecolddev.maliketh.cms.api.entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/entries", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntryController {

  private final Logger log = LoggerFactory.getLogger(EntryController.class);

  private final EntryService entryService;

  public EntryController(EntryService entryService) {
    this.entryService = entryService;
  }

  @GetMapping("")
  public HttpEntity<EntryContainer> all(@RequestParam Optional<Integer> page) {
    return ResponseEntity.ok(
      EntryContainerBuilder.builder()
        .entries(entryService.all(page.orElseGet(() -> 0)))
        .build());
  }

  @PostMapping("")
  public HttpEntity<Entry> create(@RequestBody Entry entry) {
    log.info("Creating entry {}", entry);
    return ResponseEntity.ok(entryService.create(entry));
  }

  @GetMapping("/{year}/{month}/{day}/{slug}")
  public HttpEntity<Entry> find(
    @PathVariable Integer year,
    @PathVariable Integer month,
    @PathVariable Integer day,
    @PathVariable String slug
  ) {
    return ResponseEntity.ok(entryService.findOne(year, month, day, slug));
  }

  @GetMapping("/{year}")
  public HttpEntity<Set<Entry>> findForYear(@PathVariable Integer year) {
    return ResponseEntity.ok(entryService.findForYear(year));
  }
  @GetMapping("/{year}/{month}")
  public HttpEntity<Set<Entry>> findForMonth(
    @PathVariable Integer year, @PathVariable Integer month
  ) {
    return ResponseEntity.ok(entryService.findForMonth(year, month));
  }

  @GetMapping("/{year}/{month}/{day}")
  public HttpEntity<Set<Entry>> findForDay(
    @PathVariable Integer year,
    @PathVariable Integer month,
    @PathVariable Integer day
  ) {
    return ResponseEntity.ok(entryService.findForDay(year, month, day));
  }

}