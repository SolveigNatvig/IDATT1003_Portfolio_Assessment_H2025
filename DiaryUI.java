package mappe_2025_SolveigNatvig;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;

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


    // Method for adding a new diary entry
    private void newDiaryEntry(){
        Scanner scan = new java.util.Scanner(System.in);

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

   
    //Redigere diaryEntry (bare for det du har skrevet)
    /*Hvordan redigere en spessefik?*/

    //søke opp (etter emne, dato, forfatter, ord)

    //slette innlegg

    //legge til ny

    //innlogging (forskjellige brukere)

    //

    public void start(){
        Scanner scan = new java.util.Scanner(System.in);
        int option;

        while (true){

            System.out.println("What do you want to do?");
            System.out.println("1. New diary entry");
            System.out.println("2. Show all entries");
            System.out.println("3. Edit previous entry");
            System.out.println("4. Exit");
            System.out.println("Choose from the options above");

            option = scan.nextInt();

            if (option == 1){
                this.newDiaryEntry();

            }

            else if (option == 2){
                System.out.println("Dette er innleggene som finnes i dagboken:\n");
                for(DiaryEntry entries : this.getDiaryEntriesList()){
                    System.out.println(getDiaryEntriesList().indexOf(entries) + ". " + entries);
                }
            }

            else if (option == 3){
                for(DiaryEntry entries : this.getDiaryEntriesList()){
                    System.out.println(getDiaryEntriesList().indexOf(entries) + ". " + entries);
                }

                System.out.println("What entry do you want to change?");
                int index = scan.nextInt();
                scan.nextLine();

                System.out.println("Please write the new version of the entry");
                String newContent = scan.nextLine();
                
                this.getDiaryEntry(index).setContent(newContent);

            }

            else if (option == 4){
                break;
            }

        };

        scan.close();
    
    }


    private void init(){

    }
}
