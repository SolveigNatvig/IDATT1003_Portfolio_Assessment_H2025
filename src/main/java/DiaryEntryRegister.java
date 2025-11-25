import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
    private ArrayList<DiaryEntry> getDiaryEntriesList() {
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
}

