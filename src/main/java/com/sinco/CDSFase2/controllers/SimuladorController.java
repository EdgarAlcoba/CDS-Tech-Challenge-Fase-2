package com.sinco.CDSFase2.controllers;

import com.sinco.CDSFase2.OptimizationAplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
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
import org.sqlite.core.DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SimuladorController {
    @FXML
    private GridPane GP_panel;
    private String perfil;
    private ArrayList<String> centrales = new ArrayList<>();
    ArrayList<Integer> aguaUtilizable = new ArrayList<>();

    public void start(String perfil) {
        this.perfil = perfil;
        ArrayList<String> listaCentrales = new ArrayList<>();
        listaCentrales.add("Central de Aldeadávila");
        listaCentrales.add("Central José María de Oriol");
        listaCentrales.add("Central de Villarino");
        listaCentrales.add("Central de Cortes-La Muela");
        listaCentrales.add("Central de Saucelle");
        listaCentrales.add("Cedillo");
        listaCentrales.add("Estany-Gento Sallente");
        listaCentrales.add("Central de Tajo de la Encantada");
        listaCentrales.add("Central de Aguayo");
        listaCentrales.add("Mequinenza");
        listaCentrales.add("Mora de Luna");
        ArrayList<Integer> listaAguaUtilizable = new ArrayList<>();
        int column = 1;
        int row = 0;
        try {
            for (int i = 0; i < listaCentrales.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/ItemPresaView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemPresaController itemPresaController = fxmlLoader.getController();
                DBData dbData = new DBData();
                int aguaDisponible = 0;
                switch (listaCentrales.get(i)) {
                    case "Central de Aldeadávila":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Aldeadávila"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 1243 : 0, aguaDisponible);
                        break;
                    case "Central José María de Oriol":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Alcántara"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 957 : 0, aguaDisponible);
                        break;
                    case "Central de Villarino":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Almendra"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 857 : 0, aguaDisponible);
                        break;
                    case "Central de Cortes-La Muela":
                        aguaDisponible = calculaAgua(dbData.getLatestData("La Muela"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 630 : 0, aguaDisponible);
                        break;
                    case "Central de Saucelle":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Saucelle"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 520 : 0, aguaDisponible);
                        break;
                    case "Cedillo":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Cedillo"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 500 : 0, aguaDisponible);
                        break;
                    case "Estany-Gento Sallente":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Sallente"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 468 : 0, aguaDisponible);
                        break;
                    case "Central de Tajo de la Encantada":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Guadalhorce-Guadalteba"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 360 : 0, aguaDisponible);
                        break;
                    case "Central de Aguayo":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Alsa - Mediajo"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 360 : 0, aguaDisponible);
                        break;
                    case "Mequinenza":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Mequinenza"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 324 : 0, aguaDisponible);
                        break;
                    case "Mora de Luna":
                        aguaDisponible = calculaAgua(dbData.getLatestData("Barrios de Luna"));
                        itemPresaController.setData(listaCentrales.get(i), (aguaDisponible > 0) ? 80 : 0, aguaDisponible);
                        break;
                }
                if (column == 5) {
                    column = 1;
                    row++;
                }
                GP_panel.add(anchorPane, column++, row);
                listaAguaUtilizable.add(aguaDisponible);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AnchorPane ap = new AnchorPane();
        ap.setId("mapaFondo");
        GP_panel.add(ap, column++, row);
        this.centrales = listaCentrales;
        this.aguaUtilizable = listaAguaUtilizable;
    }

    private int calculaAgua(int[] data) {
        int exit = 0;
        switch (this.perfil) {
            case "AHORRADOR":
                if (data[0] > data[2]) {
                    exit = data[0] - data[2];
                }
                break;
            case "EQUILIBRADO":
                if ((data[0] > data[1]) || (data[0] > data[2])) {
                    exit = (data[1] > data[2]) ? (data[0] - data[2]) : (data[0] - data[1]);
                }
                break;
            case "PRODUCTOR":
                break;
        }
        return exit;
    }

    @FXML
    void test(ActionEvent event) {
        System.out.println("Funciona");
        PieChart pieChart = new PieChart();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (int i = 0; i < centrales.size(); i++) {
            if (aguaUtilizable.get(i) != 0) {
                data.add(new PieChart.Data(centrales.get(i), aguaUtilizable.get(i)));
            }
        }
        pieChart.setData(data);
        pieChart.setLegendSide(Side.BOTTOM);
        int produccionTotal = 0;
        for (int i = 0; i < aguaUtilizable.size(); i++) {
            if (aguaUtilizable.get(i) > 0) {
                produccionTotal += getPotencia(centrales.get(i));
            }
        }
        pieChart.setTitle("Producción total: " + produccionTotal + " MW");
        Stage stage = new Stage();
        pieChart.setStyle("-fx-background-color: BEIGE");
        Scene scene = new Scene(pieChart, 600, 400);
        stage.setScene(scene);
        try {
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/sinco/CDSFase2/images/logo.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Gráfica de la producción de las centrales en el perfil: " + perfil);
        stage.show();
    }

    private int getPotencia(String central) {
        switch (central) {
            case "Central de Aldeadávila":
                return 1243;
            case "Central José María de Oriol":
                return 957;
            case "Central de Villarino":
                return 857;
            case "Central de Cortes-La Muela":
                return 630;
            case "Central de Saucelle":
                return 520;
            case "Cedillo":
                return 500;
            case "Estany-Gento Sallente":
                return 468;
            case "Central de Tajo de la Encantada":
                return 360;
            case "Central de Aguayo":
                return 360;
            case "Mequinenza":
                return 324;
            case "Mora de Luna":
                return 80;
        }
        return -1;
    }
}
