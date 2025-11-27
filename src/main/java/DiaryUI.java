
import java.util.Scanner;

public class DiaryUI {
    private AuthorRegister authorRegister;
    private DiaryEntryRegister diaryEntryRegister;

    // Constructor 
    public DiaryUI() {
        this.authorRegister = new AuthorRegister();
        this.diaryEntryRegister = new DiaryEntryRegister();
        
        // Loads any saved entries from the json file
        init();
    }

    private DiaryEntry getDiaryEntry(int number) {
        return this.diaryEntryRegister.getDiaryEntriesList().get(number);
    }

    private void showAllEntries() {
        System.out.println("Dette er innleggene som finnes i dagboken:\n");
        for (DiaryEntry entries : this.diaryEntryRegister.getDiaryEntriesList()) {
            System.out.println(this.diaryEntryRegister.getDiaryEntriesList().indexOf(entries) + ". " + entries);
        }
    }

    // Asks the user for title, author and content. Then creates a new entry and saves it.
    private void newDiaryEntry(Scanner scan) {
        System.out.println("Title (date is added automaticly): ");
        String title = scan.nextLine();

        System.out.println("Author: "); 
        String name = scan.nextLine();
        Author author = this.authorRegister.getAuthorByName(name);
        author.addNumberOfEntries();

        System.out.println("Content: ");
        String content = scan.nextLine();

        DiaryEntry entry = new DiaryEntry(title, author, content);
        this.diaryEntryRegister.getDiaryEntriesList().add(entry); 
        this.save();
    }

    // Change content of a selected entry
    private void editEntryContent(int index, String newContent) {
        this.diaryEntryRegister.getDiaryEntriesList().get(index).setContent(newContent);
    }

    // Change title og a selected entry
    private void editEntryTitle(int index, String newTitle) {
        this.diaryEntryRegister.getDiaryEntriesList().get(index).setTitle(newTitle);
    }

    // Delete entry by index
    private void deleteEntry(int index) {
        this.diaryEntryRegister.getDiaryEntriesList().remove(index);
    }

    private void entrySearch(String searchWord){
        for (DiaryEntry entry: this.diaryEntryRegister.getDiaryEntriesList()){
            
        }

    }

    public void start(Scanner scan) {
        int option;

        while (true) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. New diary entry");
            System.out.println("2. Show all entries");
            System.out.println("3. Edit previous entry");
            System.out.println("4. Delete entry");
            System.out.println("5. Show Authors");
            System.out.println("6. Exit");
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

            else if (option == 5){
                System.out.println("Name : Entries");
                this.authorRegister.printAuthorList();
            }
            
            else if (option == 6) {
                break;
            }
        }
    }

    // This method reads potential existing entries from "entries.json" and loads them into memory
    private void init() {
       this.diaryEntryRegister.readFromJSON();
       this.authorRegister.readFromJSON();
    }

    // This method converts my ArrayList of entries (diaryEntries) into JSON and saves it to file
    private void save() {
       this.diaryEntryRegister.writeToJSON();
       this.authorRegister.writeToJSON();
    }
}
