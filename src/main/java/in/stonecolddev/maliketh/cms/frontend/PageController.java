package in.stonecolddev.maliketh.cms.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class PageController {

  private final Logger log = LoggerFactory.getLogger(PageController.class);

  @GetMapping("/")
  public String home(Model model) {
    model.addAllAttributes(
      Map.of(
        "site",
        Map.of(
        "pageTitle", "maliketh cms",
        "description", "maliketh cms description",
          "header", "maliketh header"),
        "entries",
        List.of(
          Map.of(
            "title", "test title",
            "body", "test body"
          )
        )
        ));

    return "home";
  }
}