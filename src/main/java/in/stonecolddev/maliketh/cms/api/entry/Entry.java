package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Table("entries")
@RecordBuilder
public record Entry() implements EntryBuilder.With { }