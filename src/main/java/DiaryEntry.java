import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single diary entry, consisting of a title, author, content,
 * creation date, the date of the last revision
 * 
 * <p>A {@code DiaryEntry} is created with a timestamp representing when the entry
 * was written. If the content or title is later changed, the {@link #setLastEdited()}
 * method should be called to update the {@code lastEdited} timestamp.</p>
 */
class DiaryEntry {
    private String title;
    private Author author;
    private String content;
    private final LocalDateTime date;
    private LocalDateTime lastEdited;

    /**
     * Creating a new diary entry using the current system time as both the creation date,
     * and the last edited timestamp
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
        this.lastEdited = LocalDateTime.now(); // What do I think about this one? 
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
     * Returns the date the diary entry was last edited
     * 
     * @return the last edited date
     */
    public LocalDateTime getLastEdited() {
        return this.lastEdited;
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

    // This method is supposed to be used for keeping track with when an entry was last revised
    /**
     * Updates the {@code lastEdited} timestamp to the current system time.
     * <p>
     * This method should be called whenever the entry is modified, to keep
     * track of revisions over time.
     * </p>
     */
    public void setLastEdited() {
        this.lastEdited = LocalDateTime.now();
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
        return String.format("\n%s, %s\n%s\nby %s\n", this.title, formattedDate, this.content, this.author);
    }
}
