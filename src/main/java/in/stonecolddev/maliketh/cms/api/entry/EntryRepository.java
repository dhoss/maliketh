package in.stonecolddev.maliketh.cms.api.entry;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EntryRepository extends CrudRepository<Entry,Integer> {

  // TODO: paginate
  @Query(value = """
         select
          e.id,
          e.body,
          c.name as "category_name",
          e.created,
          et.name as "entry_type",
          e.published,
          e.slug,
          e.tags,
          e.title,
          e.updated,
          u.user_name,
          e.version
        from entries e
        left join categories c on e.categories_id = c.id
        left join entry_types et on e.entry_types_id = et.id
        left join users u on e.users_id = u.id
        """,
    resultSetExtractorClass = EntryResultSet.class)
  Set<Entry> all();

  @Query(value = """
         select
          e.id,
          e.body,
          c.name as "category_name",
          e.created,
          et.name as "entry_type",
          e.published,
          e.slug,
          e.tags,
          e.title,
          e.updated,
          u.user_name,
          e.version
        from entries e
        left join categories c on e.categories_id = c.id
        left join entry_types et on e.entry_types_id = et.id
        left join users u on e.users_id = u.id
        where date_part('year', e.created) = :year
        and date_part('month', e.created) = :month
        and date_part('day', e.created) = :day
        and e.slug = :slug
        limit 1
         """,
    rowMapperClass = EntryRowMapper.class)
  Optional<Entry> findOne(
    @Param("year") Integer year,
    @Param("month") Integer month,
    @Param("day") Integer day,
    @Param("slug") String slug);


  // TODO: paginate
  @Query("""
         select
          e.id,
          e.body,
          c.name as "category_name",
          e.created,
          et.name as "entry_type",
          e.published,
          e.slug,
          e.tags,
          e.title,
          e.updated,
          u.user_name,
          e.version
        from entries e
        left join categories c on e.categories_id = c.id
        left join entry_types et on e.entry_types_id = et.id
        left join users u on e.users_id = u.id
        where date_part('year', e.created) = :year
        """)
  Set<Entry> findForYear(@Param("year") Integer year);

  // TODO: paginate
  @Query(value = """
         select
          e.id,
          e.body,
          c.name as "category_name",
          e.created,
          et.name as "entry_type",
          e.published,
          e.slug,
          e.tags,
          e.title,
          e.updated,
          u.user_name,
          e.version
        from entries e
        left join categories c on e.categories_id = c.id
        left join entry_types et on e.entry_types_id = et.id
        left join users u on e.users_id = u.id
        where date_part('year', e.created) = :year
        and date_part('month', e.created) = :month
        """,
    resultSetExtractorClass = EntryResultSet.class)
  Set<Entry> findForMonth(
    @Param("year") Integer year, @Param("month") Integer month);

  // TODO: paginate
  @Query(value = """
         select
          e.id,
          e.body,
          c.name as "category_name",
          e.created,
          et.name as "entry_type",
          e.published,
          e.slug,
          e.tags,
          e.title,
          e.updated,
          u.user_name,
          e.version
        from entries e
        left join categories c on e.categories_id = c.id
        left join entry_types et on e.entry_types_id = et.id
        left join users u on e.users_id = u.id
        where date_part('year', e.created) = :year
        and date_part('month', e.created) = :month
        and date_part('day', e.created) = :day
        """,
    resultSetExtractorClass = EntryResultSet.class)
  Set<Entry> findForDay(
    @Param("year") Integer year,
    @Param("month") Integer month,
    @Param("day") Integer day);
}