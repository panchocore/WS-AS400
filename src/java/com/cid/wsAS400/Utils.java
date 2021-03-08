/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cid.wsAS400;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asistemas
 */
public class Utils {

    public static String formatFechSystem(String fecha, int cantChars) {

        String fechFormat = "";

        if (fecha.length() == 1) {
            fechFormat = fecha;
        } else {

            if (cantChars == 8) {
                fechFormat = fecha.substring(0, 4) + "/"
                        + fecha.substring(4, 6) + "/" + fecha.substring(6, 8);
            }

            if (cantChars == 6) {
                fechFormat = "20" + fecha.substring(0, 2) + "/"
                        + fecha.substring(2, 4) + "/" + fecha.substring(4, 6);
            }
            if (cantChars > 8) {
                int cant = fecha.length();
                fechFormat = fecha.substring(cant - 4, cant) + "/"
                        + fecha.substring(cant - 6, cant - 4) + "/"
                        + fecha.substring(cant - 8, cant - 6);
            }
            if (cantChars == 0) {

                List<String> nueva_cadena = new ArrayList<String>();

                boolean valido = true;
                Character caracter = null;
                String dato = "";
                String s_caracteres = "/";
                for (int i = 0; i < fecha.length(); i++) {
                    valido = true;
                    caracter = s_caracteres.charAt(0);
                    if (fecha.charAt(i) == caracter) {
                        valido = false;
                        nueva_cadena.add(dato);
                        dato = "";
                    } else {
                        if (valido) {
                            dato += fecha.charAt(i);
                        }
                    }
                }
                nueva_cadena.add(dato);

                String año = "20" + nueva_cadena.get(0);
                String mes = "";
                String dia = "";

                if (nueva_cadena.get(1).length() < 2) {
                    mes = "0" + nueva_cadena.get(1);
                } else {
                    mes = nueva_cadena.get(1);
                }

                if (nueva_cadena.get(2).length() < 2) {
                    dia = "0" + nueva_cadena.get(2);
                } else {
                    dia = nueva_cadena.get(2);
                }

                fechFormat = año + "/" + mes + "/" + dia;

            }
        }

        return fechFormat;

    }
}
