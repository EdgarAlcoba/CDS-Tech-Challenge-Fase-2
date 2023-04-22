package com.sinco.CDSFase2;

import com.sinco.CDSFase2.controllers.ApiAccess;
import org.json.JSONObject;
import java.time.LocalTime;


public class Pablo {

    public void pruebas(){
        /*
        ApiAccess api = new ApiAccess();
        JSONObject central = (JSONObject) api.itemData("sun","SUN001").get("SUN001");
        */
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

    private double costeProduccion(JSONObject central, int poder){
        return  (poder * central.getDouble("coste_generacion") / 100.0);
    }

    private double emisiones(JSONObject central, int poder){
        return poder * central.getDouble("emisiones");
    }

    private double getScoring(JSONObject central, boolean servicios, boolean industrial, boolean residencial){
        double penInd = central.getDouble("penalizacion_industrial");
        double penSer = central.getDouble("penalizacion_servicios");
        double penRes = central.getDouble("penalizacion_residencial");
        double total = 0.0;
        if(servicios){
            total += penSer;
        }
        if(industrial) {
            total += penInd;
        }
        if(residencial){
            total += penRes;
        }
        return total;
    }

    private double getScoringTotal(JSONObject[] listaCentral){
        double score = 0.0;
        //TODO calcular a que sectores abastece cada central
        for (JSONObject central : listaCentral) {
            score += getScoring(central, true,true,true);
        }
        return score;
    }

    private int segundosLuz(String am, String an){
        LocalTime amanecer = LocalTime.parse(am);
        LocalTime anochecer = LocalTime.parse(an);
        LocalTime resultado = anochecer.minusHours(amanecer.getHour());
        resultado = resultado.minusMinutes(amanecer.getMinute());
        resultado = resultado.minusSeconds(amanecer.getSecond());
        resultado = resultado.minusNanos(amanecer.getNano());
        return resultado.getHour()*3600 + (resultado.getMinute() * 60) + resultado.getSecond() % 3600;
    }

    private double generaSolar(double radiacion, JSONObject central, String amanecer, String anochecer){
        int segundosLuz = segundosLuz(amanecer, anochecer);
        double energia;
        if(radiacion < 150){
            energia = (central.getDouble("baja") / 3600) * segundosLuz;
        }else if( radiacion > 225){
            energia = (central.getDouble("alta") / 3600) * segundosLuz;
        }else{
            energia = (central.getDouble("media") / 3600) * segundosLuz;
        }
        return energia;
    }

}
