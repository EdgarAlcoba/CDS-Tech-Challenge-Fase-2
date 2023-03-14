package com.sinco.CDSFase2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class OptimizationAplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(OptimizationAplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/
        String embalse = "Barrios de Luna";
        int  year = 2023;
        stage.setTitle("Datos del embalse "+embalse+". Año: "+year);
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Días del año");
        yAxis.setLabel("Cantidad en hm3");

        ArrayList<Integer>[] listaFinal = new ArrayList[53];
        for (int i = 0; i < 53; i++) {
            listaFinal[i] = new ArrayList<Integer>();
        }
        int matrizFinal[] = new int[53];
        DbAccess dbAccess = new DbAccess();


        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);
        XYChart.Series series = new XYChart.Series();
        for(int j = year-3; j < year; j++) {
            //series.setName("Embalse: " + embalse + "  Año: " + year);
            ArrayList<Pair<Integer, String>> list = dbAccess.getAguaActual(embalse, j);
            ArrayList<Pair<Integer, Integer>> lista = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                float day = yearDay(parseInt(list.get(i).getValue().substring(0, 2)), parseInt(list.get(i).getValue().substring(3, 5)), parseInt(list.get(i).getValue().substring(6, 10)));
                lista.add(new Pair<>(list.get(i).getKey(), (int)day/7));
                //series.getData().add(new XYChart.Data((int)day/7, list.get(i).getKey()));
            }

            ArrayList<Integer>[] listaMedia = new ArrayList[53];
            for (int i = 0; i < 53; i++) {
                listaMedia[i] = new ArrayList<Integer>();
            }

            for (int i = 0; i < lista.size(); i++){
                listaMedia[(int)lista.get(i).getValue()].add(lista.get(i).getKey());
            }
            int listaSuma[] = new int[53];
            for (int i = 0; i < 53; i++) {
                int aux = promedio(listaMedia[i]);
                listaSuma[i] = aux;
                if(aux > 0) {
                    //series.getData().add(new XYChart.Data(i, aux));
                    listaFinal[i].add(aux);
                }
            }
        }

        for (int i = 0; i < 53; i++) {
            int aux = promedio(listaFinal[i]);
            matrizFinal[i] = aux;
            if(aux > 0) {
                series.getData().add(new XYChart.Data(i, aux));
            }
        }

        XYChart.Series pastYearData = new XYChart.Series();
        XYChart.Series currentYearData = new XYChart.Series();
        ArrayList<Pair<Integer, String>> list = dbAccess.getAguaActual(embalse, year-1);
        ArrayList<Pair<Integer, String>> lista = dbAccess.getAguaActual(embalse, year);
        for (int i = 0; i < list.size(); i++) {
            float day = yearDay(parseInt(list.get(i).getValue().substring(0, 2)), parseInt(list.get(i).getValue().substring(3, 5)), parseInt(list.get(i).getValue().substring(6, 10)));
            pastYearData.getData().add(new XYChart.Data((int)day/7, list.get(i).getKey()));

        }
        for (int i = 0; i < lista.size(); i++){
            float dia = yearDay(parseInt(lista.get(i).getValue().substring(0, 2)), parseInt(lista.get(i).getValue().substring(3, 5)), parseInt(lista.get(i).getValue().substring(6, 10)));
            currentYearData.getData().add(new XYChart.Data((int)dia/7, lista.get(i).getKey()));
        }

        currentYearData.setName("Año: " + year);
        pastYearData.setName("Año: " + (year - 1));
        series.setName("Media de los últimos 15 años");
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(currentYearData);
        lineChart.getData().add(pastYearData);
        lineChart.getData().add(series);
        lineChart.setLegendSide(Side.RIGHT);

        series.getNode().setStyle("-fx-stroke-width: 2; -fx-stroke: red; -fx-stroke-dash-array: 2 12 12 2");
        pastYearData.getNode().setStyle("-fx-stroke: skyblue");
        currentYearData.getNode().setStyle("-fx-stroke: greenyellow");
        Scene scene  = new Scene(lineChart,800,800);

        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        if(checkDrivers()){
            launch();
        }
    }
    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }

    public int yearDay(int day, int month, int year){
        switch (month){
            case 12:
                day+=30;
            case 11:
                day+=31;
            case 10:
                day+=30;
            case 9:
                day+=31;
            case 8:
                day+=31;
            case 7:
                day+=30;
            case 6:
                day+=31;
            case 5:
                day+=30;
            case 4:
                day+=31;
            case 3:
                if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))){
                    day+=29;
                } else {
                    day+=28;
                }
            case 2:
                day+=31;
        }
        return day;
    }

    public int promedio(ArrayList<Integer> lista){
        int numElems = lista.size();
        int suma = 0;
        for(int i = 0; i<numElems;i++){
            if(lista.get(i) > 0){
                suma+=lista.get(i);
            } else {
                numElems--;
            }
        }
        if(numElems == 0){
            return -1;
        } else {
            return (int) suma/numElems;
        }
    }
}