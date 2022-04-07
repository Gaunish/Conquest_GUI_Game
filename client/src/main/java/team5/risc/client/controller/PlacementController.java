package team5.risc.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.sound.midi.SysexMessage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;

public class PlacementController implements Initializable {

    String unit_num = null;
    String area_name = null;

    Client client;
    int region_index;

    public PlacementController(Client c, int index) {
        this.region_index = index;
        this.client = c;
    }

    @FXML
    TextField placement_number;

    @FXML
    Label label;

    @FXML
    private AnchorPane ap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String area = "null";
        try {
            area = client.getRiscServer().readUTF();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        label.setText(area);
    }

    @FXML
    public void onSubmit(ActionEvent ae) throws IOException {
        unit_num = placement_number.getText();
        Object source = ae.getSource();
        Button btn = (Button) source;
        client.getRiscServer().writeInt(Integer.parseInt(unit_num)); // send int
        String result = client.getRiscServer().readUTF();
        if (result.equals("Success")) {
            System.out.println("Successfully placed unit!\n");
            Stage window = (Stage) btn.getScene().getWindow();

            if (region_index == client.getRegions().size()) {
                // Goto Map page
                System.out.println("Go to action phase");
                openMapPage(window);
            } else {
                // Goto Placement Page
                openPlacementPage(window, region_index + 1);
            }
        } else {
            // Remain the same area page TODO
            System.out.println(result + "\n");
        }

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
        window.setScene(new Scene(gp, 600, 800));
    }

    public void openPlacementPage(Stage window, int region_index) throws IOException {
        URL xmlResource = getClass().getResource("/ui/placement.fxml");
        if (xmlResource == null) {
            System.out.print("No resource found");
            return;
        }

        FXMLLoader loader = new FXMLLoader(xmlResource);
        PlacementController placementController = new PlacementController(client, region_index);
        loader.setController(placementController);
        StackPane gp = loader.load();

        window.setScene(new Scene(gp, 640, 480));
    }

}
