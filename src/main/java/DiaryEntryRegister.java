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
 * Manages a collection of {@link DiaryEntry} objects and provides functionality
 * for creating, storing, and serializing diary entries to a JSON file
 * 
 * <p> The register uses a {@link Gson} instance configured with a custom
 * {@code DiaryEntryTypeAdapter}, which allowes {@link java.time.LocalDateTime}
 * fields inside {@code DiaryEntry} objects to be correctly serialised and deserialised. </p>
 */

class DiaryEntryRegister {
    private ArrayList<DiaryEntry> diaryEntries;
    private final Gson gson;

    /**
     * Creates a new {@code DiaryEntryRegister} with an empty collection
     * 
     * <p> A {@link Gson} instance is initialised with a custom type adapter in 
     * order to correctly handle {@link java.time.LocalDateTime} when entries are written to JSON. </p>
     */
    public DiaryEntryRegister(){
        this.diaryEntries = new ArrayList<>();

        // Sets up Gson to use DiaryEntryTypeAdapter
        this.gson = new GsonBuilder().registerTypeAdapter(DiaryEntry.class, new DiaryEntryTypeAdapter()).setPrettyPrinting().create();
    }

    /**
     * Returns the internal list of the diary entries.
     * 
     * <p> This method is private because the method is not intended to be 
     * exposed externally. </p>
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
    private DiaryEntry getDiaryEntry(int number) {
        return this.diaryEntries.get(number);
    }

    /**
     * Prints all diary entries to the console, togehter with their index.
     * 
     * <p> The index corresponds directly to positions within the internal list. </p>
     */
    private void showAllEntries() {
        System.out.println("Dette er innleggene som finnes i dagboken:\n");
        for (DiaryEntry entries : this.getDiaryEntriesList()) {
            System.out.println(getDiaryEntriesList().indexOf(entries) + ". " + entries);
        }
    }

    /**
     * Creates a new {@link DiaryEntry} without adding it to the register.
     * 
     * @param title the title of the diary entry
     * @param author the author who wrote the entry
     * @param content the content of the entry
     * @return a newly created diary entry
     */
    private DiaryEntry newDiaryEntry(String title, Author author, String content){
        DiaryEntry entry = new DiaryEntry(title, author, content);
        return entry;
    }

    /**
     * Adds a given {@code DiaryEntry} to the internal list.
     * 
     * @param entry the diary entry to add
     */
    private void addDiaryEntryToList(DiaryEntry entry){
        this.diaryEntries.add(entry);
    }




    

    /**
     * Searches for diary entries containing the specified keyword.
     * 
     * <p>The search is case-insensitive and checks the title, content, 
     * author name, and date (formatted as dd.MM.yyyy).</p>
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

    /**
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
     * Saves all dary entries to a JSON file named {@code entries.json}
     * 
     * <p> The method uses the configured {@link Gson} instance to serialise
     * the list of entries. The output file is overwritten each time the method is invoked. </p>
     * 
     * <p> If an I/O error occurs, an error message is printed instead of throwing an exception, allowing 
     * normal programme flow to continue. </p>
     */
    private void saveToJSON(){
        String filepath = "entries.json";

        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(this.diaryEntries, writer);
            System.out.println("\nDiary saved successfully!");
        } 
        
        catch (IOException e) {
            System.out.println("Could not save entries.json: " + e.getMessage());
        }
    }

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

