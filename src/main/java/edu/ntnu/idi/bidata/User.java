package edu.ntnu.idi.bidata;

/**
 * Entity class representing the user in the application.
 * 
 * <p><b>Role and Responsibility</b></p>
 * 
 * <p>The {@code User} class represents a registered user who can log in to the diary
 * application, create diary entries, and manage their own content. It links an
 * {@link Author} identity (for diary entries) with authentication credentials
 * (username and password).</p>
 * 
 * <b>Key responsibilities include:</b>
 * <ul>
 * <li>Storing user identity information (author, username)</li>
 * <li>Managing authentication credentials (hashed password)</li>
 * <li>Providing password verification for login</li>
 * <li>Enabling password changes for logged-in users</li>
 * </ul>
 * 
 * <b>Information Stored and Data Type Justification</b>
 * <ul>
 * <li><b>Author ({@code author}):</b> The author linked to the user 
 * profile is stored as an {@link Author} because this makes it 
 * possible to create a link between diary entries and their creators. 
 * It also allowes for tracking of entry count per author. </li>
 * <li><b>Username ({@code username}):</b> Usernames are textual identifiers. 
 * {@code String} is the natural choice for variable-length text that needs to support 
 * comparison operations.</li>
 * <li><b>Password ({@code password}):</b> Storing the hashed password as a string. 
 * Using {@code String} makes it easy to compare passwords. 
 * The acctual password is never stored in clean text.</li>
 * </ul>
 * 
 * <b>Mutability Assessment</b>
 * 
 * <p>The author, username and password is set at creation by the user of the 
 * application. The author and username are final, and will not be changed. 
 * The password however can be changed later.</p>
 * 
 * <b>Invalid Data Handling Strategy</b>
 * 
 * <p>This class uses a fail-fast exception strategy for critical data:</p>
 * <ul>
 * <li><b>Blank username:</b> The constructor throws
 * {@code IllegalArgumentException} with the message "Every user needs to have
 * a username". Usernames are essential for authentication and must not be empty.</li>
 * <li><b>Blank password:</b> The constructor throws
 * {@code IllegalArgumentException} with the message "A password is required to
 * protect your profile". Passwords are essential for security.</li>
 * <li><b>Null author:</b> No explicit validation is performed for null authors.
 * If null is passed, subsequent operations like {@code getName()} will throw
 * {@code NullPointerException}. The calling code ({@link UserRegister}) ensures
 * valid authors are provided.</li>
 * <li><b>Password changes:</b> The {@code setNewPassword} method accepts any
 * string without validation. The calling code should ensure the new password is
 * properly hashed before setting.</li>
 * </ul>
 */
public class User {
  private final Author author;
  private final String username;
  private String password; // This password is supposed to be hashed


  /**
  * Creating a new user...
  * 
  * @param author the author linked to the user
  * @param username the username (unique)
  * @param hashedPassword the password (Hashed)
  */
  public User(Author author, String username, String hashedPassword) {
    if (username.isBlank()) {
      throw new IllegalArgumentException("Every user needs to have a username");
    }

    if (hashedPassword.isBlank()) {
      throw new IllegalArgumentException("A password is required to protect your profile");
    }

    this.author = author;
    this.username = username;
    this.password = hashedPassword;
        
  }

  /**
  * Returns the name belonging to the author belonging to the user.
  * 
  * @return the name
  */
  public String getName() {
    return this.author.getName();
  }

  /**
  * Returns the number of existing entries written by the author connected to this user.
  * 
  * @return the number of existing entries this author has written
  */
  public int getNumberOfEntries() {
    return this.author.getNumberOfEntries();
  }

  /**
  * Returns the author belonging to this user.
  * 
  * @return the Author object connected to this user
  */
  public Author getAuthor() {
    return this.author;
  }

  /**
  * Returns the distinct username for this user.
  * 
  * @return the username for this user
  */
  public String getUsername() {
    return this.username;
  }
  
  /**
  * Changes the pasword.
  * 
  * @param newPassword the hashed string of the new password
  */
  public void setNewPassword(String newPassword) {
    this.password = newPassword;
  }

  /**
  * Verifies if the password matches the hashed input. 
  * This method is used by to verify that the user has written the correct password.
  * 
  * @param hashedInput a pre hashed input to compare to the hashed password
  * @return {@code true} if the two passwors metches. Otherwise it returns {@code false} 
  */
  public boolean passwordMatches(String hashedInput) {
    return this.password.equals(hashedInput);
  }
    
}


