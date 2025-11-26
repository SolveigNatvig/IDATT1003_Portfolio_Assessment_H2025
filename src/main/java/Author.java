
/**
 * Represents the author of a diary entry
 * 
 * <p> An {@code Author} object stores only a name which may be changed after 
 * creation. The class ensures that a blank name does not cause an exeption, 
 * but is instead stored as an empty string.
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
        if (name.isBlank()){
            //throw new IllegalArgumentException("Author must have a name");
            this.name = "";
        }
        else{
            this.name = name;
        }
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

    public void addNumberOfEntries(){
        this.numberOfEntries += 1;
    }
}


