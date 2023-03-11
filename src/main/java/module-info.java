module com.example.cdsfase2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;


    opens com.sinco.CDSFase2 to javafx.fxml;
    exports com.sinco.CDSFase2;
}