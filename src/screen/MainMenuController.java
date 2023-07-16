package screen;

import java.util.Optional;
import java.io.IOException;

import display.listdisplay.ListDisplayController;
import display.queuedisplay.QueueDisplayController;
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

public class MainMenuController {
    @FXML
    private Button exitButton;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/display/queuedisplay/QueueDisplay.fxml"));
        Parent queueDisplay = loader.load();
        Scene scene = new Scene(queueDisplay);

        Stage queueStage = new Stage();
        queueStage.setScene(scene);

        queueStage.centerOnScreen();
        queueStage.setX((queueStage.getX() + queueStage.getWidth()) / 2 - queueStage.getWidth() / 2);
        queueStage.setY((queueStage.getY() + queueStage.getHeight()) / 2 - queueStage.getHeight() / 2);

        queueStage.setTitle("Queue Display");

        QueueDisplayController queueDisplayController = loader.getController();
        queueDisplayController.setMainMenuStage(queueStage);

        queueStage.show();
    }
    @FXML
    private void handleListButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/display/listdisplay/ListDisplay.fxml"));
        Parent listDisplay = loader.load();
        Scene scene = new Scene(listDisplay);

        Stage listStage = new Stage();
        listStage.setScene(scene);

        listStage.centerOnScreen();
        listStage.setX((listStage.getX() + listStage.getWidth()) / 2 - listStage.getWidth() / 2);
        listStage.setY((listStage.getY() + listStage.getHeight()) / 2 - listStage.getHeight() / 2);

        listStage.setTitle("List Display");

        ListDisplayController listDisplayController = loader.getController();
        listDisplayController.setMainMenuStage(listStage);

        listStage.show();
    }
    @FXML
    private void handleHelpButtonAction(ActionEvent event) {
        String message = "Welcome to the Data Structures Application!\n\n"
                + "This application demonstrates basic operations on different data structures, including Stack, Queue, and List.\n\n"
                + "To get started, select the desired data structure from the menu:\n"
                + "- Stack: Simulates a stack data structure and allows you to perform operations like push and pop.\n"
                + "- Queue: Simulates a queue data structure and allows you to perform operations like enqueue and dequeue.\n"
                + "- List: Simulates a list data structure and allows you to perform operations like add, remove,search and sort.\n\n"
                + "Enjoy exploring and learning about data structures!\n";

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