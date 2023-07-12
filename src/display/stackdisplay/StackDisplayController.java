package display.stackdisplay;

import array.RandomArrayGenerator;
import datastructures.stack.Stack;
import display.GeneralDisplayController;

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

import java.util.Optional;

public class StackDisplayController extends GeneralDisplayController {
    private Circle[] circles;
    private int[] createdArray;
    private Stack stack;
    @FXML
    private Button backButton;
    @FXML
    private Button peekButton;
    @FXML
    private ToggleButton pushButton;
    @FXML
    private Button popButton;
    @FXML
    private HBox pushBox;
    @FXML
    private TextField inputTextField;
    @FXML
    private Button goButton;
    @FXML
    private AnchorPane canvas;
    private RandomArrayGenerator randomArrayGenerator;
    public StackDisplayController() {
        randomArrayGenerator = new RandomArrayGenerator();
        stack = new Stack(9); // Thay thế "9" bằng kích thước mong muốn của stack
    }
    public void drawArray(int[] array) {

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        double radius = 15.0; // Bán kính của hình tròn
        double verticalSpacing = 20.0; // Khoảng cách dọc giữa các hình tròn

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

            // Vẽ hình tròn
            Circle circle = new Circle(circleX, circleY, radius);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            canvas.getChildren().add(circle);
            circles[i] = circle;

            // Hiển thị số
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
    public void handlePeekButtonAction() {
        if (stack.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Stack is empty");
            alert.setHeaderText(null);
            alert.setContentText("Stack is empty. Cannot peek element.");
            alert.showAndWait();
            /**System.out.println("Stack is empty. Cannot peek.");**/
            return;

        }
        int[] currentArray = stack.toArray();
        drawArray(currentArray);

        // Chuyển màu hình tròn của giá trị peek sang màu tím
        int peekIndex = stack.size() - 1;
        Circle peekCircle = circles[peekIndex];
        peekCircle.setFill(Color.web("#d8b5ff"));
    }
    @FXML
    public void handlePushButtonAction() {
        pushBox.setVisible(true);

        // Tạo một số ngẫu nhiên
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
        } else {
            stack.push(value);
            drawArray(stack.toArray());
            pushBox.setVisible(false);
            stack.print();

            // Đặt màu fill của hình tròn vừa được push thành màu xanh da trời
            int pushedIndex = stack.size() - 1;
            circles[pushedIndex].setFill(Color.SKYBLUE);

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
        int poppedValue = stack.pop();
        int[] currentArray = stack.toArray();
        drawArray(currentArray);
        // Handle the popped value as needed
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
                int value = Integer.parseInt(elements[i].trim());
                userListArray[i] = value;
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
        } else {
            System.out.println("User defined list canceled. Cannot create new Stack.");
        }
    }
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
}
