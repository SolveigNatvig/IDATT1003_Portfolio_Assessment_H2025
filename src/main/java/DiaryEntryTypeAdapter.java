import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiaryEntryTypeAdapter extends TypeAdapter<DiaryEntry> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    // This method writes out each field of a DiaryEntry into a JSON object
    @Override
    public void write(JsonWriter out, DiaryEntry entry) throws IOException {
        out.beginObject();
        out.name("title").value(entry.getTitle());
        out.name("author").value(entry.getAuthor().getName()); // Is this right?
        out.name("content").value(entry.getContent());
        out.name("date").value(entry.getDate().format(formatter));
        out.endObject();
    }

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

        return new DiaryEntry(title, author, content);
    }
}
