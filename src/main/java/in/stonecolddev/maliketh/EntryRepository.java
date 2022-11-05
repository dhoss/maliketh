package in.stonecolddev.maliketh;

import io.micronaut.context.annotation.Executable;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface EntryRepository extends CrudRepository<Entry, Integer> {

  @Executable
  Entry find(Integer id);

}