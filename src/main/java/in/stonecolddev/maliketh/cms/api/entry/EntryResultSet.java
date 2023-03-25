package in.stonecolddev.maliketh.cms.api.entry;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntryResultSet implements ResultSetExtractor<List<Entry>> {
  @Override
  public List<Entry> extractData(ResultSet rs) throws SQLException, DataAccessException {
    var entries = new ArrayList<Entry>();

    while (rs.next()) {
      entries.add(EntryMapper.fromResultSet(rs));
    }

    return entries;
  }
}