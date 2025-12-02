
class User {
    private Author author;
    private int familyID;
    private String username;
    private String password; // This password is supposed to be hashed


    public User(Author author, String username, String hashedPassword){
        this.familyID = 0;

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

    public String getName(){
        return this.author.getName();
    }

    public int getNumberOfEntries(){
        return this.author.getNumberOfEntries();
    }

    public Author getAuthor() {
        return this.author;
    }

    public String getUsername(){
        return this.username;
    }

    public int getFamilyID(){
        return this.familyID;
    }

    public void setName(String name){ //Should name be final?
        this.author.setName(name);
    }

    public void setNewPassword(String newPassword){
        this.password = newPassword;
    }

    public void setFamilyID(int familyID){
        this.familyID = familyID;
    }

    public boolean passwordMatches(String hashedInput){
        return this.password.equals(hashedInput);
    }
    
}


