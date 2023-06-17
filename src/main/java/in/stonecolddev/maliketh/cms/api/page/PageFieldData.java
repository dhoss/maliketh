package in.stonecolddev.maliketh.cms.api.page;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record PageFieldData(
  String name,
  String header,
  String title,
  String description
) implements PageFieldDataBuilder.With { }