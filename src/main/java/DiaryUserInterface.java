import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This is the user interface for the diary application.
 */
public class DiaryUserInterface {
  private UserRegister userRegister;
  private DiaryEntryRegister diaryEntryRegister;

  private User currentUser;

  /**
  * Constructor.
  */
  public DiaryUserInterface() {
    this.userRegister = new UserRegister();
    this.diaryEntryRegister = new DiaryEntryRegister();
        
    // Loads any saved entries and users from the json file
    init();
  }

  /**
  * A method for creating a new diary entry.
  * 
  * @param scan the scanner used to ask user for input
  */
  private void newDiaryEntry(Scanner scan) {
    if (this.currentUser == null) {
      System.out.println("You must be logged in to create an entry.");
      return;
    }

    String title = validateNoBlankInput(scan, "Title (date is added automatically): ");
    String content = validateNoBlankInput(scan, "Content: ");

    // Get the author connected to the current user
    Author author = this.currentUser.getAuthor();
    author.modifyNumberOfEntries(1);

    this.diaryEntryRegister.addNewDiaryEntry(title, author, content);

    this.save();
    System.out.println("Entry saved for " + author.getName());

  }

  /**
  * A method for editing the content of the selected entry. 
  * (Only if you're logged inn as the correct user).
  * 
  * @param index the index of the desired entry
  * @param newContent the changed content which is to take the old contens place
  */
  private void editEntryContent(int index, String newContent) {
    DiaryEntry entry = this.diaryEntryRegister.getDiaryEntriesList().get(index);

    if (entry.getAuthor().getName().equals(this.currentUser.getAuthor().getName())) {
      entry.setContent(newContent);
      System.out.println("The entry was successfully updated");
    } else {
      System.out.println("You're only authorised to change your own entries...");
    }
  }

  /**
  * Change title og a selected entry (Only if you're logged inn as the correct user).
  * 
  * @param index the index of the desired entry
  * @param newTitle the changed title which is to take the old titles place
  */
  private void editEntryTitle(int index, String newTitle) {
    DiaryEntry entry = this.diaryEntryRegister.getDiaryEntriesList().get(index);

    if (entry.getAuthor().getName().equals(this.currentUser.getAuthor().getName())) {
      entry.setTitle(newTitle);
      System.out.println("The entry was successfully updated");
    } else {
      System.out.println("You're only authorised to change your own entries...");
    }
  }

  /**
  * Delete entry by index (Only if you're logged inn as the correct user).
  * 
  * @param index the index of the desired entry
  */
  private void deleteEntry(int index) {
    DiaryEntry entry = this.diaryEntryRegister.getDiaryEntriesList().get(index);

    if (entry.getAuthor().getName().equals(this.currentUser.getAuthor().getName())) {
      this.diaryEntryRegister.getDiaryEntriesList().remove(index);
      System.out.println("The entry was successfully deleted");
    } else {
      System.out.println("You're only authorised to delete your own entries...");
    }
  }

  /**
  * Method for making sure the integer input from user is in fact integer and not something else.
  * 
  * @param scan the scanner which takes input fromm user
  * @return the integer the user writes if it is in fact an integer
  */
  private int validateIntInput(Scanner scan) {
    while (true) {
      if (scan.hasNextInt()) {
        int value = scan.nextInt();
        scan.nextLine();
        return value;
      } else {
        System.out.println("Input failed. Please enter a number from the menu");
        scan.nextLine();
      }
    }
  }

  /**
  * Method to make sure the desired string input from user wont be blank.
  * 
  * @param scan the scanner whick takes input from the user
  * @param instruction a string of instructions of what the input represents
  * @return return the non blank string the user wrote
  */
  private String validateNoBlankInput(Scanner scan, String instruction) {
    while (true) {
      System.out.println(instruction);
      String value = scan.nextLine();

      if (value.trim().isBlank() || value.equals(null)) {
        System.out.println("Input cannot be empty. Try again");
      } else {
        return value;
      }
    }
  }

  /**
  * metod for making sure the date input is on the correct form.
  * 
  * @param scan the scanner used to take input from the user
  * @param instruction a string of instructions of what the input represents and its desired form
  * @return the date when the user writes on the correct form
  */
  private LocalDate validateDateFormate(Scanner scan, String instruction) {
    while (true) {
      System.out.println(instruction);
      String value = scan.nextLine().trim();

      try {
        return LocalDate.parse(value);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid input, try again");
      }
    }
  }

  /** Jacadoc. */
  private int displayloginMenuAndGetChoice(Scanner scan) {
    System.out.println("\nWhat do you want to do?");
    System.out.println("1. Login");
    System.out.println("2. Register new user");
    System.out.println("3. Exit");
    System.out.println("Enter a number. Choose from the options above\n");

    return this.validateIntInput(scan);
  }

  /** Jacadoc. */
  private void displayMainMenu() {
    System.out.println("\nWhat do you want to do?");
    System.out.println("1. New diary entry");
    System.out.println("2. Show all entries");
    System.out.println("3. Edit previous entry");
    System.out.println("4. Delete entry");
    System.out.println("5. Show Authors");
    System.out.println("6. Search in entries");
    System.out.println("7. Find entries on date");
    System.out.println("8. Find entries between dates");
    System.out.println("9. Change password");
    System.out.println("10. Log out");
    System.out.println("Enter a number. Choose from the options above\n");
  }

  /** Jacadoc. */
  private void runMainMenu(Scanner scan) {
    boolean loggedIn = true;

    while (loggedIn) {
      this.displayMainMenu();
      int option = validateIntInput(scan);

      switch (option) {
        case 1 -> newDiaryEntry(scan);
        case 2 -> this.diaryEntryRegister.showAllEntries();
        case 3 -> this.handleEntryEdit(scan);
        case 4 -> this.handleEntryDelete(scan);
        case 5 -> {
          System.out.println("Name : Entries");
          this.userRegister.printAuthorStatistics();
        }
        case 6 -> this.handleSearchBySearchword(scan);
        case 7 -> this.handleSearchByDate(scan);
        case 8 -> this.handleSearchBetweenDates(scan);
        case 9 -> this.handlePasswordChange(scan);
        case 10 -> {
          System.out.println("Logging out...");
          loggedIn = false;
        }
        default -> System.out.println("Invalid choice. Try again");
      }
    }
  }

  /** Javadoc. */
  private void handleLogin(Scanner scan) {
    System.out.println("Username:");
    String username = scan.nextLine();

    System.out.println("Password:");
    String password = scan.nextLine();

    if (!this.userRegister.login(username, password)) {
      System.out.println("Login failed");
      return;
    }

    System.out.println("Login succseeded!");
    this.currentUser = this.userRegister.getByUsername(username);

    this.runMainMenu(scan);
  }

  /** Jacadoc. */
  private void handleUserRegistration(Scanner scan) {
    System.out.println("You have chosen to create a new user");
    String username;

    while (true) {
      username = validateNoBlankInput(scan, "Create a username:");

      if (this.userRegister.isUser(username)) {
        System.out.println("This username is already in use, try another one");
        continue;
      } else {
        break;
      }
    }
    String password = validateNoBlankInput(scan, "Create a password:");
    String name = validateNoBlankInput(scan, "What's your name? ");

    this.userRegister.addNewUser(name, username, password);
    this.save();
  }

  /** Jacadoc. */
  private void handleEntryEdit(Scanner scan) {
    this.diaryEntryRegister.showAllEntries();
    System.out.println("What entry do you want to change?");
    int index = validateIntInput(scan);
    //scan.nextLine();
    
    System.out.println("You have chosen to edit this entry:" 
        + this.diaryEntryRegister.getDiaryEntryByIndex(index));

    System.out.println("Choose what to change: \n 1. Title \n 2. Content");
    int change = validateIntInput(scan);
    //scan.nextLine();
    
    if (change == 1) {
      String newTitle = validateNoBlankInput(scan, "Please write the new title");
      this.editEntryTitle(index, newTitle);
    } else if (change == 2) {
      String newContent = validateNoBlankInput(scan, 
          "Please write the new version of the entry");
      this.editEntryContent(index, newContent);
    }

    this.save();
  }

  /** Jacadoc. */
  private void handleEntryDelete(Scanner scan) {
    this.diaryEntryRegister.showAllEntries();

    System.out.println("Which entry do you want to delete?");
    int index = validateIntInput(scan);
    //scan.nextLine();

    System.out.println(
        "Are you sure you want to delete this entry? (y/n)\n\n"
          + this.diaryEntryRegister.getDiaryEntryByIndex(index));
    String confirmation = scan.nextLine();

    if (confirmation.equals("y")) {
      DiaryEntry entryToDelete = this.diaryEntryRegister.getDiaryEntryByIndex(index);
      entryToDelete.getAuthor().modifyNumberOfEntries(-1);
      this.deleteEntry(index);
      this.save();
    }
  }

  /** Jacadoc. */
  private void handleSearchBySearchword(Scanner scan) {
    System.out.println("Type a searchword: ");
    String keyword = scan.nextLine();
    ArrayList<DiaryEntry> results = this.diaryEntryRegister.searchByKeyword(keyword);

    for (DiaryEntry entry : results) {
      System.out.println(entry);
    }
  }
  
  /** Jacadoc. */
  private void handleSearchByDate(Scanner scan) {
    System.out.println("You have chosen to find the entries on date");
    LocalDate date = validateDateFormate(scan, "Please type in your date (YYYY-MM-DD)");

    ArrayList<DiaryEntry> results = this.diaryEntryRegister.getEntriesOnDate(date);

    for (DiaryEntry entry : results) {
      System.out.println(entry);
    }
  }

  /** Jacadoc. */
  private void handleSearchBetweenDates(Scanner scan) {
    System.out.println("You have chosen to find the entries between two dates");
    LocalDate startDate = validateDateFormate(scan, "Start date (YYYY-MM-DD): ");
    LocalDate endDate = validateDateFormate(scan, "End date (YYYY-MM-DD): ");

    ArrayList<DiaryEntry> results =
        this.diaryEntryRegister.getEntriesBetweenDates(startDate, endDate);

    for (DiaryEntry entry : results) {
      System.out.println(entry);
    }
  }

  /** Jacadoc. */
  private void handlePasswordChange(Scanner scan) {
    System.out.println("You have chosen to change your password");
    System.out.println("Type in your new password");
    String newPassword = UserRegister.hash(scan.nextLine());
    this.currentUser.setNewPassword(newPassword);
    this.save();
  }

  /** Jacadoc. */
  public void start(Scanner scan) {
    boolean applicationRun = true;

    while (applicationRun) {
      int choice = this.displayloginMenuAndGetChoice(scan);

      switch (choice) {
        case 1 -> {
          this.handleLogin(scan);
        }
        case 2 -> this.handleUserRegistration(scan);
        case 3 -> {
          System.out.println("Exiting the Diary Application...");
          applicationRun = false;
        }
        default -> System.out.println("Invalid option. Try again");
      }
    }
  }

  /**
  * This method reads potential existing entries from "entries.json" and loads them into memory.
  */
  private void init() {
    this.diaryEntryRegister.readFromJson();
    this.userRegister.readFromJson();
  }

  /**
  * This method converts my ArrayList of entries (diaryEntries) into JSON and saves it to file.
  */
  private void save() {
    this.diaryEntryRegister.writeToJson();
    this.userRegister.writeToJson();
  }
}
