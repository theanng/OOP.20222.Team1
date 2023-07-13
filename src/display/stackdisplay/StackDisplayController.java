package display.stackdisplay;

import array.RandomArrayGenerator;
import datastructures.stack.Stack;
import display.GeneralDisplayController;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Optional;

public class StackDisplayController extends GeneralDisplayController {
    @FXML
    private Button backButton;
    @FXML
    private ToggleButton peekButton;
    @FXML
    private ToggleButton pushButton;
    @FXML
    private Button popButton;
    @FXML
    private HBox pushBox;
    @FXML
    private TextField inputTextField;
    @FXML
    private ToggleButton goButton;
    @FXML
    public TextArea noteTextArea;
    private int[] createdArray;
    private Stack stack;
    private RandomArrayGenerator randomArrayGenerator;
    public StackDisplayController() {
        randomArrayGenerator = new RandomArrayGenerator();
        stack = new Stack(9); // size of Stack
    }
    public void drawArray(int[] array) {

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        double radius = 15.0;
        double verticalSpacing = 20.0;

        double startX = 0.0; // Tọa độ x của hình tròn đầu tiên
        double startY = canvasHeight - 2 * radius - verticalSpacing; // Tọa độ y của hình tròn đầu tiên

        //Tạo hướng mũi tên
        double startX1 = 0.0; // Tọa độ x của hình tròn đầu tiên
        double startY1 = canvasHeight - 2 * radius - verticalSpacing;

        // Xóa các hình tròn và mũi tên hiện tại trên canvas
        canvas.getChildren().clear();

        //Vẽ các hình tròn và số
        circles = new Circle[array.length];
        texts = new Text[array.length];

        for (int i = 0; i < array.length; i++) {
            double circleX = startX + (canvasWidth - 2 * startX) / 2.0;
            double circleY = startY - i * (radius * 2 + verticalSpacing);

            double circleX1 = startX1 + (canvasWidth - 2 * startX) / 2.0;
            double circleY1 = startY1 - (array.length - 1 - i) * (radius * 2 + verticalSpacing);

            // draw circle
            Circle circle = new Circle(circleX, circleY, radius);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            canvas.getChildren().add(circle);
            circles[i] = circle;

            // number display
            int number = array[i];
            Text text = new Text(String.valueOf(number));
            text.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.EXTRA_BOLD, Font.getDefault().getSize()));
            text.setFill(Color.BLACK);
            text.setTextOrigin(VPos.CENTER);
            text.setX(circleX - text.getBoundsInLocal().getWidth() / 2);
            text.setY(circleY);
            canvas.getChildren().add(text);
            texts[i] = text;

            // Vẽ mũi tên (nếu có)
            if (i < array.length - 1) {
                double arrowStartX = circleX1;
                double arrowStartY = circleY1 + radius;
                double arrowEndX = circleX1;
                double arrowEndY = circleY1 + radius + verticalSpacing;

                Line arrowLine = new Line(arrowStartX, arrowStartY, arrowEndX, arrowEndY);
                arrowLine.setStroke(Color.BLACK);
                arrowLine.setStrokeWidth(2);
                canvas.getChildren().add(arrowLine);

                // Vẽ dấu mũi tên
                double arrowSize = 5.0; // Kích thước dấu mũi tên

                Polygon arrowHead = new Polygon();
                arrowHead.getPoints().addAll(
                        arrowEndX - arrowSize / 2, arrowEndY - arrowSize,
                        arrowEndX + arrowSize / 2, arrowEndY - arrowSize,
                        arrowEndX, arrowEndY
                );
                arrowHead.setFill(Color.WHITE);
                arrowHead.setStroke(Color.BLACK);
                arrowHead.setStrokeWidth(2);
                canvas.getChildren().add(arrowHead);
            }
        }
    }
    @FXML
    public void handleEmptyButtonAction() {
        stack.clear();
        createdArray = null;
        // Print the updated stack
        stack.print();
        drawArray(stack.toArray());
    }
    @FXML
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
                try {
                    int value = Integer.parseInt(elements[i].trim());
                    if (isValidNumber(value)) {
                        userListArray[i] = value;
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Please enter numbers from 1 to 99, separated by commas ',', and different array elements.");
                    alert.showAndWait();
                    return; // Kết thúc phương thức nếu có lỗi nhập số không hợp lệ
                }
            }
            createdArray = userListArray;
            // Clear the stack and push elements from the new createdArray
            stack.clear();
            for (int element : createdArray) {
                stack.push(element);
            }
            // Print the updated stack
            stack.print();
            drawArray(stack.toArray());
        }
    }
    @FXML
    public void handleRandomButtonAction() {
        String selectedOption = choiceBox.getValue();
        int size = Integer.parseInt(selectedOption);
        int[] randomArray = randomArrayGenerator.generateRandomArray(size);
        createdArray = randomArray;
        // Clear the stack and push elements from the new createdArray
        stack.clear();
        for (int element : createdArray) {
            stack.push(element);
        }
        // Print the updated stack
        stack.print();
        drawArray(stack.toArray());
    }
    @FXML
    public void handlePeekButtonAction() {
        if (stack.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Stack is empty");
            alert.setHeaderText(null);
            alert.setContentText("Stack is empty. Cannot peek element.");
            alert.showAndWait();
            return;
        }
        // Change color
        int peekIndex = stack.size() - 1;
        Circle peekCircle = circles[peekIndex];
        fadeDuration = speedSlider.getValue();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), circles[peekIndex]);
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            circles[peekIndex].setFill(Color.SKYBLUE);
        });
        peekCircle.setFill(Color.web("#d8b5ff"));
        noteTextArea.setText("Peek operation:\n" +
                    "Provide a way to examine the top element\n" +
                    "without modifying the stack.\n"+
                    "Example in this case, "+stack.peek()+" is the element top." );
    }
    @FXML
    public void handlePushButtonAction() {
        pushBox.setVisible(true);
        // create a random number
        int[] randomArray = RandomArrayGenerator.generateRandomArray(1);
        int value = randomArray[0];
        // Gán giá trị số ngẫu nhiên vào TextField
        inputTextField.setText(String.valueOf(value));
    }
    @FXML
    public void handleGoButtonAction() {
        String valueString = inputTextField.getText();
        int value = Integer.parseInt(valueString);

        if (stack.isFull()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Push");
            alert.setContentText("The stack is full. Cannot push more elements.");
            alert.showAndWait();
        } else if (stack.contains(value)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Push");
            alert.setContentText("The value " + value + " already exists in the stack.");
            alert.showAndWait();
        } else {
            stack.push(value);
            drawArray(stack.toArray());
            pushBox.setVisible(false);
            stack.print();
            // Change color
            int pushedIndex = stack.size() - 1;
            fadeTransitionCircle = new FadeTransition(Duration.seconds(fadeDuration), circles[pushedIndex]);
            fadeTransitionCircle.setFromValue(0.0);
            fadeTransitionCircle.setToValue(1.0);
            fadeTransitionCircle.play();
            fadeTransitionCircle.setOnFinished(event -> {
                circles[pushedIndex].setFill(Color.SKYBLUE);
            });
            noteTextArea.setText("Push operation:\n" +
                    "Allow you to expand the stack by adding \n" +
                    "an element to the top. \n" +
                    "Example in this case, " + stack.peek() + " is added to the top.");
        }
    }
    @FXML
    public void handlePopButtonAction() {
        if (stack.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Stack is empty");
            alert.setHeaderText(null);
            alert.setContentText("Stack is empty. Cannot pop element.");
            alert.showAndWait();
            return;
        }
        int poppedElement = stack.peek();
        int poppedIndex = stack.size() - 1;

        fadeTransitionCircle = new FadeTransition(Duration.seconds(fadeDuration), circles[poppedIndex]);
        fadeTransitionCircle.setFromValue(1.0);
        fadeTransitionCircle.setToValue(0.0);
        fadeTransitionCircle.play();
        fadeTransitionCircle.setOnFinished(event -> {
            // Sau khi hiệu ứng hoàn thành, pop phần tử khỏi stack và cập nhật hiển thị mảng
            stack.pop();
            int[] currentArray = stack.toArray();
            drawArray(currentArray);
        });
        noteTextArea.setText("Pop operation:\n" +
                "Allow you to shrink the stack by discarding\n" +
                "the top element.\n" +
                "Example in this case, " + poppedElement + " has just been removed.");
    }

}