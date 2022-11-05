package in.stonecolddev.maliketh;


import io.micronaut.core.annotation.Introspected;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.time.OffsetDateTime;

@Introspected
@RecordBuilder
public record Author(Integer id, String name, OffsetDateTime created, OffsetDateTime updated) {}