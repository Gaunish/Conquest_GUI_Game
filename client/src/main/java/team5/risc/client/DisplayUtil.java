package team5.risc.client;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class DisplayUtil {

    public static void displayAlertAndWait(String alert) {
        Stage thirdStage = new Stage();
        StackPane thirdPane = new StackPane(new Label(alert));
        Scene thirdScene = new Scene(thirdPane, 300, 150);
        thirdStage.setScene(thirdScene);
        thirdStage.showAndWait();
    }

    public static void playSound(String path) {
        Media sound = new Media(Media.class.getResource(path).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    
}
