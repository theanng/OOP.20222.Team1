package display.queuedisplay;

import array.RandomArrayGenerator;
import datastructures.queue.Queue;
import display.GeneralDisplayController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class QueueDisplayController extends GeneralDisplayController {
    @FXML
    private HBox peekBox;
    @FXML
    private HBox enqueueBox;
    @FXML
    private TextField inputTextField;
    private int[] createdArray;
    private Queue queue;
    private RandomArrayGenerator randomArrayGenerator;
    public QueueDisplayController() {
        randomArrayGenerator = new RandomArrayGenerator();
        queue = new Queue(12);
    }
    @FXML
    public void handleEmptyButtonAction() {
        queue.clear();
        queue.print();
        drawArray(queue.toArray());
    }
    public void handleUserListButtonAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("User Defined List");
        dialog.setHeaderText("Enter a list of elements separated by commas");
        dialog.setContentText("List:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String input = result.get();
            String[] elements = input.split(",");
            int[] userListArray = new int[elements.length];

            for (int i = 0; i < elements.length; i++) {
                int value = Integer.parseInt(elements[i].trim());
                userListArray[i] = value;
            }
            createdArray = userListArray;
            queue.clear();
            for (int element : createdArray) {
                queue.enqueue(element);
            }
            queue.print();
            drawArray(queue.toArray());
        } else {
        }
    }

    public void handleRandomButtonAction() {
        String selectedOption = choiceBox.getValue();
        int size = Integer.parseInt(selectedOption);
        int[] randomArray = randomArrayGenerator.generateRandomArray(size);
        createdArray = randomArray;
        queue.clear();
        for (int element : createdArray) {
            queue.enqueue(element);
        }
        queue.print();
        drawArray(queue.toArray());
    }
    public void handlePeekButtonAction() {
        if (queue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Queue is empty");
            alert.setHeaderText(null);
            alert.setContentText("Queue is empty. Cannot peek element.");
            alert.showAndWait();
            return;
        } else {
            peekBox.setVisible(true);
        }
    }
    @FXML
    public void handleFrontButtonAction() {
        int[] currentArray = queue.toArray();
        drawArray(currentArray);

        int frontIndex = 0;
        Circle frontCircle = circles[frontIndex];
        fadeDuration = speedSlider.getValue();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), frontCircle );
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            frontCircle.setFill(Color.ORANGE);
        });
        noteTextArea.setText("Peek operation:\n" +
        "Provide a way to examine the front element\n" +
        "without modifying the queue.\n"+
        "Example in this case, "+queue.peek()+" is the element at \nthe front." );
        peekBox.setVisible(false);
    }
    public void handleBack1ButtonAction() {
        int[] currentArray = queue.toArray();
        drawArray(currentArray);

        int backIndex = queue.size() - 1; // Vị trí back
        Circle backCircle = circles[backIndex];
        fadeDuration = speedSlider.getValue();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), backCircle );
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            backCircle.setFill(Color.ORANGE);
        });
        noteTextArea.setText("Peek operation:\n" +
                    "Provide a way to examine the back element\n" +
                    "without modifying the queue.\n"+
                    "Example in this case, "+queue.peekBack()+" is the element at \nthe back." );
        peekBox.setVisible(false);

    }
    public void handleEnqueueButtonAction() {
        if (queue.isFull()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Enqueue");
            alert.setContentText("The queue is full. Cannot enqueue more elements.");
            alert.showAndWait();
        } else {
            enqueueBox.setVisible(true);
            int[] randomArray = RandomArrayGenerator.generateRandomArray(1);
            int value = randomArray[0];
            inputTextField.setText(String.valueOf(value));
        }
    }
    @FXML
    public void handleGoButtonAction() {
        String valueString = inputTextField.getText();
        int value = Integer.parseInt(valueString);
        queue.enqueue(value);
        drawArray(queue.toArray());
        queue.print();
        // Đặt màu fill của hình tròn vừa được enqueue thành màu xanh da trời
        int enqueuedIndex = queue.size() - 1;
        Circle addCircle = circles[enqueuedIndex];
        fadeDuration = speedSlider.getValue();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), addCircle );
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            addCircle.setFill(Color.SKYBLUE);
        });
        noteTextArea.setText("Enqueue operation:\n" +
        "Allow you to expand the queue by adding \n" +
        "an element to the back. \n" +
        "Example in this case, " + queue.peekBack() + " is added to the back.");
        enqueueBox.setVisible(false);
    }
    @FXML
    public void handleDequeueButtonAction() {
        if (queue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Queue is empty");
            alert.setHeaderText(null);
            alert.setContentText("Queue is empty. Cannot dequeue element.");
            alert.showAndWait();
        } else {
            int removedItem = queue.dequeue();
            int index = 0;
            circles[index].setFill(Color.PINK);
            fadeDuration = speedSlider.getValue();
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), circles[index]);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
            fadeTransition.setOnFinished(event -> {
                // Kiểm tra trạng thái của circles[index] trước khi tiếp tục
                if (canvas.getChildren().contains(circles[index])) {
                    drawArray(queue.toArray());
                }
            });
            noteTextArea.setText("Dequeue operation:\n" +
                    "Allow you to shrink the queue by removing \n" +
                    "an element from the front. \n" +
                    "Example in this case, " + removedItem + " is removed from\nthe front.");
        }
    }

}
