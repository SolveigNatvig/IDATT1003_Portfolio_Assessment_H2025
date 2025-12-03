import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class UserRegister {
    private ArrayList<User> userList;
    private Gson gson;
    
    public UserRegister(){
        this.userList = new ArrayList<>();
        this.gson = new Gson();
    }

    public static String hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(text.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } 
        
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewUser(String name, String username, String password){
        Author author = new Author(String.format("%s_%s", name, username));
        String hashed = hash(password);    
        User user = new User(author, username, hashed);
        this.userList.add(user);
    }

    public boolean isUser(String username){
        return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public boolean login(String username, String password) {
        String hashInput = hash(password);

        return userList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username) && user.passwordMatches(hashInput));
    }


    public User getByUsername(String username) {
        return userList.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public void printAuthorStatistics() {
        for (User user : this.userList){
            System.out.println(String.format("%s : %d", user.getAuthor().getName(), user.getAuthor().getNumberOfEntries()));
        }
    }

    public void readFromJSON() {
        String filepath = "users.json";

        try {
            if (Files.exists(Paths.get(filepath))) {
                ArrayList<User> users = gson.fromJson(
                    new FileReader(filepath),
                    new TypeToken<ArrayList<User>>() {}.getType()
                );

                if (users != null) {
                    this.userList.addAll(users);
                }
            }
        }

        catch (IOException e) {
            System.out.println("Could not read users.json: " + e.getMessage());
        }
    }

    public void writeToJSON() {
        String filepath = "users.json";

        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(this.userList, writer);
            System.out.println("\nUsers saved successfully!");
        }

        catch (IOException e) {
            System.out.println("Could not save users.json: " + e.getMessage());
        }
    }

}