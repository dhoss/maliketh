package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.Set;

@Table("users")
@RecordBuilder
public record User(
  @Id
  Integer id,
  String name,
  String userName,
  String email,
  Set<Entry> entries,
  OffsetDateTime created,
  OffsetDateTime updated
) implements UserBuilder.With {

  public Set<Entry> addEntry(Entry entry) {
    this.entries.add(entry);

    return this.entries();
  }
}