package com.sinco.CDSFase2;

import com.sinco.CDSFase2.controllers.ApiAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        FXMLLoader fxmlLoader = new FXMLLoader(OptimizationAplication.class.getResource("/views/EmbalsesView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.getIcons().add(new Image(OptimizationAplication.class.getResourceAsStream("/com/sinco/CDSFase2/images/logo.png")));
        stage.setTitle("Green lake city");
        stage.show();
        ApiAccess api = new ApiAccess();
        api.locationData();
        api.listData("wind");
        api.itemData("wind","WIND001");
    }

    public static void main(String[] args) {
        if(checkDrivers()){
            launch();
        }
    }
    private static boolean checkDrivers() {
        /*try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }*/
        return true;
    }

}