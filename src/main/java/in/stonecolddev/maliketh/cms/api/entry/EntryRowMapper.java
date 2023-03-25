package in.stonecolddev.maliketh.cms.api.entry;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashSet;

public class EntryRowMapper implements RowMapper<Entry> {
  @Override
  public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
    return EntryMapper.fromResultSet(rs);
  }
}