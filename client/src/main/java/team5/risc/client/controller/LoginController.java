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
import team5.risc.client.DisplayUtil;
import team5.risc.client.RISCServer;

public class LoginController extends UIController {
    @FXML
    TextField currentUsername;

    @FXML
    TextField currentPassword;

    @FXML
    Label login_log;

    Client client;

    public void setClient(Client c) {
        client = c;
    }

    @FXML
    public void onSubmit(ActionEvent ae) throws IOException, ClassNotFoundException {
        Object source = ae.getSource();
        if (source instanceof Button) {
            Button btn = (Button) source;
            String userName = currentUsername.getText();
            String passWord = currentPassword.getText();

            if (userName.equals("") && passWord.equals("")) {
                client.getRegionPhase();
                Stage window = (Stage) btn.getScene().getWindow();
                openPlacementPage(window);

            } else {
                login_log.setText("Login failed! Just press Submit to join game.");
            }
        } else {
            throw new IllegalArgumentException(
                    "Invalid source " + source + " for ActionEvent");
        }
    }

    public void openPlacementPage(Stage window) throws IOException {
        PlacementController placementController = new PlacementController(client, 1);
        String ui_path = "/ui/placement.fxml";
        openNewPage(ui_path, placementController, window);
    }

}