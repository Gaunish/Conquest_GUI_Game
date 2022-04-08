package team5.risc.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.checkerframework.common.subtyping.qual.Bottom;

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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;
import team5.risc.common.AttackAction;
import team5.risc.common.MoveAction;
import team5.risc.common.UpgradeAction;

public class AllActionController extends UIController implements Initializable {

    Client client;
    ArrayList<Integer> levelList;
    ArrayList<String> areaList;

    public AllActionController(Client c) {
        this.client = c;
        this.levelList = new ArrayList<Integer>();
        for (Integer i = 0; i < 7; i++) {
            levelList.add(i);
        }
        this.areaList = new ArrayList<String>();
        for (int i = 0; i < 6; i++) {
            areaList.add("area" + i);
        }
        System.out.print("constructor!!!\n");
    }

    public GridPane map;
    public Label user_id;
    public Label status;
    public Label food;
    public Label tech;
    public Label log;

    public ChoiceBox<String> up_src;
    public ChoiceBox<Integer> up_st_level;
    public ChoiceBox<Integer> up_ed_level;
    public Spinner<Integer> up_num;
    public Button up_submit;

    public ChoiceBox<String> move_src;
    public ChoiceBox<String> move_dst;
    public ChoiceBox<Integer> move_level;
    public Spinner<Integer> move_num;
    public Button move_submit;

    public ChoiceBox<String> att_src;
    public ChoiceBox<String> att_dst;
    public ChoiceBox<Integer> att_level;
    public Spinner<Integer> att_num;
    public Button att_submit;

    public Button done;

    public TabPane tabs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.print("start init!!!\n");

        updateMapInfo();
        try {
            updatePlayerInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateUpgradeTab();
        updateMoveTab();
        updateAttackTab();
        System.out.print("init done!!!\n");
    }

    @FXML
    public void updateMapInfo() {
        String map_str = null;
        try {
            map_str = client.getRiscServer().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] components = map_str.split("\n\n");

        int index = 0;
        for (Node node : map.getChildren()) {
            if (node instanceof Label) {
                ((Label) node).setText(components[index]);
            }
            index += 1;
        }

        // TODO: get food, tech from map
        int food_num = 100;
        int tech_num = 100;
        food.setText(" " + food_num);
        tech.setText(" " + tech_num);
        log.setText("Welcome");
    }

    @FXML
    public void updatePlayerInfo() throws IOException {
        // get winner status of game
        String game_status = client.getRiscServer().readUTF();
        System.out.println(game_status);

        user_id.setText(" " + client.getID());
        status.setText(game_status);
        // DisplayUtil.displayAlertAndWait("Player " + client.getID() + ":" +
        // game_status);

        // has winner
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
    public void updateUpgradeTab() {
        up_src.setItems(
                FXCollections.observableArrayList(client.getRegions()));
        up_st_level.setItems(FXCollections.observableArrayList(levelList));
        up_ed_level.setItems(FXCollections.observableArrayList(levelList));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50);
        valueFactory.setValue(0);
        up_num.setValueFactory(valueFactory);
    }

    @FXML
    public void updateMoveTab() {
        move_src.setItems(
                FXCollections.observableArrayList(client.getRegions()));
        move_dst.setItems(
                FXCollections.observableArrayList(client.getRegions()));
        move_level.setItems(FXCollections.observableArrayList(levelList));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50);
        valueFactory.setValue(0);
        move_num.setValueFactory(valueFactory);
    }

    @FXML
    public void updateAttackTab() {
        att_src.setItems(
                FXCollections.observableArrayList(client.getRegions()));
        att_dst.setItems(
                FXCollections.observableArrayList(areaList));
        att_level.setItems(
                FXCollections.observableArrayList(levelList));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50);
        valueFactory.setValue(0);
        att_num.setValueFactory(valueFactory);
    }

    @FXML
    public void onUpgrade(ActionEvent ae) throws IOException {
        client.getRiscServer().writeUTF("Upgrade");
        UpgradeAction upgrade = new UpgradeAction(
                client.getID(),
                up_src.getValue().toString(),
                up_st_level.getValue(),
                up_num.getValue(),
                up_ed_level.getValue());

        client.getRiscServer().writeObject(upgrade);

        // TODO: Server side validation on
        // String response = client.getRiscServer().readUTF();

        String alert_string;
        // if (response.equals("correct"))
        // alert_string = "Action executed successfully!\n";
        // else
        // alert_string = "Error: " + response + "\n";
        alert_string = "Successfully upgrade!";
        log.setText(alert_string);
    }

    @FXML
    public void onMove(ActionEvent ae) throws IOException {
        client.getRiscServer().writeUTF("Move");
        MoveAction move = new MoveAction(
            client.getID(),
            move_src.getValue().toString(),
            move_dst.getValue().toString(),
            move_level.getValue(),
            move_num.getValue());

        client.getRiscServer().writeObject(move);
        String response = client.getRiscServer().readUTF();
        String alert_string;
        if (response.equals("correct"))
            alert_string = "Action executed successfully!\n";
        else
            alert_string = "Error: " + response + "\n";
        log.setText(alert_string);
    }

    @FXML
    public void onAttack(ActionEvent ae) throws IOException {
        client.getRiscServer().writeUTF("Attack");
        AttackAction attack = new AttackAction(
            client.getID(),
            att_src.getValue().toString(),
            att_dst.getValue().toString(),
            att_level.getValue(),
            att_num.getValue());

        client.getRiscServer().writeObject(attack);
        String response = client.getRiscServer().readUTF();
        String alert_string;
        if (response.equals("correct"))
            alert_string = "Action executed successfully!\n";
        else
            alert_string = "Error: " + response + "\n";
        log.setText(alert_string);
    }

    @FXML
    public void onDone(ActionEvent ae) throws IOException {
        // System.out.print("Done with sending action, please wait\n");
        log.setText("Done with sending action, please wait");// + client.getID() + "wait for others");
        // log.paintImmediately(log.getVisibleRect());
        // System.out.print("updated log\n");
        tabs.setDisable(true);
        done.setDisable(true);
        DisplayUtil.displayAlertAndWait("Done with sending action, please wait");
        client.getRiscServer().writeUTF("Done");
        Stage window = (Stage) ((Button) ae.getSource()).getScene().getWindow();
        System.out.print("278!!!\n");
        openMapPage(window);
        System.out.print("280!!!\n");


        // updateMapInfo();
        // try {
        //     updatePlayerInfo();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // updateUpgradeTab();
        // updateMoveTab();
        // updateAttackTab();

    }

    public void openMapPage(Stage window) throws IOException {
        String ui_path = "/ui/allaction.fxml";
        AllActionController allActionController = new AllActionController(client);
        openNewPage(ui_path, allActionController, window);
        
    }

    @FXML
    public void resetLog(){
        log.setText("please input your action");
    }
}
