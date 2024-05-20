module org.example.cine_proyecto_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires kotlin.result.jvm;
    requires logging.jvm;
    requires sqlite.driver;
    requires kotlinx.serialization.core;
    requires runtime.jvm;
    requires org.slf4j;
    requires java.sql;
    requires kotlinx.serialization.json;


    opens org.example.cine_proyecto_final to javafx.fxml;
    exports org.example.cine_proyecto_final;

    // Controllers
    opens org.example.cine_proyecto_final.controllers to javafx.fxml;
    exports org.example.cine_proyecto_final.controllers;
}