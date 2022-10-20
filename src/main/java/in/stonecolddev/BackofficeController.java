package in.stonecolddev;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/backoffice")
public class BackofficeController {

  @Get
  @Produces(MediaType.TEXT_PLAIN)
  public String index() {
    return "Hello.";
  }
}