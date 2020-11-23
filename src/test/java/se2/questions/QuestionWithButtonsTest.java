package se2.questions;

import org.junit.Test;
import se2.exception.WrongAnswerException;

import static org.junit.Assert.*;

public class QuestionWithButtonsTest {

    @Test
    public void Test_Constructor() {
        try {

            QuestionWithButtons z=new QuestionWithButtons("","","","","","");

            assertTrue(true);


        }
        catch (Exception ex)
        {
            assertTrue(false);
        }
    }
    @Test
    public void Test_überprüfeAntwort() {
        try
        {
            QuestionWithButtons z = new QuestionWithButtons("a b oder c oder d", "", "a", "b", "c", "d");

            if (z.checkAnswer("a")) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
        catch (WrongAnswerException e)
        {
            assertTrue(false);
        }
    }

}