
import java.util.ArrayList;

public class AuthorRegister {
    private ArrayList<Author> authorList;
    
    public AuthorRegister(){
        this.authorList = new ArrayList<>();
    }

    public void addNewAuthor(String name){
        Author author = new Author(name);
        this.authorList.add(author);
    }

    public boolean isAuthor(String name){
        Author author = new Author(name);
        if (this.authorList.contains(author)){
            return true;
        }

        else {
            return false;
        }
    }

    // Never run if isAuthor() returns false __________ NOT DONE
    // If the author dosnt exist, then a new authr is added
    public Author getAuthorByName(String name){
        Author author = new Author(name);
        
        if (this.authorList.indexOf(author) != -1){
            return this.authorList.get(this.authorList.indexOf(author));
        }
        else{
            this.authorList.add(author);
            return author;
        }
    }
    


    
}
