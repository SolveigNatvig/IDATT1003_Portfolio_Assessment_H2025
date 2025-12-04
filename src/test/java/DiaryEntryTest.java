import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Uses Arrange-Act-Assert
 */
public class DiaryEntryTest {

    // POSITIVE TESTS
    
    @ Test
    public void DiaryEntry_shouldInitiliseField_whenGivenExpextedValues() {
        
        // Arrange
        String expectedTitle = "Title";
        Author expectedAuthor = new Author("Jane");
        String expectedContent = "This is the content of the entry";

        // Act
        DiaryEntry entry = new DiaryEntry(expectedTitle, expectedAuthor, expectedContent);
        
        // Assert
        assertEquals(expectedTitle, entry.getTitle());
        assertEquals(expectedAuthor, entry.getAuthor());
        assertEquals(expectedContent, entry.getContent());
        assertNotNull(entry.getDate());
    }

    @ Test
    public void DiaryEntry_shouldUseGivenDate_whenProvided() {

        // Arrange
        String expectedTitle = "Title";
        Author expectedAuthor = new Author("Jane");
        String expectedContent = "This is the content of the entry";
        LocalDateTime expectedDate = LocalDateTime.of(2025, 12, 1, 10, 21);

        // Act 
        DiaryEntry entry = new DiaryEntry(expectedTitle, expectedAuthor, expectedContent, expectedDate);

        // Assert
        assertEquals(expectedTitle, entry.getTitle());
        assertEquals(expectedAuthor, entry.getAuthor());
        assertEquals(expectedContent, entry.getContent());
        assertEquals(expectedDate, entry.getDate());
    }

    @ Test
    public void setTitle_shouldUpdateTitle_whenNewTitleIsValid() {

        // Arrange
        DiaryEntry entry = new DiaryEntry("Old Title", new Author("Jane"), "Content"); 
        String expectedTitle = "New Title";

        // Act
        entry.setTitle(expectedTitle);

        // Assert
        assertEquals(expectedTitle, entry.getTitle());
    }

    @ Test
    public void setContent_shouldUpdatesContent_whenNewContentIsValid() {

        // Arrange
        DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Old Content");
        String expectedContent = "New Content";

        // Act
        entry.setContent(expectedContent);

        // Assert
        assertEquals(expectedContent, entry.getContent());
    }

    @Test
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

    

}
