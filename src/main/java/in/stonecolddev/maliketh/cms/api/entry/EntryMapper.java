package in.stonecolddev.maliketh.cms.api.entry;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class EntryMapper {

  public static Entry fromResultSet(ResultSet rs) throws SQLException {
    var entry = EntryBuilder.builder();

    entry.id(rs.getInt("entry_id"));
    entry.body(rs.getString("body"));
    entry.category(
      CategoryBuilder.builder()
        .name(rs.getString("category_name"))
        .slug(rs.getString("category_slug"))
        .build());

    var created = rs.getTimestamp("entry_created");
    var updated = rs.getTimestamp("entry_updated");

    if (created != null) {
      entry.created(created.toInstant().atOffset(ZoneOffset.UTC));
    }
    if (updated != null) {
      entry.updated(updated.toInstant().atOffset(ZoneOffset.UTC));
    }

    entry.type(
      TypeBuilder.builder()
        .name(rs.getString("entry_type"))
        .build());
    entry.published(rs.getBoolean("published"));
    entry.slug(rs.getString("entry_slug"));
    //(String[])rs.getArray("tags").getArray();
    var tags = new HashSet<String>();
    var tagsColumn = rs.getArray("tags");
    if (tagsColumn != null) {
      tags.addAll(Arrays.asList((String[]) tagsColumn.getArray()));
    }
    entry.tags(tags);
    //  new HashSet<>(
    //    Arrays.asList(
    //      //(String[]) tagsArray.getArray())));
    entry.title(rs.getString("title"));
    entry.author(
      UserBuilder.builder()
        .userName(rs.getString("user_name"))
        .build());
    entry.version(rs.getInt("version"));

    return entry.build();
  }
}
