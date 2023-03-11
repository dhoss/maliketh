package in.stonecolddev.maliketh.cms.api.entry;

import in.stonecolddev.maliketh.cms.api.entry.Type;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntryTypeRepository extends CrudRepository<Type,Integer> {

  @Query(
    """
    select * from entry_types
    where name = :name
    limit 1
    """
  )
  Optional<Type> findByName(@Param("name") String name);

}