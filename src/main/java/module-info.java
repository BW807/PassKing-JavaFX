module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.javafx to javafx.fxml;
    exports com.example.javafx;
    exports com.example.javafx.Windows;
    opens com.example.javafx.Windows to javafx.fxml;
    exports com.example.javafx.Utils;
    opens com.example.javafx.Utils to javafx.fxml;
}