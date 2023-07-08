package in.stonecolddev.maliketh.cms.api.user;

import in.stonecolddev.maliketh.cms.api.entry.Entry;
import in.stonecolddev.maliketh.cms.api.entry.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Integer> {

  @Query("select * from entries where author_id = :authorId")
  Set<Entry> entries(@Param("authorId") Integer authorId);

  @Query(
  """
  select
     u.id
   , u.name
   , u.user_name as "userName"
   , u.email
  -- , u.created
  -- , u.updated
   from users u
   where u.user_name = :userName
  """)
  Optional<User> findByUserName(@Param("userName") String userName);

}