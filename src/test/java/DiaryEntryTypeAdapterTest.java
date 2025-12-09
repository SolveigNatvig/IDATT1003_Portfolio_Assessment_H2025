import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.ntnu.idi.bidata.Author;
import edu.ntnu.idi.bidata.DiaryEntry;
import edu.ntnu.idi.bidata.DiaryEntryTypeAdapter;

/** 
 * This is the test class for the DiaryEntryTypeAdapter
 */
public class DiaryEntryTypeAdapterTest {
    
  // POSITIVE TESTS
  @Test
  void writeAndRead_shouldPreserveFields() throws IOException {
    // Arrange
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"),"Content",LocalDateTime.of(2025, 12, 9, 12, 0));
    DiaryEntryTypeAdapter adapter = new DiaryEntryTypeAdapter();
    StringWriter writer = new StringWriter();

    // Act
    adapter.write(new JsonWriter(writer), entry);
    DiaryEntry result = adapter.read(new JsonReader(new StringReader(writer.toString())));

    // Assert
    assertEquals("Title", result.getTitle());
    assertEquals("Jane", result.getAuthor().getName());
    assertEquals("Content", result.getContent());
    assertEquals(entry.getDate(), result.getDate());
  }

  // NEGATIVE TESTS

  @Test
  void read_shouldThrowDateTimeParceExeption_whenInvalidDate() {
    // Arrange
    String json = "{\"title\":\"T\",\"author\":{\"name\":\"A\"},\"content\":\"C\",\"date\":\"invalid-date\"}";
    DiaryEntryTypeAdapter adapter = new DiaryEntryTypeAdapter();
    JsonReader reader = new JsonReader(new StringReader(json));

    // Act + Assert
    assertThrows(DateTimeParseException.class, () -> adapter.read(reader));
  }

}
