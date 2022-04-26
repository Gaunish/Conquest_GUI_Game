package team5.risc.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import team5.risc.client.controller.AllActionController;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import team5.risc.client.controller.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

public class AllActionTest extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws Exception {
        RISCServer mockServer = mock(RISCServer.class);
        Client client = new Client(mockServer, null);

        Mockito.lenient().when(client.getID()).thenReturn(0);

        // For Map Info
        String area0 = "area0\nOwner: Player0\nDefender:\nLevel 0: 20 units\nLevel 1: 0 units\nLevel 2: 0 units\nLevel 3: 0 units\nLevel 4: 0 units\nLevel 5: 0 units\nLevel 6: 0 units\nFood production: 2\nTech production: 3\nSize: 1\n\nReachable: true\nSpy: false\nCloak: false\nOld: false\n\n";
        String area1 = "area1\nOwner: Player1\nDefender:\nLevel 0: 15 units\nLevel 1: 0 units\nLevel 2: 0 units\nLevel 3: 0 units\nLevel 4: 0 units\nLevel 5: 0 units\nLevel 6: 0 units\nFood production: 2\nTech production: 3\nSize: 1\n\nReachable: true\nSpy: false\nCloak: false\nOld: false\n\n";
        String area2 = "area2\nOwner: Player0\nDefender:\nLevel 0: 15 units\nLevel 1: 0 units\nLevel 2: 0 units\nLevel 3: 0 units\nLevel 4: 0 units\nLevel 5: 0 units\nLevel 6: 0 units\nFood production: 2\nTech production: 3\nSize: 1\n\nReachable: true\nSpy: false\nCloak: false\nOld: false\n\n";
        String area3 = "area3\nOwner: Player1\nDefender:\nLevel 0: 15 units\nLevel 1: 0 units\nLevel 2: 0 units\nLevel 3: 0 units\nLevel 4: 0 units\nLevel 5: 0 units\nLevel 6: 0 units\nFood production: 2\nTech production: 3\nSize: 1\n\nReachable: true\nSpy: false\nCloak: false\nOld: false\n\n";
        String area4 = "area4\nOwner: Player0\nDefender:\nLevel 0: 15 units\nLevel 1: 0 units\nLevel 2: 0 units\nLevel 3: 0 units\nLevel 4: 0 units\nLevel 5: 0 units\nLevel 6: 0 units\nFood production: 2\nTech production: 3\nSize: 1\n\nReachable: true\nSpy: false\nCloak: false\nOld: false\n\n";
        String area5 = "area5\nOwner: Player1\nDefender:\nLevel 0: 15 units\nLevel 1: 0 units\nLevel 2: 0 units\nLevel 3: 0 units\nLevel 4: 0 units\nLevel 5: 0 units\nLevel 6: 0 units\nFood production: 2\nTech production: 3\nSize: 1\n\nReachable: true\nSpy: false\nCloak: false\nOld: false\n\n\n";
        String food_tech_info = "Food: 100\nTech: 100\n\n";
        String map_info = area0 + area2 + area4 + area1 + area3 + area5 + food_tech_info;
        
        Mockito.lenient().when(mockServer.readUTF()).thenReturn(map_info, "No winner", "No Lose", "correct", "correct", "correct");

        AllActionController allActionController 
            = new AllActionController(client);

        URL xmlResource = getClass().getResource("/ui/allaction.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        loader.setController(allActionController);
        AnchorPane gp = loader.load();
        Scene scene = new Scene(gp, 1000, 500);
        stage.setScene(scene);
        stage.show();

    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
        // FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }

    @Test
    public void TestAllAction() throws IOException {
        testMove();
        testAttack();
        testUpgrade();

        Platform.exit();
        System.exit(0);
    }

    public void testMove() throws IOException {
        clickOn("#menu");
        // try {
        //     Thread.sleep(3000);
        // } catch(Exception e) {

        // }
        
        clickOn("#move_tab");
        clickOn("#move_src");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        // clickOn("#move_tab");
        clickOn("#move_dst");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
    
        // clickOn("#move_tab");
        clickOn("#move_level"); 
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#move_num");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.ENTER);

        clickOn("#move_submit");


    }

    public void testAttack() throws IOException {
     
        clickOn("#attack_tab");
        clickOn("#att_src");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#attack_tab");
        clickOn("#att_dst");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
    
        clickOn("#attack_tab");
        clickOn("#att_level"); 
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#att_num");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.ENTER);

        clickOn("#att_submit");

        
    }

    public void testUpgrade() throws IOException {
       

        clickOn("#upgrade_tab");
        clickOn("#up_src");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#upgrade_tab");
        clickOn("#up_st_level");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);


        clickOn("#upgrade_tab");
        clickOn("#up_ed_level");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
    
        clickOn("#upgrade_tab");
        clickOn("#up_num");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.ENTER);

        clickOn("#up_submit");
    }
    
}