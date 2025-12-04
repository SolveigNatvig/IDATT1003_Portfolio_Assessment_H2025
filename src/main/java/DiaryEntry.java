import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entity class for diary entry
 * Represents an entry, consisting of a title, author, content and creation date
 * 
 * <br><br>
 * 
 * An {@code DiaryEntry} object stores a title, author, content and a date of creation. The {@code DiaryEntry} is created with a timestamp representing when the entry was written.
 * 
 * <br><br>
 * 
 * <b>Responsibility:</b><br>
 * This class holds information about the diary entry. 
 * It is used to get the title, author, content and date. And to set (change) the title and content.
 * 
 * <br><br>
 * 
 * <b>Information:</b><br>
 * This class stores:
 * - {@code title}: The title of the diary entry, stored as a {@code String}
 * - {@code author}: The author who has written the entry, stored as a {@code Author}
 * - {@code content}: The content of the diary entry, stored as a {@code String}
 * - {@code date}: The timestamp of when the diary entry was created, stored as LocalDateTime
 * 
 * <b>Methods:</b> The DiaryEntry class contains these following methods
 * DiaryEntry(String title, Author author, String content)
 * DiaryEntry(String title, Author author, String content, LocalDateTime date)
 * getTitle()
 * getAuthor()
 * getContent()
 * getDate()
 * setTitle()
 * setContent()
 * toString()
 */
public class DiaryEntry {
    private String title;
    private Author author;
    private String content;
    private final LocalDateTime date;

    /**
     * Creating a new diary entry using the current system time as the creation date
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
     * <p>
     * This constructor can be useful when loading diary entries from storage
     * where the original creation date must be preserved.
     * </p>
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
     * Returns the content of the diary entry
     * 
     * @return the content
     */

    public String getContent() {
        return this.content;
    }

    /**
     * Returns the date the diary entry was written
     * 
     * @return the publish date
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Updates the title of this diary entry
     * 
     * @param newTitle the new title to set
     */

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    /**
     * Updates the content of the diary entry
     * 
     * @param newContent the new content to set
     */
    public void setContent(String newContent) {
        this.content = newContent;
    }

    /**
     * Updates the {@code lastEdited} timestamp to the current system time
     * 
     * <p> This method should be called whenever the entry is modified, to keep
     * track of revisions over time. </p>
     */
    @Override 
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedDate = this.date.format(formatter);
        return String.format("\n%s, %s\n%s\nby %s\n", this.title, formattedDate, this.content, this.author.getName());
    }
}
