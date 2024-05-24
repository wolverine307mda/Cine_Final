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
    requires koin.core.jvm;

    // Agrega el módulo kotlin.test
    requires kotlin.test;

    opens org.example.cine_proyecto_final to javafx.fxml;
    exports org.example.cine_proyecto_final;

    // Controllers
    opens org.example.cine_proyecto_final.controllers.general to javafx.fxml;
    exports org.example.cine_proyecto_final.controllers.general;

    // Administrador Controllers
    opens org.example.cine_proyecto_final.controllers.administrador to javafx.fxml;
    exports org.example.cine_proyecto_final.controllers.administrador;

    // Cliente Controllers
    opens org.example.cine_proyecto_final.controllers.cliente to javafx.fxml;
    exports org.example.cine_proyecto_final.controllers.cliente;

    // Sesión Controllers
    opens org.example.cine_proyecto_final.controllers.sesion to javafx.fxml;
    exports org.example.cine_proyecto_final.controllers.sesion;

    // Abre el paquete de pruebas para kotlin.test
    opens org.example.cine_proyecto_final.cuentas.repository;
}
