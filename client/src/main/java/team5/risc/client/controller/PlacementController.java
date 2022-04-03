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
import javafx.stage.Window;
import team5.risc.client.Client;

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
    int region_index = 0;
    
    public void setClient(Client c) {
        client = c;
    }

    @FXML
    public void onSubmit(ActionEvent ae) throws IOException {
        unit_num = placement_number.getText();
        Object source = ae.getSource();
        Button btn = (Button) source;
        if (region_index >= client.getRegions().size()) {
            //Goto Action Page
            System.out.println("Go to action phase");
            Stage window = (Stage) btn.getScene().getWindow();
            URL xmlResource = getClass().getResource("/ui/action.fxml"); 
            if (xmlResource == null) {
                System.out.print("No resource found");
                return;
            }
            FXMLLoader loader = new FXMLLoader(xmlResource);
            StackPane gp = loader.load();
            ActionController actionController = loader.<ActionController>getController();
            actionController.setClient(client);
            window.setScene(new Scene(gp, 640, 480));

        } else {
            client.getRiscServer().writeInt(Integer.parseInt(unit_num)); // send int
            String result = client.getRiscServer().readUTF();
            Stage window = (Stage) btn.getScene().getWindow();
            if (result.equals("Success")) {
                System.out.println("Successfully placed unit!\n");
                //Next area page by modify area name;
                ++region_index;
                
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
                window.setScene(new Scene(gp, 640, 480));
            } else {
                System.out.println(result + "\n");
                //Remain the same area page
            }
        }

        return;
    }



}
