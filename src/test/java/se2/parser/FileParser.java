package se2.parser;

import org.junit.Assert;
import org.junit.Test;
import se2.db.InMemoryDB;
import se2.questions.QuestionWithButtons;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {

    @Test
    public void fileRead(){
        ArrayList<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/test.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(lines.get(0).equalsIgnoreCase("test"));
    }
}
