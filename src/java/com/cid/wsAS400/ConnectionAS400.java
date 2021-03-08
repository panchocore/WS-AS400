package com.cid.wsAS400;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author asistemas
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionAS400 {

    public static Connection GetConnection() {
        Connection conexion = null;

        try {
            String url = "";
            Properties tmpP = new Propiedades().getProperties();
            String userName = tmpP.getProperty("Usuario");
            String password = tmpP.getProperty("Clave");
            url = tmpP.getProperty("Url");

            /*System.out.println("userName "+userName);
             System.out.println("password "+ password);
             System.out.println("url "+ url);*/
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            conexion = DriverManager.getConnection(url, userName, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Error1 en la Conexion con la BD "
                    + ex.getMessage());
            conexion = null;
        } catch (SQLException ex) {
            System.out.println("Error2 en la Conexion con la BD "
                    + ex.getMessage());
            conexion = null;
        } catch (Exception ex) {
            System.out.println("Error3 en la Conexion con la BD "
                    + ex.getMessage());
            conexion = null;
        } finally {
            return conexion;
        }
    }
}
