package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;
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
  Integer authorId,
  Integer version,
  Type type,
  String body,
  Set<Tag> tags,
  Integer categoryId,
  Boolean published,
  OffsetDateTime created,
  OffsetDateTime updated
) implements EntryBuilder.With { }