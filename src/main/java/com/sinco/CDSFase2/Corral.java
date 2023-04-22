package com.sinco.CDSFase2;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import org.json.JSONObject;



public class Corral {

    public double capacidadActual1;
    public double capacidadActual2;

    public double quitadoEmbalse1;
    public double quitadoEmbalse2;


    public double[] generarElectricidad(JSONObject listaCentrales, JSONObject climatologia, JSONObject demandaEnergetica, JSONObject eventos, JSONObject datosHidraulicos){
        Pablo p = new Pablo();
        double ETotal = p.calcularTotal(demandaEnergetica);
        double energia = 0;
        double[] generacionCentrales = {0,0,0,0,0,0,0,0,0,0,0};

        //comprobar el 10% de los embalses si se puede
        if(capacidadActual1>= 90){
            generacionCentrales[2]+= p.generarHidraulica((JSONObject) listaCentrales.get("WAT001"), 10);
            energia+=generacionCentrales[2];
            if(energia>=ETotal){
                quitadoEmbalse1 = 10;
                return generacionCentrales;
            }
        }
        if(capacidadActual2 >= 90) {
            generacionCentrales[3]+= p.generarHidraulica((JSONObject) listaCentrales.get("WAT001"), 10);
            energia+=generacionCentrales[3];
            if(energia>=ETotal){
                quitadoEmbalse1 = 10;
                return generacionCentrales;
            }
        }
        //usar la fuentes verdes
        generacionCentrales[0] = p.generarGeo((JSONObject) climatologia.get("GEO001"),(JSONObject) listaCentrales.get("GEO001"));
        generacionCentrales[1] = p.generarGeo((JSONObject) climatologia.get("GEO002"),(JSONObject) listaCentrales.get("GEO002"));
        generacionCentrales[4] = p.generarGeo((JSONObject) climatologia.get("WIND001"),(JSONObject) listaCentrales.get("WIND001"));
        generacionCentrales[5] = p.generarGeo((JSONObject) climatologia.get("WIND002"),(JSONObject) listaCentrales.get("WIND002"));
        generacionCentrales[6] = p.generarGeo((JSONObject) climatologia.get("WIND003"),(JSONObject) listaCentrales.get("WIND003"));
        generacionCentrales[7] = p.generarGeo((JSONObject) climatologia.get("SUN001"),(JSONObject) listaCentrales.get("SUN001"));
        generacionCentrales[8] = p.generarGeo((JSONObject) climatologia.get("SUN002"),(JSONObject) listaCentrales.get("SUN002"));
        generacionCentrales[9] = p.generarGeo((JSONObject) climatologia.get("SUN003"),(JSONObject) listaCentrales.get("SUN003"));
        energia = energia + generacionCentrales[0] + generacionCentrales[1] + generacionCentrales[4] +  generacionCentrales[5] + generacionCentrales[6] + generacionCentrales[7] + generacionCentrales[8] + generacionCentrales[9];
        if(energia >= ETotal){
            return  generacionCentrales;
        }else{
            //se vacian embalses para suplir demanda
            if(capacidadActual1 > 35){
                double quitar = capacidadActual1 - 35;
                generacionCentrales[2]+= p.generarHidraulica((JSONObject) listaCentrales.get("WAT001"), quitar);
                energia+=generacionCentrales[2];
                if(energia>=ETotal){
                    quitadoEmbalse1 = quitar;
                    return generacionCentrales;
                }
            }
            if(capacidadActual2 > 35){
                double quitar = capacidadActual2 - 35;
                generacionCentrales[3]+= p.generarHidraulica((JSONObject) listaCentrales.get("WAT002"), quitar);
                energia+=generacionCentrales[3];
                if(energia>=ETotal){
                    quitadoEmbalse2 = quitar;
                    return generacionCentrales;
                }
            }
            if(energia >= ETotal){
                return generacionCentrales;
            }else{
                //no da con las verdes, toca usar carbon
                generacionCentrales[10] = ETotal -energia;
                return generacionCentrales;

            }
        }


        /*
        MPSolver solver = MPSolver.createSolver("GLOP");

        MPVariable solar1 = solver.makeNumVar(0.0, 1.0, "x");
        MPVariable solar2 = solver.makeNumVar(0.0, 2.0, "y");
        MPVariable solar3 = solver.makeNumVar(0.0, 2.0, "y");
         */


        return generacionCentrales;


    }
}
