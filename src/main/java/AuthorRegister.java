
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

    public Author getAuthorByName(String name) {
        for (Author author : this.authorList) {
            if (author.getName().equalsIgnoreCase(name)) {
                return author;
            }
        }

        // Author not found — create a new one
        Author newAuthor = new Author(name);
        this.authorList.add(newAuthor);
        return newAuthor;
    }

    public void printAuthorList(){
        for (Author author : this.authorList){
            System.out.println(String.format("%s : %d", author.getName(), author.getNumberOfEntries()));
        }
    }
}
