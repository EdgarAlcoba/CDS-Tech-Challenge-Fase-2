package com.sinco.CDSFase2;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalTime;

public class OptimizationAlgorithm {
    JSONObject climatology = new JSONObject();
    JSONObject demand = new JSONObject();
    JSONObject events = new JSONObject();
    JSONObject hydrodata1 = new JSONObject();
    JSONObject hydrodata2 = new JSONObject();
    private JSONArray ubicaciones;
    private JSONArray centralesSolares;
    private JSONArray centralesEolicas;
    private JSONArray centralesHidraulicas;
    private JSONArray centralesGeotermicas;
    private JSONArray centralCarbon;

    private JSONArray listaCentrales;

    private double ETotal;
    private double capacidadActual1;
    private double capacidadActual2;
    private double quitadoEmbalse1;
    private double quitadoEmbalse2;
    double[] generacionCentrales = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    double[] emisiones = {0,0,0,0,0,0,0,0,0,0,0,};
    double[] coste = {0,0,0,0,0,0,0,0,0,0,0,};

    public void iniciar(JSONObject climatology, JSONObject demand) {
        iniciaCentrales();
        generarElectricidad(climatology, demand);
        for(int i = 0; i < 11; i++){
            System.out.println(generacionCentrales[i]);
        }
        emisiones[0] = emisiones((JSONObject) centralesGeotermicas.get(0), generacionCentrales[0]);
        emisiones[1] = emisiones((JSONObject) centralesGeotermicas.get(1),generacionCentrales[1]);
        emisiones[2] = emisiones((JSONObject) centralesHidraulicas.get(0),generacionCentrales[2]);
        emisiones[3] = emisiones((JSONObject) centralesHidraulicas.get(1),generacionCentrales[3]);
        emisiones[4] = emisiones((JSONObject) centralesEolicas.get(0),generacionCentrales[4]);
        emisiones[5] = emisiones((JSONObject) centralesEolicas.get(1),generacionCentrales[5]);
        emisiones[6] = emisiones((JSONObject) centralesEolicas.get(2),generacionCentrales[6]);
        emisiones[7] = emisiones((JSONObject) centralesSolares.get(0),generacionCentrales[7]);
        emisiones[8] = emisiones((JSONObject) centralesSolares.get(1),generacionCentrales[8]);
        emisiones[9] = emisiones((JSONObject) centralesSolares.get(2),generacionCentrales[9]);
        emisiones[10] = emisiones((JSONObject) centralCarbon.get(0),generacionCentrales[10]);

        coste[0] = costeProduccion((JSONObject) centralesGeotermicas.get(0), generacionCentrales[0]);
        coste[1] = costeProduccion((JSONObject) centralesGeotermicas.get(1),generacionCentrales[1]);
        coste[2] = costeProduccion((JSONObject) centralesHidraulicas.get(0),generacionCentrales[2]);
        coste[3] = costeProduccion((JSONObject) centralesHidraulicas.get(1),generacionCentrales[3]);
        coste[4] = costeProduccion((JSONObject) centralesEolicas.get(0),generacionCentrales[4]);
        coste[5] = costeProduccion((JSONObject) centralesEolicas.get(1),generacionCentrales[5]);
        coste[6] = costeProduccion((JSONObject) centralesEolicas.get(2),generacionCentrales[6]);
        coste[7] = costeProduccion((JSONObject) centralesSolares.get(0),generacionCentrales[7]);
        coste[8] = costeProduccion((JSONObject) centralesSolares.get(1),generacionCentrales[8]);
        coste[9] = costeProduccion((JSONObject) centralesSolares.get(2),generacionCentrales[9]);
        coste[10] = costeProduccion((JSONObject) centralCarbon.get(0),generacionCentrales[10]);

        JSONObject centralC = (JSONObject) centralCarbon.get(0);
        double emisionCarbon = ETotal * centralC.getDouble("emisiones");


    }

    public void iniciaCentrales() {
        ApiAccess api = new ApiAccess();
        ubicaciones = api.listData("locations");
        centralesSolares = api.listData("sun");
        centralesEolicas = api.listData("wind");
        centralesHidraulicas = api.listData("water");
        centralesGeotermicas = api.listData("geo");
        centralCarbon = api.listData("coal");
        listaCentrales = api.getCentrales();
    }


    public void setClimatology(JSONObject climatology) {
        this.climatology = climatology;
    }

    public void setDemand(JSONObject demand) {
        this.demand = demand;
    }

    public void setEvents(JSONObject events) {
        this.events = events;
    }

    public void setHydrodata1(JSONObject hydrodata1) {
        this.hydrodata1 = hydrodata1;
    }

    public void setHydrodata2(JSONObject hydrodata2) {
        this.hydrodata2 = hydrodata2;
    }



    public void consultarListas() {
        iniciaCentrales();
        System.out.println("\n" + ubicaciones);
        System.out.println(centralesSolares);
        System.out.println(centralesEolicas);
        System.out.println(centralesHidraulicas);
        System.out.println(centralesGeotermicas);
        System.out.println(centralCarbon);
    }

    /**
     * Método que calcula la distancia entre dos centrales
     */
    private double distancia(double lat1, double lon1, double lat2, double lon2) {
        double lat1rad = Math.toRadians(lat1);
        double lon1rad = Math.toRadians(lon1);
        double lat2rad = Math.toRadians(lat2);
        double lon2rad = Math.toRadians(lon2);

        double DifLat = (lat1rad - lat2rad);
        double DifLon = (lon1rad - lon2rad);

        double a = Math.pow(Math.sin(DifLat / 2), 2) +
                Math.cos(lat1rad) * Math.cos(lat2rad) *
                        Math.pow(Math.sin(DifLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double radioT = 6378.0;
        return radioT * c;
    }

    /**
     * Calcula el coste de transporte entre un área y una central
     *
     * @param central
     * @param zona
     * @param poder
     * @return
     */
    private double costeTransporte(JSONObject central, JSONObject zona, double poder) {
        double dist = distancia((Double) central.get("Latitude"), (Double) central.get("Longitude"), (Double) zona.get("Latitude"), (Double) zona.get("Longitude"));
        return dist * poder * (double) central.get("coste_transporte");
    }

    /**
     * Calcula el coste de producción de una central
     *
     * @param central
     * @param poder
     * @return
     */
    private double costeProduccion(JSONObject central, double poder) {
        return (poder * central.getDouble("coste_generacion") / 100.0);
    }

    /**
     * Calcula las emisiones de una central
     *
     * @param central
     * @param poder
     * @return
     */
    private double emisiones(JSONObject central, double poder) {
        return poder * central.getDouble("emisiones");
    }

    /**
     * Calcula el score de una central
     *
     * @param central
     * @param servicios
     * @param industrial
     * @param residencial
     * @return
     */
    private double getScoring(JSONObject central, boolean servicios, boolean industrial, boolean residencial) {
        double penInd = central.getDouble("penalizacion_industrial");
        double penSer = central.getDouble("penalizacion_servicios");
        double penRes = central.getDouble("penalizacion_residencial");
        double total = 0.0;
        if (servicios) {
            total += penSer;
        }
        if (industrial) {
            total += penInd;
        }
        if (residencial) {
            total += penRes;
        }
        return total;
    }

    private int segundosLuz(String am, String an) {
        LocalTime amanecer = LocalTime.parse(am);
        LocalTime anochecer = LocalTime.parse(an);
        LocalTime resultado = anochecer.minusHours(amanecer.getHour());
        resultado = resultado.minusMinutes(amanecer.getMinute());
        resultado = resultado.minusSeconds(amanecer.getSecond());
        resultado = resultado.minusNanos(amanecer.getNano());
        return resultado.getHour() * 3600 + (resultado.getMinute() * 60) + resultado.getSecond() % 3600;
    }

    private double generarSolar(JSONObject zonaCentral, JSONObject cent) {
        JSONObject central = (JSONObject) cent.get(cent.keys().next());
        String amanecer = zonaCentral.getString("SUNRISE");
        String anochecer = zonaCentral.getString("SUNSET");
        int segundosLuz = segundosLuz(amanecer, anochecer);
        double energia;
        String stringAux = zonaCentral.getString("RADMED");
        double radiacion = Double.parseDouble(stringAux.replaceFirst(",","."));
        if (radiacion < 150) {
            energia = (central.getInt("baja") / 3600.0)  * segundosLuz;
        } else if (radiacion > 225) {
            energia = (central.getInt("alta") / 3600.0) * segundosLuz;
        } else {
            energia = (central.getInt("media") / 3600.0) * segundosLuz;
        }
        return energia;
    }

    private double generarGeo(JSONObject zonaCentral, JSONObject cent) {
        JSONObject central = (JSONObject) cent.get(cent.keys().next());
        String stringAux = zonaCentral.getString("TMED");
        double temp = Double.parseDouble(stringAux.replaceFirst(",","."));
        double energia;
        int aux = 0;
        if (temp < 10) {
            aux = central.getInt("baja");
            energia = aux;
        } else if (temp > 25) {
            aux = central.getInt("alta");
            energia =  aux;
        } else {
            aux = central.getInt("media");
            energia = aux;
        }
        return energia;
    }

    private double generarEolica(JSONObject zonaCentral, JSONObject cent) {
        JSONObject central = (JSONObject) cent.get(cent.keys().next());
        String stringAux = zonaCentral.getString("VVMED");
        double viento = Double.parseDouble(stringAux.replaceFirst(",","."));
        double energia;
        if (viento < 10) {
            energia = central.getInt("leve");
        } else if (viento > 25) {
            energia = central.getInt("fuerte");
        } else {
            energia = central.getInt("moderado");
        }
        return energia;
    }

    public double calcularTotal(JSONObject demanda) {
        double total = 0.0;
        int tam = demanda.length();
        Object[] keys = demanda.keySet().toArray();
        JSONObject areas = (JSONObject) demanda.get(keys[0].toString());
        Object[] keysAreas = areas.keySet().toArray();
        for (int i = 0; i < tam; i++) {
            JSONObject area = (JSONObject) areas.get(keysAreas[i].toString());
            String numPunto = area.getString("Total").replaceFirst(",",".");
            total += Double.parseDouble(numPunto);
        }
        return total;
    }

    public double capacidadEmbalse(JSONObject zonaEmbalse,JSONObject datosEmbalses, JSONObject embalse, int i){
        double evo = zonaEmbalse.getDouble("ETO");
        double precipitaciones = zonaEmbalse.getDouble("PREC");
        double superficie = embalse.getDouble("superficie");
        double sumaPrec = (((superficie * 10000) * precipitaciones) / 1000000000);
        double consumo = datosEmbalses.getDouble("Consumption");
        double rios = datosEmbalses.getDouble("River contribution");
        evo = (((evo * 24) / 1000));
        double altura = 0.0;
        if(i == 1){
            altura = capacidadActual1 / superficie;
        }else{
            altura = capacidadActual2 / superficie;
        }
        altura -= evo;
        double capacidad = altura * superficie;
        capacidad = (capacidad + sumaPrec + rios) - consumo;
        return capacidad;
    }
    public double porcentajeEmbalse(double capacidad, JSONObject embalse){
        double capTotal = embalse.getDouble("capacidad_total");
        return ((capacidad / capTotal) * 100);
    }

    public double generarHidraulica(JSONObject central, double quitar, int i) {
        double capMax = central.getDouble("capacidad_total");
        double volumenQuitado = (capMax / 100) * quitar;
        if (i == 1) {
            quitadoEmbalse1 += quitar;
        } else {
            quitadoEmbalse2 += quitar;
        }
        return volumenQuitado * central.getDouble("ratio");
    }

    public void generarElectricidad(JSONObject clima, JSONObject demand) {
        ApiAccess api = new ApiAccess();
        Object[] keys = clima.keySet().toArray();
        JSONObject climatology = (JSONObject) clima.get(keys[0].toString());
        double cap1 = capacidadEmbalse((JSONObject) climatology.get("WATER001"),hydrodata1, api.itemData("water", centralesHidraulicas.getString(0)),1);
        double cap2 = capacidadEmbalse((JSONObject) climatology.get("WATER002"),hydrodata2, api.itemData("water", centralesHidraulicas.getString(1)),2);
        capacidadActual1 = porcentajeEmbalse(cap1,api.itemData("water", centralesHidraulicas.getString(0)));
        capacidadActual2 = porcentajeEmbalse(cap1,api.itemData("water", centralesHidraulicas.getString(1)));
        ETotal = calcularTotal(demand);
        double energia = 0;

        //comprobar el 10% de los embalses si se puede
        if (capacidadActual1 >= 90) {
            generacionCentrales[2] += generarHidraulica(api.itemData("water", centralesHidraulicas.getString(0)), 10, 1);
            energia += generacionCentrales[2];
            if (energia >= ETotal) {
                quitadoEmbalse1 = 10;
                return;
            }
        }
        if (capacidadActual2 >= 90) {
            generacionCentrales[3] += generarHidraulica(api.itemData("water", centralesHidraulicas.getString(1)), 10, 2);
            energia += generacionCentrales[3];
            if (energia >= ETotal) {
                quitadoEmbalse1 = 10;
                return;
            }
        }
        //usar la fuentes verdes
        generacionCentrales[0] = generarGeo((JSONObject) climatology.get("GEO001"), api.itemData("geo", centralesGeotermicas.getString(0)));
        generacionCentrales[1] = generarGeo((JSONObject) climatology.get("GEO002"), api.itemData("geo", centralesGeotermicas.getString(1)));
        generacionCentrales[4] = generarEolica((JSONObject) climatology.get("WIND001"), api.itemData("wind", centralesEolicas.getString(0)));
        generacionCentrales[5] = generarEolica((JSONObject) climatology.get("WIND002"), api.itemData("wind", centralesEolicas.getString(1)));
        generacionCentrales[6] = generarEolica((JSONObject) climatology.get("WIND003"), api.itemData("wind", centralesEolicas.getString(2)));
        generacionCentrales[7] = generarSolar((JSONObject) climatology.get("SUN001"), api.itemData("sun", centralesSolares.getString(0)));
        generacionCentrales[8] = generarSolar((JSONObject) climatology.get("SUN002"), api.itemData("sun", centralesSolares.getString(1)));
        generacionCentrales[9] = generarSolar((JSONObject) climatology.get("SUN003"), api.itemData("sun", centralesSolares.getString(2)));
        energia = energia + generacionCentrales[0] + generacionCentrales[1] + generacionCentrales[4] + generacionCentrales[5] + generacionCentrales[6] + generacionCentrales[7] + generacionCentrales[8] + generacionCentrales[9];
        if (energia >= ETotal) {
            return;
        } else {
            //se vacian embalses para suplir demanda
            if (capacidadActual1 > 35) {
                double quitar = capacidadActual1 - 35;
                generacionCentrales[2] += generarHidraulica(api.itemData("water", centralesHidraulicas.getString(0)), quitar, 1);
                energia += generacionCentrales[2];
                if (energia >= ETotal) {
                    quitadoEmbalse1 = quitar;
                    return;
                }
            }
            if (capacidadActual2 > 35) {
                double quitar = capacidadActual2 - 35;
                generacionCentrales[3] += generarHidraulica(api.itemData("water", centralesHidraulicas.getString(1)), quitar, 2);
                energia += generacionCentrales[3];
                if (energia >= ETotal) {
                    quitadoEmbalse2 = quitar;
                    return;
                }
            }
            //no da con las verdes, toca usar carbon
            generacionCentrales[10] = ETotal - energia;
            return;
        }
    }

    public double[] optimizacion(double [] centralesEnUso){
        double optimizacion[] = {0,0,0,0,0,0,0,0,0,0,0};

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
        JSONObject geo = (JSONObject) listaCentrales.get(3);
        JSONObject eol = (JSONObject) listaCentrales.get(1);
        JSONObject sun = (JSONObject) listaCentrales.get(0);
        JSONObject wat = (JSONObject) listaCentrales.get(2);
        JSONObject col = (JSONObject) listaCentrales.get(4);
        objective.setCoefficient(geo1, ((JSONObject) geo.get("GEO001")).getDouble("coste_generacion"));
        objective.setCoefficient(geo2, ((JSONObject) geo.get("GEO002")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) wat.get("WAT001")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) wat.get("WAT002")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) eol.get("WIND001")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) eol.get("WIND002")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) eol.get("WIND003")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) sun.get("SUN001")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) sun.get("SUN002")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) sun.get("SUN003")).getDouble("coste_generacion"));
        objective.setCoefficient(geo1, ((JSONObject) col.get("COAL001")).getDouble("coste_generacion"));
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
