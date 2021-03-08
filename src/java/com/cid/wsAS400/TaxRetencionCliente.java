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
public class TaxRetencionCliente {

    private String cliente;
    private String cia;
    private String tipoInvt;
    private String tipoBienServicio;
    private Double taxRetFuente;
    private Double taxRetIva;
    private String estado;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCia() {
        return cia;
    }

    public void setCia(String cia) {
        this.cia = cia;
    }

    public String getTipoInvt() {
        return tipoInvt;
    }

    public void setTipoInvt(String tipoInvt) {
        this.tipoInvt = tipoInvt;
    }

    public String getTipoBienServicio() {
        return tipoBienServicio;
    }

    public void setTipoBienServicio(String tipoBienServicio) {
        this.tipoBienServicio = tipoBienServicio;
    }

    public Double getTaxRetFuente() {
        return taxRetFuente;
    }

    public void setTaxRetFuente(Double taxRetFuente) {
        this.taxRetFuente = taxRetFuente;
    }

    public Double getTaxRetIva() {
        return taxRetIva;
    }

    public void setTaxRetIva(Double taxRetIva) {
        this.taxRetIva = taxRetIva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
