package team5.risc.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.SysexMessage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class PlacementController {
    @FXML
    TextField placement_number;

    @FXML
    Label label;

    @FXML
    private AnchorPane ap;

    String unit_num = null;
    String area_name = null;

    Client client;
    int region_index;

    public void setClient(Client c) {
        client = c;
    }

    public void setRegionIndex(int index) {
        region_index = index;
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
                // Goto Action(Map) Page
                System.out.println("Go to action phase");
                URL xmlResource = getClass().getResource("/ui/map.fxml");
                if (xmlResource == null) {
                    System.out.print("No resource found");
                    return;
                }
                FXMLLoader loader = new FXMLLoader(xmlResource);
                AnchorPane gp = loader.load();
                ActionController actionController = loader.<ActionController>getController();
                actionController.setClient(client);

                // Read the map string info
                String map_str = client.getRiscServer().readUTF();
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

                // get winner status of game
                String game_status = client.getRiscServer().readUTF();
                System.out.println(game_status);

                DisplayUtil.displayAlertAndWait(client.getID() + ":" + game_status);

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
            } else {
                // Goto Placement Page
                URL xmlResource = getClass().getResource("/ui/placement.fxml");
                if (xmlResource == null) {
                    System.out.print("No resource found");
                    return;
                }
                String area = client.getRiscServer().readUTF();
                FXMLLoader loader = new FXMLLoader(xmlResource);
                StackPane gp = loader.load();
                PlacementController pc = loader.<PlacementController>getController();
                pc.label.setText(area);
                pc.setClient(client);
                pc.setRegionIndex(region_index + 1);
                window.setScene(new Scene(gp, 640, 480));
            }
        } else {
            // Remain the same area page TODO
            System.out.println(result + "\n");
        }

        return;
    }

}
