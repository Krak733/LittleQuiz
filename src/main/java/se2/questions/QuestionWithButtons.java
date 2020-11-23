package se2.questions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se2.exception.WrongAnswerException;

public class QuestionWithButtons extends Question {

    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;

    private static Logger log = LogManager.getLogger(QuestionWithButtons.class);

    /**
     * Verhindert, dass Antwort null oder leer ist.
     *
     * @param category
     * @param questionText
     * @param correctAnswer
     * @param wrongAnswer1
     * @param wrongAnswer2
     * @param wrongAnswer3
     * @throws WrongAnswerException
     */

    public QuestionWithButtons(String category, String questionText, String correctAnswer, String wrongAnswer1,
                               String wrongAnswer2, String wrongAnswer3) throws WrongAnswerException {

        super(category, questionText);
        if(correctAnswer.equals("") || correctAnswer == null) {
            log.error("Fehler im Konstruktor: Correct Answer can not be null or empty");
            throw new WrongAnswerException("Correct Answer can not be null or empty!");

        }

        if(wrongAnswer1.equals("") || wrongAnswer1 == null) {

            log.error("Fehler im Konstruktor: Wrong Answer can not be null or empty");
            throw new WrongAnswerException("Wrong Answer can not be null or empty!");
        }
        if(wrongAnswer2.equals("") || wrongAnswer2 == null) {
            log.error("Fehler im Konstruktor: Wrong Answer can not be null or empty");
            throw new WrongAnswerException("Wrong Answer can not be null or empty!");
        }
        if(wrongAnswer3.equals("") || wrongAnswer3 == null) {
            log.error("Fehler im Konstruktor: Wrong Answer can not be null or empty");
            throw new WrongAnswerException("Wrong Answer can not be null or empty!");
        }
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongAnswer1() {
        return this.wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public boolean checkAnswer(String incomeAnswer) {
        return incomeAnswer.equalsIgnoreCase(correctAnswer);
    }
}
