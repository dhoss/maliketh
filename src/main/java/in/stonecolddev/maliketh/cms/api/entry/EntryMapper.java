package in.stonecolddev.maliketh.cms.api.entry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

import static in.stonecolddev.maliketh.cms.api.util.ResultSetUtils.timeStampToDateTime;

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
    var published = rs.getTimestamp("published");

    if (created != null) {
      entry.created(timeStampToDateTime(created));
    }

    if (updated != null) {
      entry.updated(timeStampToDateTime(updated));
    }

    if (published != null) {
      entry.published(timeStampToDateTime(published));
    }

    entry.type(
      TypeBuilder.builder()
        .name(rs.getString("entry_type"))
        .build());

    entry.slug(rs.getString("entry_slug"));

    var tags = new HashSet<String>();
    var tagsColumn = rs.getArray("tags");
    if (tagsColumn != null) {
      tags.addAll(Arrays.asList((String[]) tagsColumn.getArray()));
    }

    entry.tags(tags);
    entry.title(rs.getString("title"));
    entry.author(
      UserBuilder.builder()
        .userName(rs.getString("user_name"))
        .build());
    entry.version(rs.getInt("version"));

    return entry.build();
  }
}
