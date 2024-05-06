module org.example.cine {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    // Kotlin
    requires kotlin.stdlib;
    // Logger
    requires logging.jvm;
    requires org.slf4j;
    // Result
    requires kotlin.result.jvm;
    // Open
    requires open;
    // Koin
    requires koin.core.jvm;


    opens org.example.cine to javafx.fxml;
    exports org.example.cine;
}