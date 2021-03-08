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
public class OrdenPedido {

    private String codigoCia;
    private String ordenNumero;
    private String numeroPedido;
    private String cliente;
    private String tipoCliente;
    private String bodega;
    private String fechaCreacion;
    private String usuarioCrea;
    private String usuarioAprueba;
    private String fechaAprobacion;

    public String getCodigoCia() {
        return codigoCia;
    }

    public void setCodigoCia(String codigoCia) {
        this.codigoCia = codigoCia;
    }

    public String getOrdenNumero() {
        return ordenNumero;
    }

    public void setOrdenNumero(String ordenNumero) {
        this.ordenNumero = ordenNumero;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public String getUsuarioAprueba() {
        return usuarioAprueba;
    }

    public void setUsuarioAprueba(String usuarioAprueba) {
        this.usuarioAprueba = usuarioAprueba;
    }

    public String getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(String fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

}
