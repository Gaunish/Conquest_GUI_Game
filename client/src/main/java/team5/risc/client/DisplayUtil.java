package team5.risc.client;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DisplayUtil {

    public static void displayAlertAndWait(String alert) {
        Stage thirdStage = new Stage();
        StackPane thirdPane = new StackPane(new Label(alert));
        Scene thirdScene = new Scene(thirdPane, 600, 200);
        thirdStage.setScene(thirdScene);
        thirdStage.showAndWait();
    }
}
