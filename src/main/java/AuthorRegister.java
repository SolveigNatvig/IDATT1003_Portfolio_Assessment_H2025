
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

public class AuthorRegister {
    private ArrayList<Author> authorList;
    private Gson gson;
    
    public AuthorRegister(){
        this.authorList = new ArrayList<>();
        this.gson = new Gson();

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


    public void readFromJSON(){
        String filepath = "authors.json";

        try {
            if (Files.exists(Paths.get(filepath))) {
                ArrayList<Author> authors = gson.fromJson(
                    new FileReader(filepath),
                    new TypeToken<ArrayList<Author>>() {}.getType()
                );

                if (authors != null) {
                    this.authorList.addAll(authors);
                }
            }
        } 
        
        catch (IOException e) {
            System.out.println("Could not read authors.json: " + e.getMessage());
        }
    }

    public void writeToJSON(){
        String filepath = "authors.json";

        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(this.authorList, writer);
            System.out.println("\nAuthors saved successfully!");
        } 
        
        catch (IOException e) {
            System.out.println("Could not save authors.json: " + e.getMessage());
        }
    }



    
}
