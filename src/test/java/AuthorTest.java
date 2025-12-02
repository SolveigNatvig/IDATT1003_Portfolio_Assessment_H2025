
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthorTest{
    
    @ Test
    public void testValidAuthorCreation() {
        Author author = new Author("Solveig");
        assertEquals("Solveig", author.getName());
    }

    
}