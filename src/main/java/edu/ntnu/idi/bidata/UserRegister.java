package edu.ntnu.idi.bidata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;


/**
 * Registry class for managing users in the diary application. 
 * 
 * <p><b>Role and Responsibility</b></p>
 * 
 * <p>The {@code UserRegister} class is responsible for managing a collection of 
 * {@link User} objects while they are in the application.</p>
 * 
 * <b>Key Responsibilities:</b>
 * <ul>
 * <li><b>User registration:</b> Creating new users with password hashing</li>
 * <li><b>Authentication:</b> Verifying login credentials</li>
 * <li><b>User lookup:</b> Finding users by username</li>
 * <li><b>Persistence:</b> Saving and loading users to/from JSON files</li>
 * <li><b>Password security:</b> Providing hashing for passwords</li>
 * <li><b>Author statistics:</b> Displaying entry counts per author</li>
 * </ul>
 * 
 * <b>Information Stored and Data Type Justification</b>
 * 
 * <p>This class stores:</p>
 * 
 * <ul>
 * <li><b>List of Users ({@code userList}):</b> A list of users. 
 * {@code ArrayList} was chosen because:
 * <ul>
 * <li>The number of users is unknown at startup and grows as users register</li>
 * <li>Users need to be iterated through for login verification and lookups</li>
 * <li>Stream operations provide clean, functional-style querying</li>
 * <li>Easy serialisation/deserialisation with Gson library</li>
 * </ul>
 * </li>
 * <li><b>gson ({@code Gson}):</b> A standard Gson instance is used because:
 * <ul>
 * <li>User objects don't contain {@code LocalDateTime} (unlike DiaryEntry),
 * so no custom adapter is needed</li>
 * <li>Gson handles Author and String fields automatically</li>
 * </ul>
 * </li>
 * </ul>
 * <b>Mutability Assessment</b>
 * 
 * <p>The userList and the gson is set by the constructor. 
 * Both are final and wont be changed later. Only the content of the userList will change. </p>
 * 
 * <b>Invalid Data Handling Strategy</b>
 * 
 * <p>This class uses a combination of graceful degradation,
 * exception propagation, and boolean return values</p>
 */
public class UserRegister {
  private final ArrayList<User> userList;
  private final Gson gson;
    
  /**
  * Creates a new empty UserRegister. 
  * 
  * <p>Initalises an empty user list ({@code ArrayList}) 
  * and a standard Gson instance for JSON operations. 
  * Users can be added via {@code addNewUser()} or loaded from file via {@code readFromJSON()}</p> 
  */
  public UserRegister() {
    this.userList = new ArrayList<>();
    this.gson = new Gson();
  }

  /**
  * Methods which hashes a string using SHA-256 algorithm and 
  * returns it as a Base64-encoding string. 
  * 
  * <p>This method is used to hash passwords so that it can be stored securely. 
  * The acctual password cannot be recovered from the hash.</p>
  * 
  * @param text the string to be hashed (in our case a password)
  * @return the hashed text as a Base64-encoded string
  * @throws RuntimeException if the SHA-256 algorithm is not avaliable on the system.
  */
  public static String hash(String text) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] bytes = md.digest(text.getBytes());
      return Base64.getEncoder().encodeToString(bytes);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  /**
  * Creating and registrating a new user in the system.
  * 
  * <p>Creates an {@link Author} object with a combined name-username idetifyer, 
  * hashes the password for secure storage, creates a new user and adds it to the register. 
  * 
  * @param name the display name of the user
  * @param username the distinct username for login
  * @param password the password in plain text (this will be hashed before storage)
  * @throws IllegalArgumentException if username or password is blank (thrown by User constructor)
  */
  public void addNewUser(String name, String username, String password) {
    Author author = new Author(String.format("%s_%s", name, username));
    String hashed = hash(password);    
    User user = new User(author, username, hashed);
    this.userList.add(user);
  }

  /**
  * Checks if the username belongs to an existing user within the system. 
  * 
  * <p>The comparison is case-insensitive</p>
  * 
  * @param username The username to check
  * @return {@code true} (the user exists) or {@code false} (otherwise)
  */
  public boolean isUser(String username) {
    return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
  }

  /**
  * Authenticates a user by verifying their username and password. 
  * 
  * <p>The password is hashed and compared against the stored hash.
  * Username comparison is case insensitive.</p>
  * 
  * @param username The username to authenticate
  * @param password The password in plain text to verify
  * @return true (if the username and the password matches) or false (otherwise)
  */
  public boolean login(String username, String password) {
    String hashInput = hash(password);

    return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username) 
    && user.passwordMatches(hashInput));
  }

  /**
  * Retrieving the user by the username (which is distinct). 
  * 
  * <p>The comparison is case insensitive </p>
  * 
  * @param username The username to search for
  * @return the user if the user exits, else returns {@code null}
  */
  public User getByUsername(String username) {
    return userList.stream().filter(u -> u.getUsername()
    .equalsIgnoreCase(username)).findFirst().orElse(null);
  }

  /**
  * Printing the author statistics to console.
  * 
  * <p>Displays each author's name and the number of diary entries they have written.</p>  
  */
  public void printAuthorStatistics() {
    this.userList.forEach(user -> System.out.printf(String.format("%s : %d\n", user.getAuthor()
        .getName(), user.getAuthor().getNumberOfEntries())));
  }

  /**
  * Reads users from the JSON file "users.json" into the user list.
  * 
  * <p>If the file does not exist, no action is taken to allow a fresh start.
  * If the file is exists but is empty or invalid an error message is printed, 
  * but the program continues.</p>
  */
  public void readFromJson() {
    String filepath = "users.json";

    try {
      if (Files.exists(Paths.get(filepath))) {
        ArrayList<User> users = gson.fromJson(
          new FileReader(filepath),
          new TypeToken<ArrayList<User>>() {}.getType()
        );

        if (users != null) {
          this.userList.addAll(users);
        }
      }
    } catch (IOException e) {
      System.out.println("Could not read users.json: " + e.getMessage());
    }
  }

  /**
  * Writes all users to the JSON file "users.json".
  * 
  * <p>Overwrites any existing file content. <br><br>
  * 
  * <b>Error handeling:</b> 
  * <br>
  * Uses graceful degradiation if writing fails an error message is printed to console, 
  * but the application continue running </p>
  */
  public void writeToJson() {
    String filepath = "users.json";

    try (FileWriter writer = new FileWriter(filepath)) {
      gson.toJson(this.userList, writer);
      //System.out.println("\nUsers saved successfully!");
    } catch (IOException e) {
      System.out.println("Could not save users.json: " + e.getMessage());
    }
  }


}