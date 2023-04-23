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

    public double ETotal;



    public double[] optimizacion(double [] centralesEnUso, JSONObject listaCentrales){
        double optimizacion[] = {0,0,0,0,0,0,0,0,0,0,0};

        Pablo p = new Pablo();

        MPSolver solver = MPSolver.createSolver("GLOP");

        MPVariable geo1 = solver.makeNumVar(0.0, centralesEnUso[0], "geo1");
        MPVariable geo2 = solver.makeNumVar(0.0, centralesEnUso[1], "geo2");
        MPVariable hid1 = solver.makeNumVar(0.0, centralesEnUso[2], "hid1");
        MPVariable hid2 = solver.makeNumVar(0.0, centralesEnUso[3], "hid2");
        MPVariable eo1 = solver.makeNumVar(0.0, centralesEnUso[4], "eo1");
        MPVariable eo2 = solver.makeNumVar(0.0, centralesEnUso[5], "eo2");
        MPVariable eo3 = solver.makeNumVar(0.0, centralesEnUso[6], "eo3");
        MPVariable sol1 = solver.makeNumVar(0.0, centralesEnUso[7], "sol1");
        MPVariable sol2 = solver.makeNumVar(0.0, centralesEnUso[8], "sol2");
        MPVariable sol3 = solver.makeNumVar(0.0, centralesEnUso[9], "sol3");
        MPVariable carbon = solver.makeNumVar(0.0, centralesEnUso[10], "carbon");

        MPConstraint ct = solver.makeConstraint(ETotal, MPSolver.infinity() , "ct");
        ct.setCoefficient(geo1,1);
        ct.setCoefficient(geo2,1);
        ct.setCoefficient(hid1,1);
        ct.setCoefficient(hid2,1);
        ct.setCoefficient(eo1,1);
        ct.setCoefficient(eo2,1);
        ct.setCoefficient(eo3,1);
        ct.setCoefficient(sol1,1);
        ct.setCoefficient(sol2,1);
        ct.setCoefficient(sol3,1);
        ct.setCoefficient(carbon,1);

        MPObjective objective = solver.objective();
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("GEO001")).getDouble("coste_generacion"));
        objective.setCoefficient(geo2, ((JSONObject) listaCentrales.get("GEO002")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("WAT001")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("WAT002")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("WIND001")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("WIND002")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("WIND003")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("SUN001")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("SUN002")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("SUN003")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) listaCentrales.get("COAL001")).getDouble("coste_generacion"));
        objective.setMinimization();


        solver.solve();

        optimizacion[0]=geo1.solutionValue();
        optimizacion[1]=geo1.solutionValue();
        optimizacion[2]=geo1.solutionValue();
        optimizacion[3]=geo1.solutionValue();
        optimizacion[4]=geo1.solutionValue();
        optimizacion[5]=geo1.solutionValue();
        optimizacion[6]=geo1.solutionValue();
        optimizacion[7]=geo1.solutionValue();
        optimizacion[8]=geo1.solutionValue();
        optimizacion[9]=geo1.solutionValue();
        optimizacion[10]=geo1.solutionValue();

        return optimizacion;
    }
}
