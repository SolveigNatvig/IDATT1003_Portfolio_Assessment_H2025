package Mappevurdering_hovedprosjekt;

import java.time.LocalDateTime;

class DiaryEntry{
    private String title; // The title of the diary entry
    private String content; // The content of the diary entry
    private LocalDateTime date; // The date the diary entry was written

    // Konstruktør
    public DiaryEntry(String title, String content) {
    this.title = title;
    this.content = content;
    this.date = LocalDateTime.now(); // automatisk sett dato/tid
    }

    // Get methods (return)
    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public LocalDateTime getDate(){
        return this.date;
    }

    // Set methods (no return)
    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public void setContent(String newContent){ // Kanskje fikse sånn at man kan legge til innhold?
        this.content = newContent;
    }

    // Sørger for at ting blir returnert på en spessiell måte når man printer et objekt av kalassen
    @Override 
    public String toString() {
        return "Title: " + title + "\nDate: " + date + "\nContent: " + content;
    }

    public static void main(String[] args){
        DiaryEntry entry1 = new DiaryEntry("Hei :)", "I dag så jeg på Vaiana2 med Marion og Hanne i fellesområdet. Samtidig har jeg jobbet med mappevurderingen i programmering 1. Senere i dag skal jeg vaske klær og se på The Witcher mer kollektivet.");
        System.out.println(entry1);
        System.out.println("Dette er en test");
    }
}