package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.Set;

@Table("categories")
@RecordBuilder
public record Category(
  @Id
  Integer id,
  String name,
  Set<Entry> entries,
  OffsetDateTime created,
  OffsetDateTime updated
) {}