package se2.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se2.db.InMemoryDB;
import se2.exception.WrongAnswerException;
import se2.questions.QuestionWithInput;
import se2.questions.QuestionWithButtons;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {

    private static final Logger log = LogManager.getLogger(FileParser.class);

    public void constructingQuestionsAndPopulateDatabase() {
        String dateiPfad = "src/main/resources/Fragen.txt";
        readAndPopulateDbWithTimeQuestion(dateiPfad);
        String dateiPfad2 = "src/main/resources/ausschreibeFragen.txt";
        readAndPopulateDbWithQuestionsWithInput(dateiPfad2);
    }

    /**
     *
     * in dieser Methode wird die Textdatei ausgelesen. Ebenso wird die Methode splitLineAndCollectTimeQuestion
     * hier angewendet und in der Liste "Fragen" abgespeichert.
     *
     * @param Fragen
     */

    private void readAndPopulateDbWithTimeQuestion(String Fragen) {
        try(BufferedReader reader = new BufferedReader(new FileReader(Fragen))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                QuestionWithButtons readedQuestionWithButtons = splitLineAndCollectTimeQuestion(line);
                InMemoryDB.getInstance().getAllQuestions().add(readedQuestionWithButtons);
                InMemoryDB.getInstance().getAllQuestionWithButtons().add(readedQuestionWithButtons);
                InMemoryDB.getInstance().getTimeQuestionAnswers().add(readedQuestionWithButtons.getCorrectAnswer());
            }
        } catch (IOException e) {
            log.error("File not found");
            e.printStackTrace();
        }
    }

    /**
     * In diesem Filereader wird nach der Textdatei gesucht, in der alle richtigen Antworten
     * stehen werden für die eigeneAntwortFragen. Erstellt ein QuestionWithInput- Objekt
     * und fügt es anschließend in die Liste alleFragen
     *
     * @param ausschreibeFragen
     */
    private void readAndPopulateDbWithQuestionsWithInput(String ausschreibeFragen) {
        try(BufferedReader reader = new BufferedReader(new FileReader(ausschreibeFragen))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                QuestionWithInput readedQuestionWithInput = splitLineAndCollectQuestionWithInput(line);
                InMemoryDB.getInstance().getAllQuestions().add(readedQuestionWithInput);
                InMemoryDB.getInstance().getAllQuestionsWithInput().add(readedQuestionWithInput);
            }
        } catch (IOException e) {
            log.error("File not found");
            e.printStackTrace();
        }
    }

    private QuestionWithInput splitLineAndCollectQuestionWithInput(String incomeLine) {
        String[] werte = incomeLine.split(";");
        String[] aa = new String[werte.length - 2];
        for (int i = 2; i < werte.length; i++) {
            aa[i - 2] = werte[i].toLowerCase().replace(" ", "");
        }
        try {
            QuestionWithInput questionWithInput = new QuestionWithInput(werte[0], werte[1], aa);
            return questionWithInput;
        }
        catch (WrongAnswerException e)
        {
            log.error(e.getMessage());
           return null;
        }
    }

    /**
     * diese Methode splittet den ganzen Array,überprüft, dass die Länge des Arrays nicht überschritten wird.
     * ein Objekt z wird erstellt, in der der gesplittete Array gespeichert wird.
     *
     * @param incomeLine
     * @return
     */
    private QuestionWithButtons splitLineAndCollectTimeQuestion(String incomeLine) {
        String[] values = incomeLine.split(";");
        if (values.length != 6) {
            log.error("Fehler in der Question: " + incomeLine);
            log.info(values.length);
            return null;
        }
        try {
            QuestionWithButtons questionWithButtons = new QuestionWithButtons(values[0], values[1], values[2], values[3], values[4], values[5]);
            return questionWithButtons;
        }
        catch (WrongAnswerException e)
        {
            log.error(e.getMessage());
            return null;
        }
    }


}
