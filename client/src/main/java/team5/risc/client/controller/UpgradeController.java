package team5.risc.client.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;
import team5.risc.common.MoveAction;
import team5.risc.common.UpgradeAction;

public class UpgradeController {
    @FXML
    ChoiceBox<String> area_cb;

    @FXML
    TextField index_army;

    @FXML
    TextField end_level;

    @FXML
    TextField num_unit;
    Client client;

    public void setClient(Client c) {
        client = c;
    }

    @FXML
    public void onSubmit(ActionEvent ae) throws IOException {
        client.getRiscServer().writeUTF("Upgrade");
        UpgradeAction upgrade = new UpgradeAction(
                client.getID(),
                area_cb.getValue().toString(),
                Integer.parseInt(index_army.getText()),
                Integer.parseInt(num_unit.getText()),
                Integer.parseInt(end_level.getText()));

        client.getRiscServer().writeObject(upgrade);

        // TODO: Server side validation on
        // String response = client.getRiscServer().readUTF();

        String alert_string;
        // if (response.equals("correct"))
        // alert_string = "Action executed successfully!\n";
        // else
        // alert_string = "Error: " + response + "\n";
        alert_string = "Successfully upgrade!";
        // Stage secondStage = new Stage();
        // Label label = new Label(alert_string);
        // StackPane secondPane = new StackPane(label);
        // Scene secondScene = new Scene(secondPane, 600, 200);
        // secondStage.setScene(secondScene);
        // secondStage.show();
        DisplayUtil.displayAlertAndWait(alert_string);
    }
}
