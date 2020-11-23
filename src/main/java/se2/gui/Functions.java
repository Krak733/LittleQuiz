package se2.gui;


import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die Werte für die Animation/Linie der Menü-Box werden festgelegt.
 *
 * @return line
 */

public class Functions {

    final private static Logger log = LogManager.getLogger(Functions.class);


    public static Line makeLine(double startX, double startY,
                                double endX, double endY,
                                boolean direction,
                                final Pane root) {


        log.info("x=" + startX + ", y=" + startY+ ", panel=" + root);
        if(direction){
            endY = startY;
        }else{
            endX = startX;
        }
        Line line = new Line(startX, startY, endX, endY);

        if (direction) {
            line.setScaleX(1);
        } else {
            line.setScaleY(0);
        }
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        root.getChildren().add(line);
        return line;
    }

    public static void startAnimation(Line line, boolean direction, int seconds) {
        ScaleTransition st2 = new ScaleTransition(Duration.seconds(1), line);
        if (direction) {
            st2.setToX(seconds);
        } else {
            st2.setToY(seconds);
        }
        //st2.setDuration(Duration.seconds(3));
        st2.play();
    }

    public static void buildTabs(VBox menuBox){
        for (int i = 0; i < menuBox.getChildren().size(); i++) {
            Node n = menuBox.getChildren().get(i);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
            tt.setToX(0);
            tt.setOnFinished(e2 -> n.setClip(null));
            tt.play();
        }
    }

}
