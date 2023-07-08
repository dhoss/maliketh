package in.stonecolddev.maliketh.cms.api.entry;

import io.soabase.recordbuilder.core.RecordBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@RecordBuilder
public record EntryContainer(List<Entry> entries) implements EntryContainerBuilder.With { }