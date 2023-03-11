package com.sinco.CDSFase2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        ApiAccess apiAccess = new ApiAccess();
        welcomeText.setText(apiAccess.peticionApi().toString());
    }
}