package se2.factory;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se2.gui.MultiPlayerShowScore;
import se2.gui.ShowScoreView;

public class SingleAndMultiPlayerShowScore {

    public static Scene singleOrMultiPlayerShowScore(Stage stage, String gameMode) {
        if (gameMode.equals("singlePlayer")) {
            Parent parent = new ShowScoreView(stage);
            return new Scene(parent);
        } else if (gameMode.equals("multiPlayer")) {
            Parent parent = new MultiPlayerShowScore(stage);
            return new Scene(parent);
        }
        return null;
    }
}


