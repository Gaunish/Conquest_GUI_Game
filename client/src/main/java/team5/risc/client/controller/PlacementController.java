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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;

public class PlacementController extends UIController implements Initializable {

    String unit_num = null;
    String area_name = null;
    int client_id;

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
    Label placement_log;

    @FXML
    private AnchorPane ap;

    public Label user_id;
    public ImageView avatar_image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.client_id = client.getID();
        Image image = new Image("image/korok" + this.client_id + ".png");
        final Circle clip = new Circle(75, 75, 75);
        avatar_image.setClip(clip);
        avatar_image.setImage(image);

        user_id.setText("Player " + this.client_id);

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
        if (!unit_num.matches("[0-9]+")) {
            placement_log.setText("Please only input positive digits");
            return;
        }
        Object source = ae.getSource();
        Button btn = (Button) source;
        client.getRiscServer().writeInt(Integer.parseInt(unit_num)); // send int
        String result = client.getRiscServer().readUTF();
        if (result.equals("Success")) {
            placement_log.setText(result);
            System.out.println("Successfully placed unit!\n");
            Stage window = (Stage) btn.getScene().getWindow();

            if (region_index == client.getRegions().size()) {
                // Goto Map
                DisplayUtil.displayAlertAndWait("Done with placing units.");
                System.out.println("Go to action phase");
                openMapPage(window);
            } else {
                // Goto Placement Page
                openPlacementPage(window, region_index + 1);
            }
        } else {
            // Remain the same area page TODO
            System.out.println(result + "\n");
            placement_log.setText(result);
        }
        return;
    }

    public void openMapPage(Stage window) throws IOException {
        // String ui_path = "/ui/map.fxml";
        String ui_path = "/ui/allaction.fxml";
        AllActionController allActionController = new AllActionController(client);
        openNewPage(ui_path, allActionController, window);
        ;

    }

    public void openPlacementPage(Stage window, int region_index) throws IOException {
        PlacementController placementController = new PlacementController(client, region_index);
        String ui_path = "/ui/placement.fxml";
        openNewPage(ui_path, placementController, window);
    }

}
