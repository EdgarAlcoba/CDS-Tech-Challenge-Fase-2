package com.sinco.CDSFase2.controllers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiAccess {
    public int peticionApi(String url, String apiKey) {
        String respuesta = "";
        //String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlYWxjb2MwMEBlc3R1ZGlhbnRlcy51bmlsZW9uLmVzIiwianRpIjoiNjhiZDVhZWMtMWJjMC00NGJkLWI5NmYtODA3YTdiOGIzYTNiIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE2NzgyOTkyNzQsInVzZXJJZCI6IjY4YmQ1YWVjLTFiYzAtNDRiZC1iOTZmLTgwN2E3YjhiM2EzYiIsInJvbGUiOiIifQ.HYwpMbvqdfiQgUtbAX2EWg-lh8rmNBXyAflCt1L3V2U";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/?api_key=" + apiKey)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                respuesta = response.body().string();
                Request requestData = new Request.Builder()
                        .url(respuesta.substring(respuesta.indexOf("https"), respuesta.indexOf("metadatos") - 6))
                        .get()
                        .addHeader("cache-control", "no-cache")
                        .build();
                response = client.newCall(requestData).execute();
                if (response.isSuccessful()) {
                    respuesta = response.body().string();
                    respuesta = respuesta.substring(respuesta.indexOf("probPrecipitacion"), respuesta.indexOf("cotaNieveProv"));
                    int max = 0;
                    while (respuesta.indexOf("value") != -1) {
                        String value = respuesta.substring(respuesta.indexOf("value") + 9, respuesta.indexOf("value") + 12);
                        if (value.charAt(1) == ',') {
                            value = value.substring(0, 1);
                        } else if (value.charAt(2) == ',') {
                            value = value.substring(0, 2);
                        }
                        if (max < Integer.parseInt(value)) {
                            max = Integer.parseInt(value);
                        }
                        respuesta = respuesta.substring(respuesta.indexOf("periodo") + 7, respuesta.length());
                    }
                    return max;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;
        return -1;
    }

    public String getUrl(String central) {
        switch (central) {
            case "Central de Aldeadávila":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/37014";
            case "Central José María de Oriol":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/10008";
            case "Central de Villarino":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/37028";
            case "Central de Cortes-La Muela":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/46099";
            case "Central de Saucelle":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/37302";
            case "Cedillo":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/10062";
            case "Estany-Gento Sallente":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/25227";
            case "Central de Tajo de la Encantada":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/29012";
            case "Central de Aguayo":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/39070";
            case "Mequinenza":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/50165";
            case "Mora de Luna":
                return "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/24012";
        }
        return "";
    }
}
