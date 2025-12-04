/**
 * Entity class for User. <br>
 * Represents the user of the application
 * 
 * <br><br>
 * 
 * An {@code User} object stores an {@code Author} object, a username and a password. The Author object and the username is set to be final, and thus it will not be possible to change these after creation. The password however is possible to change, but the user will have to remember the previous password because the user have to be logged in in order to change their password.
 * 
 * <b>Responsibility:</b><br>
 * This class holds information about a user, which is used to identify which author writes a new entry once logged in. 
 * 
 * <br><br>
 * 
 * <b>Information:</b><br>
 * This class stores: <br>
 * - {@code author}: the author belonging to the user, stored as an {@code Author} object.
 * - {@code username}: the username belonging to the user, stored as a {@code String}
 * - {@code password}: the hashed password used to log in, stored as a {@code String}
 * 
 * <b>Methods:</b> The User class contains these following methods
 * User(Author author, String username, String hashedPassword)
 * getName()
 * getNumberOfEntries()
 * getAuthor()
 * getUsername()
 * setNewPassword(String newPassword)
 * passwordMatches(String hashedInput)
 */
public class User {
    private final Author author;
    private final String username;
    private String password; // This password is supposed to be hashed


    /**
     * Creating a new user...
     */
    public User(Author author, String username, String hashedPassword){
        if (username.isBlank()){
            throw new IllegalArgumentException("Every user needs to have a username");
        }

        if (hashedPassword.isBlank()){
            throw new IllegalArgumentException("A password is required to protect your profile");
        }

        this.author = author;
        this.username = username;
        this.password = hashedPassword;
        
    }

    /**
     * Returns the name belonging to the author belonging to the user
     * 
     * @return the name
     */
    public String getName(){
        return this.author.getName();
    }

    /**
     * Returns the number of existing entries written by the author connected to this user
     * @return the number of existing entries this author has written
     */
    public int getNumberOfEntries(){
        return this.author.getNumberOfEntries();
    }

    /**
     * Returns the author belonging to this user
     * 
     * @return the Author object connected to this user
     */
    public Author getAuthor() {
        return this.author;
    }

    /**
     * Returns the distinct username for this user
     * 
     * @return the username for this user
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Changes the pasword.
     * 
     * @param newPassword the hashed string of the new password
     */
    public void setNewPassword(String newPassword){
        this.password = newPassword;
    }

    /**
     * Verifies if the password matches the hashed input. This method is used by to verify that the user has written the correct password.
     * 
     * @return {@code true} if the two passwors metches. Otherwise it returns {@code false}
     */
    public boolean passwordMatches(String hashedInput){
        return this.password.equals(hashedInput);
    }
    
}


