package com.sinco.CDSFase2;

import com.sinco.CDSFase2.ApiAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class OptimizationAplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(OptimizationAplication.class.getResource("/views/EmbalsesView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.getIcons().add(new Image(OptimizationAplication.class.getResourceAsStream("/com/sinco/CDSFase2/images/logo.png")));
        stage.setTitle("Green lake city");
        stage.show();*/

        ApiAccess api = new ApiAccess();
        System.out.println(api.getCentrales());

        FXMLLoader fxmlLoader = new FXMLLoader(OptimizationAplication.class.getResource("/views/Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.getIcons().add(new Image(OptimizationAplication.class.getResourceAsStream("/com/sinco/CDSFase2/images/logo.png")));
        stage.setTitle("Green lake city");
        stage.show();
/*
        OptimizationAlgorithm oa = new OptimizationAlgorithm();
        ApiKafka apiKafka = new ApiKafka();
        apiKafka.setAlgorithm(oa);
        apiKafka.getKafkaAlgo();
        ApiAccess api = new ApiAccess();
        JSONArray listaCentrales = api.listData("geo");
        System.out.println(api.itemData("geo",listaCentrales.getString(0)));*/
        /*Edgar edgar = new Edgar();
        edgar.main();*/
    }

    public static void main(String[] args) {
        launch();
    }


}