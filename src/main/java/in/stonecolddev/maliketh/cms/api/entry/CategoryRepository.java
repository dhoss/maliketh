package in.stonecolddev.maliketh.cms.api.entry;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Entry,Integer> {

  @Query(value = """
                  select
                      c.id "category_id"
                    , c.name as "category_name"
                    , c.slug as "category_slug"
                    , c.created as "category_created"
                    , c.updated as "category_updated"
                    , e.id as "entry_id"
                    , e.body
                    , e.created as "entry_created"
                    , et.name as "entry_type"
                    , e.published
                    , e.slug as "entry_slug"
                    , e.tags
                    , e.title
                    , e.updated as "entry_updated"
                    , u.user_name
                    , e.version
                  from categories c
                  left join entries e on e.categories_id = c.id
                  left join entry_types et on e.entry_types_id = et.id
                  left join users u on e.users_id = u.id
                  where c.slug = :slug
                  group by c.id, e.id, et.id, u.id
                  limit 1
                  """,
          resultSetExtractorClass = EntryResultSet.class)
  Set<Entry> categoryEntries(@Param("slug") String slug);

  @Query("select id, slug, name from categories where slug=:slug limit 1")
  Optional<Category> findBySlug(@Param("slug") String slug);
}