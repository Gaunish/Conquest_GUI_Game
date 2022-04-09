package team5.risc.client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

public class GUIClientTest extends ApplicationTest {
   /* @Override
    public void start(Stage stage) throws Exception {
        URL xmlResource = getClass().getResource("/ui/login.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        StackPane gp = loader.load();
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
    public void testEnglishInput() {
        clickOn("#currentUsername");
        write("abc");
        clickOn("#Button");
        Platform.exit();
        System.exit(0);
    }*/
    
}
