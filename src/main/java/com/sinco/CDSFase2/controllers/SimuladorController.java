package com.sinco.CDSFase2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SimuladorController implements Initializable {
    @FXML
    private GridPane GP_panel;
    private String perfil;
    public SimuladorController(String perfil){
        this.perfil = perfil;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        /*System.out.println("Entra");
        Stage stage = new Stage();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data(1,5));
        lineChart.getData().add(series);
        Scene scene = new Scene(lineChart, 400, 200);
        stage.setScene(scene);
        stage.show();*/
        Stage stage = new Stage();
        /*FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("src/main/resources/views/ItemPresaView.fxml"));
        stage.setTitle("Simulacion");
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent, 600, 400));
        *
        ArrayList<String> listaCentrales = new ArrayList<>();
        listaCentrales.add("Central de Aldeadávila");
        listaCentrales.add("Central José María de Oriol");
        listaCentrales.add("Central de Villarino");
        listaCentrales.add("Central de Cortes-La Muela");
        listaCentrales.add("La Muela");
        listaCentrales.add("Central de Saucelle");
        listaCentrales.add("Cedillo");
        listaCentrales.add("Estany-Gento Sallente");
        listaCentrales.add("Central de Tajo de la Encantada");
        listaCentrales.add("Central de Aguayo");
        listaCentrales.add("Mora de Luna");
        for(String central:listaCentrales){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../src/main/resources/views/ItemPresaView.fxml"));
            HBox hBox = fxmlLoader.load();
            ItemPresaController itemPresaController == fxmlLoader.getController();
            //itemPresaController.setData();
        }*/
    }
}
