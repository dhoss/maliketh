package in.stonecolddev.maliketh.cms.api.entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

  private final Logger log = LoggerFactory.getLogger(CategoryController.class);

  private final EntryService entryService;

  public CategoryController(EntryService entryService) {
    this.entryService = entryService;
  }


 // // TODO: pagination
 // @GetMapping("")
 // public HttpEntity<Set<Entry>> all() {
//    return ResponseEntity.ok(entryService.all());
//  }

 // @PostMapping("")
 // public HttpEntity<Category> create(@RequestBody Category category) {
 //   log.info("Creating category {}", category);
 //   return ResponseEntity.ok(entryService.createCategory(category));
 // }

  @GetMapping("/{slug}")
  public HttpEntity<Set<Entry>> find(@PathVariable String slug) {
    return ResponseEntity.ok(entryService.findCategory(slug));
  }
}