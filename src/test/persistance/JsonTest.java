package persistance;

import model.Fruit;
import model.Enemy;
import model.Basket;
import model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPos(int pos, Fruit fruit) {
        assertEquals(pos, fruit.getX());
    }
}
