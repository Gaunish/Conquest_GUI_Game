package team5.risc.client.controller;

import java.nio.file.Paths;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.checkerframework.checker.units.qual.C;
import org.checkerframework.common.subtyping.qual.Bottom;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

import team5.risc.client.Client;
import team5.risc.client.DisplayUtil;
import team5.risc.common.AttackAction;
import team5.risc.common.MoveAction;
import team5.risc.common.UpgradeAction;
import team5.risc.common.CloakAction;
import team5.risc.common.SpyAction;

public class AllActionController extends UIController implements Initializable {

    Client client;
    ArrayList<Integer> levelList;
    ArrayList<String> areaList;
    int client_id;
    ArrayList<String> ownArrayList;
    ArrayList<String> enemyArrayList;

    public AllActionController(Client c) {
        this.client = c;
        this.levelList = new ArrayList<Integer>();
        this.client_id = -1;
        for (Integer i = 0; i < 7; i++) {
            levelList.add(i);
        }
        this.areaList = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            areaList.add("area" + i);
        }
        this.ownArrayList = new ArrayList<String>();
        this.enemyArrayList = new ArrayList<String>();
    }

    public Pane map;
    public Label user_id;
    public ImageView avatar_image;
    public Label status;
    public Label food;
    public ProgressBar food_bar;
    public Label tech;
    public ProgressBar tech_bar;
    public Label log;

    public ChoiceBox<String> up_src;
    public ChoiceBox<Integer> up_st_level;
    public ChoiceBox<Integer> up_ed_level;
    public Spinner<Integer> up_num;
    public Button up_submit;
    public Label up_log;

    public ChoiceBox<String> move_src;
    public ChoiceBox<String> move_dst;
    public ChoiceBox<Integer> move_level;
    public Spinner<Integer> move_num;
    public Button move_submit;
    public Label move_log;

    public ChoiceBox<String> att_src;
    public ChoiceBox<String> att_dst;
    public ChoiceBox<Integer> att_level;
    public Spinner<Integer> att_num;
    public Button att_submit;
    public Label att_log;

    public ChoiceBox<String> spy_src;
    public ChoiceBox<String> spy_dst;
    public Button spy_submit;
    public Label spy_log;

    public ChoiceBox<String> cloak_src;
    public Button cloak_submit;
    public Label cloak_log;

    public Button done;
    public AnchorPane sliding_pane;
    public Button menu;
    public TabPane tabs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.print("start initialize\n");
        updateMapInfo();
        try {
            updatePlayerInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateUpgradeTab();
        updateMoveTab();
        updateAttackTab();
        updateSpyTab();
        updateCloakTab();
        prepareSlideMenuAnimation();
    }

    @FXML
    public void updateMapInfo() {
        this.client_id = client.getID();
        Image image = new Image("image/korok" + this.client_id + ".png");
        final Circle clip = new Circle(75, 75, 75);
        avatar_image.setClip(clip);
        avatar_image.setImage(image);
        String map_str = null;
        try {
            map_str = client.getRiscServer().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] msg = map_str.split("\n\n\n");
        String map_info = msg[0];
        // System.out.println("map info:\n" + map_info);
        String player_info = msg[1];
        // System.out.println("player info:\n"+player_info);
        String[] components = map_info.split("\n\narea");

        for (int i = 0; i < components.length; i++) {
            String[] sub_msg = components[i].split("\n\n");
            String area_info = sub_msg[0];
            String display_info = sub_msg[1];
        
            String[] display_map = display_info.split("\n");
            String reachable = display_map[0].split(": ")[1];
            String spy = display_map[1].split(": ")[1];
            String cloak = display_map[2].split(": ")[1];
            String old = display_map[3].split(": ")[1];

            System.out.println(reachable);
            System.out.println(spy);
            System.out.println(cloak);
            System.out.println(old);

            String[] lines = area_info.split("\n");
            String area_name = null;
            if (lines[0].equals("area0")) {
                area_name = "area0";
            } else {
                area_name = "area" + lines[0];
            }

            int player_id = Character.getNumericValue(lines[1].charAt(lines[1].length() - 1));

            Boolean showFlag = false; //0 for show empty, 1 for show scene

            //spy, cloak, reachable, old
            if (spy.equals("true")) {
                showFlag = true;
            } else {
                if (cloak.equals("true"))
                    showFlag = false;
                else {
                    if (old.equals("true")) showFlag = true;
                    else {
                        showFlag = reachable.equals("true") ? true: false;
                    }
                        
                }
            }

    
        

            // if (reachable.equals("true")) {
            //     if (cloak.equals("false")) {
            //         showFlag = 1;
            //     } else {
            //         if (spy.equals("true")) showFlag = 1;
            //     }
            // } else {
            //     if (cloak.equals("false")) {
            //         if (spy.equals("true")) showFlag = 1;
            //         else showFlag = 2;
            //     } else if (spy.equals("true")) showFlag = 1;
            // }

            System.out.println("showFlag:"+showFlag);



            for (Node node : map.getChildren()) {
                if (node instanceof Label) {
                    String node_id = node.getId();
                    if (node_id.equals(area_name)) {
                        if (player_id == client_id) {
                            ownArrayList.add(area_name);
                        }
                        // System.out.println(node_id);
                        // System.out.println(player_id);
                        if (showFlag == true) {
                            String show_on_map = area_name + "\nplayer" + player_id;
                            ((Label) node).setText(show_on_map);
                            if (((Control) node).getTooltip() == null)
                                ((Control) node).setTooltip(new Tooltip());
                            ((Control) node).getTooltip().setText(area_info);

                            if (player_id == 0) {
                                node.setStyle("-fx-background-color: #8C251A");
                            } else {
                                node.setStyle("-fx-background-color: #1A3D8C");
                            }
                        } else if (showFlag == false) {
                            ((Label) node).setText("Invisible");
                            node.setStyle("-fx-background-color: #FFFFFF");
                        }
                    }
                }
            }
        }
        Collections.sort(ownArrayList);
        for (String name : areaList) {
            if (!ownArrayList.contains(name)) {
                enemyArrayList.add(name);
            }
        }

        String twoline = player_info;

        int food_num = Integer.parseInt(twoline.split("\n")[0].split(": ")[1]);
        int tech_num = Integer.parseInt(twoline.split("\n")[1].split(": ")[1]);

        // System.out.println("index::" + food_num);
        // System.out.println("index::" + tech_num);
        food.setText(" " + food_num);
        food_bar.setProgress((double) food_num / 2000.0);
        tech.setText(" " + tech_num);
        tech_bar.setProgress((double) tech_num / 2000.0);
        log.setText("Welcome");
    }

    @FXML
    public void updatePlayerInfo() throws IOException {
        // get winner status of game
        String game_status = client.getRiscServer().readUTF();
        System.out.println(game_status);

        user_id.setText("Player " + client.getID());

        // doesn't has winner
        if (game_status.equals("No winner")) {
            status.setText(game_status);
        } else { // has winner
            status.setText("Game ends");
            tabs.setDisable(true);
            done.setDisable(true);
            DisplayUtil.displayAlertAndWait(game_status);
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
                FXCollections.observableArrayList(ownArrayList));
        up_st_level.setItems(FXCollections.observableArrayList(levelList));
        up_ed_level.setItems(FXCollections.observableArrayList(levelList));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50);
        valueFactory.setValue(0);
        up_num.setValueFactory(valueFactory);
    }

    @FXML
    public void updateMoveTab() {
        move_src.setItems(
                FXCollections.observableArrayList(ownArrayList));
        move_dst.setItems(
                FXCollections.observableArrayList(ownArrayList));
        move_level.setItems(FXCollections.observableArrayList(levelList));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50);
        valueFactory.setValue(0);
        move_num.setValueFactory(valueFactory);
    }

    @FXML
    public void updateAttackTab() {
        att_src.setItems(
                FXCollections.observableArrayList(ownArrayList));
        att_dst.setItems(
                FXCollections.observableArrayList(enemyArrayList));
        att_level.setItems(
                FXCollections.observableArrayList(levelList));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50);
        valueFactory.setValue(0);
        att_num.setValueFactory(valueFactory);
    }

    @FXML
    public void updateSpyTab() {
        spy_src.setItems(
                FXCollections.observableArrayList(areaList));
        spy_dst.setItems(
                FXCollections.observableArrayList(areaList));
    }

    @FXML
    public void updateCloakTab() {
        cloak_src.setItems(
                FXCollections.observableArrayList(ownArrayList));
    }

    @FXML
    private void prepareSlideMenuAnimation() {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), sliding_pane);
        openNav.setToX(-140);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), sliding_pane);
        menu.setOnAction((ActionEvent evt) -> {
            if (sliding_pane.getTranslateX() == 0) {
                openNav.play();
            } else {
                closeNav.setToX(0); // (-(navList.getWidth()));
                closeNav.play();
            }
        });
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

        String response = client.getRiscServer().readUTF();

        String alert_string;
        if (response.equals("correct")) {
            DisplayUtil.playSound("/sound/ring.wav");
            alert_string = "Action executed successfully!\n";
            up_log.setText("Success");
        } else {
            DisplayUtil.playSound("/sound/alert.wav");
            alert_string = "Error: " + response + "\n";
            up_log.setText("Error");
        }
        log.setText(alert_string);
    }

    @FXML
    public void onSpy(ActionEvent ae) throws IOException {
        // TODO
        client.getRiscServer().writeUTF("Spy");
        SpyAction spy = new SpyAction(
                client.getID(),
                spy_src.getValue().toString(),
                spy_dst.getValue().toString()
        );

        client.getRiscServer().writeObject(spy);
        String response = client.getRiscServer().readUTF();
        String alert_string;
        if (response.equals("correct")) {
            DisplayUtil.playSound("/sound/ring.wav");
            alert_string = "Action executed successfully!\n";
            move_log.setText("Success");
        } else {
            DisplayUtil.playSound("/sound/alert.wav");
            alert_string = "Error: " + response + "\n";
            move_log.setText("Error");
        }
        log.setText(alert_string);

    }

    @FXML
    public void onCloak(ActionEvent ae) throws IOException {
        // TODO

        client.getRiscServer().writeUTF("Cloak");
        CloakAction cloak = new CloakAction(
                client.getID(),
                cloak_src.getValue().toString()
        );

        client.getRiscServer().writeObject(cloak);
        String response = client.getRiscServer().readUTF();
        String alert_string;
        if (response.equals("correct")) {
            DisplayUtil.playSound("/sound/ring.wav");
            alert_string = "Action executed successfully!\n";
            move_log.setText("Success");
        } else {
            DisplayUtil.playSound("/sound/alert.wav");
            alert_string = "Error: " + response + "\n";
            move_log.setText("Error");
        }
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
        if (response.equals("correct")) {
            DisplayUtil.playSound("/sound/ring.wav");
            alert_string = "Action executed successfully!\n";
            move_log.setText("Success");
        } else {
            DisplayUtil.playSound("/sound/alert.wav");
            alert_string = "Error: " + response + "\n";
            move_log.setText("Error");
        }
        log.setText(alert_string);
    }

    @FXML
    public void onAttack(ActionEvent ae) throws IOException {
        client.getRiscServer().writeUTF("Attack");

        // AudioClip currentMusic = new
        // AudioClip(Paths.get("/sound/ring.wav").toUri().toString());
        // currentMusic.play();

        AttackAction attack = new AttackAction(
                client.getID(),
                att_src.getValue().toString(),
                att_dst.getValue().toString(),
                att_level.getValue(),
                att_num.getValue());

        client.getRiscServer().writeObject(attack);
        String response = client.getRiscServer().readUTF();
        String alert_string;
        if (response.equals("correct")) {
            DisplayUtil.playSound("/sound/ring.wav");
            alert_string = "Action executed successfully!\n";
            att_log.setText("Success");
        } else {
            DisplayUtil.playSound("/sound/alert.wav");
            alert_string = "Error: " + response + "\n";
            att_log.setText("Error");
        }
        log.setText(alert_string);
    }

    @FXML
    public void onDone(ActionEvent ae) throws IOException {
        log.setText("Done with sending action, please wait");// + client.getID() + "wait for others");
        // log.paintImmediately(log.getVisibleRect());

        tabs.setDisable(true);
        done.setDisable(true);
        DisplayUtil.displayAlertAndWait("Done with sending action, please wait");
        client.getRiscServer().writeUTF("Done");
        Stage window = (Stage) ((Button) ae.getSource()).getScene().getWindow();
        DisplayUtil.playSound("/sound/ring.wav");
        openMapPage(window);
    }

    public void openMapPage(Stage window) throws IOException {
        String ui_path = "/ui/allaction.fxml";
        AllActionController allActionController = new AllActionController(client);
        openNewPage(ui_path, allActionController, window);

    }

    @FXML
    public void resetLog() {
        log.setText("please input your action");
    }
}
