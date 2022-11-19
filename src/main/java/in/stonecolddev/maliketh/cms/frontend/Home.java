package in.stonecolddev.maliketh.cms.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class Home {

  @GetMapping("/")
  public ModelAndView homePage(Map<String, Object> model) {
    return new ModelAndView("index", model);
  }
}