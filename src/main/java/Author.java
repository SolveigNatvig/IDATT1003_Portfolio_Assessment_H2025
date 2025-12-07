
/**
 * Entity class representing an author in the diary application.
 * 
 * <p><b>Role and Responsibility</b></p>
 * 
 * <p>The {@code Author} class represents the identity of a person who writes diary entries.
 * It serves as an entity class that tracks author information and maintains
 * a count of how many existing diary entries the author has written. 
 * (If an entry is deleted, then the count will also go down). This class is linked to
 * {@link User} objects to associate diary entries with registered users. </p>
 *
 * <b>Information Stored and Data Type Justification</b>
 * <ul>
 * <li><b>Name ({@code name}):</b> The name of the author. Containing both the 
 * authors name and the connected users username, in a {@code String}, 
 * in order to make it unique. The name is stored as a String because it is in text format.</li>
 * <li><b>Entry Count ({@code numberOfEntries}):</b> The count, stored as an {@code int} 
 * used to keep track of how many existing entries the author har written. Integer is chosen for 
 * this variable because it is a counting variable which only counts whole entries</li>
 * </ul>
 * 
 * <b>Mutability Assessment</b>
 * 
 * <p>Both name and entry count is set at creation. 
 * The name is typed in by the user, and the entry count is set to 0. Both variables can be changed 
 * after creation. This is to handle JSON deserialisation and entry tracking.</p>
 * 
 * <b>Invalid Data Handling Strategy</b>
 * 
 * <p>This class follows a permissive/accepting strategy for invalid data:</p>
 * <ul>
 * <li><b>Blank or null names:</b> The class accepts any string value, including 
 * blank or empty strings. No validation is performed in the constructor, allowing 
 * flexibility for JSON deserialisation. The calling code ({@link UserRegister}) 
 * is responsible for ensuring valid names are provided.</li>
 * <li><b>Negative entry counts:</b> The {@code modifyNumberOfEntries} method 
 * allows negative adjustments, which could theoretically result in a negative count. 
 * This is intentional to support entry deletion, and the calling code should ensure 
 * the count does not go below zero.</li>
 * </ul>
*/
public class Author {
  private String name;
  private int numberOfEntries;

  /**
  * Creates a new {@code Author} with the given name.
  * 
  * @param name the name of the author; if the value is blank, an empty string is stored instead
  */
  public Author(String name) {
    this.name = name;
    this.numberOfEntries = 0;
  }

  /**
  * Returns the name of the author.
  * 
  * @return the author's name, which may be an empty string
  */
  public String getName() {
    return this.name;
  }

  /**
  * Returns the number of entries written by this author.
  * 
  * @return the number of entries
  */
  public int getNumberOfEntries() {
    return this.numberOfEntries;
  }

  /**
  * Sets a name for the author.
  * 
  * <p>This method is primarily used during JSON deserialisation to restore
  * author names from stored data.</p>
  * 
  * @param name the new name to assign. This value is stored exactly as given
  */
  public void setName(String name) {
    this.name = name;
  }

  /**
  * Modifies the number of diary entries connected to this author.
  * 
  * <p>Uses a positive number to increase the count (when a new entry is created)
  * or a negative number to decrease the count(when an entry is deleted).</p>
  * 
  * @param number the number to add (or subtract if negative) to the current count
  */
  public void modifyNumberOfEntries(int number) {
    this.numberOfEntries += number;
  }
}


