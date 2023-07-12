package screen;

import java.util.Optional;
import java.io.IOException;

import display.stackdisplay.StackDisplayController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

//import display.stackdisplay.StackDisplayController;

public class MainMenuController {

    @FXML
    private Button stackButton;

    @FXML
    private Button queueButton;

    @FXML
    private Button listButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button exitButton;

    // Các phương thức xử lý sự kiện cho các nút
    @FXML
    private void handleStackButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/display/stackdisplay/StackDisplay.fxml"));
        Parent stackDisplay = loader.load();
        Scene scene = new Scene(stackDisplay);

        // Tạo một Stage mới cho StackView
        Stage stackStage = new Stage();
        stackStage.setScene(scene);

        // Đặt vị trí và kích thước của cửa sổ
        stackStage.centerOnScreen();
        stackStage.setX((stackStage.getX() + stackStage.getWidth()) / 2 - stackStage.getWidth() / 2);
        stackStage.setY((stackStage.getY() + stackStage.getHeight()) / 2 - stackStage.getHeight() / 2);

        // Gán tiêu đề cho Stage của StackView
        stackStage.setTitle("Stack Display");

        // Truyền thông tin về Stage của MainMenu vào StackViewController
        StackDisplayController stackDisplayController = loader.getController();
        stackDisplayController.setMainMenuStage(stackStage);

        // Hiển thị cửa sổ StackView
        stackStage.show();
    }

    @FXML
    private void handleQueueButtonAction(ActionEvent event) throws IOException {
    }


    @FXML
    private void handleListButtonAction(ActionEvent event) throws IOException {
    }

    @FXML
    private void handleHelpButtonAction(ActionEvent event) {
        // Xử lý sự kiện khi nút "Help" được nhấn
        String message = "Welcome to the Data Structures Application!\n\n"
                + "This application demonstrates basic operations on different data structures, including Stack, Queue, and List.\n\n"
                + "To get started, select the desired data structure from the menu:\n"
                + "- Stack: Simulates a stack data structure and allows you to perform operations like push and pop.\n"
                + "- Queue: Simulates a queue data structure and allows you to perform operations like enqueue and dequeue.\n"
                + "- List: Simulates a list data structure and allows you to perform operations like add, remove, and search.\n\n"
                + "Enjoy exploring and learning about data structures!\n";

        // Hiển thị thông báo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {

        confirmExit();
    }
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Scene scene = exitButton.getScene();
            if (scene != null) {
                Stage stage = (Stage) scene.getWindow();
                stage.setOnCloseRequest(this::handleWindowCloseRequest);
            }
        });
    }

    private void handleWindowCloseRequest(WindowEvent event) {
        event.consume(); // Ngăn không đóng cửa sổ tự động

        confirmExit();
    }

    private void confirmExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            Platform.exit(); // Đóng ứng dụng
        }
    }
}