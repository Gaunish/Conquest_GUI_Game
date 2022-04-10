package team5.risc.client;

import java.io.IOException;
import java.net.URL;

// import org.testfx.service.finder.WindowFinder;
import org.testfx.matcher.base.WindowMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import team5.risc.client.controller.LoginController;

@ExtendWith(ApplicationExtension.class)
class GUITest {
    StackPane gp;
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) {
        // button = new Button("click me!");
        // button.setId("myButton");
        // button.setOnAction(actionEvent -> button.setText("clicked!"));
        // stage.setScene(new Scene(new StackPane(button), 100, 100));
        // stage.show();

        URL xmlResource = getClass().getResource("/ui/login.fxml");
        if (xmlResource == null) {
            System.out.print("No resource found");
            return;
        }

        FXMLLoader loader = new FXMLLoader(xmlResource);
        try {
            gp = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        LoginController loginController = loader.<LoginController>getController();
        // loginController.setClient(client);

        Scene scene = new Scene(gp, 640, 480);
        stage.setScene(scene);

        stage.show();
        stage.toFront();
    }

    @Test
    void test_login(FxRobot robot) {
        robot.clickOn("#currentUsername").write("123");
        // robot.write("");
        // robot.clickOn("#currentUsername").type("123");
        robot.clickOn("#login_button");

        // FxAssert.verifyThat(robot.window("Login failed"), WindowMatchers.isShowing());
        // assertTrue(robot.window.popup_returner().isShowing());
    }

    // /**
    //  * @param robot - Will be injected by the test runner.
    //  */
    // @Test
    // void should_contain_button_with_text(FxRobot robot) {
    //     FxAssert.verifyThat(button, LabeledMatchers.hasText("click me!"));
    //     // or (lookup by css id):
    //     FxAssert.verifyThat("#myButton", LabeledMatchers.hasText("click me!"));
    //     // or (lookup by css class):
    //     FxAssert.verifyThat(".button", LabeledMatchers.hasText("click me!"));
    // }

    // /**
    //  * @param robot - Will be injected by the test runner.
    //  */
    // @Test
    // void when_button_is_clicked_text_changes(FxRobot robot) {
    //     // when:
    //     robot.clickOn(".button");

    //     // then:
    //     FxAssert.verifyThat(button, LabeledMatchers.hasText("clicked!"));
    //     // or (lookup by css id):
    //     FxAssert.verifyThat("#myButton", LabeledMatchers.hasText("clicked!"));
    //     // or (lookup by css class):
    //     FxAssert.verifyThat(".button", LabeledMatchers.hasText("clicked!"));
    // }
}