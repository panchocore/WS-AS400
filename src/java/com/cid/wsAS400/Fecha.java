/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cid.wsAS400;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dguilcapi
 */
public class Fecha {

    private static String formato;

    public static Date DeStringADate(String fecha) {
        // System.out.println("FECHA INICIAL DE STRING A DATE " + fecha);

        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy/MM/dd");

        Date fechaDate = null;
        try {
            fechaDate = dmyFormat.parse(fecha);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaDate;
    }

    public static String DeDateAString(Date fecha){

        // System.out.println("FECHA INICIAL DE DATE A STRING " + fecha);
        // SimpleDateFormat formato2 = new SimpleDateFormat("yyyy/MM/dd");
        // Date datFecha = fecha;
        // String strFecha = null;
        // strFecha = formato2.format(datFecha);
        // Se pueden definir formatos diferentes con la clase DateFormat
        // Obtenemos la fecha y la hora con el formato yyyy-MM-dd HH:mm:ss
        DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
        String convertido = formatoFecha.format(fecha);
        // System.out.println(convertido);

        return convertido;

    }

    public static int obtenerAnio(Date date) {

        if (null == date) {

            return 0;

        } else {

            formato = "yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Integer.parseInt(dateFormat.format(date));

        }

    }

    public static int obtenerMes(Date date) {

        if (null == date) {

            return 0;

        } else {

            formato = "MM";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Integer.parseInt(dateFormat.format(date));

        }

    }

    public static int obtenerDia(Date date) {

        if (null == date) {

            return 0;

        } else {

            formato = "dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Integer.parseInt(dateFormat.format(date));

        }

    }

    public static int compararFechasConDate(String fecha1, String fechaActual) {

        if (fecha1 != null) {

            // System.out.println("Parametro String Fecha 1 = " + fecha1 + "\n"
            // + "Parametro String fechaActual = " + fechaActual + "\n");
            String resultado = "";
            int dato = 0;
            /**
             * Obtenemos las fechas enviadas en el formato a comparar
             */
            SimpleDateFormat formateador = new SimpleDateFormat(
                    "yyyy/MM/dd");
            Date fechaDate1 = null;
            try {
                fechaDate1 = formateador.parse(fecha1);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            Date fechaDate2 = null;
            try {
                fechaDate2 = formateador.parse(fechaActual);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
            }
            // System.out.println("Parametro Date Fecha 1 = " + fechaDate1
            // + "\n" + "Parametro Date fechaActual = " + fechaDate2
            // + "\n");
            if (fechaDate1.before(fechaDate2)) {
                resultado = "La Fecha 1 es menor ";
                dato = -1;
            } else {
                if (fechaDate2.before(fechaDate1)) {
                    resultado = "La Fecha 1 es Mayor ";
                    dato = 1;
                } else {
                    resultado = "Las Fechas Son iguales ";
                    dato = 0;
                }
            }
            // System.out.println("RESUL: " + resultado);
            return dato;
        } else {
            return 1;
        }
    }

    public static long diferenciaDias(int anio, int mes, int dia) {

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; // Milisegundos al
        // día
        Date hoy;
        hoy = new Date(); // Fecha de hoy
        Calendar calendar = new GregorianCalendar(anio, mes - 1, dia);
        java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());
        long diferencia = (hoy.getTime() - fecha.getTime()) / MILLSECS_PER_DAY;
        System.out.println(diferencia);
        return diferencia;
    }

    public static String formatFecha(String siglo, String fecha) {

        char[] letras = null;
        String date = fecha.toString();
        letras = date.toCharArray();

        return siglo + String.valueOf(letras[1]) + String.valueOf(letras[2])
                + "/" + String.valueOf(letras[3]) + String.valueOf(letras[4])
                + "/" + String.valueOf(letras[5]) + String.valueOf(letras[6]);
    }

    public static String formatHora(String hora) {

        char[] letras = null;

        letras = hora.toCharArray();
        int longitud = hora.length();

        if (longitud > 5) {
            return String.valueOf(letras[0]) + String.valueOf(letras[1]) + ":"
                    + String.valueOf(letras[2]) + String.valueOf(letras[3])
                    + ":" + String.valueOf(letras[4])
                    + String.valueOf(letras[5]);
        } else {
            return "0" + String.valueOf(letras[0]) + ":"
                    + String.valueOf(letras[1]) + String.valueOf(letras[2])
                    + ":" + String.valueOf(letras[3])
                    + String.valueOf(letras[4]);
        }

    }

}
