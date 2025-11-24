import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class DiaryEntry {
    private String title;
    private Author author;
    private String content;
    private final LocalDateTime date;
    private LocalDateTime lastEdited;

    public DiaryEntry(String title, Author author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = LocalDateTime.now();
        this.lastEdited = LocalDateTime.now(); // What do I think about this one? 
    }

    // Hvorfor denne !!!
    public DiaryEntry(String title, Author author, String content, LocalDateTime date) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public Author getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public LocalDateTime getLastEdited() {
        return this.lastEdited;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    // This method is supposed to be used for keeping track with when an entry was last revised
    public void setLastEdited() {
        this.lastEdited = LocalDateTime.now();
    }

    @Override 
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedDate = this.date.format(formatter);
        return String.format("\n%s, %s\n%s\nby %s\n", this.title, formattedDate, this.content, this.author);
    }
}
