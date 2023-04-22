package com.sinco.CDSFase2;

public class Pablo {

    public void pruebas(){
        System.out.print(distancia(40.8785,-4.6899,40.4168,-3.7038));
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

    private double costeDistancia(Object central, Object zona, int poder){
        double dist = distancia(central.latitud, central.longitud, zona.latitud,zona.longitud);
        return dist * poder * central.coste;
    }

    private double emisiones(Object central, int poder){
        return poder * central.emisiones;
    }



}
