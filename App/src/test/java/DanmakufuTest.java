import org.junit.Test;
import static org.junit.Assert.*;

public class DanmakufuTest {
    @Test
    public void testNotNull() {
        assertNotNull("Danmakufu should not be null", new Danmakufu());
    }

    @Test
    public void testGetNextDefaultNull() {
        assertNull("getNext should be default as null",
                new Danmakufu().getNext());
    }

    @Test
    public void testSetNext() {
        Danmakufu test = new Danmakufu();
        Danmakufu nextDan = new Danmakufu();

        test.setNext(nextDan);

        assertEquals("Next Danmakufu should be set correctly", nextDan,
                test.getNext());
    }

    @Test
    public void testDefaultIsInUse() {
        assertEquals("default isInUse should be false", false,
                new Danmakufu().isInUse());
    }

    @Test
    public void testIsInUseAfterInit() {
        Danmakufu dan = new Danmakufu();
        dan.init(0, 0, 0, 0);

        assertTrue("isInUse should be true after init", dan.isInUse());
    }

    @Test
    public void testNotInUseAfterUpdate() {
        Danmakufu dan = new Danmakufu();
        dan.init(-1, -1, 0 ,0);
        dan.update();

        assertFalse("Dan should not be in use after updating with below 0 coords",
                dan.isInUse());
    }
}
