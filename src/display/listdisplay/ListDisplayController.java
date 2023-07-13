package display.listdisplay;

import array.RandomArrayGenerator;
import datastructures.list.List;
import display.GeneralDisplayController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.NoSuchElementException;
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

    private RandomArrayGenerator randomArrayGenerator;

    private List list;

    private int[] createdArray;

    public ListDisplayController() {
        randomArrayGenerator = new RandomArrayGenerator();
        list = new List(15); // Replace "12" with the desired size of the stack
    }
    public void handleRandomButtonAction(ActionEvent actionEvent) {
        String selectedOption = choiceBox.getValue();
        int size = Integer.parseInt(selectedOption);
        int[] randomArray = randomArrayGenerator.generateRandomArray(size);
        createdArray = randomArray;
        // Clear the stack and push elements from the new createdArray
        list.clear();
        for (int element : createdArray) {
            list.add(element);
        }
        // Print the updated stack
        list.print();
        drawArray(list.toArray());
    }

    @FXML
    public void handleEmptyButtonAction(ActionEvent actionEvent) {
        list.clear();
        // In ra thông báo hàng đợi đã được xóa
        System.out.println("Đã xóa hàng đợi.");
        // In ra hàng đợi đã cập nhật
        list.print();
        // Vẽ lại mảng đã cập nhật
        drawArray(list.toArray());
    }

    public void handleUserListButtonAction(ActionEvent actionEvent) {
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
            list.clear();
            for (int element : createdArray) {
                list.add(element);
            }
            // Print the updated stack
            list.print();
            drawArray(list.toArray());
        } else {
            System.out.println("User defined list canceled. Cannot create new Stack.");
        }
    }

    public void handleFindButtonAction(ActionEvent actionEvent) {
        if (list.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("List is empty");
            alert.setHeaderText(null);
            alert.setContentText("List is empty. Cannot find element.");
            alert.showAndWait();
            return;
        } else {
            searchBox.setVisible(findButton.isSelected());
        }
    }

    @FXML
    public void handleGoFindButtonAction(ActionEvent actionEvent) {
        try {
            int number = Integer.parseInt(inputTextField.getText());

            int index = list.find(number);

            for (int i = 0; i < list.size(); i++) {
                if (i == index) {
                    circles[i].setFill(Color.LIME);
                    break;// Đặt màu sắc xanh lá cây cho số đã tìm thấy
                } else {
                    circles[i].setFill(Color.ORANGE); // Đặt màu sắc mặc định cho các số khác
                }
            }
        } catch (NumberFormatException e) {
            // Xử lý nếu người dùng không nhập số hợp lệ
        } catch (NoSuchElementException e) {
            // Xử lý nếu không tìm thấy số trong mảng
        }
    }

    public void handleInsertButtonAction(ActionEvent actionEvent) {
        insertBox.setVisible(insertButton.isSelected());
    }

    public void handleInsertChoiceBoxAction(ActionEvent actionEvent) {
        String selectedChoice = insertchoiceBox.getValue();
        if (selectedChoice.equals("i in [1..N-1]")) {
            insertiTextField.setVisible(true);
            insertvTextField.setVisible(true);
            insertGoButton.setVisible(true);
        } else {
            insertiTextField.setVisible(false);
            insertvTextField.setVisible(true);
            insertGoButton.setVisible(true);
        }
    }

    @FXML
    public void handleInsertGoButtonAction(ActionEvent actionEvent) {
        try {
            String selectedValue = insertchoiceBox.getValue();

            if (selectedValue.equals("i=0(Head)")) {
                int value = Integer.parseInt(insertvTextField.getText());

                // Thực hiện chèn vào đầu mảng
                list.insertAtIndex(0, value);
            } else if (selectedValue.equals("i=N(After Tail)")) {
                int value = Integer.parseInt(insertvTextField.getText());

                // Thực hiện chèn vào vị trí sau phần tử cuối mảng
                list.insertAtIndex(list.size(), value);
            } else if (selectedValue.equals("i in [1..N-1]")) {
                int index = Integer.parseInt(insertiTextField.getText());
                int value = Integer.parseInt(insertvTextField.getText());

                // Thực hiện chèn vào vị trí index trong mảng
                list.insertAtIndex(index, value);
            }

            // Gọi lại phương thức drawArray để cập nhật giao diện
            drawArray(list.toArray());
        } catch (NumberFormatException e) {
            // Xử lý nếu người dùng không nhập số hợp lệ
        } catch (IndexOutOfBoundsException e) {
            // Xử lý nếu vị trí chèn không hợp lệ
        }
    }

    public void handleRemoveButtonAction(ActionEvent actionEvent) {
        removeBox.setVisible(removeButton.isSelected());

    }

    @FXML
    private void handleRemoveChoiceBoxAction() {
        String selectedChoice = removechoiceBox.getValue();
        if (selectedChoice.equals("i in [1 ... N-2]")) {
            iofremoveTextField.setVisible(true);
            removeGoButton.setVisible(true);
        } else {
            iofremoveTextField.setVisible(false);
            removeGoButton.setVisible(true);
        }
    }

    @FXML
    public void handleRemoveGoButtonAction(ActionEvent actionEvent) {
        try {
            String selectedItem = removechoiceBox.getValue();
            int index = -1;

            if (selectedItem.equals("i=0(Head)")) {
                index = 0;
            } else if (selectedItem.equals("i=N-1 (Tail)")) {
                index = list.size() - 1;
            } else if (selectedItem.equals("i in [1 ... N-2]")) {
                index = Integer.parseInt(iofremoveTextField.getText());

            }

            if (index >= 0 && index < list.size()) {
                list.remove(index);

                // Thực hiện cập nhật hiển thị mảng
                drawArray(list.toArray());
            }
        } catch (NumberFormatException e) {
            // Xử lý nếu người dùng không nhập số hợp lệ (trường hợp i in [1 ... N-2])
        } catch (IndexOutOfBoundsException e) {
            // Xử lý nếu người dùng chọn vị trí không hợp lệ (trường hợp i in [1 ... N-2])
        }
    }

    @FXML
    private Button stopButton; // Đặt ID cho nút dừng lại trong file FXML

    @FXML
    private Button sortButton;

    private boolean isSortingPaused = false;

    @FXML
    public void handleSortButtonAction(ActionEvent actionEvent) {
        // Vô hiệu hóa nút sắp xếp và kích hoạt nút dừng lại
        sortButton.setDisable(true);
        stopButton.setDisable(false);

        Task<Void> sortingTask = new Task<Void>() {
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
                            circles[currentIndex].setFill(Color.RED);
                            circles[currentIndex + 1].setFill(Color.RED);
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
                            circles[currentIndex].setFill(Color.ORANGE);
                            circles[currentIndex + 1].setFill(Color.ORANGE);
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

    private Task<Void> sortingTask;
    @FXML
    public void handleStopButtonAction(ActionEvent actionEvent) {
        isSortingPaused = !isSortingPaused; // Đảo ngược trạng thái tạm ngừng
        stopButton.setText(isSortingPaused ? "Resume" : "Pause"); // Đổi văn bản của nút dừng lại

        if (!isSortingPaused) {
            synchronized (sortingTask) {
                sortingTask.notify(); // Gửi thông báo để tiếp tục quá trình sắp xếp nếu đang tạm ngừng
            }
        }
    }
}

