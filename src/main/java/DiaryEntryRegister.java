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

class DiaryEntryRegister {
    private ArrayList<DiaryEntry> diaryEntries;
    private final Gson gson;

    public DiaryEntryRegister(){
        this.diaryEntries = new ArrayList<>();

        // Sets up Gson to use DiaryEntryTypeAdapter
        this.gson = new GsonBuilder().registerTypeAdapter(DiaryEntry.class, new DiaryEntryTypeAdapter()).setPrettyPrinting().create();
    }

    private ArrayList<DiaryEntry> getDiaryEntriesList() {
        return this.diaryEntries;
    }

    private DiaryEntry getDiaryEntry(int number) {
        return this.diaryEntries.get(number);
    }

    private void showAllEntries() {
        System.out.println("Dette er innleggene som finnes i dagboken:\n");
        for (DiaryEntry entries : this.getDiaryEntriesList()) {
            System.out.println(getDiaryEntriesList().indexOf(entries) + ". " + entries);
        }
    }

    // Method which creates a new diary entry
    private DiaryEntry newDiaryEntry(String title, Author author, String content){
        DiaryEntry entry = new DiaryEntry(title, author, content);
        return entry;
    }

    private void addDiaryEntryToList(DiaryEntry entry){
        this.diaryEntries.add(entry);
    }

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

