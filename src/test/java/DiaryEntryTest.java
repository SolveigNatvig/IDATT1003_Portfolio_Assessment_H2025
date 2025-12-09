import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.bidata.Author;
import edu.ntnu.idi.bidata.DiaryEntry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

/**
 * The test class for the Diary Entry class
 */
public class DiaryEntryTest {

  // POSITIVE TESTS
    
  @ Test
  public void constructorDiaryEntry_shouldInitiliseAllFields_whenGivenExpextedValues() {
        
    // Arrange
    String expectedTitle = "Title";
    Author expectedAuthor = new Author("Jane");
    String expectedContent = "This is the content of the entry";
    LocalDateTime timeBeforeCreation = LocalDateTime.now();
    

    // Act
    DiaryEntry entry = new DiaryEntry(expectedTitle, expectedAuthor, expectedContent);
    
    // Assert
    assertEquals(expectedTitle, entry.getTitle());
    assertEquals(expectedAuthor, entry.getAuthor());
    assertEquals(expectedContent, entry.getContent());
    assertNotNull(entry.getDate());
    assertTrue(entry.getDate().isAfter(timeBeforeCreation));
  }

  @ Test
    public void constructorDiaryEntry_shouldUseGivenDate_whenProvided() {

    // Arrange
    LocalDateTime expectedDate = LocalDateTime.now();

    // Act 
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content", expectedDate);

    // Assert
    assertEquals(expectedDate, entry.getDate());
  }

  @Test
  public void getTitle_shouldReturnTitle_setByConstructor() {

    // Arrange
    String expectedTitle = "My Diary Entry";

    // Act
    DiaryEntry entry = new DiaryEntry(expectedTitle, new Author("Jane"), "Content");

    // Assert
    assertEquals(expectedTitle, entry.getTitle());
  }

  @Test
  public void getAuthor_shouldReturnAuthor_setByConstructor() {

    // Arrange
    Author expectedAuthor = new Author("Jane");

    // Act
    DiaryEntry entry = new DiaryEntry("Title", expectedAuthor, "Content");

    // Assert
    assertEquals(expectedAuthor, entry.getAuthor());
  }

  @Test
  public void getContent_shouldReturnContent_setByConstructor() {

    // Arrange
    String expectedContent = "This is my diary content";

    // Act
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), expectedContent);

    // Assert
    assertEquals(expectedContent, entry.getContent());
  }

  @Test
  public void getDate_shouldReturnDate_setByConstructor() {

    // Arrange
    LocalDateTime expectedDate = LocalDateTime.of(2025, 6, 15, 14, 30);

    // Act
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content", expectedDate);

    // Assert
    assertEquals(expectedDate, entry.getDate());
  }


  @ Test
  public void setTitle_shouldUpdateTitle_whenGivenNewTitle() {

    // Arrange
    DiaryEntry entry = new DiaryEntry("Old Title", new Author("Jane"), "Content"); 
    String expectedTitle = "New Title";

    // Act
    entry.setTitle(expectedTitle);

    // Assert
    assertEquals(expectedTitle, entry.getTitle());
  }

  @ Test
  public void setContent_shouldUpdatesContent_whenGivenNewContent() {

    // Arrange
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Old Content");
    String expectedContent = "New Content";

    // Act
    entry.setContent(expectedContent);

    // Assert
    assertEquals(expectedContent, entry.getContent());
  }

  @ Test
  void toString_shouldContainTitleAuthorNameContentAndFormattedDate() {

    // Arrange
    LocalDateTime fixedDate = LocalDateTime.of(2025, 12, 1, 10, 30);
    String expectedDate = fixedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    String expectedTitle = "Title";
    String expectedAuthorName = "Jane";
    String expectedContent = "Content";

    Author author = new Author(expectedAuthorName);
    DiaryEntry entry = new DiaryEntry(expectedTitle, author, expectedContent, fixedDate);

    // Act
    String result = entry.toString();

    // Assert
    assertTrue(result.contains(expectedTitle));
    assertTrue(result.contains(expectedAuthorName));
    assertTrue(result.contains(expectedContent));
    assertTrue(result.contains(expectedDate));
  }


  // NEGATIVE TESTS

  @Test
  public void constructor_shouldAcceptNullTitle_whenGivenNullValue() {

    // Arrange
    String nullTitle = null;

    // Act
    DiaryEntry entry = new DiaryEntry(nullTitle, new Author("Jane"), "Content");

    // Assert
    assertNull(entry.getTitle());
  }

  @Test
  public void constructor_shouldAcceptNullContent_whenGivenNullValue() {

    // Arrange
    String nullContent = null;

    // Act
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), nullContent);

    // Assert
    assertNull(entry.getContent());
  }

  @Test
  public void constructor_shouldAcceptNullAuthor_whenGivenNullValue() {

    // Arrange
    Author nullAuthor = null;

    // Act
    DiaryEntry entry = new DiaryEntry("Title", nullAuthor, "Content");

    // Assert
    assertNull(entry.getAuthor());
  }

  @Test
  public void constructor_shouldAcceptEmptyStrings_whenGivenEmptyTitleAndContent() {

    // Arrange
    String emptyTitle = "";
    String emptyContent = "";

    // Act
    DiaryEntry entry = new DiaryEntry(emptyTitle, new Author("Jane"), emptyContent);

    // Assert
    assertEquals(emptyTitle, entry.getTitle());
    assertEquals(emptyContent, entry.getContent());
  }

  @Test
  public void constructorWithDate_shouldAcceptNullDate_whenGivenNullValue() {

    // Arrange
    LocalDateTime nullDate = null;

    // Act
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content", nullDate);

    // Assert
    assertNull(entry.getDate());
  }

  @Test
  public void setTitle_shouldAcceptNull_whenGivenNullValue() {

    // Arrange
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content");

    // Act
    entry.setTitle(null);

    // Assert
    assertNull(entry.getTitle());
  }

  @Test
  public void setTitle_shouldAcceptEmptyString_whenGivenEmptyValue() {

    // Arrange
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content");
    String emptyTitle = "";

    // Act
    entry.setTitle(emptyTitle);

    // Assert
    assertEquals(emptyTitle, entry.getTitle());
  }

  @Test
  public void setContent_shouldAcceptNull_whenGivenNullValue() {

    // Arrange
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content");

    // Act
    entry.setContent(null);

    // Assert
    assertNull(entry.getContent());
  }
  
  @Test
  public void setContent_shouldAcceptEmptyString_whenGivenEmptyValue() {

    // Arrange
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content");
    String emptyContent = "";

    // Act
    entry.setContent(emptyContent);

    // Assert
    assertEquals(emptyContent, entry.getContent());
  }
}
