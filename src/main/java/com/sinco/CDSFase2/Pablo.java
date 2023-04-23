package com.sinco.CDSFase2;

import com.sinco.CDSFase2.ApiAccess;
import org.json.JSONObject;
import java.time.LocalTime;


public class Pablo {

    public double capacidadActual;

    public double quitadoEmbalse = 0;

    public void pruebas(){
        /*
        ApiAccess api = new ApiAccess();
        JSONObject central = (JSONObject) api.itemData("sun","SUN001").get("SUN001");
        */
    }

    public double distancia(double lat1, double lon1, double lat2, double lon2){
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

    public double costeTransporte(JSONObject central, JSONObject zona, int poder){
        double dist = distancia((Double) central.get("Latitude"), (Double) central.get("Longitude"), (Double) zona.get("Latitude"), (Double) zona.get("Longitude"));
        return dist * poder * (double) central.get("coste_transporte");
    }

    public double costeProduccion(JSONObject central, int poder){
        return  (poder * central.getDouble("coste_generacion") / 100.0);
    }

    public double emisiones(JSONObject central, int poder){
        return poder * central.getDouble("emisiones");
    }

    public double getScoring(JSONObject central, boolean servicios, boolean industrial, boolean residencial){
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

    public double getScoringTotal(JSONObject[] listaCentral){
        double score = 0.0;
        //TODO calcular a que sectores abastece cada central
        for (JSONObject central : listaCentral) {
            score += getScoring(central, true,true,true);
        }
        return score;
    }



    public double generarGeo(JSONObject zonaCentral, JSONObject central){
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

    public double generarEolica(JSONObject zonaCentral, JSONObject central){
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

    public double capacidadEmbalse(JSONObject zonaEmbalse,JSONObject datosEmbalses, JSONObject embalse){
        double evo = zonaEmbalse.getDouble("ETO");
        double precipitaciones = zonaEmbalse.getDouble("PREC");
        double superficie = embalse.getDouble("superficie");
        double sumaPrec = (((superficie * 10000) * precipitaciones) / 1000000000);
        double consumo = datosEmbalses.getDouble("Consumption");
        double rios = datosEmbalses.getDouble("River contribution");
        evo = (((evo * 24) / 1000));
        double altura = capacidadActual / superficie;
        altura -= evo;
        double capacidad = altura * superficie;
        capacidad = (capacidad + sumaPrec + rios) - consumo;
        return capacidad;
    }

    public double porcentajeEmbalse(double capacidad, JSONObject embalse){
        double capTotal = embalse.getDouble("capacidad_total");
        return ((capacidad / capTotal) * 100);
    }

    public double generarHidraulica(JSONObject central, double quitar){
        double capMax = central.getDouble("capacidad_total");
        double volumenQuitado = (capMax / 100) * quitar;
        quitadoEmbalse += quitar;
        return volumenQuitado * central.getDouble("ratio");
    }

    public double calcularTotal(JSONObject demanda){
        double total = 0.0;
        int tam = demanda.length();
        Object[] keys = demanda.keySet().toArray();
        for (int i = 0; i < tam; i++){
            JSONObject area = (JSONObject) demanda.get(keys[i].toString());
            total += area.getDouble("Total");
        }
        return total;
    }


}
