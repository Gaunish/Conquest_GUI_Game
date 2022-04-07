package team5.risc.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;
import team5.risc.common.MoveAction;

public class MoveController  extends UIController implements Initializable {
    @FXML
    ChoiceBox<String> source_cb;

    @FXML
    ChoiceBox<String> destination_cb;

    @FXML
    TextField num_unit;

    @FXML
    TextField level;

    Client client;

    public MoveController(Client c) {
        this.client = c;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.source_cb.setItems(
                FXCollections.observableArrayList(client.getRegions()));
        this.destination_cb.setItems(
                FXCollections.observableArrayList(client.getRegions()));
    }

    @FXML
    public void onSubmit(ActionEvent ae) throws IOException {
        client.getRiscServer().writeUTF("Move");
        MoveAction move = new MoveAction(
                client.getID(),
                source_cb.getValue().toString(),
                destination_cb.getValue().toString(),
                Integer.parseInt(level.getText()),
                Integer.parseInt(num_unit.getText()));

        client.getRiscServer().writeObject(move);
        String response = client.getRiscServer().readUTF();

        String alert_string;
        if (response.equals("correct"))
            alert_string = "Action executed successfully!\n";
        else
            alert_string = "Error: " + response + "\n";

        // Stage secondStage = new Stage();
        // Label label = new Label(alert_string);
        // StackPane secondPane = new StackPane(label);
        // Scene secondScene = new Scene(secondPane, 600, 200);
        // secondStage.setScene(secondScene);
        // secondStage.show();
        DisplayUtil.displayAlertAndWait(alert_string);
    }
}
