package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("tags")
@RecordBuilder
public record Tag(
  @Id
  Integer id,
  Integer entryId,
  String name,
  OffsetDateTime created,
  OffsetDateTime updated
) implements TagBuilder.With {}