package com.sinco.CDSFase2.controllers;

import com.sinco.CDSFase2.ApiKafka;
import com.sinco.CDSFase2.OptimizationAlgorithm;
import com.sinco.CDSFase2.OptimizationAplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private Label lblCoste;

    @FXML
    private Label lblEmiCoal;

    @FXML
    private Label lblEmiReno;

    @FXML
    private Label lblPantano1;

    @FXML
    private Label lblPantano2;

    @FXML
    private Label lblProdCoal;

    @FXML
    private Label lblProdReno;

    @FXML
    private Label lblScoring;

    @FXML
    private VBox vbAreas;

    @FXML
    private VBox vbCentrales;

    @FXML
    void btnIniciar(ActionEvent event) {
        OptimizationAlgorithm oa = new OptimizationAlgorithm();
        ApiKafka apk = new ApiKafka();
        apk.iniciarHilo(oa);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (int i = 0; i < 95; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/AreaItem.fxml"));
                HBox hb = fxmlLoader.load();
                vbAreas.getChildren().add(hb);
                AreaController ac = fxmlLoader.getController();
                ac.setData("Area "+ (i+1));
            }

            for (int i = 0; i < 11; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/CentralItem.fxml"));
                HBox hb = fxmlLoader.load();
                vbCentrales.getChildren().add(hb);
                CentralController cc = fxmlLoader.getController();
                cc.setData("Central "+ (i+1),"SI");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
