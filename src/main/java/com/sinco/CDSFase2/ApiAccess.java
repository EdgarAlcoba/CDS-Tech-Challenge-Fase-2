package com.sinco.CDSFase2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiAccess {
    public String peticionApi(String url, String apiKey) {
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
            //System.out.println("Código de respuesta: " + response.code());
            if(response.isSuccessful()){
                respuesta=response.body().string();
                Request requestData = new Request.Builder()
                        .url(respuesta.substring(respuesta.indexOf("https"), respuesta.indexOf("metadatos")-6))
                        .get()
                        .addHeader("cache-control", "no-cache")
                        .build();
                response = client.newCall(requestData).execute();
                if(response.isSuccessful()){
                    respuesta = response.body().string();
                    System.out.println(respuesta);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;
        return respuesta;
    }
}
