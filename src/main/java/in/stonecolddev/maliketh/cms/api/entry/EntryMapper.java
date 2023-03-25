package in.stonecolddev.maliketh.cms.api.entry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashSet;

public class EntryMapper {

  public static Entry fromResultSet(ResultSet rs) throws SQLException {
    var entry = EntryBuilder.builder();
    entry.id(rs.getInt("id"));
    entry.body(rs.getString("body"));
    entry.category(CategoryBuilder.builder().name(rs.getString("category_name")).build());
    Timestamp created = rs.getTimestamp("created");
    if (created != null) {
      entry.created(created.toInstant().atOffset(ZoneOffset.UTC));
    }
    Timestamp updated = rs.getTimestamp("updated");
    if (updated != null) {
      entry.updated(updated.toInstant().atOffset(ZoneOffset.UTC));
    }
    entry.type(TypeBuilder.builder().name(rs.getString("entry_type")).build());
    entry.published(rs.getBoolean("published"));
    entry.slug(rs.getString("slug"));
    entry.tags(new HashSet<String>(Arrays.asList((String[])rs.getArray("tags").getArray())));
    entry.title(rs.getString("title"));
    entry.author(UserBuilder.builder().userName(rs.getString("user_name")).build());
    entry.version(rs.getInt("version"));

    return entry.build();
  }
}
