package persistance;

import model.Fruit;
import model.Enemy;
import model.Basket;
import model.Game;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExist.json");
        try {
            Game g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }

    }

    @Test
    void testReaderNewGame() {
        JsonReader reader = new JsonReader("./data/testNewGame.json");
        try {
            Game g = reader.read();
            assertEquals(0, g.getScore());
            assertFalse(g.isEnded());
            assertEquals(0, g.getScore());
            assertEquals(0, g.getX());
            assertEquals(0, g.getY());
            assertEquals(0, g.getFruit().size());
            assertEquals(0, g.getFruitInBasket().size());
            assertEquals(0, g.getEnemies().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderArbitrary() {
        JsonReader reader = new JsonReader("./data/testArbitraryGame.json");

        try {
            Game g = reader.read();
            assertEquals(0, g.getScore());
            assertFalse(g.isEnded());
            assertEquals(0, g.getScore());
            assertEquals(20, g.getX());
            assertEquals(20, g.getY());
            assertEquals(6, g.getFruit().size());
            assertEquals(1, g.getFruitInBasket().size());
            assertEquals(3, g.getEnemies().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }

}
