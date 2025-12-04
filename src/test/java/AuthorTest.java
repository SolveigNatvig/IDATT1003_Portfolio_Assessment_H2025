
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** 
 * Using Arrange-Act-Assert structure
 */
class AuthorTest{
    
    // POSITIVE TESTS

    // TEST THE CONSTRUCTOR!!!

    @ Test
    public void getName_shouldReturnName_setByTheConstructor() {
        
        // Arrange
        String expectedName = "Jane";

        // Act 
        Author author = new Author(expectedName);
        
        // Assert
        assertEquals(expectedName, author.getName());
    }

    @ Test
    public void getNumberOfEntries_shouldReturnNumberOfEntries() {

        // Arrange
        int expectedNumberOfEntries = 4;

        // Act
        Author author = new Author("Jane");
        author.modifyNumberOfEntries(4);

        // Assert
        assertEquals(expectedNumberOfEntries, author.getNumberOfEntries());
    }

    @ Test
    public void setName_shouldChangeAuthorsNameToInputString() {

        // Arrange
        String changedName = "Jonh";

        // Act
        Author author = new Author("Jane");
        author.setName(changedName);

        // Assert
        assertEquals(changedName, author.getName());
    }

    @ Test
    public void modifyNumberOfEntries() {

        // Arrange
        int change = 2;

        // Act 
        Author author = new Author("Jane");
        author.modifyNumberOfEntries(change);

        // Assert
        assertEquals(change, author.getNumberOfEntries());
    }

    // NEGATIVE TESTS


}