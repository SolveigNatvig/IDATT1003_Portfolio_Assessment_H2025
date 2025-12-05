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
 * <br><br>
 * 
 * <b>Responsibility</b>
 * <br>
 * This class is responsible for managing a collection of {@link User} objects.
 * It handles user registration, authentication (login), looking upp users, 
 * and persistence (saving/lading users to/from JSON files). 
 * The class also provides password hashing functionality to ensure passwords are stored securely.
 * 
 * <br><br>
 * UserRegister acts as the single point of access for all user-related operations within the 
 * system.
 * 
 * <br><br>
 * 
 * <b>Information stored and datatype choices:</b>
 * <br>
 * - {@code userList} ({@code ArrayList<User>}): A dynamic list for all registrerd users. 
 * ArrayList was chosen because: <br>
 *    - The number of users is unknown when starting the program, 
 * and will grow as new users is added <br>
 *    - Users needs to be iterated through for login verification and looking upp users <br>
 *    - ArrayList provides access by index and efficient iteration with streams. <br>
 *    - Easy serialisation/deserialisation with the Gson libary <br>
 * - {@code gson} ({@code Gson}): A Gson instance for JSON serialisation. 
 * Gson was chosen because: <br>
 *      - It handles complex object serialisation authomatically. <br>
 *      - It is a widely used and well documented libary for JSON in java. <br>
 *      - Supports generic types like ArrayList&lt;User&gt;
 * 
 * <br><br>
 * 
 * <b>Mutability assessment:</b> what should be set at creation vs changable <br>
 * (Table)
 * 
 * <br><br>
 * 
 * <b>How does the class respond to invalid data?</b>
 * <br>
 * (INFO)
 *  
 */
public class UserRegister {
  private ArrayList<User> userList;
  private Gson gson;
    
  /**
  * Creates a new empty UserRegister. Initalises an empty user list ({@code ArrayList}) 
  * and a Gson instance for JSON operations. 
  * <br>
  * Users can be added via {@code addNewUser()} or loaded from file via {@code readFromJSON()}
  */
  public UserRegister() {
    this.userList = new ArrayList<>();
    this.gson = new Gson();
  }

  /**
  * Methods which hashes a string using SHA-256 algorithm and returns it as a Base64-encoding 
  *   string. 
  * This method is used to hash passwords so that it can be stored securely. The acctual password 
  * cannot be recovered from the hash.
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
  * Method for creating and registrating a new user in the system.
  * Creates an Author object with a combined name-username idetifyer, 
  * hashes the password for secure storage, and adds the new User to the register. 
  * a new {@link User} object, containing an {@link Author} object, a username and 
  * a hashed password. After the User object is created it is added to the {@code userList}.
  * 
  * @param name the display name of the user
  * @param username the distinct username for login
  * @param password the password in plain text (this will be hashed before storage)
  * @throws IllegalArgumentException if username or password is blank 
  *     (thrown by User constructor)
  */
  public void addNewUser(String name, String username, String password) {
    Author author = new Author(String.format("%s_%s", name, username));
    String hashed = hash(password);    
    User user = new User(author, username, hashed);
    this.userList.add(user);
  }

  /**
  * Method to check if the username belongs to an existing user within the system. 
  * The comparison is case-insensitive
  * 
  * @param username The username to check
  * @return {@code true} (the user exists) or {@code false} (otherwise)
  */
  public boolean isUser(String username) {
    return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
  }

  /**
  * Method for authenticating a user by verifying their username and password. 
  * The password is hashed and compared against the stored hash.
  * Username comparison is case insensitive. 
  * 
  * <br><br>
  * 
  * <b>Error handeling:</b>
  * Returns {@code false} for any invalid credentials rather than throwing exeptions, 
  * allowing the UI to display appropriate messages.
  * 
  * @param username The username used to atempt to log in
  * @param password The password in plain text to verify
  * @return true (if the username and the password matches) or false (otherwise)
  */
  public boolean login(String username, String password) {
    String hashInput = hash(password);

    return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username) 
    && user.passwordMatches(hashInput));
  }

  /**
  * Method to retrieve the user by the username (which is distinct). 
  * The comparison is case insensitive 
  * 
  * <b>Error handeling:</b>
  * Returns {@code null} if no user is found.
  * 
  * @param username The username to search for
  * @return the user if the user exits, else returns {@code null}
  */
  public User getByUsername(String username) {
    return userList.stream().filter(u -> u.getUsername()
    .equalsIgnoreCase(username)).findFirst().orElse(null);
  }

  /** NEW JAVADOC
  * Method for printing the author statistics.
  * Displays each author's name and the number of diary entries they have written.
  * <b>Output formate:</b>
  * AuthorName : NumberOfEntries
  */
  public void printAuthorStatistics() {
    this.userList.forEach(user -> System.out.printf(String.format("%s : %d", user.getAuthor()
        .getName(), user.getAuthor().getNumberOfEntries())));
  }

  /**
  * Reads users fron the JSON file "users.json" into the user list.
  * If the file does not exist, no action is taken to allow a fresh start.
  * If the file is exists but is empty or invalid an error message is printed, 
  * but the program continues.
  * 
  * <br><br>
  * <b>Error handeling:</b>
  * Uses graceful dgradiation if the reading fails, an error message is printed to 
  * consolle but the application continues with an empty or partially loaded user list.
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
  * Method to write all users to the JSON file "users.json".
  * Overwrites any existing file content.
  * 
  * <br><br> 
  * <b>Error handeling:</b> 
  * <br>
  * Uses graceful degradiation if writing fails an error message is printed to console, 
  * but the application continue running 
  */
  public void writeToJson() {
    String filepath = "users.json";

    try (FileWriter writer = new FileWriter(filepath)) {
      gson.toJson(this.userList, writer);
      System.out.println("\nUsers saved successfully!");
    } catch (IOException e) {
      System.out.println("Could not save users.json: " + e.getMessage());
    }
  }


}