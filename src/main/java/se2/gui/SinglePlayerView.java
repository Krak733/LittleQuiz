package se2.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;
import se2.db.InMemoryDB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static se2.gui.GuiDriver.HEIGHT;
import static se2.gui.GuiDriver.WIDTH;


public class SinglePlayerView extends Parent {

    private static Logger log = org.apache.logging.log4j.LogManager.getLogger(SinglePlayerView.class);

    private Pane pane = new Pane();
    private double lineX = WIDTH / 2 - 80;
    private double lineY = HEIGHT / 1.5 - 10;
    private int roundNumber;


    public SinglePlayerView(Stage stage) {
        // Scene
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });

        Image image = new Image("space.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        // Textfield
        GuiTextfield textfield = new GuiTextfield("Gib Deinen Namen ein");
        textfield.setTranslateX(WIDTH / 2 - 150);
        textfield.setTranslateY(HEIGHT / 3);

        // Buttons
        Button startButton = new Button("Start");
        Button backButton = new Button("Zurück");
        startButton.setTranslateX(WIDTH / 2 + 103);
        startButton.setTranslateY(HEIGHT / 1.5);
        backButton.setTranslateX(WIDTH / 2 - 250);
        backButton.setTranslateY(HEIGHT / 1.5);
        // css


        startButton.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;" +
                " -fx-background-color: black; -fx-opacity: 0.60; -fx-pref-width: 150; " +
                "-fx-text-fill: white; -fx-pref-height: 40; -fx-stroke: white;");
        backButton.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;" +
                " -fx-background-color: black; -fx-opacity: 0.60; -fx-pref-width: 150; " +
                "-fx-text-fill: white; -fx-pref-height: 40; -fx-stroke: white;");
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 16);
        startButton.setFont(font);
        backButton.setFont(font);

        startButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                InMemoryDB.getInstance().getSingleModePlayer().clearData();
                String playerName = textfield.textfield.getText();

                    if (checkPlayerName(playerName)) {
                        InMemoryDB.getInstance().getSingleModePlayer().setName(playerName);
                        Parent newGameChoiceQType = new ChoiceQWindow(stage);
                        Scene sceneQ = new Scene(newGameChoiceQType);
                        stage.setScene(sceneQ);
                    }
                    else
                    {
                        log.error("Incorrect player name");
                        Parent singlePlayerView = new SinglePlayerView(stage);
                        Scene scene = new Scene(singlePlayerView);
                        stage.setScene(scene);
                    }

            }
        });
        backButton.setOnMouseClicked(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Your class that extends Application
                    new GuiDriver().start(new Stage());

                }
            });
        });

        /*
         *   Animationobjects
         */

        pane.getChildren().addAll(imageView, textfield, startButton, backButton);
        Line line = Functions.makeLine(lineX, lineY, lineX + 165, 0, true, pane);
        Functions.startAnimation(line, true, 3);

        this.getChildren().addAll(pane);
    }

    /**
     *
     *
     * @param playerName
     * @return
     */
    boolean checkPlayerName(String playerName)  {
        String USERNAME_PATTERN = "^[a-z0-9_-]{2,20}$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher mtch = pattern.matcher(playerName);
        return mtch.matches();
    }
}
