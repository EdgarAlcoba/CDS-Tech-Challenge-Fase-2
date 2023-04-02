package com.sinco.CDSFase2.controllers;

import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;

public class DbAccess {
    private Connection connection;
    private final String dbPath;

    public DbAccess() {
        dbPath = "C:/Users/edgar/IdeaProjects/CDS-Tech-Challenge-Fase-2/src/main/resources/EmbalsesDB.sqlite3";
    }

    /*
    public void initDB(String host, String user, String pass, String dbName) {
        DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbName, user, pass);
    }

    public boolean hasCredentials() {
        return !(HOST.isBlank() || USER.isBlank() || PASS.isBlank());
    }

    public void connect() throws ErrorException {
        try {
            connection = DriverManager.getConnection(HOST, USER, PASS);
        } catch (SQLException e) {
            throw new ErrorException("Hubo un error conectandose al servidor de la aplicación", "Database connection error: " + e.getMessage());
        }
    }

    public Connection getConnection() throws ErrorException {
        if (connection == null) {
            throw new ErrorException("Hubo un error conectandose al servidor de la aplicación", "The database connection is null");
        }
        return connection;
    }*/
    private Connection connect() {
        // SQLite connection string
        String url = dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * select all rows in the warehouses table
     */
    public void selectAll() {
        String sql = "SELECT AMBITO_NOMBRE,EMBALSE_NOMBRE,FECHA,AGUA_TOTAL,AGUA_ACTUAL,ELECTRICO_FLAG FROM embalses  LIMIT 5";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("AMBITO_NOMBRE") + "\t" +
                        rs.getString("EMBALSE_NOMBRE") + "\t" +
                        rs.getString("FECHA") + "\t" +
                        rs.getInt("AGUA_TOTAL") + "\t" +
                        rs.getInt("AGUA_ACTUAL") + "\t" +
                        rs.getInt("ELECTRICO_FLAG") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Pair<Integer, String>> getAguaActual(String nombreEmbalse, int year) {
        String sql = "SELECT * FROM embalses WHERE EMBALSE_NOMBRE = '" + nombreEmbalse + "' AND FECHA LIKE '__/__/" + year + "'";
        //System.out.println(sql);
        ArrayList<Pair<Integer, String>> lista = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Pair<Integer, String> pair = new Pair<>(rs.getInt("AGUA_ACTUAL"), rs.getString("FECHA"));
                lista.add(pair);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(list.toString());
        return lista;
    }

    public int getCapacidad(String nombreEmbalse) {
        String sql = "SELECT * FROM embalses WHERE EMBALSE_NOMBRE = '" + nombreEmbalse + "'";
        try (
                Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            if(rs.next()){
                return rs.getInt("AGUA_TOTAL");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error");
        }
        return -1;
    }
}
