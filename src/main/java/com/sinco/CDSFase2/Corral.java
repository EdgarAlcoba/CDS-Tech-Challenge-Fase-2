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


    public double[] optimizacionDia(JSONObject embalse1, JSONObject embalse2, JSONObject climatologia, JSONObject demandaEnergetica, JSONObject eventos, JSONObject datosHidraulicos){
        Pablo p = new Pablo();
        double ETotal = p.calcularTotal(demandaEnergetica);
        double energia = 0;
        double generacionCentrales[]= {0,0,0,0,0,0,0,0,0,0,0};

        if(capacidadActual1 >= 90){
            generacionCentrales[2]+= p.generarHidraulica(embalse1, 10);
            energia+=generacionCentrales[2];

            if(energia>=ETotal){
                capacidadActual1-=quitadoEmbalse1;
                return generacionCentrales;
            }
        }


        if(capacidadActual2 >= 90) {
            generacionCentrales[3]+= p.generarHidraulica(embalse2, 10);
            energia+=generacionCentrales[3];

            if(energia>=ETotal){
                capacidadActual1-=quitadoEmbalse1;
                return generacionCentrales;
            }

        }



        MPSolver solver = MPSolver.createSolver("GLOP");

        MPVariable solar1 = solver.makeNumVar(0.0, 1.0, "x");
        MPVariable solar2 = solver.makeNumVar(0.0, 2.0, "y");
        MPVariable solar3 = solver.makeNumVar(0.0, 2.0, "y");

        return generacionCentrales;


    }
}
