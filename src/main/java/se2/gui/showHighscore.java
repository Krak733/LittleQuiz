package se2.gui;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import se2.db.InMemoryDB;
import se2.model.Player;

import java.util.ArrayList;

import static se2.gui.GuiDriver.HEIGHT;
import static se2.gui.GuiDriver.WIDTH;

public class showHighscore extends Parent {

    private Pane pane = new Pane();
    private VBox displayScore = new VBox(0);


    private double startX = WIDTH / 2 - 100;
    private double startY = HEIGHT / 4;
    private double lineX = startX - 10;
    private double lineY = startY + 70;

    public showHighscore(Stage stage) {
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });

        Image image = new Image("aurora_iss_wheelock_25july2010_900x500.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

       // Button
        Button backButton = new Button("Zurück");
        backButton.setTranslateX(WIDTH / 2 - 270);
        backButton.setTranslateY(HEIGHT/ 1.5);
        backButton.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;" +
                " -fx-background-color: black; -fx-opacity: 0.60; -fx-pref-width: 150; " +
                "-fx-text-fill: white; -fx-pref-height: 40; -fx-stroke: white;");

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 16);
        backButton.setFont(font);
        backButton.setOnMouseClicked(e -> {
            Parent game = new ShowScoreView(stage);
            Scene scene = new Scene(game);
            stage.setScene(scene);
        });

        // Je nach dem wieviele spieler drin sind, stellt sich die anzeige ein
        GuiItem2 tableTitle = new GuiItem2(InMemoryDB.getInstance().getSingleModePlayer().getName());

        tableTitle.setTranslateX(startX);
        tableTitle.setTranslateY(startY);
        tableTitle.setOpacity(0.75);

        pane.getChildren().addAll(imageView, tableTitle, backButton);
        makeScoreMenu(startX, startY + 50);

        // Animation Line
        Line line = Functions.makeLine(lineX, lineY , lineX,lineY + 50, false, pane);
        Functions.startAnimation(line, false, 2);

        startAnimation();

        this.getChildren().add(pane);
    }

    /**
     * Punkte in der jeweiligen Runde werden angezeigt, wenn der Spieler nach dem Spiel auf den Button
     * Punktestand klickt.
     *
     * @param x
     * @param y
     */

    public void makeScoreMenu(double x, double y) {
        displayScore.setTranslateX(x);
        displayScore.setTranslateY(y);

        ArrayList<Integer> roundScores = InMemoryDB.getInstance().getSingleModePlayer().getRoundScores();

        for (int i =0; i < roundScores.size(); i++) {
            GuiItem2 tab2 = new GuiItem2(String.format("Runde %d: %d punkte", i+1, roundScores.get(i)) );
            Rectangle clip2 = new Rectangle(360, 30);
            tab2.setClip(clip2);
            clip2.translateXProperty().bind(tab2.translateXProperty().negate());
            tab2.setClip(clip2);
            displayScore.getChildren().addAll(tab2);
            tab2.setTranslateX(-200);
        }
        pane.getChildren().add(displayScore);
    }

    public void startAnimation() {
        Functions.buildTabs(displayScore);
    }

    public int multiplyLineLength(Player player){
        return 0;
    }
}
