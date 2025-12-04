import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Registry class for managing diary entries in the application.
 * 
 * <br><br>
 * 
 * <b>Responsibility:</b> 
 * <br>
 * This class is responsible for managing a collection of {@link DiaryEntry} objects.
 * It provides functionality for creating, storing and parsistence (saving/loading diary entries to/from JSON files).
 * 
 * <br><br>
 * 
 * DiaryEntryRegister acts as the single point of access for all diary entry related operations within the system. The register uses a {@link Gson} instance configured with a custom {@code DiaryEntryTypeAdapter}, which allowes {@link java.time.LocalDateTime} fields inside {@code DiaryEntry} objects to be correctly serialised and deserialised.
 * 
 * <br><br>
 * 
 * <b>Information stored and datatype choices:</b>
 * <br>
 * - {@code diaryEntries} ({@code ArrayList<DiaryEntry>}): A dynamic list for all existing diary entries. ArrayList was chosen because:
 * - When the application is first started, the number of diary entries is zero, but as the application is used the bumber of diary entries will expand.
 * - The entries needs to be iterated through for seraching.
 * - ArrayList provides access by index and efficient iteration with streams.
 * - Easy serialisation/deserialisation with the Gson libary
 * 
 * - {@code gson} ({@code Gson}): A Gson instance for JSON serialisation. Gson was chosen because: <br>
 *      - It handles complex object serialisation authomatically. <br>
 *      - It is a widely used and well documented libary for JSON in java. <br>
 *      - Supports generic types like ArrayList&lt;User&gt;
 * 
 * <br><br>
 * 
 * <b>Mutability assessment:</b> what should be set at creation vs changable
 * (TABLE)
 * 
 * <br><br>
 * 
 * <b>How does the class respond to invalid data?</b>
 * (INFO)
 * 
 */

public class DiaryEntryRegister {
    private ArrayList<DiaryEntry> diaryEntries;
    private final Gson gson;

    /**
     * Creates a new empty DiaryEntryRegister. Initialises an empty list of diary entries ({@code ArrayList}) and a custom Gson instance for JSON operations which allowes {@link java.time.LocalDateTime} fields inside {@code DiaryEntry} objects to be correctly serialised and deserialised.
     * 
     * Diary entries can be added via {@code newDiaryEntry()} or added from file via {@code readFromJSON()}
     */
    public DiaryEntryRegister(){
        this.diaryEntries = new ArrayList<>();

        // Sets up Gson to use DiaryEntryTypeAdapter
        this.gson = new GsonBuilder().registerTypeAdapter(DiaryEntry.class, new DiaryEntryTypeAdapter()).setPrettyPrinting().create();
    }

    /**
     * Returns the internal list of the diary entries.
     * 
     * @return a list containing all diary entries
     */
    public ArrayList<DiaryEntry> getDiaryEntriesList() {
        return this.diaryEntries;
    }

    /**
     * Retrieves a specific diary entry by index.
     * 
     * @param number the index of the desired entry
     * @returns the diary entry at the specified index
     * @throws IndexOutOfBounds
     */
    public DiaryEntry getDiaryEntryByIndex(int number) {
        return this.diaryEntries.get(number);
    }

    /**
     * Prints all diary entries to the console, togehter with their index.
     * 
     * <br><br>
     * 
     * The index corresponds directly to positions within the internal list.
     */
    public void showAllEntries() {
        System.out.println("This is all the existing diary entries:\n");
        for (DiaryEntry entries : this.getDiaryEntriesList()) {
            System.out.println(getDiaryEntriesList().indexOf(entries) + ". " + entries);
        }
    }

    /**
     * Method used to create a new diary entry and adding the new entry to the list {@code diaryEntries}
     * @param title the title of the new entry
     * @param author the author belonging to the user logged inn which is the author who writes the new entry
     * @param content the content of the new diary entry
     */
    public void addNewDiaryEntry(String title, Author author, String content) {
        DiaryEntry entry = new DiaryEntry(title, author, content);
        this.diaryEntries.add(entry);
    }

    /**
     * Searches for diary entries containing the specified keyword.
     * 
     * The search is case-insensitive and checks the title, content, 
     * author name, and date (formatted as dd.MM.yyyy).
     * Since it checks the title, content and date, it allowes for users to serch for words, authors or dates.
     * 
     * @param keyword the word or phrase to search for
     * @return a list of diary entries that contain the keyword
     */
    public ArrayList<DiaryEntry> searchByKeyword(String keyword) {
        ArrayList<DiaryEntry> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for (DiaryEntry entry : this.diaryEntries) {
            String title = entry.getTitle().toLowerCase();
            String content = entry.getContent().toLowerCase();
            String authorName = entry.getAuthor().getName().toLowerCase();
            String dateString = entry.getDate().format(formatter);

            if (title.contains(lowerKeyword) || 
                content.contains(lowerKeyword) || 
                authorName.contains(lowerKeyword) ||
                dateString.contains(lowerKeyword)) {
                results.add(entry);
            }
        }

        return results;
    }

    /**
     * Returns all diary entries created between two dates (inclusive).
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of diary entries within the specified date range
     */
    public ArrayList<DiaryEntry> getEntriesBetweenDates(LocalDate startDate, LocalDate endDate) {
        ArrayList<DiaryEntry> results = new ArrayList<>();

        for (DiaryEntry entry : this.diaryEntries) {
            LocalDate entryDate = entry.getDate().toLocalDate();

            if ((entryDate.isEqual(startDate) || entryDate.isAfter(startDate)) &&
                (entryDate.isEqual(endDate) || entryDate.isBefore(endDate))) {
                results.add(entry);
            }
        }

        return results;
    }

    /** I DONT ACCTUALLY NEED THIS ONE...(DO I WANT TO KEEP IT?)
     * Returns all diary entries created on a specific date.
     * 
     * @param date the date to search for
     * @return a list of diary entries created on the specified date
     */
    public ArrayList<DiaryEntry> getEntriesOnDate(LocalDate date) {
        ArrayList<DiaryEntry> results = new ArrayList<>();

        for (DiaryEntry entry : this.diaryEntries) {
            LocalDate entryDate = entry.getDate().toLocalDate();

            if (entryDate.isEqual(date)) {
                results.add(entry);
            }
        }

        return results;
    }

    /**
     * Method used to read information from json file
     */
    public void readFromJSON(){
        String filepath = "entries.json";

        try {
            if (Files.exists(Paths.get(filepath))) {
                ArrayList<DiaryEntry> entries = gson.fromJson(
                    new FileReader(filepath),
                    new TypeToken<ArrayList<DiaryEntry>>() {}.getType()
                );

                if (entries != null) {
                    this.diaryEntries.addAll(entries);
                }
            }
        } 
        
        catch (IOException e) {
            System.out.println("Could not read entries.json: " + e.getMessage());
        }
    }

    /**
     * method used to write to json
     */
    public void writeToJSON(){
        String filepath = "entries.json";

        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(this.diaryEntries, writer);
            System.out.println("\nDiary saved successfully!");
        } 
        
        catch (IOException e) {
            System.out.println("Could not save entries.json: " + e.getMessage());
        }
    }
}

