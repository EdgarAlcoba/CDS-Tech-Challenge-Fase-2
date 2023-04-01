package com.sinco.CDSFase2.controllers;

import com.sinco.CDSFase2.OptimizationAplication;
import com.sinco.CDSFase2.controllers.DBData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class EmbalsesController {

    @FXML
    private Button Btn_ObtenerDatos;

    @FXML
    private MenuButton MB_Centrales;

    @FXML
    private MenuItem MI_Aguayo;

    @FXML
    private MenuItem MI_Aldeadavila;

    @FXML
    private MenuItem MI_Cedillo;

    @FXML
    private MenuItem MI_Luna;

    @FXML
    private MenuItem MI_Mequinenza;

    @FXML
    private MenuItem MI_Muela;

    @FXML
    private MenuItem MI_Oriol;

    @FXML
    private MenuItem MI_Sallente;

    @FXML
    private MenuItem MI_Saucelle;

    @FXML
    private MenuItem MI_Villarino;

    @FXML
    private MenuItem MI_encantada;

    @FXML
    private TextArea TA_Info;

    @FXML
    private TextField TF_year;

    private String tipoPerfil = "";
    private String embalse = "";

    @FXML
    void obtenerDatos(ActionEvent event) {
        if (MB_Centrales.getText().equals("Centrales")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione una central", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            int year = parseInt(TF_year.getText());
            if (year > 2004 && year <= 2023) {
                DBData dbData = new DBData();
                switch (MB_Centrales.getText()) {
                    case "Central de Aldeadávila":
                        dbData.tablaEmbalse("Aldeadávila", parseInt(TF_year.getText()));
                        break;
                    case "Central José María de Oriol":
                        dbData.tablaEmbalse("Alcántara", parseInt(TF_year.getText()));
                        break;
                    case "Central de Villarino":
                        dbData.tablaEmbalse("Almendra", parseInt(TF_year.getText()));
                        break;
                    case "Central de Cortes-La Muela":
                        dbData.tablaEmbalse("La Muela", parseInt(TF_year.getText()));
                        break;
                    case "Central de Saucelle":
                        dbData.tablaEmbalse("Saucelle", parseInt(TF_year.getText()));
                        break;
                    case "Cedillo":
                        dbData.tablaEmbalse("Cedillo", parseInt(TF_year.getText()));
                        break;
                    case "Estany-Gento Sallente":
                        dbData.tablaEmbalse("Sallente", parseInt(TF_year.getText()));
                        break;
                    case "Central de Tajo de la Encantada":
                        dbData.tablaEmbalse("Guadalhorce-Guadalteba", parseInt(TF_year.getText()));
                        break;
                    case "Central de Aguayo":
                        dbData.tablaEmbalse("Alsa - Mediajo", parseInt(TF_year.getText()));
                        break;
                    case "Mequinenza":
                        dbData.tablaEmbalse("Mequinenza", parseInt(TF_year.getText()));
                        break;
                    case "Mora de Luna":
                        dbData.tablaEmbalse("Barrios de Luna", parseInt(TF_year.getText()));
                        break;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Introduzca un año mayor a 2005", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Introduzca un año mayor a 2005", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void onHelloButtonClick(ActionEvent event) {
        /*ApiAccess apiAccess = new ApiAccess();
        String url = "https://opendata.aemet.es/opendata/api/prediccion/especifica/playa/0406601";
        String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlYWxjb2MwMEBlc3R1ZGlhbnRlcy51bmlsZW9uLmVzIiwianRpIjoiNjhiZDVhZWMtMWJjMC00NGJkLWI5NmYtODA3YTdiOGIzYTNiIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE2NzgyOTkyNzQsInVzZXJJZCI6IjY4YmQ1YWVjLTFiYzAtNDRiZC1iOTZmLTgwN2E3YjhiM2EzYiIsInJvbGUiOiIifQ.HYwpMbvqdfiQgUtbAX2EWg-lh8rmNBXyAflCt1L3V2U";
        welcomeText.setText(apiAccess.peticionApi(url,apiKey).toString());*/
    }

    @FXML
    void information(ActionEvent event) {
        TextArea ta = new TextArea();
        ta.setText("Información sobre los perfiles:\n\n" +
                    "Perfil ahorrador:\n" +
                    "Garantiza el abastecimiento de agua durante todo el año manteniendo una producción de energía moderada.\n\n" +
                    "Perfil equilibrado:\n" +
                    "Prioriza la generación de energía sobre la cantidad de agua almacenada. Habrá menos margen ante una sequía, pero se garantiza el suministro de agua durante todo el año.\n\n" +
                    "Perfil productor:\n" +
                    "Prioriza la generación de energía por encima de todo. Tiene en cuenta la previsión de precipitaciones para aumentar la producción por encima del perfil equilibrado. Tiene el inconveniente de que si las previsiones son erróneas la disponibilidad de agua puede verse afectada.");

        ta.setId("info");
        ta.setStyle("-fx-wrap-text: true; -fx-font-size: 18; -fx-background-color: #61d4f8");
        ta.setMaxSize(600,400);
        Stage stage = new Stage();
        Scene scene = new Scene(ta, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Información sobre los perfiles");
        try {
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/sinco/CDSFase2/images/logo.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void iniciarSimulacion(ActionEvent event) {
        if (!tipoPerfil.equals("")) {
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader();
                stage.setTitle("Simulando la producción de todas las centrales en el perfil: " + tipoPerfil);
                stage.getIcons().add(new Image(new FileInputStream("src/main/resources/com/sinco/CDSFase2/images/logo.png")));
                fxmlLoader.setLocation(getClass().getResource("/views/SimuladorView.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent, 1280, 720);
                stage.setScene(scene);

                SimuladorController simuladorController = fxmlLoader.getController();
                simuladorController.start(tipoPerfil);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un perfil", ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    void selectedAguayo(ActionEvent event) {
        embalse = MI_Aguayo.getText();
        MB_Centrales.setText(MI_Aguayo.getText());
        TA_Info.setText("Ubicación: Cantabria\n" +
                "Potencia: 360 MW\n" +
                "Coordenadas: 43.097493232291775, -3.9998186165069036");
        MB_Centrales.setId("colorAguayo");
    }

    @FXML
    void selectedAldeadavila(ActionEvent event) {
        embalse = MI_Aldeadavila.getText();
        MB_Centrales.setText(MI_Aldeadavila.getText());
        TA_Info.setText("Ubicación: Salamanca\n" +
                "Potencia: 1.243 MW\n" +
                "Coordenadas: 41.21211159893668, -6.685594897938708");
        MB_Centrales.setId("colorAldeadavila");
    }

    @FXML
    void selectedCedillo(ActionEvent event) {
        embalse = MI_Cedillo.getText();
        MB_Centrales.setText(MI_Cedillo.getText());
        TA_Info.setText("Ubicación: Cáceres\n" +
                "Potencia: 500 MW\n" +
                "Coordenadas: 39.66779396509576, -7.539474589040334");
        MB_Centrales.setId("colorCedillo");
    }

    @FXML
    void selectedEncantada(ActionEvent event) {
        embalse = MI_encantada.getText();
        MB_Centrales.setText(MI_encantada.getText());
        TA_Info.setText("Ubicación: Málaga\n" +
                "Potencia: 360 MW\n" +
                "Coordenadas: 36.90878214306153, -4.76308581446198");
        MB_Centrales.setId("colorEncantada");
    }

    @FXML
    void selectedLuna(ActionEvent event) {
        embalse = MI_Luna.getText();
        MB_Centrales.setText("Mora de Luna");
        TA_Info.setText("Ubicación: León\n" +
                "Potencia: 80 MW\n" +
                "Coordenadas: 42.82296141753982, -5.838159455507269");
        MB_Centrales.setId("colorLuna");
    }

    @FXML
    void selectedMequinenza(ActionEvent event) {
        embalse = MI_Mequinenza.getText();
        MB_Centrales.setText(MI_Mequinenza.getText());
        TA_Info.setText("Ubicación: Zaragoza\n" +
                "Potencia: 324 MW\n" +
                "Coordenadas: 41.36934224588879, 0.2742791636874091");
        MB_Centrales.setId("colorMequinenza");
    }

    @FXML
    void selectedMuela(ActionEvent event) {
        embalse = MI_Muela.getText();
        MB_Centrales.setText(MI_Muela.getText());
        TA_Info.setText("Ubicación: Valencia\n" +
                "Potencia: 630 MW\n" +
                "Coordenadas: 39.26920917847571, -0.9187704088862684");
        MB_Centrales.setId("colorMuela");
    }

    @FXML
    void selectedOriol(ActionEvent event) {
        embalse = MI_Oriol.getText();
        MB_Centrales.setText(MI_Oriol.getText());
        TA_Info.setText("Ubicación: Cáceres\n" +
                "Potencia: 957 MW\n" +
                "Coordenadas: 39.729256026713024, -6.885491270171535");
        MB_Centrales.setId("colorOriol");
    }

    @FXML
    void selectedSallente(ActionEvent event) {
        embalse = MI_Sallente.getText();
        MB_Centrales.setText(MI_Sallente.getText());
        TA_Info.setText("Ubicación: LLeida\n" +
                "Potencia: 468 MW\n" +
                "Coordenadas: 42.5073217441974, 0.990033255645025");
        MB_Centrales.setId("colorSallente");
    }

    @FXML
    void selectedSaucelle(ActionEvent event) {
        embalse = MI_Saucelle.getText();
        MB_Centrales.setText(MI_Saucelle.getText());
        TA_Info.setText("Ubicación: Salamanca\n" +
                "Potencia: 520 MW\n" +
                "Coordenadas: 41.03809694749071, -6.802881200882077");
        MB_Centrales.setId("colorSaucelle");
    }

    @FXML
    void selectedVillarino(ActionEvent event) {
        embalse = MI_Villarino.getText();
        MB_Centrales.setText(MI_Villarino.getText());
        TA_Info.setText("Ubicación: Salamanca\n" +
                "Potencia: 857 MW\n" +
                "Coordenadas: 41.26547534089663, -6.495000406071093");
        MB_Centrales.setId("colorVillarino");
    }


    @FXML
    void setPerfilAhorrador(ActionEvent event) {
        tipoPerfil = "AHORRADOR";
    }

    @FXML
    void setPerfilEquilibrado(ActionEvent event) {
        tipoPerfil = "EQUILIBRADO";
    }

    @FXML
    void setPerfilProductor(ActionEvent event) {
        tipoPerfil = "PRODUCTOR";
    }

    @FXML
    void datosProduccion(ActionEvent event) {
        if (MB_Centrales.getText().equals("Centrales")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione una central", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (tipoPerfil.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione un perfil", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        switch (tipoPerfil) {
            case "AHORRADOR":
                if (!embalse.equals("")) {
                    DBData dbData = new DBData();
                    int data[] = new int[3];
                    switch (MB_Centrales.getText()) {
                        case "Central de Aldeadávila":
                            data = dbData.getLatestData("Aldeadávila");
                            break;
                        case "Central José María de Oriol":
                            data = dbData.getLatestData("Alcántara");
                            break;
                        case "Central de Villarino":
                            data = dbData.getLatestData("Almendra");
                            break;
                        case "Central de Cortes-La Muela":
                            data = dbData.getLatestData("La Muela");
                            break;
                        case "Central de Saucelle":
                            data = dbData.getLatestData("Saucelle");
                            break;
                        case "Cedillo":
                            data = dbData.getLatestData("Cedillo");
                            break;
                        case "Estany-Gento Sallente":
                            data = dbData.getLatestData("Sallente");
                            break;
                        case "Central de Tajo de la Encantada":
                            data = dbData.getLatestData("Guadalhorce-Guadalteba");
                            break;
                        case "Central de Aguayo":
                            data = dbData.getLatestData("Alsa - Mediajo");
                            break;
                        case "Mequinenza":
                            data = dbData.getLatestData("Mequinenza");
                            break;
                        case "Mora de Luna":
                            data = dbData.getLatestData("Barrios de Luna");
                            break;
                    }
                    if (data[0] > data[2]) {
                        //System.out.println("Dato de este año: " + data[0] + "\nDato del año pasado: " + data[1] + "\nDato media: " + data[2]);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "La cantidad de agua que se puede consumir esta semana es: " + (data[0] - data[2]) + " hm3", ButtonType.OK);
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Perfil actual: ahorrador\nNo se puede producir porque el embalse no tiene el nivel de agua suficiente para garantizar el consumo de agua de la zona durante todo el año. \nCapacidad actual: " + data[0] + " hm3", ButtonType.OK);
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione una central", ButtonType.OK);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.showAndWait();
                }
                break;
            case "EQUILIBRADO":
                if (!embalse.equals("")) {
                    DBData dbData = new DBData();
                    int data[] = new int[3];
                    switch (MB_Centrales.getText()) {
                        case "Central de Aldeadávila":
                            data = dbData.getLatestData("Aldeadávila");
                            break;
                        case "Central José María de Oriol":
                            data = dbData.getLatestData("Alcántara");
                            break;
                        case "Central de Villarino":
                            data = dbData.getLatestData("Almendra");
                            break;
                        case "Central de Cortes-La Muela":
                            data = dbData.getLatestData("La Muela");
                            break;
                        case "Central de Saucelle":
                            data = dbData.getLatestData("Saucelle");
                            break;
                        case "Cedillo":
                            data = dbData.getLatestData("Cedillo");
                            break;
                        case "Estany-Gento Sallente":
                            data = dbData.getLatestData("Sallente");
                            break;
                        case "Central de Tajo de la Encantada":
                            data = dbData.getLatestData("Guadalhorce-Guadalteba");
                            break;
                        case "Central de Aguayo":
                            data = dbData.getLatestData("Alsa - Mediajo");
                            break;
                        case "Mequinenza":
                            data = dbData.getLatestData("Mequinenza");
                            break;
                        case "Mora de Luna":
                            data = dbData.getLatestData("Barrios de Luna");
                            break;
                    }
                    if ((data[0] > data[1]) || (data[0] > data[2])) {
                        //System.out.println("Dato de este año: " + data[0] + "\nDato del año pasado: " + data[1] + "\nDato media: " + data[2]);
                        int valor = (data[1] > data[2]) ? (data[0] - data[2]) : (data[0] - data[1]);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "La cantidad de agua que se puede consumir esta semana es: " + valor + " hm3", ButtonType.OK);
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Perfil actual: equilibrado\nPerfil No se puede producir porque el embalse no tiene el nivel de agua suficiente para garantizar el consumo de agua de la zona durante todo el año. \nCapacidad actual: " + data[0] + " hm3", ButtonType.OK);
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione una central", ButtonType.OK);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.showAndWait();
                }
                break;
            case "PRODUCTOR":
                if (true) {
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Perfil actual: productor\nNo se puede producir porque el embalse no tiene el nivel de agua suficiente para garantizar el consumo de agua de la zona durante todo el año. \nCapacidad actual: ", ButtonType.OK);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.showAndWait();
                }
                break;
        }
    }
}