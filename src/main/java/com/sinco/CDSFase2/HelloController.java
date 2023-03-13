package com.sinco.CDSFase2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        ApiAccess apiAccess = new ApiAccess();
        String url = "https://opendata.aemet.es/opendata/api/prediccion/especifica/playa/0406601";
        String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlYWxjb2MwMEBlc3R1ZGlhbnRlcy51bmlsZW9uLmVzIiwianRpIjoiNjhiZDVhZWMtMWJjMC00NGJkLWI5NmYtODA3YTdiOGIzYTNiIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE2NzgyOTkyNzQsInVzZXJJZCI6IjY4YmQ1YWVjLTFiYzAtNDRiZC1iOTZmLTgwN2E3YjhiM2EzYiIsInJvbGUiOiIifQ.HYwpMbvqdfiQgUtbAX2EWg-lh8rmNBXyAflCt1L3V2U";
        welcomeText.setText(apiAccess.peticionApi(url,apiKey).toString());
    }
}