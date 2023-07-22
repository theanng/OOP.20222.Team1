module OOP_Project_Module {
    requires javafx.swing;
    requires javafx.media;
    requires javafx.base;
    requires javafx.web;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens screen;
    opens display.listdisplay;
    opens display.stackdisplay;
    opens display.queuedisplay;
    opens display;

    exports screen;
    exports display;
    exports display.stackdisplay;
    exports display.listdisplay;
    exports display.queuedisplay;
}