package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.Set;

@RecordBuilder
public record CreateEntryRequest(
  String title,
  String author,
  String type,
  String body,
  Set<String> tags,

  String category,
  Boolean published
) implements CreateEntryRequestBuilder.With { }