package com.sinco.CDSFase2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class AreaController {
    @FXML
    private CheckBox checkbox;

    @FXML
    private Label label;

    private boolean estado = false;

    @FXML
    void selected(ActionEvent event) {
            estado = !estado;
    }
    public boolean getEstado(){
        return estado;
    }

    public void setData(String name){
        label.setText(name);
    }
}
