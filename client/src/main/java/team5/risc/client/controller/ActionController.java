package team5.risc.client.controller;

import java.io.IOException;
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
import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;

public class ActionController implements Initializable {

    Client client;

    public ActionController(Client c) {
        this.client = c;
    }
    // public void setClient(Client c) {
    // client = c;
    // }

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
        // MoveAction m = user_in.getMove(id);
        // System.out.println("Recieved move "+m.source+" "+m.destination);

        URL xmlResource = getClass().getResource("/ui/move.fxml");
        if (xmlResource == null) {
            System.out.print("No fxml resource found");
            return;
        }
        FXMLLoader loader = new FXMLLoader(xmlResource);
        MoveController moveController = new MoveController(client);
        loader.setController(moveController);

        AnchorPane movePane = loader.load();

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
        UpgradeController upgradeController = new UpgradeController(client);
        loader.setController(upgradeController);

        AnchorPane upgradePane = loader.load();
      
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
        AttackController attackController = new AttackController(client);
        loader.setController(attackController);

        AnchorPane attackPane = loader.load();
 
        Stage secondStage = new Stage();
        Scene secondScene = new Scene(attackPane, 600, 500);
        secondStage.setScene(secondScene);
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
        URL xmlResource = getClass().getResource("/ui/map.fxml");
        if (xmlResource == null) {
            System.out.print("No fxml resource found");
            return;
        }
        FXMLLoader loader = new FXMLLoader(xmlResource);
        ActionController actionController = new ActionController(client);
        loader.setController(actionController);

        AnchorPane gp = loader.load();
        window.setScene(new Scene(gp, 600, 800));
    }
}
