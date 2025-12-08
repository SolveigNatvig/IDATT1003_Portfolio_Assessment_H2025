package edu.ntnu.idi.bidata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Registry class for managing diary entries in the application.
 * 
 * <p><b>Role and Responsibility:</b></p>
 * 
 * <p>The {@code DiaryEntryRegister} class is responsible for managing a collection of all 
 * {@link DiaryEntry} objects while they exist in the application. It provides functionality 
 * for:</p>
 * <ul>
 * <li>Creating and storing new diary entries</li>
 * <li>Retrieving entries by index or through search operations</li>
 * <li>Searching entries by keyword, date, or date range</li>
 * <li>Saving/loading to/from JSON files</li>
 * <li>Displaying entries to the console</li>
 * </ul>
 * 
 * <p>This class acts as the single point of access for all diary 
 * entry-related operations within the system. 
 * The register uses a {@link Gson} instance configured with a custom 
 * {@code DiaryEntryTypeAdapter}, which allowes {@code LocalDateTime} 
 * fields inside {@code DiaryEntry} objects to be correctly serialised and deserialised.</p>
 * 
 * <b>Information Stored and Data Type Justification</b>
 * <ul>
 * <li><b>List of entries ({@code diaryEntries}):</b> An {@code ArrayList} containing 
 * {@link DiaryEntry} objects. The type ArrayList was chosen because:
 * <ul>
 * <li>The number of entries starts at zero and grows dynamically as users add entries</li>
 * <li>Entries need to be accessed by index for editing and deletion operations</li>
 * <li>Iteration is required for search operations using streams</li>
 * <li>Easy serialisation/deserialisation with the Gson library</li>
 * </ul>
 * </li>
 * <li>{@code gson} ({@code Gson}): A Gson instance for JSON serialisation. 
 * Gson was chosen because:
 * <ul>
 * <li>It handles complex object serialisation authomatically.</li>
 * <li>It is a widely used and well documented libary for JSON in java.</li>
 * </ul>
 * <li>But Gson does not automatically support {@code LocalDateTime}. 
 * Thus a custom type adapter was required.</li>
 * </ul>
 * 
 * <b>Mutability Assessment</b>
 * 
 * <p>The entry list and the Gson instance is created at creation of 
 * the {@link DiaryEntryRegister} object. The content of the list change, 
 * while the Gson instance is final</p>
 *
 * <b>Invalid Data Handling Strategy</b>
 * 
 * <p>This class uses graceful degradation for I/O errors, 
 * and exception propagation for invalid indices:</p>
 * <ul>
 * <li><b>Invalid index access:</b> The {@code getDiaryEntryByIndex} method
 * delegates to {@code ArrayList.get()}, which throws {@code IndexOutOfBoundsException}
 * for invalid indices. The calling code must handle this exception.</li>
 * <li><b>Null or blank search keywords:</b> The {@code searchByKeyword} method
 * returns an empty list if the keyword is null or blank, allowing the application
 * to continue without error.</li>
 * <li><b>File I/O errors:</b> Both {@code readFromJson} and {@code writeToJson}
 * catch {@code IOException} and print error messages to the console, allowing the
 * application to continue running. This graceful degradation prevents crashes from
 * file system issues.</li>
 * <li><b>Missing JSON file:</b> The {@code readFromJson} method checks if the
 * file exists before attempting to read, silently skipping if not found to support
 * first-time application runs.</li>
 * <li><b>Null entries from JSON:</b> If the JSON file contains null or invalid
 * data, the method checks for null before adding entries to prevent errors.</li>
 * </ul>
 */
public class DiaryEntryRegister {
  private final ArrayList<DiaryEntry> diaryEntries;
  private final Gson gson;

  /**
  * Creates a new empty DiaryEntryRegister. 
  * 
  * <p>Initialises an empty list of diary entries 
  * ({@code ArrayList}) and a custom Gson instance for JSON operations which allowes 
  * {@link java.time.LocalDateTime} fields inside {@code DiaryEntry} objects to be correctly 
  * serialised and deserialised.</p>
  * 
  * <p>Diary entries can be added via {@code newDiaryEntry()} or 
  * added from file via {@code readFromJSON()}</p>
  */
  public DiaryEntryRegister() {
    this.diaryEntries = new ArrayList<>();

    // Sets up Gson to use DiaryEntryTypeAdapter
    this.gson = new GsonBuilder().registerTypeAdapter(DiaryEntry.class, 
        new DiaryEntryTypeAdapter()).setPrettyPrinting().create();
  }

  /**
  * Returns the internal list of the diary entries.
  * 
  * @return a list {@code ArrayList} containing all diary entries
  */
  public ArrayList<DiaryEntry> getDiaryEntriesList() {
    return this.diaryEntries;
  }

  /**
  * Retrieves a specific diary entry by index.
  * 
  * @param number the index of the desired entry
  * @return the diary entry at the specified index
  * @throws IndexOutOfBoundsException if the index is invalid
  */
  public DiaryEntry getDiaryEntryByIndex(int number) {
    return this.diaryEntries.get(number - 1);
  }

  /**
  * Prints all diary entries to the console, togehter with their index number.
  *
  * <p>The index corresponds to positions within the internal list. 
  * Element 0 in the list it set to be called by the index-nubmer 1 
  * because it has the first position in the list.</p>
  * 
  * <p>If the list of entries is empty, the text "(No entries)" is displayed.</p>
  */
  public void showAllEntries() {
    System.out.println("This is all the existing diary entries:\n");

    var entries = this.getDiaryEntriesList();

    // Handle empty/null
    if (entries == null || entries.isEmpty()) {
      System.out.println("No entries");
      return;
    } 

    IntStream.range(0, entries.size()).mapToObj(i -> (i + 1) + ". " + entries.get(i))
    .forEach(System.out::println);
  }

  /**
  * Creates a new diary entry and adds the
  * new entry to the list {@code diaryEntries}.
  * 
  * @param title the title of the new entry
  * @param author the author who created the entry (typically the author belonging to the user 
  *     logged inn)
  * @param content the content of the new diary entry
  */
  public void addNewDiaryEntry(String title, Author author, String content) {
    DiaryEntry entry = new DiaryEntry(title, author, content);
    this.diaryEntries.add(entry);
  }

  /** 
  * Safely converts a string to lowercase, handling null values.
  * 
  * @param text the string to convert (may be null)
  * @return the lowercase string, or an empty string if input was null 
  */
  private static String safeLower(String text) {
    return text == null ? "" : text.toLowerCase();
  }

  /**
  * Searches for diary entries containing the specified keyword.
  * The search is case-insensitive and checks the title, content, 
  * author name, and date (formatted as dd.MM.yyyy).
  * Since it checks the title, content and date, it allowes for users to serch for words, 
  * authors or dates.
  * 
  * @param keyword the word or phrase to search for
  * @return a list of diary entries that contain the keyword
  */
  public ArrayList<DiaryEntry> searchByKeyword(String keyword) {
    String lowerKeyword = keyword.trim().toLowerCase();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        
    if (keyword == null || lowerKeyword.isEmpty()) {
      return new ArrayList<>();
    }

    return this.diaryEntries.stream().filter(entry -> Stream.of(
        safeLower(entry.getTitle()), 
        safeLower(entry.getContent()), 
        entry.getAuthor() != null ? safeLower(entry.getAuthor().getName()) : "", 
        entry.getDate() != null ? entry.getDate().format(formatter) : ""
        ).anyMatch(field -> field.contains(lowerKeyword)))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
  * Returns all diary entries created between two dates (inclusive).
  * 
  * @param startDate the start date of the range
  * @param endDate the end date of the range
  * @return a list of diary entries within the specified date range
  */
  public ArrayList<DiaryEntry> getEntriesBetweenDates(LocalDate startDate, LocalDate endDate) {
    return IntStream.range(0, this.diaryEntries.size())
    .mapToObj(i -> this.diaryEntries.get(i))
    .filter(entry -> {
      LocalDate entryDate = entry.getDate().toLocalDate();
      return !entryDate.isBefore(startDate) && !entryDate.isAfter(endDate);
    }).collect(Collectors.toCollection(ArrayList::new));   
  }

  /**
  * Returns all diary entries created on a specific date.
  * 
  * <p>This method calls {@code getEntriesBetweenDates} with the same date for 
  * both start and end.</p>
  * 
  * @param date the date to search for
  * @return a list of diary entries created on the specified date
  */
  public ArrayList<DiaryEntry> getEntriesOnDate(LocalDate date) {
    return getEntriesBetweenDates(date, date);
  }

  /**
  * Reads diary entries from the JSON file "entries.json" into the register.
  * 
  * <p>If the file does not exist, no action is taken. This allows for a fresh start.
  * If an I/O error occurs, an error message is printed but the application
  * continues with any previously loaded entries.</p>
  */
  public void readFromJson() {
    String filepath = "entries.json";

    try {
      if (Files.exists(Paths.get(filepath))) {
        ArrayList<DiaryEntry> entries = gson.fromJson(
          new FileReader(filepath),
          new TypeToken<ArrayList<DiaryEntry>>() {}.getType());

        if (entries != null) {
          this.diaryEntries.addAll(entries);
        }
      }
    } catch (IOException e) {
      System.out.println("Could not read entries.json: " + e.getMessage());
    }
  }

  /**
  * Writes all diary entries to the JSON file "entries.json".
  * 
  * <p>Overwrites any existing file content. If an I/O error occurs, an error
  * message is printed but the application continues running.</p>
  */
  public void writeToJson() {
    String filepath = "entries.json";

    try (FileWriter writer = new FileWriter(filepath)) {
      gson.toJson(this.diaryEntries, writer);
      //System.out.println("\nDiary saved successfully!");
    } catch (IOException e) {
      System.out.println("Could not save entries.json: " + e.getMessage());
    }
  }
}

