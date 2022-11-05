package in.stonecolddev.maliketh;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;

@Serdeable
@MappedEntity
@Introspected
@RecordBuilder
public record Entry(
  @Id
  Integer id,
  String title,
  String body,
  Author author,
  OffsetDateTime created,
  OffsetDateTime updated,
  OffsetDateTime published) implements EntryBuilder.With {}