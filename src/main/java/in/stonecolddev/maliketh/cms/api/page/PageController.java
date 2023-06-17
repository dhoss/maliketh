package in.stonecolddev.maliketh.cms.api.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class PageController {

  private final Logger log = LoggerFactory.getLogger(PageController.class);

  private final PageService pageService;

  public PageController(PageService pageService) {
    this.pageService = pageService;
  }

  @GetMapping("/")
  public String home(Model model) {
    // TODO: pull in and cache entries
    model.addAllAttributes(
      Map.of(
        // TODO: cache pageFields
        "pageFields", pageService.pageFields("home")));

    return "home";
  }
}