import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class DiaryEntry {
    private String title;
    private String author;
    private String content;
    private final LocalDateTime date;

    public DiaryEntry(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = LocalDateTime.now();
    }

    public DiaryEntry(String title, String author, String content, LocalDateTime date) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    @Override 
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedDate = this.date.format(formatter);
        return String.format("\n%s, %s\n%s\nby %s\n", this.title, formattedDate, this.content, this.author);
    }
}
