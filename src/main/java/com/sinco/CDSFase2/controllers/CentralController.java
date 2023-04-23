package com.sinco.CDSFase2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class CentralController {
        @FXML
        private Label actividad;

        @FXML
        private Label central;

        public void setData(String name, String activity){
            central.setText(name);
            actividad.setText(activity);
        }
}
