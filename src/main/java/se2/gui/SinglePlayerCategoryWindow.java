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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;

import static se2.gui.GuiDriver.HEIGHT;
import static se2.gui.GuiDriver.WIDTH;


/**
 * Entscheidet man sich für Einzelspieler, dann erscheint die Kategorienauswahl
 * Kategorie Buttons werden erstellt, Hintergrundbild wird eingefügt, CSS Datei ist eingebuden,
 */

public class SinglePlayerCategoryWindow extends Parent {

    private Button backButton = new Button("Zurück");
    private Button category1 = new Button("Politik/Geschichte");
    private Button category2 = new Button("IT");
    private Button category3 = new Button("Unterhaltung");
    private Button category4 = new Button("Geografie");

    private Pane pane = new Pane();

    private static Logger log = org.apache.logging.log4j.LogManager.getLogger(MultiPlayerCategoryWindow.class);


    public SinglePlayerCategoryWindow(Stage stage) {

        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });

        stage.setTitle("Singleplayer game");

        //Bild einfügen
        ImageView imageView = new ImageView(new Image("saturn.jpg"));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        //css Datei eingebunden, in der die Eigenschaften der Buttons festgelegt wurden
        this.getStylesheets().add("colour.css");

        //Buttons erstellen

        backButton.setDefaultButton(true);
        backButton.setTranslateX(WIDTH / 2 - 250);
        backButton.setTranslateY(HEIGHT / 1.5);

        backButton.setId("backButton");

        //Schriftart festlegen
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 16);
        backButton.setFont(font);

        category1.setTranslateX(WIDTH / 2.5);
        category1.setTranslateY(HEIGHT / 10);

        category1.setId("category1");
        category1.setFont(font);

        category2.setTranslateX(WIDTH / 2.5);
        category2.setTranslateY(HEIGHT/ 5);

        category2.setId("category2");
        category2.setFont(font);


        category3.setTranslateX(WIDTH / 2.5);
        category3.setTranslateY(3 * HEIGHT / 10);

        category3.setId("category3");
        category3.setFont(font);


        category4.setTranslateX(WIDTH / 2.5);
        category4.setTranslateY(4 * HEIGHT/ 10);

        category4.setId("category4");
        backButton.setOnMouseClicked(e -> {

            Parent newGameChoiceQType = new ChoiceQWindow(stage);
            Scene sceneQ = new Scene(newGameChoiceQType);
            stage.setScene(sceneQ);

        });

        category4.setFont(font);

        //Back Button bringt Spieler zur Startseite zurück

        pane.getChildren().addAll(imageView, backButton, category1, category2, category3, category4);
        this.getChildren().addAll(pane);


        category1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int index = 0;
                String imageName = "geschichteWenigerkonstrast.jpg";
                Parent game = new SinglePlayerGameView(stage, imageName, index);
                Scene scene = new Scene(game);
                stage.setScene(scene);
            }
        });

        category2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int index = 10;
                String imageName = "IT.jpg";
                Parent game = new SinglePlayerGameView(stage, imageName, index);
                Scene scene = new Scene(game);
                stage.setScene(scene);
            }
        });

        category3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int index = 20;
                String imageName = "roterteppich.jpg";
                Parent game = new SinglePlayerGameView(stage, imageName, index);
                Scene scene = new Scene(game);
                stage.setScene(scene);
            }
        });

        category4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int index = 30;
                String imageName = "weltkarte.jpg";
                Parent game = new SinglePlayerGameView(stage, imageName, index);
                Scene scene = new Scene(game);
                stage.setScene(scene);
            }
        });

    }
}

