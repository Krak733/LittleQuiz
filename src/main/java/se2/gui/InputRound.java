package se2.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;
import se2.db.InMemoryDB;
import se2.questions.QuestionWithInput;

import java.util.Arrays;
import java.util.List;

import static se2.gui.GuiDriver.HEIGHT;
import static se2.gui.GuiDriver.WIDTH;


/**
 * Wählt der Spieler den Fragen - Typ mit Input aus, bekommt er das hier erstellte Fenster zu sehen.
 * Hintergrundbild wird eingebunden, die Größe festgelegt, Buttons erstellt.
 */

public class InputRound extends Parent {

    private static Logger log = org.apache.logging.log4j.LogManager.getLogger(InputRound.class);

    Pane pane = new Pane();

    private int score = InMemoryDB.getInstance().getSingleModePlayer().getScore();

    public InputRound(Stage stage){

        // Scene
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });

        Image image = new Image("spaceBlue.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 16);

            List<QuestionWithInput> questions = InMemoryDB.getInstance().getAllQuestionsWithInput();

            QuestionWithInput questionWithInputs = questions.get(0);
            String questionText = questionWithInputs.getQuestionText();

        Label label;
        label = new Label();
        label.setFont(font);
        label.setAlignment(Pos.CENTER);
        label.setId("label");
        label.setTranslateX(WIDTH / 2 - 400);
        label.setTranslateY(HEIGHT / 20);
        label.setText(questionText);

        TextArea textArea = new TextArea("Gib alle Deine Lösungen ein und trenne jede mit Komma!");
        textArea.setFont(font);
        textArea.setId("textArea");
        textArea.setTranslateX(WIDTH/2 - 400);
        textArea.setTranslateY(HEIGHT/2.4);

        textArea.getStyleClass().add("textArea");

        textArea.setStyle("-fx-text-fill: white;");
        textArea.setPrefWidth(800);
        textArea.setPrefHeight(100);
        textArea.setOpacity(0.7);

        Button backButton = new Button("Zurück");
        backButton.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;" +
                " -fx-background-color: black; -fx-opacity: 0.60; -fx-pref-width: 150; " +
                "-fx-text-fill: white; -fx-pref-height: 40; -fx-stroke: white;");
        backButton.setTranslateX(WIDTH / 2 - 250);
        backButton.setTranslateY(HEIGHT / 1.5);
        backButton.setFont(font);

        Text text = new Text(textArea.getText());
        text.setFill(Color.WHITE);
        text.setStyle("-fx-text-fill: white;");

        textArea.setStyle("-fx-background-color:rgb(230, 230, 230)");

        textArea.setOnMouseClicked(e -> {
            textArea.clear();
        });

        backButton.setOnMouseClicked(e -> {
            Parent newGameChoiceQType = new ChoiceQWindow(stage);
            Scene sceneQ = new Scene(newGameChoiceQType);
            stage.setScene(sceneQ);
        });


        Button done = new Button("Fertig!");
        done.setFont(font);
        done.setId("done");
        done.setTranslateX(WIDTH/2 +103);
        done.setTranslateY(HEIGHT/1.5);
        done.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;" +
                " -fx-background-color: black; -fx-opacity: 0.60; -fx-pref-width: 150; " +
                "-fx-text-fill: white; -fx-pref-height: 40; -fx-stroke: white;");

        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e1) {

                String tempAnswer = textArea.getText();
                List<String> tempAnswerList = Arrays.asList(tempAnswer.split(","));
                System.out.println(tempAnswerList);
                System.out.println(questionWithInputs.getAnswersList());
                for (int i = 0; i < tempAnswerList.size(); i++) {
                    System.out.println("." + tempAnswerList.get(i).toLowerCase());
                    if (questionWithInputs.getAnswersList().contains(tempAnswerList.get(i).toLowerCase().replace(" ", ""))) {
                        score++;
                    }else {
                        continue;
                    }
                }
                InMemoryDB.getInstance().getSingleModePlayer().getRoundScores().add(score);
                InMemoryDB.getInstance().getSingleModePlayer().addOneRound();
                Parent game = new ShowScoreView(stage);
                Scene scene = new Scene(game);
                stage.setScene(scene);
            }
        });

        textArea.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    textArea.appendText(",");

                }
            }
        });

        pane.getChildren().addAll(imageView, label, done, backButton, textArea);
        this.getStylesheets().add("colour.css");
        this.getChildren().addAll(pane);

    }




}
