module com.example.phonebookfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.phonebookfx to javafx.fxml;
    exports com.example.phonebookfx;
}