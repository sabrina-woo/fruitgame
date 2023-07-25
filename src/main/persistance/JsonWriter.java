package persistance;

import model.Game;
import org.json.JSONObject;

import java.io.*;

import java.io.File;
import java.io.PrintWriter;

//NOTE: the code here is modeled after the code in the link below:
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens writer; throws IOException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of workroom to file
    public void write(Game g) {
        JSONObject json = g.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
