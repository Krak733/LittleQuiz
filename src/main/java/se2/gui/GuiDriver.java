package se2.gui;


import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se2.parser.FileParser;
import java.util.Arrays;
import java.util.List;

/**
 * Das Menü wird erstellt. Hintegrundbild, die Animation am Anfang und die Menü-Box werden erstellt bzw. eingebuden.
 * Die Größe des Fensters wird festgelegt.
 * Die MAin - Methode befindet sich in dieser Klasse.
 *
 */

public class GuiDriver extends Application {

    static int WIDTH = 800;
    static int HEIGHT = 500;
    private Stage stage;

    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<>("Einzelspieler", createSinglePlayerView()),
            new Pair<>("Multispieler", createMultiPlayerView()),
            new Pair<String, Runnable>("Zum Desktop", Platform::exit)
    );

    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;
    private double lineX = WIDTH / 2 - 80;
    private double lineY = HEIGHT / 3 + 50;

    private static Logger log = LogManager.getLogger(GuiDriver.class);

    private Parent createContent() {
        makeBackground();

        makeTitle();

        makeLine(lineX, lineY);

        makeMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    private void makeBackground() {
        Image image = new Image("earth_cosmic_rays_fb.jpg");
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        root.getChildren().add(imageView);
    }

    private void makeTitle() {
        GuiTitle title = new GuiTitle("Q  u  i  z", 48);
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }

    private void makeLine(double x, double y) {
        line = new Line(x, y, x, y + 120);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            Functions.buildTabs(menuBox);

        });
        st.play();
    }

    private void makeMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            GuiItem item = new GuiItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(400, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);
    }

    /*
     *  SinglePlayerView
     */

    private Runnable createSinglePlayerView() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.info("Choosen single player mode");
                Parent singlePlayerView = new SinglePlayerView(stage);
                Scene newScene = new Scene(singlePlayerView);
                newScene.getStylesheets().add("colour.css");
                stage.setScene(newScene);
            }
        };
        return r;
    }

    /*
     *  MultiPlayerView
     */

    private Runnable createMultiPlayerView() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.info("Choosen multi player mode");
                Parent multiPlayerView = new MultiPlayerView(stage);
                Scene newScene = new Scene(multiPlayerView);
                stage.setScene(newScene);
            }
        };
        return r;
    }

    // Main-method
    public static void main(String[] args) {
        FileParser parser = new FileParser();
        parser.constructingQuestionsAndPopulateDatabase();
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        this.stage = primaryStage;
        primaryStage.setTitle("Quiz Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(x -> {
            Platform.exit();
        });
        stage.setResizable(false);

        }
    }



