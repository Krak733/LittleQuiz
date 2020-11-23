package se2.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.apache.logging.log4j.Logger;

/**
 *
 * Das Fenster für die Fragen-Typ Auswahl wird erstellt.
 * Hierzu zählt die Erstellung des Textfeldes, der Buttons und die Einbindung der CSS Datei.
 * Nach der Eingabe des Namens, wird der Spieler gefragt, welcher FragenTyp (ob mit Buttons, oder
 * Input) von ihm ausgewählt werden möchte
 *
 */


public class ChoiceQWindow extends Parent {

    private static Logger log = org.apache.logging.log4j.LogManager.getLogger(MultiPlayerCategoryWindow.class);

    Pane pane = new Pane();

    public ChoiceQWindow(Stage stage) {

        // Scene
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });

        Image image = new Image("space.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(GuiDriver.WIDTH);
        imageView.setFitHeight(GuiDriver.HEIGHT);

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 16);


        Label textfield = new Label("Wähle eine der zwei Fragentypen aus");
        textfield.setTranslateX(GuiDriver.WIDTH / 3);
        textfield.setTranslateY(1.75 * GuiDriver.HEIGHT / 10);
        textfield.setAlignment(Pos.CENTER);
        textfield.setId("textfield");
        textfield.setFont(font);

        Button backButton = new Button("Zurück");
        Button qCategory1 = new Button("Frage mit Antwortauswahl");
        Button qCategory2 = new Button("Frage mit Input");


        backButton.setTranslateX(GuiDriver.WIDTH / 2 - 250);
        backButton.setTranslateY(GuiDriver.HEIGHT / 1.5);
        backButton.setFont(font);


        qCategory1.setTranslateX(GuiDriver.WIDTH / 3);
        qCategory1.setTranslateY(3.5 * GuiDriver.HEIGHT / 10);
        qCategory2.setTranslateX(GuiDriver.WIDTH / 3);
        qCategory2.setTranslateY(5 * GuiDriver.HEIGHT / 10);

        qCategory1.setFont(font);
        qCategory2.setFont(font);

        qCategory1.setId("qCategory1");
        qCategory1.setFont(font);

        qCategory2.setId("qCategory2");
        qCategory2.setFont(font);


        backButton.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;" +
                " -fx-background-color: black; -fx-opacity: 0.60; -fx-pref-width: 150; " +
                "-fx-text-fill: white; -fx-pref-height: 40; -fx-stroke: white;");

        qCategory1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                Parent newGame = new SinglePlayerCategoryWindow(stage);
                Scene scene = new Scene(newGame);
                stage.setScene(scene);

            }
        });

        qCategory2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {


                Parent newInputGame = new InputRound(stage);
                Scene scene = new Scene(newInputGame);
                stage.setScene(scene);

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


        pane.getChildren().addAll(imageView, textfield, backButton, qCategory1, qCategory2);
        this.getStylesheets().add("colour.css");
        this.getChildren().addAll(pane);

    }

}
