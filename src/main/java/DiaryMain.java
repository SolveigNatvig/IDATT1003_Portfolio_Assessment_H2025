import java.util.Scanner;

/**
 * The Diary application class.
 * 
 * <p><b>Role and Responsibility</b></p>
 * 
 * <p>The {@code DiaryMain} classes sole responsibility is to 
 * initialise the application and transfer control to the 
 * user interface layer. It follows the Single Responsibility Principle by only
 * handling application startup, delegating all actual functionality to
 * {@link DiaryUserInterface}. </p>
 * 
 * <b>Information Stored and Data Type Justification</b>
 * 
 * <p>Creates a {@code DiaryUserInterface} object using the DiaryUserInterface class.</p>
 * 
 * <b>Mutability Assessment</b>
 * 
 * <p>The {@code DiaryUserInterface} object is set in the constructor. 
 * This object is final, and will not be changed after creation </p>
 * 
 * <b>Invalid Data Handling Strategy</b>
 * 
 * <p>This class leaves all the handeling of invalid date to the {@code DiaryUserInterface}</p>
 * 
 * 
 * */
public class DiaryMain {
  private final DiaryUserInterface ui;

  /**
  * Private constructor which initialises the {@code DiaryUserInterface}.
  * 
  * <p>The constructor is private to ensure that the Diary Application 
  * will be started from this file. When this costructor is called it 
  * authomatically creates a new user interface object, which ensures the
  * loading of any existing users and diary entries from JSON </p>
  */
  private DiaryMain() {
    this.ui = new DiaryUserInterface();
  }

  /**
  * The main method that starts the Diary application.
  * 
  * <p>This method:</p>
  * <ol>
  *   <li>Creates a Scanner to read input</li>
  *   <li>Creates a new DiaryMain instance, initialising the application</li>
  *   <li>Calls the start() method from the user interface, which runs the program.</li>
  *   <li>Closes the Scanner</li>
  * </ol>
  *
  * @param args command-line arguments (not used)
  */
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    DiaryMain diary1 = new DiaryMain();
    diary1.ui.start(scan);
    scan.close();
  }
}
