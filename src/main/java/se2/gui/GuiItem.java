package se2.gui;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GuiItem extends Pane {

    private Text text;

    public GuiItem(String tabtext) {
        Rectangle taste = new Rectangle(0, 0,200, 30);
        taste.setStroke(Color.color(1, 1, 1, 0.75));

        taste.fillProperty().bind(
                Bindings.when(pressedProperty())
                        .then(Color.color(0, 0, 0, 0.75))
                        .otherwise(Color.color(0, 0, 0, 0.40))
        );

        text = new Text(tabtext);
        text.setTranslateX(5);
        text.setTranslateY(20);
        Font font2 = Font.loadFont(getClass()
                .getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 19);
        text.setFont(font2);
        text.setFill(Color.WHITE);

        getChildren().addAll(taste, text);
    }

    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> action.run());
    }


}
