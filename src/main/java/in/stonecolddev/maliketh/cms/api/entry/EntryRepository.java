package in.stonecolddev.maliketh.cms.api.entry;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface EntryRepository extends Repository<Entry, Integer> {


  @Modifying
  @Query(
    """
    insert into entries(
      title,
      body,
      slug,
      users_id,
      version,
      entry_types_id,
      tags,
      categories_id,
      published,
      created)
    values(
      :title,
      :body,
      :slug,
      :users_id,
      :version,
      :entry_types_id,
      :tags,
      :categories_id,
      :published,
      now());
    """
  )
  void insert(
    @Param("title") String title,
    @Param("body") String body,
    @Param("slug") String slug,
    @Param("users_id") Integer authorId,
    @Param("version") Integer version,
    @Param("entry_types_id") Integer typeId,
    @Param("tags") String[] tags,
    @Param("categories_id") Integer categoryId,
    @Param("published") Boolean published
  );

  // TODO: paginate
  @Query(value = """
                  select
                   e.id as "entry_id",
                   e.body,
                   c.name as "category_name",
                   c.slug as "category_slug",
                   e.created as "entry_created",
                   e.updated as "entry_updated",
                   et.name as "entry_type",
                   e.published,
                   e.slug as "entry_slug",
                   e.tags,
                   e.title,
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
                   e.id as "entry_id",
                   e.body,
                   c.name as "category_name",
                   c.slug as "category_slug",
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