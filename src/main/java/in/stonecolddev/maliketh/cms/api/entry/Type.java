package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("entry_types")
@RecordBuilder
public record Type(
  @Id
  Integer id,
  String name
) implements TypeBuilder.With {}