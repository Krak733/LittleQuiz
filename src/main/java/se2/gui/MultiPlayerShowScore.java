package se2.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se2.db.InMemoryDB;
import se2.model.Player;
import se2.parser.FileParser;
import se2.thread.CountPlayerScore;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static se2.gui.GuiDriver.HEIGHT;
import static se2.gui.GuiDriver.WIDTH;

public class MultiPlayerShowScore extends Parent {

    private Pane pane = new Pane();
    private VBox displayScore = new VBox(0);

    private double startX = WIDTH / 2 - 100;
    private double startY = HEIGHT / 4;
    private double lineX = startX - 10;
    private double lineY;
    private int multiplySizeLine;
    private double borderMark;

    private static final Logger log = LogManager.getLogger(FileParser.class);

    public MultiPlayerShowScore(Stage stage) {
        log.info("New MultiplayerShowScore Fenster wird erstellt");
        stage.setOnCloseRequest(x -> Platform.exit());

        //int numberOfPlayers = getNumberOfPlayers();
        Image image = new Image("aurora_iss_wheelock_25july2010_900x500.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        // Button
        Button backButton = new Button("Zurück");
        backButton.setTranslateX(WIDTH / 2 - 270);
        backButton.setTranslateY(HEIGHT / 1.5);
        backButton.setStyle("-fx-arc-height: 100;-fx-arc-width: 100;" +
                " -fx-background-color: black; -fx-opacity: 0.60; -fx-pref-width: 150; " +
                "-fx-text-fill: white; -fx-pref-height: 40; -fx-stroke: white;");

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 16);
        backButton.setFont(font);
        backButton.setOnMouseClicked(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Your class that extends Application
                    new GuiDriver().start(stage);
                }
            });
        });

        // Je nach dem wieviele spieler drin sind, stellt sich die anzeige ein

        GuiItem2 tableTitle = new GuiItem2("Punktestand");

        tableTitle.setTranslateX(startX);
        tableTitle.setTranslateY(startY);
        tableTitle.setOpacity(0.75);

        pane.getChildren().addAll(imageView, backButton, tableTitle);
        makeScoreMenu(startX, startY + 50);

        // Animation Line
        switch(this.multiplySizeLine){
            case 1:
                lineY = startY + 20;
                borderMark = 40;
                break;
            case 2:
                lineY = startY + 32;
                borderMark = 64;
                break;
            case 3:
                lineY = startY + 45;
                borderMark = 90;
                break;
            case 4:
                lineY = startY + 55;
                borderMark = 112;
                break;
            case 5:
                lineY = startY + 67;
                borderMark = 130;
                break;
            case 6:
                lineY = startY + 77;
                borderMark = 156;
                break;
        }
        Line line = Functions.makeLine(lineX, lineY, lineX, lineY + this.borderMark, false, pane);
        Functions.startAnimation(line, false,2);

        startAnimation();

        this.getChildren().add(pane);
    }

    /**
     * Highscore Menü wird erstellt.
     * Namen und Highscore werden eingelesen und sortiert.
     *
     * @param x
     * @param y
     */

    public void makeScoreMenu(double x, double y) {
        displayScore.setTranslateX(x);
        displayScore.setTranslateY(y);

        Callable<String> c = new CountPlayerScore( );
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> result = executor.submit( c );

        String sortsuccess="";
        try {
            sortsuccess= result.get(1, TimeUnit.SECONDS);
            log.info("Sortierung erfolgreich");
        }
        catch (Exception e)
        {

            log.error("Fehler, Thread timeout: "+ e.getMessage());
        }
        if(!sortsuccess.equals("success"))
        {
            log.error("Fehler Sortierung fehlgeschlagen: "+ sortsuccess);
        }

       /* Thread sortPlayers = new CountPlayerScore();
        sortPlayers.setPriority(10);
        sortPlayers.start();

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

       synchronized (InMemoryDB.class) {
           LinkedHashMap<Integer, Player> sortedPlayers = InMemoryDB.getInstance().getSortedPlayers();
           log.info("Displaying PLayer Results");
        for (Map.Entry<Integer, Player> pair : sortedPlayers.entrySet()) {
            GuiItem2 tab = new GuiItem2(pair.getValue().getName() + " : " + pair.getValue().getScore());
            this.multiplySizeLine = sortedPlayers.size();
            tab.setTranslateX(-300);
            Rectangle clip = new Rectangle(300, 30);
            tab.setClip(clip);
            clip.translateXProperty().bind(tab.translateXProperty().negate());
            tab.setClip(clip);
            displayScore.getChildren().addAll(tab);
        }
        pane.getChildren().add(displayScore);
       }
    }

    public void startAnimation() {
        Functions.buildTabs(displayScore);
    }
}
