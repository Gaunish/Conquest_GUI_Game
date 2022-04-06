package team5.risc.client.controller;

import java.io.IOException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;

public class ActionController {

    Client client;

    public void setClient(Client c) {
        client = c;
    }

    @FXML
    public void onMove(ActionEvent ae) throws IOException {
        // MoveAction m = user_in.getMove(id);
        // System.out.println("Recieved move "+m.source+" "+m.destination);

        URL xmlResource = getClass().getResource("/ui/move.fxml");
        if (xmlResource == null) {
            System.out.print("No resource found");
            return;
        }
        FXMLLoader loader = new FXMLLoader(xmlResource);
        AnchorPane movePane = loader.load();
        MoveController moveController = loader.<MoveController>getController();
        moveController.setClient(client);

        moveController.setClient(client);
        moveController.source_cb.setItems(
                FXCollections.observableArrayList(client.getRegions()));
        moveController.destination_cb.setItems(
                FXCollections.observableArrayList(client.getRegions()));

        Stage secondStage = new Stage();
        Scene secondScene = new Scene(movePane, 600, 500);
        secondStage.setScene(secondScene);
        secondStage.show();
    }

    @FXML
    public void onUpgrade(ActionEvent ae) throws IOException {
        URL xmlResource = getClass().getResource("/ui/upgrade.fxml");
        if (xmlResource == null) {
            System.out.print("No resource found");
            return;
        }
        FXMLLoader loader = new FXMLLoader(xmlResource);
        AnchorPane upgradePane = loader.load();
        UpgradeController upgradeController = loader.<UpgradeController>getController();
        upgradeController.setClient(client);
        upgradeController.area_cb.setItems(
                FXCollections.observableArrayList(client.getRegions()));

        Stage secondStage = new Stage();
        Scene secondScene = new Scene(upgradePane, 600, 500);
        secondStage.setScene(secondScene);
        secondStage.show();
    }

    @FXML
    public void onAttack(ActionEvent ae) throws IOException {
        URL xmlResource = getClass().getResource("/ui/attack.fxml");
        if (xmlResource == null) {
            System.out.print("No resource found");
            return;
        }
        FXMLLoader loader = new FXMLLoader(xmlResource);
        AnchorPane attackPane = loader.load();
        AttackController attackController = loader.<AttackController>getController();
        attackController.setClient(client);
        Stage secondStage = new Stage();
        Scene secondScene = new Scene(attackPane, 600, 500);
        secondStage.setScene(secondScene);
        secondStage.show();
    }

    @FXML
    public void onDone(ActionEvent ae) throws IOException {
        client.getRiscServer().writeUTF("Done");
        DisplayUtil.displayAlertAndWait("Done with player " + client.getID() + "wait for others");

        // Read the map string info
        String map_str = client.getRiscServer().readUTF();
        System.out.println(map_str);

        // TODO: DISPLAY MAP
        // actionController.label = parse_map_str()

        // get winner status of game
        String game_status = client.getRiscServer().readUTF();
        System.out.println(game_status);

        DisplayUtil.displayAlertAndWait(game_status);

        if (!game_status.equals("No winner")) {
            client.getRiscServer().close();
            return;
        }

        // get status of player from server
        String pl_status = client.getRiscServer().readUTF();

        if (pl_status.equals("Loser")) {
            // String user_opt = user_in.getLoser();
            // TODO:Temporarily hardcode it
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Duplicate");
            alert.setHeaderText("This folder already exists");
            alert.setContentText(
                    "Do you want to continue?");

            // Boolean cont = false;
            Button show = new Button("Continue");
            show.setOnAction(e -> {
                // try {
                // cont = true;
                // } catch (IOException ee) {
                // ee.printStackTrace();
                // }
            });
            alert.setGraphic(show);

            String user_opt;
            boolean isPresent = alert.showAndWait().filter(ButtonType.OK::equals).isPresent();
            if (isPresent) {
                user_opt = "watch";
            } else {
                user_opt = "exit";
            }
            System.out.println("user_opt:" + user_opt);
            client.getRiscServer().writeUTF(user_opt);
            if (user_opt.equals("exit")) {
                return;
            }
            return;
        }

        return;
    }
}
