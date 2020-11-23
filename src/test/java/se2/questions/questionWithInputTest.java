package se2.questions;

import org.junit.Test;
import se2.exception.WrongAnswerException;

import static org.junit.Assert.*;

public class questionWithInputTest {
    public void Test_Constructor() {
        try {

            QuestionWithInput z=new QuestionWithInput("","",new String[]{"","","",""});

            assertTrue(true);


        }
        catch (Exception ex)
        {
            assertTrue(false);
        }
    }
    @Test
    public void Test_überprüfeAntwort() {
        try {
            QuestionWithInput z = new QuestionWithInput("a b oder c oder d", "", new String[]{"a", "b", "c", "d"});

            if (z.checkAnswer("a")) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
            if (z.checkAnswer("b")) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
            if (z.checkAnswer("c")) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
            if (z.checkAnswer("d")) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
            if (!z.checkAnswer("e")) {
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
    @Test
    public void ÜberprüfueAntwortNullPointer() {
        try
        {
            QuestionWithInput a = new QuestionWithInput("Wie gehts dir?", "", null);
            a.checkAnswer(null);
            assertTrue(false);
        }
        catch (WrongAnswerException e)
        {
            assertTrue(true);
        }
    }



}