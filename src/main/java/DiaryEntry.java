import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entity class representing a single diary entry in the application.
 * 
 * <p><b>Role and Responsibility</b></p>
 * 
 * <p>The {@code DiaryEntry} class stores all information related to a single diary entry.
 * It represents the core content that users create and interact with in the diary application.
 * Each entry has a title and content, created by an author at a specific point in time, with the
 * ability to later edit the title and content.</p>
 * 
 * <b>Information Stored and Data Type Justification</b>
 * <uL>
 * <li><b>Title ({@code title}):</b> The title of the diary entry, stored as a {@code String}. 
 * The data type string provides flexibility in lenght.</li>
 * <li><b>Author ({@code author}):</b> The author who has written the entry, 
 * stored as an {@code Author} object. Using a reference to an {@code Author} object 
 * (rather than just a name string) enables linking entries to author statistics 
 * and supports future extensions. It also maintains the relationship 
 * between entries and their creators.</li>
 * <li><b>Content ({@code content}):</b> The content of the diary entry, 
 * stored as a {@code String}. As diary content is text in a variaty of lengths, 
 * and a {@code String} can handle anything from a single sentence to lengthy entries.</li>
 * <li><b>Date ({@code date}):</b> The timestamp of when the diary entry was created, 
 * stored as {@code LocalDateTime}. LocalDateTime provides a precice date and time.</li>
 * </ul>
 * 
 * <b>Mutability Assessment</b>
 * 
 * <p>Both title, content, author and date is set at creation. 
 * The title and content is typed in by the user, 
 * while the author is set to the author connected to the user logged in at that moment. 
 * The time is set to the time of creation.
 * The title and content can later be changed, but the author and date are 
 * immutable and final.</p>
 * 
 * <b>Invalid Data Handling Strategy</b>
 * 
 * <p>This class follows a permissive/accepting strategy for invalid data:</p>
 * 
 * <ul>
 * <li><b>Null or blank values:</b> The constructors accept any values for 
 * title, author, content, and date without validation. This allows flexibility
 * for JSON deserialisation where fields may be reconstructed incrementally.
 * The calling code (e.g., {@link DiaryUserInterface}) is responsible for
 * validating user input before creating entries.</li>
 * <li><b>Null author:</b> If a null author is passed, subsequent calls
 * to {@code getAuthor().getName()} will throw a {@code NullPointerException}.
 * The design assumes the calling code provides valid author references.</li>
 * <li><b>Null date:</b> The second constructor allows a null date, which
 * would cause issues in {@code toString()} and date-based searches. This is
 * accepted to support flexible deserialisation with JSON.</li>
 * </ul>
 */
public class DiaryEntry {
  private String title;
  private final Author author;
  private String content;
  private final LocalDateTime date;

  /**
  * Creates a new diary entry using the current date and time as the creation date.
  * 
  * <p>This constructor is used when creating new entries through the user interface,
  * automatically setting the date and time of creation.</p>
  * 
  * @param title the title of the diary entry
  * @param author the author who wrote the entry
  * @param content the text content of the diary entry
  */
  public DiaryEntry(String title, Author author, String content) {
    this.title = title;
    this.author = author;
    this.content = content;
    this.date = LocalDateTime.now();
  }

  /**
  * Creates a new diary entry with a specified date.
  * 
  * <p>This constructor is used when loading diary entries from storage (in JSON)
  * where the original creation date must be preserved.</p>
  *
  * @param title   the title of the diary entry
  * @param author  the author who wrote the entry
  * @param content the text content of the diary entry
  * @param date    the creation date of the diary entry
  */
  public DiaryEntry(String title, Author author, String content, LocalDateTime date) {
    this.title = title;
    this.author = author;
    this.content = content;
    this.date = date;
  }

  /**
  * Returns the title of this diary entry.
  *
  * @return the title
  */
  public String getTitle() {
    return this.title;
  }

  /**
  * Returns the author of this diary entry.
  *
  * @return the author
  */
  public Author getAuthor() {
    return this.author;
  }

  /**
  * Returns the content of this diary entry.
  * 
  * @return the content
  */
  public String getContent() {
    return this.content;
  }

  /**
  * Returns the date and time the diary entry was written (created).
  * 
  * @return the publish date and time
  */
  public LocalDateTime getDate() {
    return this.date;
  }

  /**
  * Updates the title of this diary entry.
  * 
  * <p>No validation is performed. The code calling this method should ensure the new title
  * is not blank before calling this method.</p>
  * 
  * @param newTitle the new title to set
  */
  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  /**
  * Updates the content of the diary entry.
  * 
  * <p>No validation is performed. The code calling this method should ensure the new content
  * is not blank before calling this method.</p>
  * 
  * @param newContent the new content to set
  */
  public void setContent(String newContent) {
    this.content = newContent;
  }

  /**
  * Returns a formatted string representation of the diary entry.
  * 
  * <p>The format includes the title, date (dd.MM.yyyy HH:mm),
  * content, and author name, suitable for display in the console interface.</p>
  * 
  * @return a formatted string containing all details of the diary entry
  */
  @Override 
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    String formattedDate = this.date.format(formatter);
    return String.format("\n%s, %s\n%s\nby %s\n", this.title,
     formattedDate, this.content, this.author.getName());
  }
}
