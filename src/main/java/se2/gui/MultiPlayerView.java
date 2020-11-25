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
import se2.db.InMemoryDB;

import static se2.gui.GuiDriver.WIDTH;


public class MultiPlayerView extends Parent {

    private double lengthButton = 100;
    private double heigthButton = 30;
    private double lineX = WIDTH / 2 - 80;
    private double lineY = GuiDriver.HEIGHT / 1.5 - 10;
    private Pane pane = new Pane();

    public MultiPlayerView(Stage stage) {
        stage.setOnCloseRequest(x -> Platform.exit());

        Image image = new Image("bildWeltall.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(GuiDriver.HEIGHT);

        this.getChildren().add(imageView);

        GuiTextfield textfield1 = new GuiTextfield("Spieler 1, gib Deinen Namen ein");
        GuiTextfield textfield2 = new GuiTextfield("Spieler 2, gib Deinen Namen ein");
        GuiTextfield textfield3 = new GuiTextfield("Spieler 3, gib Deinen Namen ein");
        GuiTextfield textfield4 = new GuiTextfield("Spieler 4, gib Deinen Namen ein");

        // Textfields
        textfield1.setTranslateX(WIDTH / 2.5);
        textfield1.setTranslateY(GuiDriver.HEIGHT / 10);
        textfield2.setTranslateX(WIDTH / 2.5);
        textfield2.setTranslateY(GuiDriver.HEIGHT / 5);
        textfield3.setTranslateX(WIDTH / 2.5);
        textfield3.setTranslateY(3 * GuiDriver.HEIGHT / 10);
        textfield4.setTranslateX(WIDTH / 2.5);
        textfield4.setTranslateY(4 * GuiDriver.HEIGHT / 10);

        // Buttons
        Button startButton = new Button("Start");
        Button backButton = new Button("Zurück");
        startButton.setTranslateX(WIDTH / 2 + 103);
        startButton.setTranslateY(GuiDriver.HEIGHT / 1.5);
        backButton.setTranslateX(WIDTH / 2 - 250);
        backButton.setTranslateY(GuiDriver.HEIGHT / 1.5);

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

        /**
         * Maximal 4 Spieler können mitspielen, das Programm liest mit, wie viele textfelder ausgefüllt wurden.
         *
         *
         */

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event)
            {
                int countPlayers = 0;
                InMemoryDB.getInstance().getMultiPlayers().clear();
                if (!textfield1.textfield.getText().equalsIgnoreCase("Spieler 1, gib Deinen Namen ein")) {
                    countPlayers++;
                    InMemoryDB.getInstance().getMultiPlayers().put(countPlayers,
                            new se2.model.Player(textfield1.textfield.getText()));
                }
                if (!textfield2.textfield.getText().equalsIgnoreCase("Spieler 2, gib Deinen Namen ein")) {
                    countPlayers++;
                    InMemoryDB.getInstance().getMultiPlayers().put(countPlayers,
                            new se2.model.Player(textfield2.textfield.getText()));
                }
                if (!textfield3.textfield.getText().equalsIgnoreCase("Spieler 3, gib Deinen Namen ein")) {
                    countPlayers++;
                    InMemoryDB.getInstance().getMultiPlayers().put(countPlayers,
                            new se2.model.Player(textfield3.textfield.getText()));
                }
                if (!textfield4.textfield.getText().equalsIgnoreCase("Spieler 4, gib Deinen Namen ein")) {
                    countPlayers++;
                    InMemoryDB.getInstance().getMultiPlayers().put(countPlayers,
                            new se2.model.Player(textfield4.textfield.getText()));
                }
                Parent newGame = new MultiPlayerCategoryWindow(stage, countPlayers, 1);
                Scene scene = new Scene(newGame);
                stage.setScene(scene);
            }
        });

        backButton.setOnMouseClicked(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Your class that extends Application
                    new GuiDriver().start(stage);
                }
            });
        });

        // Animations
        Line line = Functions.makeLine(lineX, lineY, lineX + 165, 0, true, pane);
        Functions.startAnimation(line, true,3);

        pane.getChildren().addAll(textfield1, textfield2, textfield3, textfield4, startButton, backButton);
        this.getChildren().add(pane);

    }
}
