package com.sinco.CDSFase2.controllers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ApiAccess {
    String hostIp = "172.31.98.128";
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

    public void locationData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://"+hostIp+":8081")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "";
            if (response.isSuccessful()) {
                respuesta = response.body().string();
                System.out.println(respuesta);
            }
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }

    public void sunData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://"+hostIp+":8082")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "error";
            if (response.isSuccessful()) {
                respuesta = response.body().string();
            }
            System.out.println(respuesta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void windData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://"+hostIp+":8083")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "";
            if (response.isSuccessful()) {
                respuesta = response.body().string();
                if (response.isSuccessful()) {

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void waterData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://"+hostIp+":8084")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "";
            if (response.isSuccessful()) {
                respuesta = response.body().string();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void geoData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://"+hostIp+":8085")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "";
            if (response.isSuccessful()) {
                respuesta = response.body().string();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void coalData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://"+hostIp+":8086")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "";
            if (response.isSuccessful()) {
                respuesta = response.body().string();
                if (response.isSuccessful()) {

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listData(String dataType) {
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
                return;
        }
        OkHttpClient client = new OkHttpClient();
        System.out.println("http://"+hostIp+":" + port + "/list");
        Request request = new Request.Builder()
                .url("http://"+hostIp+":" + port + "/list")
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respuesta = "";
            if (response.isSuccessful()) {
                respuesta = response.body().string();
                System.out.println(respuesta);
            }
            JSONArray jsonArray = new JSONArray(respuesta);
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
                .url("http://"+hostIp+":" + port + "/item/" + item)
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
            System.out.println(respuesta);
            return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
