package com.sinco.CDSFase2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static java.lang.Integer.parseInt;

public class HelloController {

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

    @FXML
    void obtenerDatos(ActionEvent event) {
        if (MB_Centrales.getText().equals("Centrales")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Seleccione una central", ButtonType.YES);
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
                    case "Central José María Oriol":
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
                    case "Central de Tajo de la encantada":
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
                Alert alert = new Alert(Alert.AlertType.WARNING, "Introduzca un año mayor a 2005", ButtonType.YES);
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Introduzca un año mayor a 2005", ButtonType.YES);
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
    void selectedAguayo(ActionEvent event) {
        MB_Centrales.setText(MI_Aguayo.getText());
        TA_Info.setText("Ubicación: Cantabria\n" +
                "Potencia: 360 MW\n" +
                "Coordenadas: 43.097493232291775, -3.9998186165069036");
    }

    @FXML
    void selectedAldeadavila(ActionEvent event) {
        MB_Centrales.setText(MI_Aldeadavila.getText());
        TA_Info.setText("Ubicación: Salamanca\n" +
                "Potencia: 1.243 MW\n" +
                "Coordenadas: 41.21211159893668, -6.685594897938708");
    }

    @FXML
    void selectedCedillo(ActionEvent event) {
        MB_Centrales.setText(MI_Cedillo.getText());
        TA_Info.setText("Ubicación: Cáceres\n" +
                "Potencia: 500 MW\n" +
                "Coordenadas: 39.66779396509576, -7.539474589040334");
    }

    @FXML
    void selectedEncantada(ActionEvent event) {
        MB_Centrales.setText(MI_encantada.getText());
        TA_Info.setText("Ubicación: Málaga\n" +
                "Potencia: 360 MW\n" +
                "Coordenadas: 36.90878214306153, -4.76308581446198");
    }

    @FXML
    void selectedLuna(ActionEvent event) {
        MB_Centrales.setText("Mora de Luna");
        TA_Info.setText("Ubicación: León\n" +
                "Potencia: 80 MW\n" +
                "Coordenadas: 42.82296141753982, -5.838159455507269");
    }

    @FXML
    void selectedMequinenza(ActionEvent event) {
        MB_Centrales.setText(MI_Mequinenza.getText());
        TA_Info.setText("Ubicación: Zaragoza\n" +
                "Potencia: 324 MW\n" +
                "Coordenadas: 41.36934224588879, 0.2742791636874091");
    }

    @FXML
    void selectedMuela(ActionEvent event) {
        MB_Centrales.setText(MI_Muela.getText());
        TA_Info.setText("Ubicación: Valencia\n" +
                "Potencia: 630 MW\n" +
                "Coordenadas: 39.26920917847571, -0.9187704088862684");
    }

    @FXML
    void selectedOriol(ActionEvent event) {
        MB_Centrales.setText(MI_Oriol.getText());
        TA_Info.setText("Ubicación: Cáceres\n" +
                "Potencia: 957 MW\n" +
                "Coordenadas: 39.729256026713024, -6.885491270171535");
    }

    @FXML
    void selectedSallente(ActionEvent event) {
        MB_Centrales.setText(MI_Sallente.getText());
        TA_Info.setText("Ubicación: LLeida\n" +
                "Potencia: 468 MW\n" +
                "Coordenadas: 42.5073217441974, 0.990033255645025");
    }

    @FXML
    void selectedSaucelle(ActionEvent event) {
        MB_Centrales.setText(MI_Saucelle.getText());
        TA_Info.setText("Ubicación: Salamanca\n" +
                "Potencia: 520 MW\n" +
                "Coordenadas: 41.03809694749071, -6.802881200882077");
    }

    @FXML
    void selectedVillarino(ActionEvent event) {
        MB_Centrales.setText(MI_Villarino.getText());
        TA_Info.setText("Ubicación: Salamanca\n" +
                "Potencia: 857 MW\n" +
                "Coordenadas: 41.26547534089663, -6.495000406071093");
    }

}