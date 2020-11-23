package se2.gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GuiItem2 extends Pane {
    private Text text;


    public GuiItem2(String tabtext) {
        Rectangle tabkey = new Rectangle(0, 0,250, 30

        );
        tabkey.setFill(Color.color(0, 0, 0, 0.75));
        tabkey.setStroke(Color.color(1, 1, 1, 0.75));

        text = new Text(tabtext);
        text.setTranslateX(33);
        text.setTranslateY(22);
        Font font2 = Font.loadFont(getClass()
                .getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 19);
        text.setFont(font2);
        text.setFill(Color.WHITE);

        getChildren().addAll(tabkey, text);
    }

    public double getMyWidth(){return 100;}

}