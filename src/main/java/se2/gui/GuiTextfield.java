package se2.gui;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiTextfield extends Pane {

    private static Logger log = LogManager.getLogger(GuiTextfield.class);

    public TextField textfield;

    public GuiTextfield(String tabtext) {

        log.debug("GuiTextfield constructor");

        textfield = new TextField(tabtext);
        textfield.setPrefWidth(300);
        textfield.setPrefHeight(35);
        textfield.setStyle("-fx-text-fill: white;");

        Font font = Font.loadFont(getClass()
                .getResourceAsStream("/fonts/Penumbra-HalfSerif-Std_35114.ttf"), 14);

        textfield.setFont(font);
        Text text = new Text(textfield.getText());
        text.setFill(Color.WHITE);

        textfield.setBackground(new Background(new BackgroundFill(Paint.valueOf("010101"), CornerRadii.EMPTY, Insets.EMPTY)));
        textfield.setOpacity(0.75);

        textfield.setOnMouseClicked(e -> {
            textfield.clear();
        });

        /**
         *  Spieler gibt seinen Namen ein, dieser wird gespeichert und es geht weiter.
         */


        textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    textfield.setAlignment(Pos.CENTER);
                    String st;
                    st = textfield.getText();
                }

            }

        });

        getChildren().addAll(textfield);

    }

}
