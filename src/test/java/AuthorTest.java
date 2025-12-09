

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.ntnu.idi.bidata.Author;
import org.junit.jupiter.api.Test;

/** 
 * The test class for the Author class
 */
class AuthorTest {

  // POSITIVE TESTS

  @Test
  public void constructor_shouldInitializeNameCorrectly_whenGivenValidName() {

    // Arrange
    String expectedName = "Jane";

    // Act
    Author author = new Author(expectedName);

    // Assert
    assertEquals(expectedName, author.getName());
  }

  @Test
  public void constructor_shouldInitializeEntryCountToZero_whenCreated() {

    // Arrange
    int expectedCount = 0;

    // Act
    Author author = new Author("Jane");

    // Assert
    assertEquals(expectedCount, author.getNumberOfEntries());
  }

  @Test
  public void getName_shouldReturnName_setByConstructor() {

    // Arrange
    String expectedName = "Jane";

    // Act
    Author author = new Author(expectedName);

    // Assert
    assertEquals(expectedName, author.getName());
  }

  @Test
  public void getNumberOfEntries_shouldReturnUpdatedCount_afterModification() {

    // Arrange
    int expectedNumberOfEntries = 4;

    // Act
    Author author = new Author("Jane");
    author.modifyNumberOfEntries(4);

    // Assert
    assertEquals(expectedNumberOfEntries, author.getNumberOfEntries());
  }

  @Test
  public void setName_shouldUpdateName_whenGivenNewName() {

    // Arrange
    String changedName = "John";

    // Act
    Author author = new Author("Jane");
    author.setName(changedName);

    // Assert
    assertEquals(changedName, author.getName());
  }

  @Test
  public void modifyNumberOfEntries_shouldIncreaseCount_whenGivenPositiveNumber() {

    // Arrange
    int change = 2;

    // Act
    Author author = new Author("Jane");
    author.modifyNumberOfEntries(change);

    // Assert
    assertEquals(change, author.getNumberOfEntries());
  }


  // NEGATIVE TESTS

  @Test
  public void constructor_shouldAcceptEmptyString_whenGivenEmptyName() {

    // Arrange
    String emptyName = "";

    // Act
    Author author = new Author(emptyName);

    // Assert
    assertEquals(emptyName, author.getName());
  }

  @Test
  public void constructor_shouldAcceptNull_whenGivenNullName() {

    // Arrange
    String nullName = null;

    // Act
    Author author = new Author(nullName);

    // Assert
    assertNull(author.getName());
  }

  @Test
  public void modifyNumberOfEntries_shouldDecreaseCount_whenGivenNegativeNumber() {

    // Arrange
    Author author = new Author("Jane");
    author.modifyNumberOfEntries(5);
    int expectedCount = 3;

    // Act
    author.modifyNumberOfEntries(-2);

    // Assert
    assertEquals(expectedCount, author.getNumberOfEntries());
  }

  @Test
  public void modifyNumberOfEntries_shouldAllowNegativeTotal_whenDecrementExceedsCount() {

    // Arrange
    Author author = new Author("Jane");
    int expectedNegativeCount = -3;

    // Act
    author.modifyNumberOfEntries(-3);

    // Assert
    assertEquals(expectedNegativeCount, author.getNumberOfEntries());
  }

  @Test
  public void setName_shouldAcceptNull_whenGivenNullValue() {

    // Arrange
    Author author = new Author("Jane");

    // Act
    author.setName(null);

    // Assert
    assertNull(author.getName());
  }

  @Test
  public void setName_shouldAcceptEmptyString_whenGivenEmptyValue() {

    // Arrange
    Author author = new Author("Jane");
    String emptyName = "";

    // Act
    author.setName(emptyName);

    // Assert
    assertEquals(emptyName, author.getName());
  }
}