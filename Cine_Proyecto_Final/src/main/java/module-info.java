module org.example.cine_proyecto_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires kotlin.result.jvm;
    requires kotlinx.serialization.core;
    requires logging.jvm;
    requires sqlite.driver;
    requires runtime.jvm;


    opens org.example.cine_proyecto_final to javafx.fxml;
    exports org.example.cine_proyecto_final;
}