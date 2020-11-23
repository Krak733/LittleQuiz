package se2.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.Logger;
import se2.db.InMemoryDB;
import se2.factory.SingleAndMultiPlayerShowScore;
import se2.model.Player;
import se2.questions.QuestionWithButtons;
import se2.thread.CountPlayerScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static se2.gui.GuiDriver.HEIGHT;
import static se2.gui.GuiDriver.WIDTH;

public class MultiPlayerGameView extends Parent {


    private double xPosTab13 = WIDTH / 800 + 5;
    private double xPosTab24 = WIDTH / 1.9753;
    private double yPosTab12 = HEIGHT / 1.5;
    private double yPosTab34 = HEIGHT / 1.25;

    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private Button answer4Button;

    private int firstQuestionIndex;
    private int lastQuestionIndex;
    private int score = 0;
    private int idPlayerPlay;
    private Label label;

    private static Logger log = org.apache.logging.log4j.LogManager.getLogger(SinglePlayerGameView.class);


    public MultiPlayerGameView(Stage stage, String string, int firstQuestionIndex, int countPlayers, int idPlayerPlay) {

        this.firstQuestionIndex = firstQuestionIndex;
        this.lastQuestionIndex = firstQuestionIndex + 10;
        this.idPlayerPlay = idPlayerPlay;

        stage.setOnCloseRequest(x -> Platform.exit());

        stage.setTitle("Mögen die Spiele beginnen!");

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 16);

        //Bild einfügen
        ImageView imageView = new ImageView(new Image(string));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        Rectangle tabKey = new Rectangle(0, 0, 200, 30);
        tabKey.setFill(Color.color(0, 0, 0, 0.75));
        tabKey.setStroke(Color.color(1, 1, 1, 0.75));

        Text playerName = new Text(InMemoryDB.getInstance().getMultiPlayers().get(idPlayerPlay).getName());
        playerName.setFont(font);
        playerName.setTranslateX(55);
        playerName.setTranslateY(22);
        playerName.setFill(Color.WHITE);

        answer1Button = new Button("Answer1");
        answer1Button.setId("answer1Button");
        answer1Button.getStyleClass().addAll("answer1Button");

        answer2Button = new Button("Answer2");
        answer2Button.setId("answer2Button");
        answer2Button.getStyleClass().addAll("answer2Button");

        answer3Button = new Button("Answer3");
        answer3Button.setId("answer3Button");
        answer3Button.getStyleClass().addAll("answer3Button");

        answer4Button = new Button("Answer4");
        answer4Button.setId("answer4Button");
        answer4Button.getStyleClass().addAll("answer4Button");

        answer1Button.setTranslateX(xPosTab13);
        answer1Button.setTranslateY(yPosTab12);
        answer1Button.setFont(font);

        answer2Button.setTranslateX(xPosTab24);
        answer2Button.setTranslateY(yPosTab12);
        answer2Button.setFont(font);

        answer3Button.setTranslateX(xPosTab13);
        answer3Button.setTranslateY(yPosTab34);
        answer3Button.setFont(font);

        answer4Button.setTranslateX(xPosTab24);
        answer4Button.setTranslateY(yPosTab34);
        answer4Button.setFont(font);

        // Textbox Label
        label = new Label();
        label.setFont(font);
        label.setAlignment(Pos.CENTER);
        label.setId("label");
        label.setWrapText(true);
        label.setPadding(new Insets(0,10,0,10));
        prepareQuestionsAndAnswers();
        GuiTitle title = new GuiTitle(label.getText(), 16);
        label.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        label.setTranslateY(HEIGHT / 3);

        /**
         * Ist die ausgewählt Antwort richtig, erhält der Spieler einen Punkt und der Button leuchtet grün auf.
         * Ansonsten wird im angezeigt, dass die Antwort falsch war, indem der Button rot aufleuchtet.
         * Möglich durch die eingebundene colour.css Datei.
         *
         * Das Spiel des Spielers pausiert, solange der andere Spieler am Zug ist.
         *
         *
         */

        // Resultat nach Antwort Event
        answer1Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e1) {
                String text = ((Button) e1.getSource()).getText();
                if (InMemoryDB.getInstance().getTimeQuestionAnswers().contains(text)) {
                    answer1Button.getStyleClass().removeAll("answer1Button");
                    answer1Button.getStyleClass().add("answer1ButtonCorrect");
                    score++;
                } else {
                    answer1Button.getStyleClass().removeAll("answer1Button");
                    answer1Button.getStyleClass().add("answer1ButtonIncorrect");
                }
                if (getFirstQuestionIndex() == lastQuestionIndex) {
                    Player player = InMemoryDB.getInstance().getMultiPlayers().get(idPlayerPlay);
                    player.setScore(score);
                    InMemoryDB.getInstance().getMultiPlayers().replace(idPlayerPlay, player);
                    if (countPlayers == idPlayerPlay) {
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(event ->
                                stage.setScene(SingleAndMultiPlayerShowScore
                                        .singleOrMultiPlayerShowScore(stage, "multiPlayer")));
                        pause.play();
                    } else {
                        int nextPlayerId = getIdPlayerPlay() + 1;
                        Parent newGame = new MultiPlayerCategoryWindow(stage, countPlayers, nextPlayerId);
                        Scene scene = new Scene(newGame);
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(event -> stage.setScene(scene));
                        pause.play();
                    }
                } else {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> prepareQuestionsAndAnswers());
                    pause.play();
                }
            }
        });
        answer2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e1) {
                String text = ((Button) e1.getSource()).getText();
                if (InMemoryDB.getInstance().getTimeQuestionAnswers().contains(text)) {
                    answer2Button.getStyleClass().removeAll("answer2Button");
                    answer2Button.getStyleClass().add("answer2ButtonCorrect");
                    score++;
                } else {
                    answer2Button.getStyleClass().removeAll("answer2Button");
                    answer2Button.getStyleClass().add("answer2ButtonIncorrect");
                }
                if (getFirstQuestionIndex() == lastQuestionIndex) {
                    Player player = InMemoryDB.getInstance().getMultiPlayers().get(idPlayerPlay);
                    player.setScore(score);
                    InMemoryDB.getInstance().getMultiPlayers().replace(idPlayerPlay, player);
                    if (countPlayers == idPlayerPlay) {
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(event ->
                                stage.setScene(SingleAndMultiPlayerShowScore
                                        .singleOrMultiPlayerShowScore(stage, "multiPlayer")));
                        pause.play();
                    } else {
                        int nextPlayerId = getIdPlayerPlay() + 1;
                        Parent newGame = new MultiPlayerCategoryWindow(stage, countPlayers, nextPlayerId);
                        Scene scene = new Scene(newGame);
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(event -> stage.setScene(scene));
                        pause.play();
                    }
                } else {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> prepareQuestionsAndAnswers());
                    pause.play();
                }
            }
        });
        answer3Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e1) {
                String text = ((Button) e1.getSource()).getText();
                if (InMemoryDB.getInstance().getTimeQuestionAnswers().contains(text)) {
                    answer3Button.getStyleClass().removeAll("answer3Button");
                    answer3Button.getStyleClass().add("answer3ButtonCorrect");
                    score++;
                } else {
                    answer3Button.getStyleClass().removeAll("answer3Button");
                    answer3Button.getStyleClass().add("answer3ButtonIncorrect");
                }
                if (getFirstQuestionIndex() == lastQuestionIndex) {
                    Player player = InMemoryDB.getInstance().getMultiPlayers().get(idPlayerPlay);
                    player.setScore(score);
                    InMemoryDB.getInstance().getMultiPlayers().replace(idPlayerPlay, player);
                    if (countPlayers == idPlayerPlay) {
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(event ->
                                stage.setScene(SingleAndMultiPlayerShowScore
                                        .singleOrMultiPlayerShowScore(stage, "multiPlayer")));
                        pause.play();
                    } else {
                        int nextPlayerId = getIdPlayerPlay() + 1;
                        Parent newGame = new MultiPlayerCategoryWindow(stage, countPlayers, nextPlayerId);
                        Scene scene = new Scene(newGame);
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(event -> stage.setScene(scene));
                        pause.play();
                    }
                } else {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> prepareQuestionsAndAnswers());
                    pause.play();
                }
            }
        });

        answer4Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e1) {
                String text = ((Button) e1.getSource()).getText();
                if (InMemoryDB.getInstance().getTimeQuestionAnswers().contains(text)) {
                    answer4Button.getStyleClass().removeAll("answer4Button");
                    answer4Button.getStyleClass().add("answer4ButtonCorrect");
                    score++;
                } else {
                    answer4Button.getStyleClass().removeAll("answer4Button");
                    answer4Button.getStyleClass().add("answer4ButtonIncorrect");
                }
                if (getFirstQuestionIndex() == lastQuestionIndex) {
                    Player player = InMemoryDB.getInstance().getMultiPlayers().get(idPlayerPlay);
                    player.setScore(score);
                    InMemoryDB.getInstance().getMultiPlayers().replace(idPlayerPlay, player);
                    if (countPlayers == idPlayerPlay) {
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(event ->
                                stage.setScene(SingleAndMultiPlayerShowScore
                                        .singleOrMultiPlayerShowScore(stage, "multiPlayer")));
                        pause.play();
                    } else {
                        int nextPlayerId = getIdPlayerPlay() + 1;
                        Parent newGame = new MultiPlayerCategoryWindow(stage, countPlayers, nextPlayerId);
                        Scene scene = new Scene(newGame);
                        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                        pause.setOnFinished(event -> stage.setScene(scene));
                        pause.play();
                    }
                } else {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> prepareQuestionsAndAnswers());
                    pause.play();
                }
            }
        });

        this.getStylesheets().add("colour.css");

        Pane pane = new Pane();
        pane.getChildren().addAll(imageView, label, answer1Button, answer2Button,
                answer3Button, answer4Button, tabKey, playerName);
        this.getChildren().addAll(pane);

    }

    /**
     * Die Fragen und Antworten werden aus einer Textdatei ausgelesen, bei der die erste Antwort immer die richtige ist.
     * Hier wird dafür gesorgt, dass die Antworten in beliebiger reihenfolge ausgegeben werden.
     */
    void prepareQuestionsAndAnswers() {
        answer1Button.getStyleClass().removeAll("answer1ButtonCorrect", "answer1ButtonIncorrect");
        answer2Button.getStyleClass().removeAll("answer2ButtonCorrect", "answer2ButtonIncorrect");
        answer3Button.getStyleClass().removeAll("answer3ButtonCorrect", "answer3ButtonIncorrect");
        answer4Button.getStyleClass().removeAll("answer4ButtonCorrect", "answer4ButtonIncorrect");
        answer1Button.getStyleClass().add("answer1Button");
        answer2Button.getStyleClass().add("answer2Button");
        answer3Button.getStyleClass().add("answer3Button");
        answer4Button.getStyleClass().add("answer4Button");
        List<QuestionWithButtons> questions2 = InMemoryDB.getInstance().getAllQuestionWithButtons();
        QuestionWithButtons questionWithButtons = questions2.get(firstQuestionIndex);
        String questionText = questionWithButtons.getQuestionText();
        label.setText(questionText);
        GuiTitle title = new GuiTitle(label.getText(), 16);
        label.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        label.setTranslateY(HEIGHT / 3);
        List<String> answers = new ArrayList<>();
        answers.add(questions2.get(firstQuestionIndex).getCorrectAnswer());
        answers.add(questions2.get(firstQuestionIndex).getWrongAnswer1());
        answers.add(questions2.get(firstQuestionIndex).getWrongAnswer2());
        answers.add(questions2.get(firstQuestionIndex).getWrongAnswer3());
        Collections.shuffle(answers);
        answer1Button.setText(answers.get(0));
        answer2Button.setText(answers.get(1));
        answer3Button.setText(answers.get(2));
        answer4Button.setText(answers.get(3));
        increaseIndex();
    }

    public int getFirstQuestionIndex() {
        return firstQuestionIndex;
    }

    public void setFirstQuestionIndex(int firstQuestionIndex) {
        this.firstQuestionIndex = firstQuestionIndex;
    }

    void increaseIndex() {
        firstQuestionIndex++;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIdPlayerPlay() {
        return idPlayerPlay;
    }

    public void setIdPlayerPlay(int idPlayerPlay) {
        this.idPlayerPlay = idPlayerPlay;
    }
}
