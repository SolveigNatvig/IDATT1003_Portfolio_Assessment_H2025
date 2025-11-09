package mappe_2025_solveignatvig;

import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;



public class DiaryUI {
    //private DiaryEntry diaryEntry;
    private ArrayList<DiaryEntry> diaryEntries;

    public DiaryUI(){
        this.diaryEntries = new ArrayList<>();
    }

    // Get-Method
    private ArrayList<DiaryEntry> getDiaryEntriesList(){
        return this.diaryEntries;
    }

    private DiaryEntry getDiaryEntry(int number){
        return this.diaryEntries.get(number);
    }

    private void showAllEntries(){
        System.out.println("Dette er innleggene som finnes i dagboken:\n");
        for(DiaryEntry entries : this.getDiaryEntriesList()){
            System.out.println(getDiaryEntriesList().indexOf(entries) + ". " + entries);
        }
    }


    // Method for adding a new diary entry
    private void newDiaryEntry(Scanner scan){
        System.out.println("Title (date is added automaticly): ");
        String title = scan.nextLine();

        System.out.println("Author: ");
        String author = scan.nextLine();

        System.out.println("Content: ");
        String content = scan.nextLine();

        DiaryEntry entry = new DiaryEntry(title, author, content);
        this.diaryEntries.add(entry);
        //scan.close();
    }

    private void editEntryContent(int index, String newContent){
        this.diaryEntries.get(index).setContent(newContent);
    }

    private void editEntryTitle(int index, String newTitle){
        this.diaryEntries.get(index).setTitle(newTitle);
    }

    private void deleteEntry(int index){
        this.diaryEntries.remove(index);
    }

    public void start(Scanner scan){
        int option;

        while (true){

            System.out.println("What do you want to do?");
            System.out.println("1. New diary entry");
            System.out.println("2. Show all entries");
            System.out.println("3. Edit previous entry");
            System.out.println("4. Delete entry");
            System.out.println("5. Exit");
            System.out.println("Choose from the options above");

            option = scan.nextInt();
            scan.nextLine();

            if (option == 1){
                this.newDiaryEntry(scan);
            }

            else if (option == 2){
                this.showAllEntries();
            }

            else if (option == 3){
                this.showAllEntries();

                System.out.println("What entry do you want to change?");
                int index = scan.nextInt();
                scan.nextLine();

                System.out.println("Choose what to change: \n 1. Title \n 2. Content");
                int change = scan.nextInt();
                scan.nextLine();

                if (change == 1){
                    System.out.println("Please write the new title");
                    String newTitle = scan.nextLine();

                    this.editEntryTitle(index, newTitle);
                }

                else if (change == 2){
                    System.out.println("Please write the new version of the entry");
                    String newContent = scan.nextLine();

                    this.editEntryContent(index, newContent);
                }
            }

            else if (option == 4){
                this.showAllEntries();

                System.out.println("Which entry do you want to delete?");
                int index = scan.nextInt();

                System.out.println("Are you sure you want to delete this entry? (y/n)\n \n" + this.getDiaryEntry(index));
                String confirmation = scan.nextLine();

                if (confirmation.equals("y")){
                    this.deleteEntry(index);
                }
            }

            else if (option == 5){
                break;
            }

        };
    }


    private void init(){

    }
}
