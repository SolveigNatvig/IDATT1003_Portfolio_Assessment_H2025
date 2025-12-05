# Portfolio Project – IDATT1003 – Programming 1
## Student Name: Solveig Natvig
This is the main portfolio project in the course ‘IDATT1003 Programming 1’ at NTNU — the Norwegian University of Science and Technology autumn 2025. The assignment were to create a console-based diary application using Java. 

## Pjoject Description
This is a simple console-based diary application where users can register, log in, and create, edit, search, and delete personal diary entries. Data is persisted to JSON via the applications registers. The project was develloped in Java, using Maven in Visual Studio Code.

## Features
- **User Accounts:** Register new users and log in/out. Passwords are hashed before being stored.
- **Create entres:** Add a diary entry consisting of *title* and *content* (date is auto-added by the system).
- **List Entries:** Show all saved entries in the console.
- **Edit entries:** Change the *title* or *content* of an existing entry (only by the original author).
- **Delete entries:** Remove an entry after confirmation (only by the original author).
- **Search:** Find entries containing a keyword.
- **Filter by date:**
  - Entries **between two dates** (YYYY-MM-DD)
  - Entries **on a specific date** (YYYY-MM-DD)
- **Author stats:** Print author name with number of entries.
- **Change Password:** Once logged in you have the option to change your password.
- **Persistence:** Entries and users are loaded/saved from/to JSON via the registers at startup and after changes.

## Program Flow
When you first open the program, you will see a question "What do you want to do?" and the list of options below:

1. Login
2. Register new user
3. Exit

Once you have logged in, the list of options will change:

1. New diary entry
2. Show all entries
3. Edit previous entry
4. Delete entry
5. Show Authors
6. Search in entries
7. Find entries between dates
8. Find entries on date
9. Change password
10. Log out

Each of the options triggers the matching action, described in **Features**. Input is validated for integers, non-empty text, and date format (`YYYY-MM-DD`).

## Class Overview
The application three entity classes: DiaryEntry, Author and User.
- **DiaryEntry:** "title", "content", "author", and an auto-managed "date" (added on creation). Responsible for representing each entry in the diary application.
- **Author:** "name", and a count of how many existing entries the author has written. Responsible for representing each author.
- **User:** "author", "username" and "password". The password is hashed beforehand. Responsible for representing each user.

The application also have two regriste:
- **Registers**: `UserRegister` and `DiaryEntryRegister` handle collections and JSON persistence (`readFromJSON`, `writeToJSON`).

**Note:** The user interface (UI) handles most of the verification of valid input, including checks for integers, non-empty text, and correct date format.







