package com.sinco.CDSFase2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalTime;

public class OptimizationAlgorithm {

    private JSONArray ubicaciones;
    private JSONArray centralesSolares;
    private JSONArray centralesEolicas;
    private JSONArray centralesHidraulicas;
    private JSONArray centralesGeotermicas;
    private JSONArray centralCarbon;

    private void simulacionDia(String fecha){

    }

    public void iniciaCentrales(){
        ApiAccess api = new ApiAccess();
        ubicaciones = api.listData("locations");
        centralesSolares = api.listData("sun");
        centralesEolicas = api.listData("wind");
        centralesHidraulicas = api.listData("water");
        centralesGeotermicas = api.listData("geo");
        centralCarbon = api.listData("coal");
    }

    

    public void consultarListas(){
        iniciaCentrales();
        System.out.println("\n"+ubicaciones);
        System.out.println(centralesSolares);
        System.out.println(centralesEolicas);
        System.out.println(centralesHidraulicas);
        System.out.println(centralesGeotermicas);
        System.out.println(centralCarbon);
    }

    /**
     *  Método que calcula la distancia entre dos centrales
     */
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

    /**
     * Calcula el coste de transporte entre un área y una central
     * @param central
     * @param zona
     * @param poder
     * @return
     */
    private double costeTransporte(JSONObject central, JSONObject zona, int poder){
        double dist = distancia((Double) central.get("Latitude"), (Double) central.get("Longitude"), (Double) zona.get("Latitude"), (Double) zona.get("Longitude"));
        return dist * poder * (double) central.get("coste_transporte");
    }

    /**
     * Calcula el coste de producción de una central
     * @param central
     * @param poder
     * @return
     */
    private double costeProduccion(JSONObject central, int poder){
        return  (poder * central.getDouble("coste_generacion") / 100.0);
    }

    /**
     * Calcula las emisiones de una central
     * @param central
     * @param poder
     * @return
     */
    private double emisiones(JSONObject central, int poder){
        return poder * central.getDouble("emisiones");
    }

    /**
     * Calcula el score de una central
     * @param central
     * @param servicios
     * @param industrial
     * @param residencial
     * @return
     */
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

    private int segundosLuz(String am, String an){
        LocalTime amanecer = LocalTime.parse(am);
        LocalTime anochecer = LocalTime.parse(an);
        LocalTime resultado = anochecer.minusHours(amanecer.getHour());
        resultado = resultado.minusMinutes(amanecer.getMinute());
        resultado = resultado.minusSeconds(amanecer.getSecond());
        resultado = resultado.minusNanos(amanecer.getNano());
        return resultado.getHour()*3600 + (resultado.getMinute() * 60) + resultado.getSecond() % 3600;
    }

    private double generaSolar(JSONObject zonaCentral, JSONObject central, String amanecer, String anochecer){
        int segundosLuz = segundosLuz(amanecer, anochecer);
        double energia;
        double radiacion = zonaCentral.getDouble("RADMED");
        if(radiacion < 150){
            energia = (central.getDouble("baja") / 3600) * segundosLuz;
        }else if( radiacion > 225){
            energia = (central.getDouble("alta") / 3600) * segundosLuz;
        }else{
            energia = (central.getDouble("media") / 3600) * segundosLuz;
        }
        return energia;
    }

    private double generarGeo(JSONObject zonaCentral, JSONObject central){
        double temp = zonaCentral.getDouble("TMED");
        double energia;
        if(temp < 10){
            energia = central.getDouble("baja");
        }else if(temp > 25){
            energia = central.getDouble("alta");
        }else{
            energia = central.getDouble("media");
        }
        return energia;
    }

    private double generarEolica(JSONObject zonaCentral, JSONObject central){
        double viento = zonaCentral.getDouble("VVMED");
        double energia;
        if(viento < 10){
            energia = central.getDouble("baja");
        }else if(viento > 25){
            energia = central.getDouble("alta");
        }else{
            energia = central.getDouble("media");
        }
        return energia;
    }



}