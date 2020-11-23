package se2.questions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se2.exception.WrongAnswerException;

import java.util.Arrays;
import java.util.List;

public class QuestionWithInput extends Question {

    private String[] answers;



    private static Logger log = LogManager.getLogger(QuestionWithInput.class);

    /**
     * Kontrolliert den Input, der bei QuestionWithInput geschrieben wird.
     *
     * @param questionText
     * @param category
     * @param answers
     * @throws WrongAnswerException
     */
    public QuestionWithInput(String questionText, String category, String[] answers) throws WrongAnswerException{

        super(category, questionText);
        if(answers==null)
        {
            log.error("Fehler im Konstruktor: Die Antworten der QuestionWithInput darf nicht NULL sein");
            throw new WrongAnswerException("Die Antworten der QuestionWithInput darf nicht NULL sein");
        }
        if(answers.length<=0)
        {
            log.error("Fehler im Konstruktor: Die Antworten der QuestionWithInput darf nicht leer sein");
            throw new WrongAnswerException("Die Antworten der QuestionWithInput darf nicht leer sein");
        }
        this.answers = answers;
    }

    public boolean checkAnswer(String incomeAnswer) {
        for (String answer : answers) {
            if (incomeAnswer.equalsIgnoreCase(answer)) {
                return true;
            }
        }
        return false;
    }

  public List<String> getAnswersList(){
      List<String> myInputAnswerList = Arrays.asList(this.answers);
      return myInputAnswerList;
  }


}
