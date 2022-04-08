package team5.risc.client.controller;

import java.io.IOException;
import java.net.URL;

import org.checkerframework.checker.guieffect.qual.UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public abstract class UIController {

    public void openNewPage(String ui_path, UIController new_controller, Stage window) throws IOException {
        URL xmlResource = getClass().getResource(ui_path);
        if (xmlResource == null) {
            System.out.print("No fxml resource found");
            return;
        }
        FXMLLoader loader = new FXMLLoader(xmlResource);
        // PlacementController placementController = new PlacementController(client, 1);
        loader.setController(new_controller);
        System.out.print("25!!!\n");
        AnchorPane gp = loader.load(); // run initialize
        System.out.print("27!!!\n");
        window.setScene(new Scene(gp, 600, 500));
        System.out.print("29!!!\n");
    }

}
