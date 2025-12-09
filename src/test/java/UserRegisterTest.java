
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.bidata.User;
import edu.ntnu.idi.bidata.UserRegister;
import org.junit.jupiter.api.Test;

/** 
 * The test class for the UserRegister class
 */
public class UserRegisterTest {
    
  @ Test
  public void addNewUser_shouldAddUserToList_whenExpectedValues() {

    // Arrange
    String name = "Jane";
    String username = "Username";
    String password = "Password";
    UserRegister register = new UserRegister();

    // Act
    register.addNewUser(name, username, password);

    // Assert
    assertTrue(register.getByUsername(username).getUsername().equals(username));
  } 

  @Test
  void login_shouldReturnTrue_whenGivenCorrectInput() {
    // Arrange
    UserRegister register = new UserRegister();

    // Act
    register.addNewUser("Jana", "username", "passord");

    // Assert
    assertTrue(register.login("username", "passord"));
  }

  @Test
  void getByUsername_shouldReturnUser_whenUserExists() {
    // Arrange
    UserRegister register = new UserRegister();
    register.addNewUser("Jane", "Jane123", "Password");

    // Act
    User user = register.getByUsername("jane123"); // case-insensitive

    // Assert
    assertNotNull(user);
    assertEquals("Jane123", user.getUsername());
  }

  // NEGATIVE TESTS

  @Test
  void addNewUser_shouldThrowIllegalArgument_whenBlankUsername() {
    // Arrange
    UserRegister register = new UserRegister();

    // Act + Assert
    assertThrows(IllegalArgumentException.class, () ->
        register.addNewUser("Jane", "   ", "Password")
        );
  }

  @Test
  void login_shouldReturnFalse_whenWrongPassword() {
    // Arrange
    UserRegister register = new UserRegister();

   // Act
   register.addNewUser("Jane", "Username", "Password");

   // Assert
   assertFalse(register.login("Username", "wrongPassword"));
  }

  @Test
  void login_shouldReturnFalse_whenWrongUsername() {
    // Arrange
    UserRegister register = new UserRegister();

    // Act
    register.addNewUser("Jane", "Username", "Password");

    // Assert
    assertFalse(register.login("wrongUser", "Password"));
  }

}
