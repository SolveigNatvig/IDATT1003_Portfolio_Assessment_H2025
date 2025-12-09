import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.bidata.Author;
import edu.ntnu.idi.bidata.DiaryEntry;
import edu.ntnu.idi.bidata.DiaryEntryRegister;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;


/** 
 * The test class for the DiaryEntryRegister class
 */
public class DiaryEntryRegisterTest {
  
  // POSITIVE TESTS

  @ Test
  public void cunstuctorDiaryEntryRegister_shouldInitialiseEmptyList_whenCreated() {
        
    // Arrange
    DiaryEntryRegister newRegister = new DiaryEntryRegister();

    // Act
    ArrayList<DiaryEntry> entries = newRegister.getDiaryEntriesList();

    // Assert
    assertNotNull(entries);
    assertTrue(entries.isEmpty());
  }

  @ Test
  public void getDiaryEntriesList_shouldReturnTheDiaryEntriesList_setByTheConstructor() {

    // Arrange
    DiaryEntryRegister diaryEntryRegister = new DiaryEntryRegister();
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content");
    ArrayList<DiaryEntry> expectedList = new ArrayList<>();
    expectedList.add(entry);

    // Act
    diaryEntryRegister.getDiaryEntriesList().add(entry);

    // Assert
    assertEquals(expectedList, diaryEntryRegister.getDiaryEntriesList());
  }

  @ Test
  public void getDiaryEntryByIndex_shouldReturnTheEntryAtIndex_whenIndexNotOutOfBounds() {

    // Arrange
    DiaryEntryRegister diaryEntryRegister = new DiaryEntryRegister();
    DiaryEntry entry = new DiaryEntry("Title", new Author("Jane"), "Content");
    diaryEntryRegister.getDiaryEntriesList().add(entry);


    // Act
    DiaryEntry entryByIndex = diaryEntryRegister.getDiaryEntryByIndex(1);

    // Assert
    assertEquals(entry, entryByIndex);
  }

  @ Test
  public void addNewDiaryEntry_shouldMakeAndAddEntryToList_whenValidInput() {

    // Arrange
    String expectedTitle = "New Entry";
    String expectedContent = "New Content";
    int expectedSize = 1;
    DiaryEntryRegister register = new DiaryEntryRegister();

    // Act
    register.addNewDiaryEntry(expectedTitle, new Author("Jane"), expectedContent);

    // Assert
    assertEquals(expectedSize, register.getDiaryEntriesList().size());
    assertEquals(expectedTitle, register.getDiaryEntryByIndex(1).getTitle());
    assertEquals(expectedContent, register.getDiaryEntryByIndex(1).getContent());
  }

  @Test
  public void addNewDiaryEntry_shouldSetCurrentDate_whenEntryCreated() {

    // Arrange
    LocalDateTime beforeAdd = LocalDateTime.now();
    DiaryEntryRegister register = new DiaryEntryRegister();

    // Act
    register.addNewDiaryEntry("Title", new Author("Jane"), "Content");
    LocalDateTime afterAdd = LocalDateTime.now();

    // Assert
    DiaryEntry entry = register.getDiaryEntryByIndex(1);
    assertTrue(entry.getDate().isAfter(beforeAdd.minusSeconds(1)));
    assertTrue(entry.getDate().isBefore(afterAdd.plusSeconds(1)));
  }

  @Test
  public void searchByKeyword_shouldReturnMatchingEntries_whenKeywordInTitle() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    register.addNewDiaryEntry("Christmas Eve", new Author("Jane"), "Content about Christmas");
    register.addNewDiaryEntry("New Years Eve", new Author("Jane"), "Content about New Years");

    // Act
    ArrayList<DiaryEntry> results = register.searchByKeyword("Christmas");

    // Assert
    assertEquals(1, results.size());
    assertEquals("Christmas Eve", results.get(0).getTitle());
  }

  @Test
  public void searchByKeyword_shouldReturnMatchingEntries_whenKeywordInContent() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    register.addNewDiaryEntry("Christmas Eve", new Author("Jane"), "Content about Christmas");
    register.addNewDiaryEntry("New Years Eve", new Author("Jane"), 
        "Fantastic content about New Years");

    // Act
    ArrayList<DiaryEntry> results = register.searchByKeyword("Fantastic");

    // Assert
    assertEquals(1, results.size());
    assertTrue(results.get(0).getContent().contains("Fantastic"));
  }

  @Test
  public void searchByKeyword_shouldBeCaseInsensitive_whenSearching() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    register.addNewDiaryEntry("Christmas EVE", new Author("Jane"), "Content about Christmas");

    // Act
    ArrayList<DiaryEntry> results = register.searchByKeyword("eve");

    // Assert
    assertEquals(1, results.size());
  }

  @Test
  public void searchByKeyword_shouldReturnMatchingEntries_whenKeywordInAuthorName() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    Author specificAuthor = new Author("Jonh");
    register.addNewDiaryEntry("Title", specificAuthor, "Content");
    register.addNewDiaryEntry("Other Title", new Author("Jane"), "Other Content");

    // Act
    ArrayList<DiaryEntry> results = register.searchByKeyword("Jonh");

    // Assert
    assertEquals(1, results.size());
  }

  @Test
  public void getEntriesBetweenDates_shouldReturnEntriesInRange_whenEntriesExist() {

    // Arrange
    LocalDateTime date1 = LocalDateTime.of(2025, 1, 15, 10, 0);
    LocalDateTime date2 = LocalDateTime.of(2025, 1, 20, 10, 0);
    LocalDateTime date3 = LocalDateTime.of(2025, 2, 1, 10, 0);
    DiaryEntryRegister register = new DiaryEntryRegister();

    register.getDiaryEntriesList().add(new DiaryEntry("Entry 1", 
        new Author("Jane"), "Content", date1));
    register.getDiaryEntriesList().add(new DiaryEntry("Entry 2", 
        new Author("Jane"), "Content", date2));
    register.getDiaryEntriesList().add(new DiaryEntry("Entry 3", 
        new Author("Jane"), "Content", date3));

    LocalDate startDate = LocalDate.of(2025, 1, 10);
    LocalDate endDate = LocalDate.of(2025, 1, 25);

    // Act
    ArrayList<DiaryEntry> results = register.getEntriesBetweenDates(startDate, endDate);

    // Assert
    assertEquals(2, results.size());
  }

  @Test
  public void getEntriesBetweenDates_shouldIncludeEndDates_whenEntriesOnEnds() {

    // Arrange
    LocalDateTime boundaryDate = LocalDateTime.of(2025, 3, 15, 10, 0);
    DiaryEntryRegister register = new DiaryEntryRegister();
    register.getDiaryEntriesList().add(new DiaryEntry("Boundary Entry", new Author("Jane"),
         "Content", boundaryDate));

    LocalDate searchDate = LocalDate.of(2025, 3, 15);

    // Act
    ArrayList<DiaryEntry> results = register.getEntriesBetweenDates(searchDate, searchDate);

    // Assert
    assertEquals(1, results.size());
  }

  @Test
  public void getEntriesOnDate_shouldReturnEntriesForSpecificDate_whenEntriesExist() {

    // Arrange
    LocalDateTime targetDate = LocalDateTime.of(2025, 5, 20, 14, 30);
    LocalDateTime otherDate = LocalDateTime.of(2025, 5, 21, 10, 0);
    DiaryEntryRegister register = new DiaryEntryRegister();

    register.getDiaryEntriesList().add(new DiaryEntry("Target Entry", 
        new Author("Jane"), "Content", targetDate));
    register.getDiaryEntriesList().add(new DiaryEntry("Other Entry", 
        new Author("Jane"), "Content", otherDate));

    // Act
    ArrayList<DiaryEntry> results = register.getEntriesOnDate(LocalDate.of(2025, 5, 20));

    // Assert
    assertEquals(1, results.size());
    assertEquals("Target Entry", results.get(0).getTitle());
  }

  // NEGATIVE TESTS

  @Test
  public void getDiaryEntriesList_shouldReturnEmptyList_whenNoEntriesExist() {

    // Arrange
    DiaryEntryRegister emptyRegister = new DiaryEntryRegister();

    // Act
    ArrayList<DiaryEntry> entries = emptyRegister.getDiaryEntriesList();

    // Assert
    assertNotNull(entries);
    assertTrue(entries.isEmpty());
  }

  @Test
  public void getDiaryEntryByIndex_shouldThrowException_whenIndexIsNegative() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    register.addNewDiaryEntry("Title", new Author("Jane"), "Content");

    // Act + Assert
    assertThrows(IndexOutOfBoundsException.class, () -> {
      register.getDiaryEntryByIndex(-1);
    });
  }

  @Test
  public void getDiaryEntryByIndex_shouldThrowException_whenIndexExceedsSize() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    register.addNewDiaryEntry("Title", new Author("Jane"), "Content");

    // Act + Assert
    assertThrows(IndexOutOfBoundsException.class, () -> {
      register.getDiaryEntryByIndex(5);
    });
  }

  @Test
  public void getDiaryEntryByIndex_shouldThrowException_whenListIsEmpty() {

    // Arrange
    DiaryEntryRegister emptyRegister = new DiaryEntryRegister();

    // Act + Assert
    assertThrows(IndexOutOfBoundsException.class, () -> {
      emptyRegister.getDiaryEntryByIndex(0);
    });
  }

  @Test
  public void searchByKeyword_shouldReturnEmptyList_whenNoMatchesFound() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    register.addNewDiaryEntry("Title", new Author("Jane"), "Content");

    // Act
    ArrayList<DiaryEntry> results = register.searchByKeyword("cat");

    // Assert
    assertNotNull(results);
    assertTrue(results.isEmpty());
  }

  @Test
  public void searchByKeyword_shouldReturnEmptyList_whenKeywordIsEmpty() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    register.addNewDiaryEntry("Title", new Author("Jane"), "Content");

    // Act
    ArrayList<DiaryEntry> results = register.searchByKeyword("");

    // Assert
    assertNotNull(results);
    assertTrue(results.isEmpty());
  }

  @Test
  public void getEntriesBetweenDates_shouldReturnEmptyList_whenNoEntriesInRange() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    LocalDateTime entryDate = LocalDateTime.of(2025, 6, 15, 10, 0);
    register.getDiaryEntriesList().add(new DiaryEntry("Entry", 
        new Author("Jane"), "Content", entryDate));

    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 1, 31);

    // Act
    ArrayList<DiaryEntry> results = register.getEntriesBetweenDates(startDate, endDate);

    // Assert
    assertNotNull(results);
    assertTrue(results.isEmpty());
  }

  @Test
  public void getEntriesOnDate_shouldReturnEmptyList_whenNoEntriesOnDate() {

    // Arrange
    DiaryEntryRegister register = new DiaryEntryRegister();
    LocalDateTime entryDate = LocalDateTime.of(2025, 7, 20, 10, 0);
    register.getDiaryEntriesList().add(new DiaryEntry("Entry", 
        new Author("Jane"), "Content", entryDate));

    // Act
    ArrayList<DiaryEntry> results = register.getEntriesOnDate(LocalDate.of(2025, 7, 21));

    // Assert
    assertNotNull(results);
    assertTrue(results.isEmpty());
  }

  @Test
  public void addNewDiaryEntry_shouldAcceptNullAuthor_whenGivenNullValue() {

    // Arrange
    Author nullAuthor = null;
    DiaryEntryRegister register = new DiaryEntryRegister();

    // Act
    register.addNewDiaryEntry("Title", nullAuthor, "Content");

    // Assert
    assertEquals(1, register.getDiaryEntriesList().size());
    assertNull(register.getDiaryEntryByIndex(1).getAuthor());
  }
}