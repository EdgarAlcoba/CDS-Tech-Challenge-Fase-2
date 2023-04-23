module com.example.cdsfase2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.json;
    requires kafka.clients;
    requires ortools.java;


    opens com.sinco.CDSFase2 to javafx.fxml;
    exports com.sinco.CDSFase2;
    exports com.sinco.CDSFase2.controllers;
    opens com.sinco.CDSFase2.controllers to javafx.fxml;
}