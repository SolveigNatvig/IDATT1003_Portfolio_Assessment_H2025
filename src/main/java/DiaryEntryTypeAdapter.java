import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
* A custom {@code TypeAdapter} for serialising and deserialising 
* {@link DiaryEntry} objects using Gson.
*
* <p><b>Role and Responsibility</b></p>
*
* <p>The DiaryEntryTypeAdapter is required bacause {@code LocalDateTime}
*  is not natively supported by Gson, and because {@link Author} 
* must be explicitly written and reconstructed. 
* The adapter writes each {@link DiaryEntry} to a JSON file as an object and reconstructs it by 
* reading those fields back in the same format, ensuring that entries can be reliably persisted 
* and restored across application sessions.</p>
*
* <b>Information Stored and Data Type Justification</b>
*
* <p>Thos class serialises four fields:</p>
* <ul>
* <li><b>Title:</b> Stored as a {@code String} in order to be readable and to 
* ensure the exact user input is saved.</li>
* <li><b>Author:</b> Stored as a JSON object, containing the authors name. 
* This allows for later modifications.</li>
* <li><b>Content:</b> Stored as a {@code String} in order to be readable and to 
* ensure the exact user input is saved.</li>
* <li><b>Date:</b> Stored as a ISO-8601 String, YYYY-MM-DDT00:00:00.000000. 
* The T separates date from time. Then there are houres, minutes, seconds, and microseconds. 
* This ensures a standarised format.</li>
* </ul>
*
* <b>Mutability Assessment</b>
*
* <p>The adapter is immutable because its only field the {@code DateTimeFormatter} is 
* both static and final and thus cannot change. It does not keep any links to objects 
* outside the class, and anything it creates while reading or writing is temporary and 
* thrown away afterwards.This makes the adapter safe to use in several threads at the same time.</p>
*
* <b>Invalid Data Handling Strategy</b>
 * 
 * <p>When encountering malformed or unexpected JSON input, the adapter follows a 
 * “best-effort reconstruction” strategy:</p>
 * <ul>
 * <li>Unknown fields are skipped using {@code skipValue()}, preventing failures caused by 
 * forward-compatible schema changes.</li>
 * <li>Missing fields result in {@code null} values being passed to the {@link DiaryEntry} 
 * constructor. It is the responsibility of the constructor or calling logic to validate or 
 * reject such entries.</li>
 * <li>Malformed {@code LocalDateTime} values produce a parsing exception, which is propagated as an
 * {@code IOException}, signalling to the caller that the JSON is not recoverable.</li>
 * </ul>
 *
 * <p>This approach ensures resilience to minor format variations while still surfacing genuinely
 * invalid or corrupted data.</p>
 * 
 * <b>AI Usage</b>
 * 
 * <p>This class was created with guidance from an AI tool to resolve the issue of {@code Gson}
 * not automatically supporting the {@code LocalDateTime} format. The implementation has since been
 * reviewed and adapted to suit the evolving design and requirements of the application.</p>
*/
public class DiaryEntryTypeAdapter extends TypeAdapter<DiaryEntry> {
  /** Formatter used for serialising and parsing LocalDateTime values. */
  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  /** 
   * Default constructor for the type adapter.
   * 
   * <p>No configuration is required as the formatter is statically initialised.</p>
  */
  public DiaryEntryTypeAdapter() {}

  /**
  * Serialises a {@link DiaryEntry} into JSON.
  * 
  * <p>This method writes out each field of a DiaryEntry into a JSON object</p>
  * 
  * @param out the JSON writer to write the serialised output
  * @param entry the diary entry to serialise
  * @throws IOException if writing to the output fails
  */
  @Override
  public void write(JsonWriter out, DiaryEntry entry) throws IOException {
    out.beginObject();
    out.name("title").value(entry.getTitle());

    // Write Author as a JSON object
    out.name("author");
    out.beginObject();
    out.name("name").value(entry.getAuthor().getName());
    out.endObject();

    out.name("content").value(entry.getContent());
    out.name("date").value(entry.getDate().format(formatter));
    out.endObject();
  }

  /**
  * Deserialises a {@link DiaryEntry} from JSON input.
  * 
  * <p>Reconstructs a DiaryEntry by reading fields back from JSON text. 
  * If the method finds an unknown field, it skips the field.</p>
  * 
  * @param in the JSON reader used to read the input
  * @return the reconstructed {@code DiaryEntry} instance 
  * @throws IOException if reading fails or the JSON is malformed 
  */
  @Override
  public DiaryEntry read(JsonReader in) throws IOException {
    String title = null;
    Author author = null;
    String content = null;
    LocalDateTime date = null;

    in.beginObject();
    while (in.hasNext()) {
      String name = in.nextName();
      switch (name) {
        case "title":
          title = in.nextString();
          break;

        case "author":
          Author authorObj = new Author(name);

          in.beginObject(); // start reading the author object
          while (in.hasNext()) {
            switch (in.nextName()) {
              case "name":
                authorObj.setName(in.nextString());
                break;
                                
              default:
                in.skipValue(); // skip unknown fields
            }
          }
          in.endObject(); // finish reading author object

          author = authorObj;
          break;

        case "content":
          content = in.nextString();
          break;

        case "date":
          date = LocalDateTime.parse(in.nextString(), formatter);
          break;
                    
        default:
          in.skipValue();
          break;
      }
    }
    in.endObject();

    return new DiaryEntry(title, author, content, date);
  }
}
