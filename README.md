[//]: # (TODO: Fill in your name and student ID)

STUDENT NAME = "Solveig Natvig"  
STUDENT ID = 139852  

## Project description
 
This is a console-based diary application developed in Java as part of the course **IDATT1003 – Programming 1** at NTNU (autumn 2025). Users can register, log in, and create, edit, search for, and delete personal diary entries. Data is persisted to JSON using dedicated registers, and the application emphasises secure password handling through hashing.  

## Project structure  

The project is structured as follows:  

- **Source code:** Stored in the `src/main/java/edu/ntnu/idi/bidata` directory using Maven.  
- **Packages:**  
  - `entities`: Contains the core entity classes `DiaryEntry`, `Author`, `User`, and `DiaryEntryTypeAdapter` (for JSON serialisation).  
  - `registers`: Contains `DiaryEntryRegister` and `UserRegister`, which manage collections of diary entries and user accounts.  
  - `ui`: Contains `DiaryUserInterface`, which handles all console-based interactions, and `DiaryMain`, the main entry point for the programme.  
- **Tests:** All JUnit test classes are stored in `src/test/java`. Tests verify correct functionality of registers, entities, and user actions.  

## Link to repository 

[GitHub Repository Link](https://github.com/NTNU-IDI/mappe-2025-SolveigNatvig.git)  


## How to run the project

To run the diary application, ensure you have **Java 21** and **Maven** installed. The application is run from the main class "DiaryMain" with the method start(). Press the play button or run this in the terminal: 
mvn compile exec:java -Dexec.mainClass="DiaryMain"

When the program start you will see a log in menu, once logged in you will get access to the entire program.

# How to run the tests

To run the tests, press the play button on the left side inside the test class.

## References
The class DiaryUserInterface takes inspiration from [Tri Tac Lee](NTNU-IDI/idatt1003-mappe-del-1-2024-TriTacLeidatt1003-mappe-del-1-2024-TriTacLe) 

