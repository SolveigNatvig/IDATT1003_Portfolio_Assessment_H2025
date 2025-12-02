
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class DiaryUI {
    private AuthorRegister authorRegister;
    private UserRegister userRegister;
    private DiaryEntryRegister diaryEntryRegister;

    private User currentUser;

    // Constructor 
    public DiaryUI() {
        this.authorRegister = new AuthorRegister();
        this.userRegister = new UserRegister();
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

    private void newDiaryEntry(Scanner scan) {
        if (this.currentUser == null) {
            System.out.println("You must be logged in to create an entry.");
            return;
        }

        System.out.println("Title (date is added automatically): ");
        String title = scan.nextLine();

        // Get the author connected to the current user
        Author author = this.currentUser.getAuthor();
        author.modifyNumberOfEntries(1);

        System.out.println("Content: ");
        String content = scan.nextLine();

        DiaryEntry entry = new DiaryEntry(title, author, content);
        this.diaryEntryRegister.getDiaryEntriesList().add(entry);

        this.save();
        System.out.println("Entry saved for " + author.getName() + " (" + this.currentUser.getUsername() + ")");

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

    public void start(Scanner scan) {
        int option;

        while (true) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. Login");
            System.out.println("2. Register new user");
            System.out.println("3. Exit");

            option = scan.nextInt();
            scan.nextLine();

            if (option == 1) {
                System.out.println("Username:");
                String username = scan.nextLine();

                System.out.println("Password:");
                String password = scan.nextLine();

                if (this.userRegister.login(username, password)){
                    System.out.println("Login succseeded!");
                    this.currentUser = this.userRegister.getByUsername(username);


                    while (true) {
                        System.out.println("\nWhat do you want to do?");
                        System.out.println("1. New diary entry");
                        System.out.println("2. Show all entries");
                        System.out.println("3. Edit previous entry");
                        System.out.println("4. Delete entry");
                        System.out.println("5. Show Authors");
                        System.out.println("6. Search in entries");
                        System.out.println("7. Find entries between dates");
                        System.out.println("8. Find entries on date");
                        System.out.println("9. Change password");
                        System.out.println("10. Log out");
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
                                DiaryEntry entryToDelete = this.getDiaryEntry(index);
                                entryToDelete.getAuthor().modifyNumberOfEntries(-1);
                                this.deleteEntry(index);
                                this.save();
                            }
                        }

                        else if (option == 5){
                            System.out.println("Name : Entries");
                            this.userRegister.printAuthorList();
                        }

                        else if (option == 6) {
                            System.out.println("Type a seachword: ");
                            String keyword = scan.nextLine();
                            ArrayList<DiaryEntry> results = this.diaryEntryRegister.searchByKeyword(keyword);
                            
                            for (DiaryEntry entry : results){
                                System.out.println(entry);
                            }
                        }

                        else if (option == 7) {
                            System.out.println("You have choosen to find the entries between two dates");
                            System.out.println("Start date (YYYY-MM-DD): ");
                            LocalDate startDate = LocalDate.parse(scan.nextLine());

                            System.out.println("End date (YYYY-MM-DD): ");
                            LocalDate endDate = LocalDate.parse(scan.nextLine());

                            ArrayList<DiaryEntry> results = this.diaryEntryRegister.getEntriesBetweenDates(startDate, endDate);
                            
                            for (DiaryEntry entry : results){
                                System.out.println(entry);
                            }
                        }

                        else if (option == 8) {
                            System.out.println("You have choosen to find the entries on date");
                            System.out.println("Please type in your date (YYYY-MM-DD)");
                            LocalDate date = LocalDate.parse(scan.nextLine());

                            ArrayList<DiaryEntry> results = this.diaryEntryRegister.getEntriesOnDate(date);
                            
                            for (DiaryEntry entry : results){
                                System.out.println(entry);
                            }
                        }

                        else if (option == 9) {
                            System.out.println("You have chosen to change your password");
                            System.out.println("Type in your new password");
                            String newPassword = UserRegister.hash(scan.nextLine());
                            this.currentUser.setNewPassword(newPassword);
                            this.save();
                        }
                        
                        else if (option == 10) {
                            System.out.println("Logging out...");
                            break;
                        }
                    }
                }

                else{
                    System.out.println("Login failed"); 
                }
            }

            else if (option == 2) {
                System.out.println("You have choosen to create a new user");
                String username;

                while (true) {
                    System.out.println("Create a username:");
                    username = scan.nextLine();

                    if (this.userRegister.isUser(username)){
                        System.out.println("This username is already in use, try another one");
                        continue;
                    }
                    else{
                        break;
                    }
                }

                System.out.println("Create a password:");
                String password = scan.nextLine();

                System.out.println("Whats your name? ");
                String name = scan.nextLine();

                this.userRegister.addNewUser(name, username, password);
                this.save();
            }

            else if (option == 3) {
                break; 
            }
        }
    }

    // This method reads potential existing entries from "entries.json" and loads them into memory
    private void init() {
       this.diaryEntryRegister.readFromJSON();
       this.userRegister.readFromJSON();
    }

    // This method converts my ArrayList of entries (diaryEntries) into JSON and saves it to file
    private void save() {
       this.diaryEntryRegister.writeToJSON();
       this.userRegister.writeToJSON();
    }
}
