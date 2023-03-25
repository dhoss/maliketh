package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.Set;

@Table("entries")
@RecordBuilder
public record Entry(
  @Id
  Integer id,
  String title,
  String slug,
  User author,
  Integer version,
  Type type,
  String body,
  Set<String> tags,
  Category category,
  Boolean published,
  OffsetDateTime created,
  OffsetDateTime updated
) implements EntryBuilder.With { }