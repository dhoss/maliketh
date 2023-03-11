package in.stonecolddev.maliketh.cms.api.entry;

import in.stonecolddev.maliketh.cms.api.entry.Category;
import in.stonecolddev.maliketh.cms.api.entry.Type;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Integer> {

  @Query(
    """
    select * from categories
    where name = :name
    limit 1
    """
  )
  Optional<Category> findByName(@Param("name") String name);

}