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
  @Column("users_id")
  Integer authorId,
  Integer version,
  @Column("entry_types_id")
  Type type,
  String body,
  Set<Tag> tags,

  @Column("categories_id")
  Integer categoryId,
  Boolean published,
  OffsetDateTime created,
  OffsetDateTime updated
) implements EntryBuilder.With { }