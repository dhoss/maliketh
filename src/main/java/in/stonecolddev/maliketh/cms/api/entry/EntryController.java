package in.stonecolddev.maliketh.cms.api.entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/entries", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntryController {

  private final Logger log = LoggerFactory.getLogger(EntryController.class);

  private final EntryService entryService;

  public EntryController(EntryService entryService) {
    this.entryService = entryService;
  }


  // TODO: pagination
  @GetMapping("")
  public HttpEntity<Set<Entry>> all() {
    return ResponseEntity.ok(entryService.all());
  }

  @PostMapping("")
  public HttpEntity<Entry> create(@RequestBody CreateEntryRequest entry) {
    log.debug("**** REQAUEST {}", entry);
    return ResponseEntity.ok(entryService.create(entry));
  }


}