package in.stonecolddev.maliketh.cms.api.page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.soabase.recordbuilder.core.RecordBuilder;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.annotation.Id;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.SQLException;
import java.time.OffsetDateTime;

@Table("page_fields")
@RecordBuilder
public record PageField(
  @Id
  Integer id,
  PageFieldData data,
  OffsetDateTime created,
  OffsetDateTime updated
) implements PageFieldBuilder.With {

  // TODO: Registering converter from class in.stonecolddev.maliketh.cms.api.page.PageFieldData to class org.postgresql.util.PGobject as writing converter although it doesn't convert to a store-supported type; You might want to check your annotation setup at the converter implementation

  // TODO: clean up converter code
  @WritingConverter
  public enum EntityWritingConverter implements Converter<PageFieldData, PGobject> {
    INSTANCE;
    @Override
    public PGobject convert(PageFieldData source) {
      ObjectMapper objectMapper = new ObjectMapper();

      PGobject jsonObject = new PGobject();
      jsonObject.setType("json");
      try {
        jsonObject.setValue(objectMapper.writeValueAsString(source));
      } catch (SQLException | JsonProcessingException throwables) {
        throwables.printStackTrace();
      }
      return jsonObject;
    }
  }

  @ReadingConverter
  public enum EntityReadingConverter implements  Converter<PGobject, PageFieldData> {
    INSTANCE;
    @Override
    public PageFieldData convert(PGobject pgObject) {
      ObjectMapper objectMapper = new ObjectMapper();
      String source = pgObject.getValue();
      try {
        return objectMapper.readValue(source, PageFieldData.class);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return null;
    }
  }

}