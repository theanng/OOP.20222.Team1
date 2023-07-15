package display.listdisplay;

import array.RandomArrayGenerator;
import datastructures.list.List;
import display.GeneralDisplayController;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Optional;

public class ListDisplayController extends GeneralDisplayController {
    @FXML
    private ToggleButton findButton;
    @FXML
    private HBox searchBox;
    @FXML
    private ToggleButton insertButton;
    @FXML
    private HBox insertBox;
    @FXML
    private ToggleButton removeButton;
    @FXML
    private Button removeGoButton;
    @FXML
    private Button gofindButton;
    @FXML
    private Button GoButton;
    @FXML
    private Button insertGoButton;
    @FXML
    private HBox removeBox;
    @FXML
    public ChoiceBox<String> choiceBox;
    @FXML
    public ChoiceBox<String> removechoiceBox;
    @FXML
    public ChoiceBox<String> insertchoiceBox;
    @FXML
    public TextField iofremoveTextField;
    @FXML
    public TextField insertiTextField;
    @FXML
    public TextField insertvTextField;
    @FXML
    private Button stopButton;
    @FXML
    private Button sortButton;
    @FXML
    private Label iinsert;
    @FXML
    private Label valueinsert;
    @FXML
    private Label imove;
    @FXML
    public TextArea disTextArea;
    private RandomArrayGenerator randomArrayGenerator;
    private List list;
    private int[] createdArray;
    public ListDisplayController() {
        randomArrayGenerator = new RandomArrayGenerator();
        list = new List(15); // Replace "15" with the desired size of the list
    }
    @FXML
    public void handleEmptyButtonAction() {
        list.clear();
        list.print();
        drawArray(list.toArray());
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
            list.clear();
            for (int element : createdArray) {
                list.add(element);
            }
            list.print();
            drawArray(list.toArray());
        }
    }
    @FXML
    public void handleRandomButtonAction() {
        String selectedOption = choiceBox.getValue();
        int size = Integer.parseInt(selectedOption);
        int[] randomArray = randomArrayGenerator.generateRandomArray(size);
        createdArray = randomArray;
        list.clear();
        for (int element : createdArray) {
            list.add(element);
        }
        list.print();
        drawArray(list.toArray());
    }
    @FXML
    public void handleFindButtonAction() {
        if (list.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("List is empty");
            alert.setHeaderText(null);
            alert.setContentText("List is empty. Cannot find element.");
            alert.showAndWait();
        } else {
            searchBox.setVisible(findButton.isSelected());
        }
    }
    boolean found = false;
    @FXML
    public void handleGoFindButtonAction() {
        int[] currentArray = list.toArray();
        drawArray(currentArray);

        int number = Integer.parseInt(inputTextField.getText());
        disTextArea.setText("Find operation allows searching for a specific \n"+"element within a list.\n"+"In this example, we find index of "+ number );
        noteTextArea.setText("");
        SequentialTransition sequentialTransition = new SequentialTransition();

        for (int i = 0; i < list.size(); i++) {
            final int index = i;
            Circle circle = circles[i];

            fadeDuration = speedSlider.getValue();
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), circle);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);

            FillTransition fillTransition = new FillTransition(Duration.seconds(fadeDuration), circle);
            fillTransition.setFromValue(Color.ORANGE);
            fillTransition.setToValue(number == list.get(i) ? Color.LIME : Color.ORANGE);
            fillTransition.setDelay(Duration.seconds(fadeDuration));

            SequentialTransition indexTransition = new SequentialTransition(fadeTransition, fillTransition);
            sequentialTransition.getChildren().add(indexTransition);

            if (number == list.get(i)) {
                found = true;
                indexTransition.setOnFinished(e -> {
                    noteTextArea.setText("Element found at index " + index);
                });
                break;
            } else {
                indexTransition.setOnFinished(e -> {
                    noteTextArea.setText("Element " + list.get(index) + " is not the desired number.\n"+
                            "Searching for the next number...");
                });
            }
        }
        sequentialTransition.setOnFinished(event -> {
            if (!found) {
                noteTextArea.setText("Element not found.");
            }
        });
        sequentialTransition.play();
    }
    @FXML
    public void handleInsertButtonAction() {
        insertBox.setVisible(insertButton.isSelected());
        disTextArea.setText("Insert operation in a list involves adding an\n"+"element at a specified position within the list.");
        noteTextArea.setText("");
    }
    @FXML
    public void handleInsertChoiceBoxAction() {
        // create a random number
        int[] randomArray = RandomArrayGenerator.generateRandomArray(1);
        int value = randomArray[0];
        insertvTextField.setText(String.valueOf(value));

        String selectedChoice = insertchoiceBox.getValue();
        if (selectedChoice.equals("i in [1..N-1]")) {
            iinsert.setVisible(true);
            valueinsert.setVisible(true);
            insertiTextField.setVisible(true);
            insertvTextField.setVisible(true);
            insertGoButton.setVisible(true);
        } else {
            insertiTextField.setVisible(false);
            insertvTextField.setVisible(true);
            insertGoButton.setVisible(true);
            valueinsert.setVisible(true);
            iinsert.setVisible(false);
        }
    }
    @FXML
    public void handleInsertGoButtonAction() {
        String selectedValue = insertchoiceBox.getValue();
        if (selectedValue.equals("i=0(Head)")) {
            int value = Integer.parseInt(insertvTextField.getText());
            noteTextArea.setText("In this example, we insert the number " + value +" at \nthe first position: i = 0");

            if (isValidNumber(value)) {
                list.insertAtIndex(0, value);
                drawArray(list.toArray());

                Circle insertedCircle = circles[0];
                insertedCircle.setFill(Color.SKYBLUE);

                // Tạo hiệu ứng xuất hiện cho phần tử vừa được chèn
                insertedCircle.setOpacity(0.0);
                double fadeDuration = speedSlider.getValue();
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), insertedCircle);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
        } else if (selectedValue.equals("i=N(After Tail)")) {
            int value = Integer.parseInt(insertvTextField.getText());
            noteTextArea.setText("In this example, we insert the number " + value +" at \nthe last position: i = N");

            if (isValidNumber(value)) {
                list.insertAtIndex(list.size(), value);
                drawArray(list.toArray());

                Circle insertedCircle = circles[list.size() - 1];
                insertedCircle.setFill(Color.SKYBLUE);

                insertedCircle.setOpacity(0.0);
                double fadeDuration = speedSlider.getValue();
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), insertedCircle);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
        } else if (selectedValue.equals("i in [1..N-1]")) {
            int index = Integer.parseInt(insertiTextField.getText());
            int value = Integer.parseInt(insertvTextField.getText());
            noteTextArea.setText("In this example, we insert the number " + value +" at \nthe position: i = "+ index);

            if (index >= 1 && index <= list.size() - 1) {
                if (isValidNumber(value)) {
                    list.insertAtIndex(index, value);
                    drawArray(list.toArray());

                    Circle insertedCircle = circles[index];
                    insertedCircle.setFill(Color.SKYBLUE);

                    insertedCircle.setOpacity(0.0);
                    double fadeDuration = speedSlider.getValue();
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), insertedCircle);
                    fadeTransition.setFromValue(0.0);
                    fadeTransition.setToValue(1.0);
                    fadeTransition.play();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter a position from 1 to " + (list.size() - 1) + ".");
                alert.showAndWait();
            }
        }
    }
    @FXML
    public void handleRemoveButtonAction() {
        if (list.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("List is empty");
            alert.setHeaderText(null);
            alert.setContentText("List is empty. Cannot true element.");
            alert.showAndWait();
        } else {
            removeBox.setVisible(removeButton.isSelected());
            disTextArea.setText("Remove operation in a list: deleting an element\nfrom a specified position within the list.");
        }
    }
    @FXML
    private void handleRemoveChoiceBoxAction() {
        String selectedChoice = removechoiceBox.getValue();
        if (selectedChoice.equals("i in [1 ... N-2]")) {
            iofremoveTextField.setVisible(true);
            removeGoButton.setVisible(true);
            imove.setVisible(true);
        } else {
            iofremoveTextField.setVisible(false);
            imove.setVisible(false);
            removeGoButton.setVisible(true);
        }
    }
    @FXML
    public void handleRemoveGoButtonAction() {
        String selectedItem = removechoiceBox.getValue();
        int index ;
        if (selectedItem.equals("i=0(Head)")) {
            index = 0;
            noteTextArea.setText("In this example,we insert the \nfirt position i = 0 is the number: "+ list.get(index));
            circles[index].setFill(Color.PINK);
            fadeDuration = speedSlider.getValue();
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), circles[index]);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
            fadeTransition.setOnFinished(event -> {
                list.remove(index);
                drawArray(list.toArray());
            });
        } else if (selectedItem.equals("i=N-1 (Tail)")) {
            index = list.size() - 1;
            noteTextArea.setText("In this example,we insert the \nlast position i = "+ index +" is the number: "+ list.get(index));
            circles[index].setFill(Color.PINK);
            fadeDuration = speedSlider.getValue();
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), circles[index]);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
            fadeTransition.setOnFinished(event -> {
                list.remove(index);
                drawArray(list.toArray());
            });
        } else if (selectedItem.equals("i in [1 ... N-2]")) {
            index = Integer.parseInt(iofremoveTextField.getText());
            if (index >= 1 && index < list.size()-1) {
                noteTextArea.setText("In this example,we insert the \nlast position i = "+ index +" is the number: "+ list.get(index));
                circles[index].setFill(Color.PINK);
                fadeDuration = speedSlider.getValue();
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(fadeDuration), circles[index]);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
                fadeTransition.setOnFinished(event -> {
                    list.remove(index);
                    drawArray(list.toArray());
                });
            }
            else {
                noteTextArea.setText("");
                if (list.size() == 2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Cannot perform this operation as there are only 2 elements at the first position i = 0 and the last position i = N-1.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Please enter a index i from 1 to " + (list.size() - 2) + ".");
                    alert.showAndWait();
                }
            }
        }
    }
    private boolean isSortingPaused = false;
    private Task<Void> sortingTask;
    @FXML
    public void handleSortButtonAction() {
        if (list.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("List is empty");
            alert.setHeaderText(null);
            alert.setContentText("List is empty. Cannot sort elements.");
            alert.showAndWait();
        } else {
            disTextArea.setText("We use bubble sort for this application.\nBubble sort is a simple sorting algorithm.\nExample 42 3 -> 3 42");
            noteTextArea.setText("It iterates through the list and swaps adjacent\nelements until the list is sorted.");
            stopButton.setVisible(true);
            // Vô hiệu hóa nút sắp xếp và kích hoạt nút dừng lại
            sortButton.setDisable(true);
            stopButton.setDisable(false);
            sortingTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    boolean swapped = true; // Biến swapped để kiểm tra xem có cần tiếp tục sắp xếp hay không
                    int pass = 0; // Biến pass để theo dõi số lần đi qua
                    while (swapped) {
                        swapped = false;

                        for (int j = 0; j < list.size() - pass - 1; ++j) {
                            final int currentIndex = j; // Tạo biến currentIndex trong phạm vi vòng lặp for

                            // Kiểm tra trạng thái tạm ngừng
                            while (isSortingPaused) {
                                Thread.sleep(100); // Đợi 100ms và kiểm tra lại
                            }
                            // Đặt màu sắc cho hai số so sánh
                            Platform.runLater(() -> {
                                circles[currentIndex].setFill(Color.YELLOW);
                                circles[currentIndex + 1].setFill(Color.YELLOW);
                            });

                            // Tạm dừng luồng để tạo hiệu ứng chậm rãi
                            Thread.sleep(1000);

                            int currentElement = list.get(j);
                            int nextElement = list.get(j + 1);

                            if (nextElement < currentElement) {
                                list.remove(j);
                                list.insertAtIndex(j, nextElement);
                                list.remove(j + 1);
                                list.insertAtIndex(j + 1, currentElement);
                                swapped = true; // Đánh dấu đã đổi chỗ

                                // Cập nhật hiển thị mảng sau khi đổi vị trí
                                Platform.runLater(() -> {
                                    drawArray(list.toArray());
                                });
                            }
                            // Đặt màu sắc mặc định cho hai số so sánh
                            Platform.runLater(() -> {
                                circles[currentIndex].setFill(Color.WHITE);
                                circles[currentIndex + 1].setFill(Color.WHITE);
                            });
                        }
                        pass++;
                    }
                    // Kích hoạt lại nút sắp xếp và vô hiệu hóa nút dừng lại khi hoàn thành sắp xếp
                    Platform.runLater(() -> {
                        sortButton.setDisable(false);
                        stopButton.setDisable(true);
                    });
                    return null;
                }
            };
            this.sortingTask = sortingTask; // Lưu trữ tham chiếu đến task sắp xếp
            Thread sortingThread = new Thread(sortingTask);
            sortingThread.start();
        }
    }
    @FXML
    public void handleStopButtonAction(ActionEvent actionEvent) {
        isSortingPaused = !isSortingPaused; // Đảo ngược trạng thái tạm ngừng
        stopButton.setText(isSortingPaused ? "Resume" : "Pause");
        if (!isSortingPaused) {
            synchronized (sortingTask) {
                sortingTask.notify(); // Gửi thông báo để tiếp tục quá trình sắp xếp nếu đang tạm ngừng
            }
        }
    }
}

