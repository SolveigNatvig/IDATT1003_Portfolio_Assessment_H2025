
/**
 * Represents the author of a diary entry
 * 
 * <p> An {@code Author} object stores only a name, which may be changed after 
 * creation. If a blank name is provided, it is stored as an empty string
 * rather than causing an exception </p>
 */
class Author {
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


