package display;

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


import java.io.IOException;

public class GeneralDisplayController {

    @FXML
    public TextField inputTextField;
    @FXML
    public Button backButton;
    @FXML
    public ToggleButton createArrayButton;
    @FXML
    public ChoiceBox<String> choiceBox;
    @FXML
    public HBox arrayOptionsBox;
    @FXML
    public Button emptyButton;
    @FXML
    public Button userListButton;
    @FXML
    public Button randomButton;
    @FXML
    public AnchorPane canvas;
    @FXML
    public Slider speedSlider;
    @FXML
    public TextArea noteTextArea;
    public Stage mainMenuStage;
    public Circle[] circles;
    public Text[] texts;
    public Line[] arrowLines;
    public Polygon[] arrowHeads;
    public double fadeDuration;
    private int previousNumber = -1;
    public void setMainMenuStage(Stage stage) {
        this.mainMenuStage = stage;
    }
    @FXML
    public void handleBackButtonAction() throws IOException {

        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/MainMenu.fxml"));
        Parent mainMenu = loader.load();
        Scene scene = new Scene(mainMenu);

        Stage mainMenuStage = new Stage();
        mainMenuStage.setScene(scene);
        mainMenuStage.setTitle("Data Structure Application");
        mainMenuStage.show();
    }
    @FXML
    public void handleCreateArrayButtonAction() {
        arrayOptionsBox.setVisible(createArrayButton.isSelected());
    }
    public boolean isValidNumber(int number) {
        if (number == previousNumber) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Duplicate Number");
            alert.setContentText("The number " + number + " is already in the list. Please enter a different number.");
            alert.showAndWait();
            return false;
        }

        if (number < 1 || number > 99) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter a number from 1 to 99.");
            alert.showAndWait();
            return false;
        }

        previousNumber = number;
        return true;
    }

    public void drawArray(int[] array) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        double radius = 15.0;
        double horizontalSpacing = 20.0;

        double startY = (canvasHeight - 2 * radius) / 2.0;

        canvas.getChildren().clear();

        // draw circle and number
        circles = new Circle[array.length];
        texts = new Text[array.length];
        arrowLines = new Line[array.length - 1];
        arrowHeads = new Polygon[array.length - 1];

        double totalWidth = array.length * (radius * 2 + horizontalSpacing);
        double startX = (canvasWidth - totalWidth) / 2.0;

        for (int i = 0; i < array.length; i++) {
            double circleX = startX + i * (radius * 2 + horizontalSpacing);
            double circleY = startY;

            Circle circle = new Circle(circleX, circleY, radius);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            canvas.getChildren().add(circle);
            circles[i] = circle;

            int number = array[i];
            Text text = new Text(String.valueOf(number));
            text.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.EXTRA_BOLD, Font.getDefault().getSize()));
            text.setFill(Color.BLACK);
            text.setTextOrigin(VPos.CENTER);
            text.setX(circleX - text.getBoundsInLocal().getWidth() / 2);
            text.setY(circleY);
            canvas.getChildren().add(text);
            texts[i] = text;

            if (i < array.length - 1) {
                double arrowStartX = circleX + radius;
                double arrowStartY = circleY;
                double arrowEndX = circleX + radius + horizontalSpacing;
                double arrowEndY = circleY;

                Line arrowLine = new Line(arrowStartX, arrowStartY, arrowEndX, arrowEndY);
                arrowLine.setStroke(Color.BLACK);
                arrowLine.setStrokeWidth(2);
                canvas.getChildren().add(arrowLine);
                arrowLines[i] = arrowLine;

                double arrowSize = 5.0;

                Polygon arrowHead = new Polygon();
                arrowHead.getPoints().addAll(
                        arrowEndX - arrowSize, arrowEndY - arrowSize / 2,
                        arrowEndX - arrowSize, arrowEndY + arrowSize / 2,
                        arrowEndX, arrowEndY
                );
                arrowHead.setFill(Color.WHITE);
                arrowHead.setStroke(Color.BLACK);
                arrowHead.setStrokeWidth(2);
                canvas.getChildren().add(arrowHead);
                arrowHeads[i] = arrowHead;
            }
        }
    }
}