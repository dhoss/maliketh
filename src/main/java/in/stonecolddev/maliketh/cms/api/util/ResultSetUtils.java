package in.stonecolddev.maliketh.cms.api.util;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class ResultSetUtils {

  public static OffsetDateTime timeStampToDateTime(Timestamp ts) {
    // TODO: make timezone configurable
    return ts.toInstant().atOffset(ZoneOffset.UTC);
  }
}