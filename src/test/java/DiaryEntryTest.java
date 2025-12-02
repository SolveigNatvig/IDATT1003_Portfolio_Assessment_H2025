import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DiaryEntryTest {
    
    @ Test
    public void testConstructorsInitilisesField() {
        Author author = new Author("Solveig");
        
        DiaryEntry entry = new DiaryEntry("Title", author, "This is the content of the entry");

        assertEquals("Title", entry.getTitle());
        assertEquals(author, entry.getAuthor());
        assertEquals("This is the content of the entry", entry.getContent());
        assertNotNull(entry.getDate());
    }

    @ Test
    public void testConstructorWithGivenDate() {
        Author author = new Author("Solveig");
        LocalDateTime givenDate = LocalDateTime.of(2025, 12, 1, 10, 21);
        
        DiaryEntry entry = new DiaryEntry("Title", author, "This is the content of the entry", givenDate);


        assertEquals("Title", entry.getTitle());
        assertEquals(author, entry.getAuthor());
        assertEquals("This is the content of the entry", entry.getContent());
        assertEquals(givenDate, entry.getDate());

    }

    @ Test
    public void testSetTitleUpdatesTitle() {
        DiaryEntry entry = new DiaryEntry("Old Title", new Author("TestAuthor"), "Content");
        entry.setTitle("New Title");

        assertEquals("New Title", entry.getTitle());
    }

    @ Test
    public void testSetContentUpdatesContent() {
        DiaryEntry entry = new DiaryEntry("Title", new Author("TestAuthor"), "Old Content");
        entry.setContent("New Content");

        assertEquals("New Content", entry.getContent());
    }


    @Test
    void testToStringContainsExpectedValues() {
        Author author = new Author("Solveig");
        LocalDateTime fixedDate = LocalDateTime.of(2025, 12, 1, 10, 30);
        DiaryEntry entry = new DiaryEntry("My day", author, "This is the content", fixedDate);

        String result = entry.toString();

        assertTrue(result.contains("My day"));
        assertTrue(result.contains("This is the content"));
        assertTrue(result.contains("Solveig"));

        String expectedDate = fixedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        assertTrue(result.contains(expectedDate), "The formate of the date should be dd.MM.yyyy HH:mm");
    }


}
