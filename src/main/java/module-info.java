module com.example.webremind {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.webremind to javafx.fxml;
    exports com.example.webremind;
}