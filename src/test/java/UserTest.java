import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.bidata.Author;
import edu.ntnu.idi.bidata.User;
import org.junit.jupiter.api.Test;

/** 
 * The thest class for the User class 
 */
public class UserTest {

  // POSITIVE TESTS

  @ Test
  public void constructorUser_shouldInitiliseField_whenGivenExpextedValues() {

    // Arrange
    Author expectedAuthor = new Author("Jane");
    String expectedUsername = "Username";
    String expectedPassword = "HashedPassword";

    // Act
    User user = new User(expectedAuthor, expectedUsername, expectedPassword);

    // Assert
    assertEquals(expectedAuthor, user.getAuthor());
    assertEquals(expectedUsername, user.getUsername());
    user.passwordMatches(expectedPassword);
  }

  @ Test
  public void getName_shouldReturnName_setByTheConstructor() {

    // Arrange
    String expectedName = "Jane";
    Author author = new Author(expectedName);

    // Act
    User user = new User(author, "Username", "Hashed Password");

    // Assert
    assertEquals(expectedName, user.getName());
  }

  @ Test
  public void getNumberOfEntries_shouldReturnNumberOfEntries_setByConstructor() {
        
    // Arrange
    Author author = new Author("Jane");
    int expectedNumberOfEntries = 0;

    // Act
    User user = new User(author, "Username", "Hashed Password"); 

    // Assert
    assertEquals(expectedNumberOfEntries, user.getNumberOfEntries());
  }

  @ Test
  public void getAuthor_shouldReturnAuthor_setByConstructor() {

    // Arrange
    Author expectedAuthor = new Author("Jane");

    // Act
    User user = new User(expectedAuthor, "Username", "Hashed Password"); 

    // Assert
    assertEquals(expectedAuthor, user.getAuthor());
  }

  @ Test
  public void getUsername_shouldReturnUsername_setByConstructor() {

    // Arrange
    Author author = new Author("Jane");
    String expectedUsername = "Username";

    // Act
    User user = new User(author, expectedUsername, "hashedPassword");

    // Assert
    assertEquals(expectedUsername, user.getUsername());
  }

  @ Test
  public void setNewPassword_shouldChangePassword_whenGivenValidInput() {

    // Arrange
    Author author = new Author("Jane");
    User user = new User(author, "Username", "Old Password");
    String expectedPassword = "New Password";

    // Act
    user.setNewPassword(expectedPassword);

    // Assert
    assertTrue(user.passwordMatches(expectedPassword));
  }

  @ Test
  public void passwordMatches_shouldReturnTrue_whenInputMatchesExistingPassword() {

    // Arrange
    Author author = new Author("Jane");
    String storedPassword = "password";
    User user = new User(author, "Username", storedPassword);
    String inputPassword = "password";

    // Act
    Boolean result = user.passwordMatches(inputPassword);

    // Assert
    assertTrue(result);
  }


   // NEGATIVE TESTS

   @ Test
   public void constructor_shouldThrowIllegalArgument_whenUsernameIsBlank() {
    // Arrange
    String name = "";

    // Act
    Author author = new Author(name);

    // Assert
    assertThrows(IllegalArgumentException.class, () ->
        new User(author, "username", "   "));
   }


   @ Test
   public void constructor_shouldThrowIllegalArgument_whenPasswordIsBlank() {
    // Arrange
    String password = "";

    // Act + Assert
    assertThrows(IllegalArgumentException.class, () ->
        new User(new Author("Jane"), "username", password));
   }
}
