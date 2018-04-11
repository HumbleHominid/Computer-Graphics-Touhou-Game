import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void testNotNull() {
        assertNotNull("App should not be null",
            new App());
    }
}
