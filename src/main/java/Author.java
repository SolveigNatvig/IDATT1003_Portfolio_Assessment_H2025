
/**
 * Represents the author of a diary entry
 * 
 * <p> An {@code Author} object stores only a name which may be changed after 
 * creation. The class ensures that a blank name does not cause an exeption, 
 * but is instead stored as an empty string.
 */
class Author {
    private String name;

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
     * Sets a name for the author
     * 
     * @param name the new name to assign; this value is stored exactly as given
     */
    public void setName(String name){
        this.name = name;
    }





    // maybe make the author name final?
    // if not i might get a lot of entries registrerd to ""...

    
}


