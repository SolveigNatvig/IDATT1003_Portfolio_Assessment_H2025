package mappe_2025_SolveigNatvig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class DiaryEntry{
    private String title; // The title of the diary entry
    private String author; // The author of the entry
    private String content; // The content of the diary entry
    private final LocalDateTime date; // The date the diary entry was written

    // Konstruktør
    public DiaryEntry(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = LocalDateTime.now(); // automatisk sett dato/tid
    }

    // Get methods (return) ------------------------------
    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getContent(){
        return this.content;
    }

    public LocalDateTime getDate(){
        return this.date;
    }

    // Set methods (no return) ------------------------------
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public void setContent(String newContent){ // Kanskje fikse sånn at man kan legge til innhold?
        this.content = newContent;
    }

    // Sørger for at ting blir returnert på en spessiell måte når man printer et objekt av klassen
    @Override 
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"); // Customize as you like
        String formattedDate = this.date.format(formatter);
        return String.format("\n%s, %s\n%s\nby %s\n", this.title, formattedDate, this.content, this.author);
    }
}