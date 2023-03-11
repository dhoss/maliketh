package in.stonecolddev.maliketh.cms.api.entry;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface EntryRepository extends CrudRepository<Entry,Integer> {

  // TODO: paginate
  @Query("""
         select * from entries
         """)
  Set<Entry> all();

  @Query("""
         select *
         from entries e
         where date_part('year', e.created) = :year
         and date_part('month', e.created) = :month
         and date_part('day', e.created) = :day
         and e.slug = :slug
         limit 1
         """)
  Optional<Entry> findOne(
    @Param("year") Integer year,
    @Param("month") Integer month,
    @Param("day") Integer day,
    @Param("slug") String slug);


  // TODO: paginate
  @Query("""
         select *
         from entries e
         where date_part('year', e.created) = :year
         """)
  Set<Entry> findForYear(@Param("year") Integer year);

  // TODO: paginate
  @Query("""
         select *
         from entries e
         where date_part('year', e.created) = :year
         and date_part('month', e.created) = :month
         """)
  Set<Entry> findForMonth(
    @Param("year") Integer year, @Param("month") Integer month);

  // TODO: paginate
  @Query("""
         select *
         from entries e
         where date_part('year', e.created) = :year
         and date_part('month', e.created) = :month
         and date_part('day', e.created) = :day
         """)
  Set<Entry> findForDay(
    @Param("year") Integer year,
    @Param("month") Integer month,
    @Param("day") Integer day);
}