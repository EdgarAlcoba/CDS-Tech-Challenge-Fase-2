package com.sinco.CDSFase2;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ApiAccess {
    String hostIp = "localhost";

    public JSONArray listData(String dataType) {
        String port = "";
        switch (dataType) {
            case "locations":
                port = "8081";
                break;
            case "sun":
                port = "8082";
                break;
            case "wind":
                port = "8083";
                break;
            case "water":
                port = "8084";
                break;
            case "geo":
                port = "8085";
                break;
            case "coal":
                port = "8086";
                break;
            default:
                return null;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://" + hostIp + ":" + port + "/list")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "";
            if (response.isSuccessful()) {
                respuesta = response.body().string();
            }
            JSONArray jsonArray = new JSONArray(respuesta);
            return jsonArray;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject itemData(String energyType, String item) {
        String port = "";
        switch (energyType) {
            case "locations":
                port = "8081";
                break;
            case "sun":
                port = "8082";
                break;
            case "wind":
                port = "8083";
                break;
            case "water":
                port = "8084";
                break;
            case "geo":
                port = "8085";
                break;
            case "coal":
                port = "8086";
                break;
            default:
                return null;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://" + hostIp + ":" + port + "/item/" + item)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "";
            if (response.isSuccessful()) {
                respuesta = response.body().string();
            }
            JSONObject jsonObject = new JSONObject(respuesta);
            return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONArray getCentrales() {
        String port = "";
        String factores[] = {"8082", "8083", "8084", "8085", "8086"};
        JSONArray listaCentrales = new JSONArray();
        for (int i = 0; i < factores.length; i++) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://" + hostIp + ":" + factores[i] + "/list")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String respuesta = "";
                if (response.isSuccessful()) {
                    respuesta = response.body().string();
                }
                JSONArray jsonArray = new JSONArray(respuesta);
                listaCentrales.put(jsonArray);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listaCentrales;
    }
}
