package in.stonecolddev.maliketh.cms.api.page;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PageFieldRepository extends CrudRepository<PageField, Integer> {

  @Query("""
         select data -> 'site' as site
         from page_fields
         """)
  PageFieldData siteFields();

  @Query("""
         select data -> 'page' as page
         from page_fields
         where data -> 'page' ->> 'name' = :page;
         """)
  PageFieldData pageFields(@Param("page") String page);
}