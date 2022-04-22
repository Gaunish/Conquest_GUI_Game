package team5.risc.client.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;
import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;

public class ActionController extends UIController implements Initializable {

    Client client;

    public ActionController(Client c) {
        this.client = c;
    }

    @FXML
    public AnchorPane gp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.print("init start!!!\n");
        // Read the map string info
        String map_str = null;
        try {
            map_str = client.getRiscServer().readUTF();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(map_str);

        String[] components = map_str.split("\n\n");
        int index = 0;
        for (Node node : gp.getChildren()) {
            if (node instanceof VBox) {
                String[] lines = components[index].split("\n");
                for (int i = 0; i <= 12; ++i) {
                    ((VBox) node).getChildren().add(new Label(lines[i]));
                    System.out.println(lines[i]);
                }
                ++index;
            }
        }
        try {
            checkGameStatus();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.print("init end!!!\n");
    }

    @FXML
    public void checkGameStatus() throws IOException {
        // get winner status of game
        String game_status = client.getRiscServer().readUTF();
        System.out.println(game_status);

        DisplayUtil.displayAlertAndWait("Player " + client.getID() + ":" + game_status);

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
            alert.setHeaderText("You lose your game");
            alert.setContentText(
                    "Do you want to continue?");

            // Boolean cont = false;
            // Button show = new Button("Continue");
            // show.setOnAction(e -> {
            // // try {
            // // cont = true;
            // // } catch (IOException ee) {
            // // ee.printStackTrace();
            // // }
            // });
            // alert.setGraphic(show);

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
        }
    }

    @FXML
    public void onMove(ActionEvent ae) throws IOException {
        Stage secondStage = new Stage();
        String ui_path = "/ui/move.fxml";
        MoveController moveController = new MoveController(client);
        openNewPage(ui_path, moveController, secondStage);
        secondStage.show();
    }

    @FXML
    public void onUpgrade(ActionEvent ae) throws IOException {
        Stage secondStage = new Stage();
        String ui_path = "/ui/upgrade.fxml";
        UpgradeController upgradeController = new UpgradeController(client);
        openNewPage(ui_path, upgradeController, secondStage);
        secondStage.show();
    }

    @FXML
    public void onAttack(ActionEvent ae) throws IOException {


        Stage secondStage = new Stage();
        // Scene secondScene = new Scene(attackPane, 600, 500);

        String ui_path = "/ui/attack.fxml";
        AttackController attackController = new AttackController(client);
        openNewPage(ui_path, attackController, secondStage);
        secondStage.show();

    }

    @FXML
    public void onDone(ActionEvent ae) throws IOException {
        client.getRiscServer().writeUTF("Done");
        DisplayUtil.displayAlertAndWait("Done with player " + client.getID() + "wait for others");
        Stage window = (Stage) ((Button) ae.getSource()).getScene().getWindow();
        openMapPage(window);

        return;
    }

    public void openMapPage(Stage window) throws IOException {
        // window.setScene(new Scene(gp, 600, 800));

        String ui_path = "/ui/map.fxml";
        ActionController actionController = new ActionController(client);
        openNewPage(ui_path, actionController, window);
    }
}
