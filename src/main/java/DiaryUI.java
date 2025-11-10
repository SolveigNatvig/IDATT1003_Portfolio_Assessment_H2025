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

public class DiaryUI {
    private ArrayList<DiaryEntry> diaryEntries;
    private final Gson gson;

    // Constructor 
    public DiaryUI() {
        this.diaryEntries = new ArrayList<>();

        // Sets up Gson to use DiaryEntryTypeAdapter
        this.gson = new GsonBuilder().registerTypeAdapter(DiaryEntry.class, new DiaryEntryTypeAdapter()).setPrettyPrinting().create();
        
        // Loads any saved entries from the json file
        init();
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

    // Asks the user for title, author and content. Then creates a new entry and saves it.
    private void newDiaryEntry(Scanner scan) {
        System.out.println("Title (date is added automaticly): ");
        String title = scan.nextLine();

        System.out.println("Author: ");
        String author = scan.nextLine();

        System.out.println("Content: ");
        String content = scan.nextLine();

        DiaryEntry entry = new DiaryEntry(title, author, content);
        this.diaryEntries.add(entry); // Do I NEED THIS LIST!!?
        this.save();
    }

    // Change content of a selected entry
    private void editEntryContent(int index, String newContent) {
        this.diaryEntries.get(index).setContent(newContent);
    }

    // Change title og a selected entry
    private void editEntryTitle(int index, String newTitle) {
        this.diaryEntries.get(index).setTitle(newTitle);
    }

    // Delete entry by index
    private void deleteEntry(int index) {
        this.diaryEntries.remove(index);
    }

    public void start(Scanner scan) {
        int option;

        while (true) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. New diary entry");
            System.out.println("2. Show all entries");
            System.out.println("3. Edit previous entry");
            System.out.println("4. Delete entry");
            System.out.println("5. Exit");
            System.out.println("Choose from the options above");

            option = scan.nextInt();
            scan.nextLine();

            if (option == 1) {
                this.newDiaryEntry(scan);
            } 
            
            else if (option == 2) {
                this.showAllEntries();
            } 
            
            else if (option == 3) {
                this.showAllEntries();

                System.out.println("What entry do you want to change?");
                int index = scan.nextInt();
                scan.nextLine();

                System.out.println("Choose what to change: \n 1. Title \n 2. Content");
                int change = scan.nextInt();
                scan.nextLine();

                if (change == 1) {
                    System.out.println("Please write the new title");
                    String newTitle = scan.nextLine();
                    this.editEntryTitle(index, newTitle);
                } else if (change == 2) {
                    System.out.println("Please write the new version of the entry");
                    String newContent = scan.nextLine();
                    this.editEntryContent(index, newContent);
                }

                this.save();
            } 
            
            else if (option == 4) {
                this.showAllEntries();

                System.out.println("Which entry do you want to delete?");
                int index = scan.nextInt();
                scan.nextLine();

                System.out.println("Are you sure you want to delete this entry? (y/n)\n \n" + this.getDiaryEntry(index));
                String confirmation = scan.nextLine();

                if (confirmation.equals("y")) {
                    this.deleteEntry(index);
                    this.save();
                }
            } 
            
            else if (option == 5) {
                break;
            }
        }
    }

    // This method reads potential existing entries from "entries.json" and loads them into memory
    private void init() {
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

    // This method converts my ArrayList of entries (diaryEntries) into JSON and saves it to file
    private void save() {
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

// Add the possibility to write multiple paragraphs within an entry.
// When an entry is edited it should add "Edited by User, time"
// Add a way to search. (f.eks all entries that contain the word midninght)
// Add a way to sort my date. It is sort of sorted already though
// Add a way to see all entries within the last week
// Add a way to see all entries by one author


// I have to start to use branches
// Positive and negative tests?
// I will also have to fully understand the DiaryEntryTypeAdapter.java in order to be able to explain the code and make smart changes. Do I understand this enough to implement it into my project files?
// Is this a maven setup?
