package team5.risc.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;


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

public class PlacementTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        RISCServer mockServer = mock(RISCServer.class);
        Client client = new Client(mockServer, null);

        Mockito.lenient().when(mockServer.readUTF()).thenReturn("ABC");
        Mockito.lenient().when(mockServer.readObject())
                .thenReturn(new ArrayList<String>(Arrays.asList("area0", "area3", "area6")));
        
            
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("area0");
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");

        Mockito.lenient().when(mockServer.readUTF()).thenReturn("area3");
        Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");

        //Mockito.lenient().when(mockServer.readUTF()).thenReturn("area6");
        //Mockito.lenient().when(mockServer.readUTF()).thenReturn("Success");
 
        URL xmlResource = getClass().getResource("/ui/login.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        AnchorPane gp = loader.load();

        LoginController loginController = loader.<LoginController>getController();
        loginController.setClient(client);
        loader.setController(loginController);
        Scene scene = new Scene(gp, 640, 480);
        stage.setScene(scene);
        stage.show();

    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});
    }

    @Test
    public void testEnglishInput() throws IOException {
        clickOn("#Button");
        clickOn("#placement_number");
        write("1");
        clickOn("#submit");
        clickOn("#placement_number");
        write("10");
        clickOn("#submit");
        //clickOn("#placement_number");
        //write("20");
        //clickOn("#submit");
        Platform.exit();
        System.exit(0);
    }
    
}
