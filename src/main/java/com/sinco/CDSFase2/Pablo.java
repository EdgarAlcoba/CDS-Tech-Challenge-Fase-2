package com.sinco.CDSFase2;

import com.sinco.CDSFase2.controllers.ApiAccess;
import org.json.JSONObject;

import java.math.BigDecimal;

public class Pablo {

    public void pruebas(){
        ApiAccess api = new ApiAccess();
        JSONObject central = (JSONObject) api.itemData("wind","WIND003").get("WIND003");
        System.out.println(emisiones(central, 5000));
    }

    private double distancia(double lat1, double lon1, double lat2, double lon2){
        double lat1rad = Math.toRadians(lat1);
        double lon1rad = Math.toRadians(lon1);
        double lat2rad = Math.toRadians(lat2);
        double lon2rad = Math.toRadians(lon2);

        double DifLat = (lat1rad - lat2rad);
        double DifLon = (lon1rad - lon2rad);

        double a = Math.pow(Math.sin(DifLat/2), 2) +
                Math.cos(lat1rad) * Math.cos(lat2rad) *
                Math.pow(Math.sin(DifLon / 2),2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double radioT = 6378.0;
        return radioT * c;
    }

    private double costeTransporte(JSONObject central, JSONObject zona, int poder){
        double dist = distancia((Double) central.get("Latitude"), (Double) central.get("Longitude"), (Double) zona.get("Latitude"), (Double) zona.get("Longitude"));
        return dist * poder * (double) central.get("coste_transporte");
    }

    private double emisiones(JSONObject central, int poder){
        return poder * central.getDouble("emisiones");
    }



}
