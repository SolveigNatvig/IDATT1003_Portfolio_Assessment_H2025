import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The {@code DiaryUserInterface} class represents the text-based user interface of the diary
 * application.
 * 
 * <p><b>Role and Responsibility</b></p>
 * 
 * <p>This class is responsible for controlling program flow, handling all user input,
 * validating that input, and delegating requests to underlying registers such as
 * {@link UserRegister} and {@link DiaryEntryRegister}.</p>
 * 
 * <b>Key-Responsibilities:</b>
 * 
 * <p>This class acts as the bridge between the user and the core application logic. 
 * It is responsible for:</p>
 * <ul>
 * <li>User login and registration</li>
 * <li>Creating, editing, and deleting diary entries</li>
 * <li>Displaying menus and steering the application’s control flow</li>
 * <li>Validating all types of input (text, integers, dates)</li>
 * <li>Performing searches (by date, date range, keyword)</li>
 * <li>Ensuring correct access control based on the currently logged-in user</li>
 * <li>Loading and saving persistent data via JSON through the register classes</li>
 * </ul>
 * 
 * <b>Information Stored and Data Type Justification</b>
 * 
 * <ul>
 * <li><b>The registry of users ({@link UserRegister}):</b> Manages all user-related operations. 
 * Using a dedicated register class separates user management concerns from UI logic.</li>
 * <li><b>The reistry of diary entries ({@link DiaryEntryRegister}):</b> 
 * Manages all diary entry operations. By seperating the the management of the diary 
 * entries from the UI, it allows for a cleaner code organising.</li>
 * <li><b>The current user ({@code currentUser}):</b> Tracks the currently logged-in user by 
 * using a {@link User} reference. This allows for direct access to the users author object. 
 * If the currentUser holds the value null it indicates that 
 * no user is currently logged in.</li>
 * </ul>
 * 
 * <b>Mutability Assessment</b>
 * 
 * <p>The userRegister and the diaryEntryRegister is set at creation. 
 * These two are final and wont be changed. 
 * The currentUser is also set at creation to the value null. 
 * It will later be changed when a user logges in to the application.</p> 
 * 
 * <b>Invalid Data Handling Strategy</b>
 * 
 * <p>This class is the primarely line of defence against invalid input. 
 * It users input validation using retry loops</p>
 * 
 * <b>Inspiration</b>
 * 
 * <p>This class takes some inspiration from Tri Tac Le's repository 
 * NTNU-IDI/idatt1003-mappe-del-1-2024-TriTacLeidatt1003-mappe-del-1-2024-TriTacLe 
 * in the design of the two methods {@code runMainMenu(Scanner scan)} 
 * and {@code start(Scanner scan)}</p>
 */
public class DiaryUserInterface {
  private final UserRegister userRegister;
  private final DiaryEntryRegister diaryEntryRegister;

  private User currentUser;

  /**
  * Creates a new {@code DiaryUserInterface} instance.
  * 
  * <p>Initialises the user and diary entry registers, then loads any existing
  * data from JSON files.</p>
  */
  public DiaryUserInterface() {
    this.userRegister = new UserRegister();
    this.diaryEntryRegister = new DiaryEntryRegister();
        
    // Loads any saved entries and users from the json file
    init();
  }

  /**
  * A method for creating a new diary entry for the current logged in user.
  * 
  * <p>Asks for title and content, then creates the entry with the current
  * user's author. The author's entry count is incremented and data is saved. </p>
  * 
  * @param scan the scanner used to ask user for input
  */
  private void newDiaryEntry(Scanner scan) {
    if (this.currentUser == null) {
      System.out.println("\nYou must be logged in to create an entry.");
      return;
    }

    String title = validateNoBlankInput(scan, "Title (date is added automatically): ");
    String content = validateNoBlankInput(scan, "Content: ");

    // Get the author connected to the current user
    Author author = this.currentUser.getAuthor();
    author.modifyNumberOfEntries(1);

    this.diaryEntryRegister.addNewDiaryEntry(title, author, content);

    this.save();
    System.out.println("\nEntry saved for " + author.getName());

  }

  /**
  * A method for editing the content of the selected entry. 
  * 
  * <p>Only allows editing if the current user is the entry's author. </p>
  * 
  * @param index the index of the desired entry
  * @param newContent the changed content which is to take the old contens place
  */
  private void editEntryContent(int index, String newContent) {
    DiaryEntry entry = this.diaryEntryRegister.getDiaryEntriesList().get(index - 1);

    if (entry.getAuthor().getName().equals(this.currentUser.getAuthor().getName())) {
      entry.setContent(newContent);
      System.out.println("\nThe entry was successfully updated");
    } else {
      System.out.println("Y\nou're only authorised to change your own entries...");
    }
  }

  /**
  * Change title of a selected entry.
  * 
  * <p>Only allows editing if the current user is the entry's author. </p>
  * 
  * @param index the index of the desired entry
  * @param newTitle the changed title which is to take the old titles place
  */
  private void editEntryTitle(int index, String newTitle) {
    DiaryEntry entry = this.diaryEntryRegister.getDiaryEntriesList().get(index - 1);

    if (entry.getAuthor().getName().equals(this.currentUser.getAuthor().getName())) {
      entry.setTitle(newTitle);
      System.out.println("\nThe entry was successfully updated");
    } else {
      System.out.println("\nYou're only authorised to change your own entries...");
    }
  }

  /**
  * Delete entry by index.
  * 
  * <p>Only allows editing if the current user is the entry's author. </p>
  * 
  * @param index the index of the desired entry
  */
  private void deleteEntry(int index) {
    DiaryEntry entry = this.diaryEntryRegister.getDiaryEntriesList().get(index - 1);

    if (entry.getAuthor().getName().equals(this.currentUser.getAuthor().getName())) {
      this.diaryEntryRegister.getDiaryEntriesList().remove(index - 1);
      System.out.println("\nThe entry was successfully deleted");
    } else {
      System.out.println("\nYou're only authorised to delete your own entries...");
    }
  }

  /**
  * Checks if the given index is valid for the diary entries list.
  * 
  * @param index the index to validate
  * @return {@code true} if the index is valid and {@code false} otherwise
  */
  private boolean isValidEntryIndex(int index) {
    int size = this.diaryEntryRegister.getDiaryEntriesList().size();
    return (index) >= 1 && (index) <= size;
  }

  /**
  * Validates that the user enters a valid integer.
  * 
  * <p>Asks until a valid integer is typed in.</p>
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
        System.out.println("\nInput failed. Please enter a number from the menu");
        scan.nextLine();
      }
    }
  }

  /**
  * Validates that the string input from user is not blank.
  * 
  * <p>Asks until a valid integer is typed in.</p>
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
        System.out.println("\nInput cannot be empty. Try again");
      } else {
        return value;
      }
    }
  }

  /**
  * Validates that the user enters a date on the correct form.
  * 
  * <p>Asks until a valid integer is typed in.</p>
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
        System.out.println("\nInvalid input, try again");
      }
    }
  }

  /**
   * Displays the menu before you are logged in, and returns the users choice. 
   * 
   * @param scan the scanner with the users choice
   * @return one of the nombers from the menu (1, 2 or 3)
   * 
  */
  private int displayloginMenuAndGetChoice(Scanner scan) {
    System.out.println("\nWhat do you want to do?");
    System.out.println("1. Login");
    System.out.println("2. Register new user");
    System.out.println("3. Exit");
    System.out.println("Enter a number. Choose from the options above\n");

    return this.validateIntInput(scan);
  }

  /**
   * Displays the main menu of the application.
   */
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

  /**
   * Runs the main application menu loop.
   * 
   * <p>Continues until the user chooses to log out.</p>
   * 
   * @param scan the scanner with input from the user
   */
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
          System.out.println("\nName : Entries");
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
        default -> System.out.println("\nInvalid choice. Try again");
      }
    }
  }

  /**
   * Handles the login process.
   * 
   * <p>Asks for username and password, and validates. If the login succseeds 
   * the {@code currentUser} is set and it enters the program revealing the main menu. </p>
   * 
   * @param scan the scanner with input from the user
   */
  private void handleLogin(Scanner scan) {
    System.out.println("Username:");
    String username = scan.nextLine();

    System.out.println("Password:");
    String password = scan.nextLine();

    if (!this.userRegister.login(username, password)) {
      System.out.println("\nLogin failed");
      return;
    }

    System.out.println("\nLogin succseeded!");
    this.currentUser = this.userRegister.getByUsername(username);

    this.runMainMenu(scan);
  }

  /**
   * Handles new user registration.
   * 
   * <p>Asks for username and makes sure that the username typed in is unique. 
   * It will also ask for password, and display name. 
   * Then it creates the new user and saves to JSON.</p>
   * 
   * @param scan the scanner with input from the user
   */
  private void handleUserRegistration(Scanner scan) {
    System.out.println("\nYou have chosen to create a new user");
    String username;

    while (true) {
      username = validateNoBlankInput(scan, "Create a username:");

      if (this.userRegister.isUser(username)) {
        System.out.println("\nThis username is already in use, try another one");
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

  /**
   * Handles editing a diary entry.
   * 
   * <p>Shows all entries, then let you choose which entry to edit. 
   * Then asks if you would like to change title or content. 
   * Then it calls the methods {@code editDiaryContent(Scanner scan)} or 
   * {@code editDiaryTitle(Scanner scan)}</p>
   * 
   * @param scan the scanner with input from the user
   */
  private void handleEntryEdit(Scanner scan) {
    this.diaryEntryRegister.showAllEntries();
    System.out.println("\nWhat entry do you want to change?");
    int index = validateIntInput(scan);

    if (!isValidEntryIndex(index)) {
      System.out.println("\nInvalid entry number. Please choose a number from the list.");
      return;
    }
    
    System.out.println("\nYou have chosen to edit this entry:" 
        + this.diaryEntryRegister.getDiaryEntryByIndex(index));

    System.out.println("\nChoose what to change: \n 1. Title \n 2. Content");
    int change = validateIntInput(scan);
    
    if (change == 1) {
      String newTitle = validateNoBlankInput(scan, "\nPlease write the new title");
      this.editEntryTitle(index, newTitle);
    } else if (change == 2) {
      String newContent = validateNoBlankInput(scan, 
          "\nPlease write the new version of the entry");
      this.editEntryContent(index, newContent);
    }

    this.save();
  }

  /**
   * Handles the deletion of a diary entry.
   * 
   * <p>Shows all the diary entries, then asks which one you'll like to delete. 
   * Asks for confirmation and calls the method {@code deleteEntry(int index)}.
   * 
   * @param scan the scanner with input from the user
  */
  private void handleEntryDelete(Scanner scan) {
    this.diaryEntryRegister.showAllEntries();

    System.out.println("\nWhich entry do you want to delete?");
    int index = validateIntInput(scan);
  
    if (!this.isValidEntryIndex(index)) {
      System.out.println("\nInvalid entry number. Please choose a number from the list.");
      return;
    }

    System.out.println(
        "\nAre you sure you want to delete this entry? (y/n)\n\n"
          + this.diaryEntryRegister.getDiaryEntryByIndex(index));
    String confirmation = scan.nextLine();

    if (confirmation.equals("y")) {
      DiaryEntry entryToDelete = this.diaryEntryRegister.getDiaryEntryByIndex(index);
      entryToDelete.getAuthor().modifyNumberOfEntries(-1);
      this.deleteEntry(index);
      this.save();
    }
  }

  /**
   * Handles seaching for entries by searchword.
   * 
   * <p>Asks for a search term and displays all maching entries.</p>
   * 
   * @param scan the scanner with input from the user
  */
  private void handleSearchBySearchword(Scanner scan) {
    System.out.println("\nType a searchword: ");
    String keyword = scan.nextLine();
    ArrayList<DiaryEntry> results = this.diaryEntryRegister.searchByKeyword(keyword);

    for (DiaryEntry entry : results) {
      System.out.println(entry);
    }
  }
  
  /**
   * Handles searching for entries by date.
   * 
   * <p>Asks for a date and displays all entries on that date</p>
   * 
   * @param scan the scanner with input from the user
   */
  private void handleSearchByDate(Scanner scan) {
    System.out.println("\nYou have chosen to find the entries on date");
    LocalDate date = validateDateFormate(scan, "Please type in your date (YYYY-MM-DD)");

    ArrayList<DiaryEntry> results = this.diaryEntryRegister.getEntriesOnDate(date);

    for (DiaryEntry entry : results) {
      System.out.println(entry);
    }
  }

  /**
   * Handles searching for entries in between dates (inclusive).
   * 
   * <p>Asks for start and end dates and displays all entries in that range, 
   * including the entries on the start- and end date.</p>
   * 
   * @param scan the scanner with input from the user
   */
  private void handleSearchBetweenDates(Scanner scan) {
    System.out.println("\nYou have chosen to find the entries between two dates");
    LocalDate startDate = validateDateFormate(scan, "Start date (YYYY-MM-DD): ");
    LocalDate endDate = validateDateFormate(scan, "End date (YYYY-MM-DD): ");

    ArrayList<DiaryEntry> results =
        this.diaryEntryRegister.getEntriesBetweenDates(startDate, endDate);

    for (DiaryEntry entry : results) {
      System.out.println(entry);
    }
  }

  /**
   * Handles password for the current user.
   * 
   * <p>Asks for a new password, hashes it, and saves the new hashed password</p>
   * 
   * @param scan the scanner with input from the user
   */
  private void handlePasswordChange(Scanner scan) {
    System.out.println("\nYou have chosen to change your password");
    System.out.println("\nType in your new password");
    String newPassword = UserRegister.hash(scan.nextLine());
    this.currentUser.setNewPassword(newPassword);
    this.save();
  }

  /**
   * Starts the application and runns the main login menu loop.
   * 
   * <p>Continues until the user chooses to exit.</p>
   * 
   * @param scan the scanner with input from the user
   */
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
        default -> System.out.println("\nInvalid option. Try again");
      }
    }
  }

  /**
  * Initialises the application by loading data from JSON files.
  * 
  * <p>Reads existing diary entries and users from their respective JSON files.
  * If files don't exist, the application starts with empty registers.</p>
  */
  private void init() {
    this.diaryEntryRegister.readFromJson();
    this.userRegister.readFromJson();
  }

  /**
  * Saves all data to JSON files.
  * 
  * <p>Called after any data modification to ensure that the JSON files are up to date.</p>
  */
  private void save() {
    this.diaryEntryRegister.writeToJson();
    this.userRegister.writeToJson();
  }
}
