/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cid.wsAS400;

/**
 *
 * @author asistemas
 */
public class Ventas {

    private String cia;
    private String lopCodigo;
    private String lop;
    private String codigoItem;
    private String nombreItem;
    private String tipoInventario;
    private String nombreTipoInventario;
    private int stock;
    private int stockMenorAnio;
    private int stockMayorAnio;

    public String getCia() {
        return cia;
    }

    public void setCia(String cia) {
        this.cia = cia;
    }

    public String getLopCodigo() {
        return lopCodigo;
    }

    public void setLopCodigo(String lopCodigo) {
        this.lopCodigo = lopCodigo;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public String getTipoInventario() {
        return tipoInventario;
    }

    public void setTipoInventario(String tipoInventario) {
        this.tipoInventario = tipoInventario;
    }

    public String getNombreTipoInventario() {
        return nombreTipoInventario;
    }

    public void setNombreTipoInventario(String nombreTipoInventario) {
        this.nombreTipoInventario = nombreTipoInventario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMenorAnio() {
        return stockMenorAnio;
    }

    public void setStockMenorAnio(int stockMenorAnio) {
        this.stockMenorAnio = stockMenorAnio;
    }

    public int getStockMayorAnio() {
        return stockMayorAnio;
    }

    public void setStockMayorAnio(int stockMayorAnio) {
        this.stockMayorAnio = stockMayorAnio;
    }

}
