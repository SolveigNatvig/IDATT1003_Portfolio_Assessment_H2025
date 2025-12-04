import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;


public class DiaryEntryRegisterTest {
    
    // POSITIVE TESTS

    @ Test
    public void DiaryEntryRegister_InitialisesField_whenGivenExpectedValues() {
        
        // Arrange
        

        // Act

        // Assert
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
        DiaryEntry entryByIndex = diaryEntryRegister.getDiaryEntryByIndex(0);

        // Assert
        assertEquals(entry, entryByIndex);
    }

    @ Test
    public void showAllEntries_shouldPrintAllExistingEntries_whenEntriesExist() {
        // Hvordan gjøre denne testen?
    }

    @ Test
    public void addNewDiaryEntry_shouldMakeAndAddEntryToList_whenValidInput() {

        // Arrange
        DiaryEntry expectedEntry = new DiaryEntry("Title", new Author("Jane"), "Content");
        DiaryEntryRegister register = new DiaryEntryRegister();

        // Act
        register.addNewDiaryEntry("Title", new Author("Jane"), "Content");

        // Assert
        assertEquals(expectedEntry, register.getDiaryEntryByIndex(0));
    }

    @ Test
    public void searchByKeyword_shouldReturnDiaryEntryResult_whenResultExist() {

    }

    @ Test
    public void getEntriesBetweenDates_shouldReturnAllEntriesBetweenDates_whenSuchEntriesExist() {

    }

    @ Test
    public void getEntriesOnDate_shouldReturnAllEntriesOnDate_whenSuchEntriesExist() {

    }

    @ Test
    public void readFromJSON_shouldWriteFromJSONToList_whenJSONFileExist() {

    }

    @ Test
    public void writeToJSON_shouldWriteFromListToJSON_whenListAndJSONExists() {

    }

    // NEGATIVE TESTS
}
