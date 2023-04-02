package com.sinco.CDSFase2.controllers;

import com.sinco.CDSFase2.OptimizationAplication;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemPresaController implements Initializable {
    @FXML
    private Label central;

    @FXML
    private Label produccion;

    @FXML
    private StackedBarChart<?, ?> tabla;

    @FXML
    private Circle turbina;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(String central, int potencia, int aguaUsable) {
        this.central.setText(central);
        this.produccion.setText(potencia + " MW");
        XYChart.Series serie = new XYChart.Series<>();
        serie.getData().add(new XYChart.Data("Cantidad utilizable: "+aguaUsable+" hm3",aguaUsable));
        tabla.getData().addAll(serie);
        if (aguaUsable > 0) {
            Image image = new Image(getClass().getResourceAsStream("/com/sinco/CDSFase2/images/turbinaAgua.png"));
            turbina.setFill(new ImagePattern(image));
        } else {
            Image image = new Image(getClass().getResourceAsStream("/com/sinco/CDSFase2/images/turbina.png"));
            turbina.setFill(new ImagePattern(image));
        }
        if (aguaUsable != 0) {
            RotateTransition rt = new RotateTransition(Duration.seconds(8), this.turbina);
            rt.setByAngle(360);
            rt.setAutoReverse(false);
            rt.setRate(3);
            rt.setCycleCount(100);
            rt.play();
        }
    }
}
