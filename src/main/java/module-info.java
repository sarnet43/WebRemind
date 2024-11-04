module com.example.webremind {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.webremind to javafx.fxml;
    exports com.example.webremind;
}