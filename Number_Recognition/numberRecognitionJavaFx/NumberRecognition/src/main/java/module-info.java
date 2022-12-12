module com.example.numberrecognition {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
//    requires javafx.swing;
//    requires javafx.swt;
    requires java.desktop;
    requires java.security.jgss;
    requires javafx.swing;


    opens numberrecognition to javafx.fxml;
    exports numberrecognition;
}