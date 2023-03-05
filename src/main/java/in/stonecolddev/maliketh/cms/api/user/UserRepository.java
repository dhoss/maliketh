package in.stonecolddev.maliketh.cms.api.user;

import in.stonecolddev.maliketh.cms.api.entry.Entry;
import in.stonecolddev.maliketh.cms.api.entry.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserRepository extends CrudRepository<User, Integer> {

  @Query("select * from entries where authorId = :authorId")
  Set<Entry> entries(@Param("authorId") Integer authorId);

}