package persistance;

import model.Fruit;
import model.Enemy;
import model.Basket;
import model.Game;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testInvalidFile() {
        try {
            Game g = new Game(0, 0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterNewGame() {
        try {
            Game g = new Game(0, 0);
            JsonWriter writer = new JsonWriter("./data/testNewGame.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testNewGame.json");
            g = reader.read();
            assertEquals(0, g.getScore());
            assertFalse(g.isEnded());
            assertEquals(0, g.getScore());
            assertEquals(0, g.getX());
            assertEquals(0, g.getY());
            assertEquals(0, g.getFruit().size());
            assertEquals(0, g.getFruitInBasket().size());
            assertEquals(0, g.getEnemies().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

        @Test
        void testWriterArbitraryGame() {
            try {
                Game g = new Game(20, 20);
                g.tick();
                JsonWriter writer = new JsonWriter("./data/testArbitraryGame.json");
                writer.open();
                writer.write(g);
                writer.close();

                JsonReader reader = new JsonReader("./data/testArbitraryGame.json");
                g = reader.read();
                assertEquals(0, g.getScore());
                assertFalse(g.isEnded());
                assertEquals(0, g.getScore());
                assertEquals(20, g.getX());
                assertEquals(20, g.getY());
                assertEquals(6, g.getFruit().size());
                assertEquals(0, g.getFruitInBasket().size());
                assertEquals(3, g.getEnemies().size());
            } catch (IOException e) {
                fail("Exception should not have been thrown");
            }
        }



    }






