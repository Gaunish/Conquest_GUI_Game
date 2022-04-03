package team5.risc.client.controller;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import team5.risc.client.Client;
import team5.risc.client.RISCServer;

public class LoginController {
    @FXML
    TextField currentUsername;

    @FXML
    TextField currentPassword;

    Client client;

    public void setClient(Client c) {
        client = c;
    }

    @FXML
    public void onSubmit(ActionEvent ae) throws IOException {
      Object source = ae.getSource();
      if (source instanceof Button) {
        Button btn = (Button) source;
        String userName = currentUsername.getText();
        String passWord = currentPassword.getText();

        if (userName.equals("abc") && passWord.equals("123")) {
            try {
                client.getRegionPhase();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }    

            Stage window = (Stage) btn.getScene().getWindow();
            URL xmlResource = getClass().getResource("/ui/placement.fxml"); 
            if (xmlResource == null) {
                System.out.print("No resource found");
                return;
            }
            FXMLLoader loader = new FXMLLoader(xmlResource);
            StackPane gp = loader.load();
            PlacementController placementController = loader.<PlacementController>getController();
            placementController.setClient(client);
            
            String area = client.getRiscServer().readUTF();
            for (Node node: gp.getChildren()) {
                AnchorPane anchor = (AnchorPane) node;
                for (Node node2: anchor.getChildren()) {
                    if (node2 instanceof Label) {
                        ((Label) node2).setText(area);
                    }
                }
            }
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
        throw new IllegalArgumentException(
            "Invalid source " + source + " for ActionEvent"
        );
      }
    }
}