package display;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
    public Stage mainMenuStage;
    public Circle[] circles;
    public Text[] texts;
    public Line[] arrowLines;
    public Polygon[] arrowHeads;
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
}