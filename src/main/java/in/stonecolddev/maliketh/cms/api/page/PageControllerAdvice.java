package in.stonecolddev.maliketh.cms.api.page;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class PageControllerAdvice {

  private final PageService pageService;

  public PageControllerAdvice(PageService pageService) {
    this.pageService = pageService;
  }

  @ModelAttribute("siteFields")
  public PageFieldData populateSiteFields() {
    return pageService.siteFields();
  }
}