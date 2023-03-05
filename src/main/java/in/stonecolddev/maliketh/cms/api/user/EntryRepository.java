package in.stonecolddev.maliketh.cms.api.user;

import in.stonecolddev.maliketh.cms.api.entry.Entry;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface EntryRepository extends CrudRepository<Entry,Integer> {

  // TODO: paginate
  @Query("""
         select * from entries
         """)
  Set<Entry> all();

}