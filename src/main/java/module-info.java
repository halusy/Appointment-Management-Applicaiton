module com.example.AMP {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.AMP to javafx.fxml;
    exports com.example.AMP;

    exports com.example.AMP.controller;
    opens com.example.AMP.controller to javafx.fxml;

}