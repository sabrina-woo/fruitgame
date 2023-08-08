package persistance;

import model.*;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//NOTE: the code here is modeled after the code in the link below:
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java

//Represents a reader that reads workroom from JSON data stored in the file
public class JsonReader {
    private String source;

    //EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads workroom from file and returns it;
    //throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: pareses workroom from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        int screenX = jsonObject.getInt("screen x");
        int screenY = jsonObject.getInt("screen y");


        Game g = new Game(screenX, screenY);
        addRetrievedFallingFruit(g, jsonObject);
        addRetrievedFallingEnemies(g, jsonObject);
        addRetrievedBasketFruit(g, jsonObject);
        addScore(g, jsonObject);
        addBasket(g, jsonObject);
        addEnded(g, jsonObject);
        return g;
    }

    //MODIFIES: g
    //EFFECTS: parses ended from JSON object and adds them to game
    private void addEnded(Game g, JSONObject jsonObject) {
        boolean isEnded = jsonObject.getBoolean("ended");
        g.setIsEnded(isEnded);
    }

    //MODIFIES: g
    //EFFECTS: parses basket from JSON object and adds them to game
    private void addBasket(Game g, JSONObject jsonObject) {
        int basketX = jsonObject.getInt("basket x");
        int basketY = jsonObject.getInt("basket y");

        g.setBasketY(basketY);
        g.setBasketX(basketX);
    }

    //MODIFIES: g
    //EFFECTS: parses score from JSON object and adds them to game
    private void addScore(Game g, JSONObject jsonObject) {
        int score = jsonObject.getInt("score");
        g.setScore(score);
    }

    //MODIFIES: g
    //EFFECTS: parses falling fruit from JSON object and adds them to game
    private void addRetrievedFallingFruit(Game g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("falling fruit");
        for (Object json : jsonArray) {
            JSONObject nextFruit = (JSONObject) json;
            addOneRetrievedFallingFruit(g, nextFruit);
        }
    }

    //MODIFIES: g
    //EFFECTS: parses fruit from JSON object and adds it to game
    private void addOneRetrievedFallingFruit(Game g, JSONObject jsonObject) {
        int posX = jsonObject.getInt("x position");
        int posY = jsonObject.getInt("y position");
        Fruit fruit = new Fruit(posX, posY);
        g.addFallingFruit(fruit);
    }

    //MODIFIES: g
    //EFFECTS: parses falling enemies from JSON object and adds them to game
    private void addRetrievedFallingEnemies(Game g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("falling enemies");
        for (Object json : jsonArray) {
            JSONObject nextEnemy = (JSONObject) json;
            addOneRetrievedFallingEnemy(g, nextEnemy);
        }
    }

    //MODIFIES: g
    //EFFECTS: parses enemy from JSON object and adds it to game
    private void addOneRetrievedFallingEnemy(Game g, JSONObject jsonObject) {
        int posX = jsonObject.getInt("x position");
        int posY = jsonObject.getInt("y position");
        Enemy enemy = new Enemy(posX, posY);
        g.addFallingEnemies(enemy);
    }

    //MODIFIES: g
    //EFFECTS: parses fruit in basket from JSON object and adds them to game
    private void addRetrievedBasketFruit(Game g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("basket fruit");
        for (Object json : jsonArray) {
            JSONObject nextFruit = (JSONObject) json;
            addOneRetrievedFruitInBasket(g, nextFruit);
        }
    }

    //MODIFIES: g
    //EFFECTS: parses fruit from JSON object and adds it to game
    private void addOneRetrievedFruitInBasket(Game g, JSONObject jsonObject) {
        int posX = jsonObject.getInt("x position");
        int posY = jsonObject.getInt("y position");
        Fruit fruit = new Fruit(posX, posY);
        g.getBasket().addFruitInBasket(fruit);
    }
}
