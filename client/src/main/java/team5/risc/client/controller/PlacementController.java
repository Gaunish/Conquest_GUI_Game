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

public class PlacementController extends UIController implements Initializable {

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
        // String ui_path = "/ui/map.fxml";
        String ui_path = "/ui/allaction.fxml";
        AllActionController allActionController = new AllActionController(client);
        openNewPage(ui_path, allActionController, window);;

    }

    public void openPlacementPage(Stage window, int region_index) throws IOException {
        PlacementController placementController = new PlacementController(client, region_index);
        String ui_path = "/ui/placement.fxml";
        openNewPage(ui_path, placementController, window);
    }

}
