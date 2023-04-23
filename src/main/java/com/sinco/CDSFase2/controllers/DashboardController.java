package com.sinco.CDSFase2.controllers;

import com.sinco.CDSFase2.ApiKafka;
import com.sinco.CDSFase2.OptimizationAlgorithm;
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

    private String nombres[] = {"GEO001", "GEO002", "WAT001", "WAT002","WIND001","WIND002","WIND003", "SUN001", "SUN002", "SUN003", "COAL001"};

    @FXML
    void btnIniciar(ActionEvent event) {
        OptimizationAlgorithm oa = new OptimizationAlgorithm();
        oa.setInstance(this);
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
                cc.setData(nombres[i],"NO");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData(double[] optimizacion, double[] emisiones, double[] costes, double emisionC, double porPant1, double porPant2){
        vbCentrales.getChildren().clear();
        for (int i = 0; i < 11; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/CentralItem.fxml"));
                HBox hb = fxmlLoader.load();
                vbCentrales.getChildren().add(hb);
                CentralController cc = fxmlLoader.getController();
                if(optimizacion[i] != 0.0){
                    cc.setData(nombres[i],"SI");
                }else{
                    cc.setData(nombres[i],"NO");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        double costeTotal = 0.0;
        for ( double coste: costes) {
            costeTotal +=  coste;
        }
        double emisionVerde = 0.0;
        for(int i = 0; i < 10; i++){
            emisionVerde += emisiones[i];
        }
        double produccionVerde = 0.0;
        for(int i = 0; i < 10; i++){
            produccionVerde += optimizacion[i];
        }
        this.lblCoste.setText(costeTotal + " €");
        this.lblEmiReno.setText(emisionVerde + " T");
        this.lblEmiCoal.setText(emisionC + " T");
        this.lblProdReno.setText(produccionVerde + " MWH");
        this.lblProdCoal.setText(optimizacion[10] + " MWh");
        this.lblPantano1.setText(porPant1 + " %");
        this.lblPantano2.setText(porPant2 + " %");
    }
}
