package se2.db;

import se2.model.Player;
import se2.questions.Question;
import se2.questions.QuestionWithInput;
import se2.questions.QuestionWithButtons;

import java.util.*;

public class InMemoryDB {

    private static InMemoryDB instance;

    private List<Question> allQuestions = new ArrayList<>();

    private List<QuestionWithButtons> allQuestionWithButtons = new ArrayList<>();

    private List<QuestionWithInput> allQuestionsWithInput = new ArrayList<>();

    private List<String> timeQuestionAnswers = new ArrayList<>();

    private Map<Integer, Player> multiPlayers = new HashMap<>();

    private LinkedHashMap<Integer, Player> sortedPlayers = new LinkedHashMap<>();

    private Player singleModePlayer = new Player();


    private InMemoryDB() {

    }

    public static synchronized InMemoryDB getInstance() {
        if (instance == null) {
            instance = new InMemoryDB();
        }
        return instance;
    }



    public List<Question> getAllQuestions() {
        return allQuestions;
    }

    public void setAllQuestions(List<Question> allQuestions) {
        this.allQuestions = allQuestions;
    }

    public List<QuestionWithButtons> getAllQuestionWithButtons() {
        return allQuestionWithButtons;
    }

    public void setAllQuestionWithButtons(List<QuestionWithButtons> allQuestionWithButtons) {
        this.allQuestionWithButtons = allQuestionWithButtons;
    }

    public List<QuestionWithInput> getAllQuestionsWithInput() {
        return allQuestionsWithInput;
    }

    public void setAllQuestionsWithInput(List<QuestionWithInput> allQuestionsWithInput) {
        allQuestionsWithInput = allQuestionsWithInput;
    }

    public List<String> getTimeQuestionAnswers() {
        return timeQuestionAnswers;
    }

    public void setTimeQuestionAnswers(List<String> timeQuestionAnswers) {
        this.timeQuestionAnswers = timeQuestionAnswers;
    }

    public synchronized Map<Integer, Player> getMultiPlayers() {
        return multiPlayers;
    }

    public synchronized void setMultiPlayers(Map<Integer, Player> multiPlayers) {
        this.multiPlayers = multiPlayers;
    }

    public Player getSingleModePlayer() {
        return singleModePlayer;
    }

    public void setSingleModePlayer(Player singleModePlayer) {
        this.singleModePlayer = singleModePlayer;
    }

    public LinkedHashMap<Integer, Player> getSortedPlayers() {
        return sortedPlayers;
    }

    public void setSortedPlayers(LinkedHashMap<Integer, Player> sortedPlayers) {
        this.sortedPlayers = sortedPlayers;
    }
}
