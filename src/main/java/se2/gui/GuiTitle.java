package se2.gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GuiTitle extends Pane {
    private Text text;

    public GuiTitle(String str, int fontsize) {


        text = new Text(str);

        //Font font = Font.loadFont(getClass().getClassLoader().getResource("Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48);
        //Font font = Font.loadFont("file:resources/fonts/Penumbra-HalfSerif-Std_35114.ttf", 48);
        Font font =
               Font.loadFont(getClass()
                        .getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), fontsize);
        //Font font = new Font("Penumbra-HalfSerif-Std", 48);
        //System.out.println(font);
        text.setFont(font);


        text.setFill(Color.WHITE);
        getChildren().addAll(text);
        }

    public double getTitleWidth() {
        return text.getLayoutBounds().getWidth();
    }

    public double getTitleHeight() {
        return text.getLayoutBounds().getHeight();
    }
}
