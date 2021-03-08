/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cid.wsAS400;

/**
 *
 * @author fgallardo
 */
public class Destruccion {
    
    private String codigo;
    private String descripcion;
    private String lote;
    private String anio;
    private String mes;
    private String anioExpira;
    private String mesExpira;
    private String saldo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnioExpira() {
        return anioExpira;
    }

    public void setAnioExpira(String anioExpira) {
        this.anioExpira = anioExpira;
    }

    public String getMesExpira() {
        return mesExpira;
    }

    public void setMesExpira(String mesExpira) {
        this.mesExpira = mesExpira;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
    
}
