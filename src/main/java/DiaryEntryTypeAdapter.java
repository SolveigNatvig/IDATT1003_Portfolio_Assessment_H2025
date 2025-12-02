import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A custom {@link TypeAdapter} for serialising and deserialising
 * {@link DiaryEntry} objects using Gson
 * 
 * <p> This adapter is required bacause {@link java.time.LocalDateTime} is not 
 * natively supported by Gson, and because {@link Author} must be explicitly 
 * written and reconstructed. </p>
 * 
 * <p> The adapter writes each file of a {@code DiaryEntry} as a JSON object and
 * reconstructs the object by reading those fields back in the same format. </p>
 */
public class DiaryEntryTypeAdapter extends TypeAdapter<DiaryEntry> {
    /** Formatter used for serialising and parsing LocalDateTime values */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    /**
     * Serialises a {@link DiaryEntry} into JSON
     * 
     * @param out the JSON writer
     * @param entry the diary entry to serialise
     * @throws IOExeption if writing to the output fails
     */
    // This method writes out each field of a DiaryEntry into a JSON object
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
     * @param in the JSON reader used to read fields
     * @return the reconstructed {@code DiaryEntry} instance 
     * @throws IOExeption if reading fails or JSON is malformed 
     */
    // Reconstructs a DiaryEntry by reading fields back from JSON text
    @Override
    public DiaryEntry read(JsonReader in) throws IOException {
        String title = null;
        Author author = null;
        String content = null;
        LocalDateTime date = null;
        LocalDateTime lastEdited = null;

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

                case "lastEdited":
                    lastEdited = LocalDateTime.parse(in.nextString(), formatter);
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
