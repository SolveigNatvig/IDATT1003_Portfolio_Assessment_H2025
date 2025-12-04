
/**
 * Entity class for Author.<br>
 * Represents the author of a diary entry.
 * 
 * <br><br>
 *
 * An {@code Author} object stores only a name, which may be changed after
 * creation. This method for changing the name of the author is used for the JSON files,
 * and will not be a part of the user interface. In addition to the name, the Author object
 * has a diary entry count {@code numberOfEntries} which is set to zero when the author is created.
 * Later this counter will be changed to count how many existing diary entries that specific author has written.
 * 
 * <br><br>
 * 
 * <b>Responsibility:</b><br>
 * This class holds information about an author, which is linked to the diary entries the author has written.
 * It is used to identify who has written the different diary entries and to count how many existing entries the author has written. (If an entry is deleted, then the count will also * the author has written. (If an entry is deleted, then the count will also go down)
 * 
 * <br><br>
 * 
 * <b>Information:</b><br>
 * This class stores: <br>
 * - {@code name}: The name of the author, stored as a {@code String} because names are textual. <br>
 * - {@code numberOfEntries}: The count of diary entries written by the author, stored as an {@code int} because it is a simple numeric counter. <br>
 * <br>
 * <b>Methods:</b> The Author class contains these following methods
 * Author(String name)
 * getName()
 * getNumberOfEntries()
 * setName(String name)
 * modifyNumberOfEntries(int number)
*/
public class Author {
    private String name;
    private int numberOfEntries;

    /**
     * Creates a new {@code Author} with the given name.
     * 
     * @param name the name of the author; if the value is blank, an empty string is stored instead
     */
    public Author(String name){
        this.name = name;
        this.numberOfEntries = 0;
    }

    /**
     * Returns the name of the author
     * 
     * @return the author's name, which may be an empty string
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the number of entries written by this author
     * 
     * @return the number of entries
     */
    public int getNumberOfEntries(){
        return this.numberOfEntries;
    }

    /**
     * Sets a name for the author
     * 
     * @param name the new name to assign; this value is stored exactly as given
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Modifies the number of diary entries connected to this author
     * 
     * @param number the number to add (or subtract if negative) to the current count
     */
    public void modifyNumberOfEntries(int number){
        this.numberOfEntries += number;
    }

}


