package team5.risc.client.controller;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    TextField currentUsername;

    @FXML
    TextField currentPassword;

    @FXML
    public void onSubmit(ActionEvent ae) throws IOException {
      Object source = ae.getSource();
      if (source instanceof Button) {
        Button btn = (Button) source;
        String userName = currentUsername.getText();
        String passWord = currentPassword.getText();

        if (userName.equals("abc") && passWord.equals("123")) {
            Stage window = (Stage) btn.getScene().getWindow();

            URL xmlResource = getClass().getResource("/ui/main.fxml"); 
            if (xmlResource == null) {
                System.out.print("No resource found");
                return;
            }
            Parent gp = FXMLLoader.load(xmlResource);
            window.setScene(new Scene(gp, 640, 480));
        } else {
            // Alert 
            Stage secondStage = new Stage();
            Label label = new Label("Login failed"); 
            StackPane secondPane = new StackPane(label);
            Scene secondScene = new Scene(secondPane, 300, 200);
            secondStage.setScene(secondScene);
            secondStage.show();
        }
      } else {
        throw new IllegalArgumentException("Invalid source " + source +
            " for ActionEvent");
      }
    }
}
