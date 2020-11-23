package se2.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import se2.db.InMemoryDB;

import static se2.gui.GuiDriver.HEIGHT;
import static se2.gui.GuiDriver.WIDTH;


public class ShowScoreView extends Parent {


    private Pane pane = new Pane();

    /**
     * Dem Spieler werden nach Ende der Runde die erreichten Punkte angezeigt.
     * Fenster dafür wird erstellt.
     *
     * @param stage
     */

    public ShowScoreView(Stage stage) {
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });

        int roundNumber = InMemoryDB.getInstance().getSingleModePlayer().getRoundNumbers();

        int score = InMemoryDB.getInstance().getSingleModePlayer().getRoundScores().get(InMemoryDB.getInstance().getSingleModePlayer().getRoundScores().size() -1);

        stage.setTitle("Congratulations!");

        Label text = new Label(String.format("Hey %s in Runde %d hast du %d Punkte erreicht!",
                InMemoryDB.getInstance().getSingleModePlayer().getName(), roundNumber, score));
        Font font = Font.loadFont(getClass()
                .getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 24);
        text.setFont(font);
        text.setAlignment(Pos.CENTER);
        text.setId("congratsLabel");
        text.setTranslateX(WIDTH / 800 - 1);
        text.setTranslateY(HEIGHT / 15);

        Image image = new Image("winner.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        Button nextRound = new Button("Nächste Runde");
        Button showScores = new Button("Punktestand");
        Button mainMenu = new Button("Zurück zum Menu");

        nextRound.setId("nextRound");
        nextRound.setFont(font);
        nextRound.setTranslateX(WIDTH / 2);
        nextRound.setTranslateY(3 * HEIGHT / 8);

        showScores.setTranslateX(WIDTH / 2 );
        showScores.setTranslateY(3 * HEIGHT / 6);
        showScores.setId("showScores");
        showScores.setFont(font);

        mainMenu.setTranslateX(WIDTH / 2.5+20);
        mainMenu.setTranslateY(3 * HEIGHT / 10+200);
        mainMenu.setId("mainMenu");
        mainMenu.setFont(font);

        nextRound.setOnMouseClicked(event -> {
            int nextRound2 = roundNumber+1;
            Parent newGameChoiceQType = new ChoiceQWindow(stage);
            Scene sceneQ = new Scene(newGameChoiceQType);
            stage.setScene(sceneQ);
        });

        showScores.setOnMouseClicked(event -> {
            Parent newGame = new showHighscore(stage);
            Scene scene = new Scene(newGame);
            stage.setScene(scene);
        });

        mainMenu.setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                new GuiDriver().start(new Stage());
                InMemoryDB.getInstance().getSingleModePlayer().clearData();
            });
        });

        this.getStylesheets().add("colour.css");
        pane.getChildren().addAll(imageView, text, nextRound, showScores, mainMenu);
        this.getChildren().add(pane);
    }
}
