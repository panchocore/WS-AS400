/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cid.wsAS400;

import com.ibm.as400.access.*;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author fgallardo
 */
@WebService(serviceName = "wsMain")
public class wsMain {

    private Connection miConexion;
    private List<Guias> lstGuias;
    private List<Ventas> lstVentas;
    private List<Shipment> lstShipment;
    private List<CuentasCobrar> lstCuentasCobrar;
    private List<Costos> lstCostos;
    private List<Provincia> lstProvincia;
    private List<Canton> lstCanton;
    private List<Parroquia> lstParroquia;
    private List<OrdenPedido> lstOrdenPedido;
    private List<ComprobantesRetencion> lstComprobantes;
    private List<Cias> lstCias;
    private List<Cliente> lstClientes;
    private List<TipoInventario> lstTipoInvt;
    private List<TaxRetencionCliente> lstTaxRet;
    private List<CarteraInstitucionalAS> lstCartera;
    private List<Destruccion> lstDestruccion;
    private List<Producto> lstProducto;
    private List<Documentos> lstDocs;
    private List<Proveedor> lstProv;
    private List<Familias> lstFamilias;
    private List<CountDocAS400> lstCountDoc;
    private List<Facturacion> lstFacturacion;
    private List<Reservas> lstReservas;
    private List<Bodegas> lstBodegas;
    private List<Multas> lstMultas;
    private List<AreaPrincipal> lstArea;
    private List<Responsable> lstResponsable;

    /*private static final String HOST = "192.168.0.6";
     private static final String UID = "EARAUJO";
     private static final String PWD = "EA20GR30";*/
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "traerListaGuias")
    public List<Guias> traerListaGuias(@WebParam(name = "fechaInicio") String fechaInicio, @WebParam(name = "fechaFin") String fechaFin) {

        Statement stmt = null;
        ResultSet rs;
        Guias tmp = new Guias();
        lstGuias = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                rs = stmt
                        .executeQuery("SELECT GCFECH fecha_guia, GCNRO numero_guia, "
                                + "RTRIM(CEIB5I) transportista, GDSHPT factura,KYIAOQ concat '/' concat KYIAOR concat '/' concat KYIAOS fecha_factura, "
                                + "ACIAAA codigo_cliente, RTRIM(ACIB5I) cliente, RTRIM(ACIAP6) ciudad_cliente, RTRIM(ACIAP5) direccion_cliente, SUBSTR(DHIARL,2,7) fecha_despacho, "
                                + "GDFECR fecha_recpcion_cli, GDSTS estado_despacho, GDBULT bultos, GDFRSI fecha_sis_recib, GDFECB fecha_back_bod, "
                                + "GDFBSI fecha_sis_back_bod, RTRIM(GCCHOF) chofer, ESRES1 concat '-' concat ESRPT1 concat '-' concat ESRSE1 secuencial "
                                + "FROM EFCP00D.IOPGDC, EFCP00D.IOPGD, EFCP00D.IOPLSIN2, EFCP00D.IRFLTSP2, EFCP00D.IRFLCUS2, EFCP00D.IOPLIVC2, EFCP00D.IRFLESR0  "
                                + "WHERE GCFECH between '"
                                + fechaInicio
                                + "' AND '"
                                + fechaFin
                                + "'AND GDNRO = GCNRO AND GDCIA = GCCIA AND DHIABN = GCCIA "
                                + "AND DHIB72 = GDSHPT AND GCTRAN = CEIAE1 AND GCSTS in ('C','D') AND DHIAAA = ACIAAA AND GDSHPT = KYIB74 "
                                + "AND KYIABN = GCCIA  AND ESRCIA = GCCIA  AND ESRTIP = 'DINV' AND ESRPRE = GDPREF  AND ESRSEC = GDSHPT "
                                + "ORDER BY GCFECH DESC");

                while (rs.next()) {
                    tmp = new Guias();
                    tmp.setFechaGuia(Utils.formatFechSystem(rs.getString(1), 8));
                    tmp.setNumeroGuia(rs.getString(2));
                    tmp.setTransportista(rs.getString(3));
                    tmp.setFactura(rs.getString(4));
                    tmp.setFechaFactura(Utils.formatFechSystem(rs.getString(5), 0));
                    tmp.setCodigoCliente(rs.getString(6));
                    tmp.setNombreCliente(rs.getString(7));
                    tmp.setCiudadCliente(rs.getString(8));
                    tmp.setDireccionCliente(rs.getString(9));
                    tmp.setFechaDespacho(Utils.formatFechSystem(rs.getString(10), 6));
                    tmp.setFechaRecepcionCliente(Utils.formatFechSystem(
                            rs.getString(11), 8));
                    tmp.setEstadoDespacho(rs.getString(12));
                    tmp.setBultos(rs.getInt(13));
                    tmp.setFechaRecibido(Utils.formatFechSystem(rs.getString(14),
                            10));
                    tmp.setFechaBackBod(Utils.formatFechSystem(rs.getString(15), 8));
                    tmp.setFechaSisBackBod(Utils.formatFechSystem(
                            rs.getString(16), 10));
                    tmp.setChofer(rs.getString(17));
                    tmp.setSecuencial(rs.getString(18));

                    lstGuias.add(tmp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstGuias = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstGuias;
    }

    @WebMethod(operationName = "traerArchivoVentas")
    public List<Ventas> traerArchivoVentas() {

        Statement stmt = null;
        ResultSet rs;
        Ventas tmp = new Ventas();
        lstVentas = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                rs = stmt
                        .executeQuery("SELECT LOTE.ASIABN cia_codigo, APIAA9 lop_codigo, Ltrim(Rtrim(LOP.AQIABA)) lop_nombre, trim(LOTE.ASIATC) prd_codigo, "
                                + "trim(PRODUCTO.APIAA1) prd_descripcion, TIPOINV.R0ICV4 Tipoinv_codigo, "
                                + "trim(TIPOINV.R0ICXM) Tipoinv_descripcion, SUM(LOTE.ASIAK6) Stock, "
                                + "( SELECT coalesce(SUM(L.ASIAK6),0) "
                                + "FROM  EFCP00D.IFGLFGB2 AS L "
                                + "WHERE L.ASIAK6 >= 0 "
                                + "AND L.ASIABN='002' "
                                + "AND  L.ASIAAY IN ('030', '031', '034') "
                                + "AND  ((L.ASIA9G = INTEGER(SUBSTR(YEAR (current timestamp) - 1, 3, 2)) "
                                + "AND L.ASIA9H >= MONTH (current timestamp)) or (L.ASIA9G = INTEGER(SUBSTR(YEAR (current timestamp), 3, 2)) "
                                + "AND L.ASIA9H <= MONTH (current timestamp))) "
                                + "AND  L.ASIATC = LOTE.ASIATC ) as StockMenorAnio, "
                                + "( SELECT coalesce(SUM(L.ASIAK6),0) "
                                + "FROM  EFCP00D.IFGLFGB2 AS L "
                                + "WHERE L.ASIAK6 >= 0 "
                                + "AND  L.ASIABN='002' "
                                + "AND  L.ASIAAY IN ('030', '031', '034') "
                                + "AND ((L.ASIA9G = INTEGER(SUBSTR(YEAR (current timestamp) - 1, 3, 2)) "
                                + "AND L.ASIA9H < MONTH (current timestamp)) "
                                + "OR (L.ASIA9G < INTEGER(SUBSTR(YEAR (current timestamp) - 1, 3, 2)))) "
                                + "AND  L.ASIATC = LOTE.ASIATC ) as StockMayorAnio "
                                + "FROM  EFCP00D.IFGLFGB2 AS LOTE, EFCP00D.IRFLPRD2 AS PRODUCTO, EFCP00D.IRFLWHS2 AS BODEGA , EFCP00D.IRFLIVT2 AS TIPOINV, EFCP00D.IRFLLOP2 AS LOP "
                                + "WHERE BODEGA.AMIAAY=LOTE.ASIAAY "
                                + "AND  LOTE.ASIAA0 = PRODUCTO.APIAA0 "
                                + "AND  LOTE.ASIATC=PRODUCTO.APIATC "
                                + "AND  LOTE.ASIATD=PRODUCTO.APIATD "
                                + "AND  TIPOINV.R0ICV4=PRODUCTO.APICV4 "
                                + "AND  LOTE.ASIAK6 >= 0 "
                                + "AND  LOP.AQIAA9=PRODUCTO.APIAA9 "
                                + "AND  LOTE.ASIABN='002' "
                                + "AND  LOTE.ASIAAY IN ('030', '031', '034') "
                                + "AND  PRODUCTO.APICV4 IN ('06', '07') "
                                + "AND  APIAA9 not in ('T', '6', '2', 'I') "
                                + "GROUP BY LOTE.ASIATC, LOTE.ASIABN, LOTE.ASIATC, PRODUCTO.APIAA1, APIAA9, LOP.AQIABA, TIPOINV.R0ICV4, TIPOINV.R0ICXM "
                                + "ORDER BY TIPOINV.R0ICXM, LOTE.ASIATC ");

                while (rs.next()) {
                    tmp = new Ventas();
                    tmp.setCia(rs.getString(1));
                    tmp.setLopCodigo(rs.getString(2));
                    tmp.setLop(rs.getString(3));
                    tmp.setCodigoItem(rs.getString(4));
                    tmp.setNombreItem(rs.getString(5));
                    tmp.setTipoInventario(rs.getString(6));
                    tmp.setNombreTipoInventario(rs.getString(7));
                    tmp.setStock(rs.getInt(8));
                    tmp.setStockMenorAnio(rs.getInt(9));
                    tmp.setStockMayorAnio(rs.getInt(10));

                    lstVentas.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstVentas = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstVentas;
    }

    @WebMethod(operationName = "traerValidacionShipment")
    public List<Shipment> traerValidacionShipment() {

        Statement stmt = null;
        ResultSet rs;
        Shipment tmp = new Shipment();
        lstShipment = new ArrayList<Shipment>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                rs = stmt
                        .executeQuery("SELECT  DHIABN, DHIB72, DHIAAY, DHIARN, DHIARO, "
                                + "DHIARP, DHIAAA, DHICWL, DHIB74, DHIAOL, DHIAOQ, DHIAOR, DHIAOS "
                                + "FROM EFCP00D.IOPLSIN2 "
                                + "WHERE DHIARR = '' " + "AND DHIBZG = 0");

                while (rs.next()) {
                    tmp = new Shipment();
                    tmp.setCia(rs.getString(1));
                    tmp.setNumeroShipment(rs.getString(2));
                    tmp.setCodigoBodega(rs.getString(3));
                    tmp.setFechaContable(rs.getString(4) + rs.getString(5)
                            + rs.getString(6));
                    tmp.setCodigoCliente(rs.getString(7));
                    tmp.setTipoFactura(rs.getString(8));
                    tmp.setNumeroShipment(rs.getString(9));
                    tmp.setUsuario(rs.getString(10));
                    tmp.setFechaDocumento(rs.getString(11) + rs.getString(12)
                            + rs.getString(13));

                    lstShipment.add(tmp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstShipment = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstShipment;
    }

    @WebMethod(operationName = "erroresCuentasPorCobrar")
    public List<CuentasCobrar> erroresCuentasPorCobrar() {

        Statement stmt = null;
        ResultSet rs;
        CuentasCobrar tmp = new CuentasCobrar();
        lstCuentasCobrar = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                rs = stmt
                        .executeQuery("SELECT CTIABN, CTIBB3, CTIB5D, CTIB5E, RTRIM(CTIAAD), CTIB3N, RTRIM(SUBSTR(CTIAJC,2,7)), CTIBQL, CTIBQM, CTIBQN "
                                + " FROM EFCP00D.IARLOPN0 "
                                + " WHERE CTIAMB =' ' AND CTIBZG=' ' AND CTIBQL >=17 ");

                while (rs.next()) {
                    tmp = new CuentasCobrar();
                    tmp.setCia(rs.getString(1));
                    tmp.setTipoDocumento(rs.getString(2));
                    tmp.setPrefijo(rs.getInt(3));
                    tmp.setNumeroDocumento(rs.getString(4));
                    tmp.setEstado(rs.getString(5));
                    tmp.setCliente(rs.getString(6));
                    tmp.setFechaDocumento(rs.getString(7));
                    tmp.setAnio(rs.getInt(8));
                    tmp.setMes(rs.getInt(9));
                    tmp.setDia(rs.getInt(10));
                    lstCuentasCobrar.add(tmp);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstCuentasCobrar = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstCuentasCobrar;
    }

    @WebMethod(operationName = "obtenerCostos")
    public List<Costos> obtenerCostos(@WebParam(name = "anio") String anio, @WebParam(name = "mes") String mes) {

        Statement stmt = null;
        ResultSet rs;
        Costos tmp = new Costos();
        lstCostos = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT SDIABN AS CIA, SDIC2M AS ANIO, SDIC2N AS MES, SDIACX AS CUENTA, SDIAWC AS SUBCUENTA, SDIAA9 AS CODIGO_LOP, RTRIM(AQIABA) AS LOP, RTRIM(Q1IA5E) AS FAMILIA, SDIC2T AS VALOR, "
                        + "Q1ICPQ AS TIPO, RTRIM(Q1ICPS) AS DESCRIPCION, Q1IC0T AS COD_PROVEEDOR, RTRIM(IHIB5I) AS NOMBRE_PROVEEDOR, IHIC1O AS TIPO_PROVEEDOR, SDIC10 AS TIP_DOC, "
                        + "SDIC12 AS NUM_DOC, SDIC2L AS NUM_FACT, RTRIM(SDIDHI) AS OC, '00000' AS EP "
                        + "FROM S0646ED5.EFCP00D.IGLLJNL2, S0646ED5.EFCP00D.IAPMOPID, S0646ED5.EFCP00D.IGLLJND2, S0646ED5.EFCP00D.IRFLVND2, S0646ED5.EFCP00D.IRFLLOP2 "
                        + "WHERE ONIABN = SDIABN "
                        + "AND ONIB85 = SDIC2M "
                        + "AND ONICBK = SDIC2N "
                        + "AND ONIB8H = SDIC12 "
                        + "AND ONIABN = Q1IABN "
                        + "AND ONIB85 = Q1IB85 "
                        + "AND Q1ICBK = ONICBK "
                        + "AND Q1IC0T = SDIC0T "
                        + "AND ONICPO = Q1ICPO "
                        + "AND Q1IC0T = IHIC0T "
                        + "AND Q1IAA9 = AQIAA9 "
                        + "AND ONIABN = '002' "
                        + "AND ONIB85 = " + anio
                        + " AND ONICBK = " + mes
                        + " AND Q1IC0T != '' "
                        + "AND Q1IACX IN ('02110', '02210') "
                        + "AND Q1IAWC IN ('10000', '00000') "
                        + "ORDER BY ONIB8H";

                rs = stmt.executeQuery(qry);

                //System.out.println("Query " + qry);
                while (rs.next()) {
                    tmp = new Costos();
                    tmp.setCia(rs.getString(1));
                    tmp.setAnio(rs.getInt(2));
                    tmp.setMes(rs.getInt(3));
                    tmp.setCuenta(rs.getString(4));
                    tmp.setSubcuenta(rs.getString(5));
                    tmp.setCodigoLop(rs.getString(6));
                    tmp.setNombreLop(rs.getString(7));
                    tmp.setFamilia(rs.getString(8));
                    tmp.setValor(rs.getDouble(9));
                    tmp.setTipo(rs.getString(10));
                    tmp.setDescripcion(rs.getString(11));
                    tmp.setCodigoProveedor(rs.getString(12));
                    tmp.setNombreProveedor(rs.getString(13));
                    tmp.setTipoProveedor(rs.getString(14));
                    tmp.setTipoDocumento(rs.getString(15));
                    tmp.setNumeroDocumento(rs.getString(16));
                    tmp.setNumeroFactura(rs.getString(17));
                    tmp.setOrdenCompra(rs.getString(18));
                    tmp.setEntradaPlanta(rs.getString(19));

                    lstCostos.add(tmp);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstCostos = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstCostos;
    }

    @WebMethod(operationName = "insertUpdateCliente")
    public int insertUpdateCliente(@WebParam(name = "cliente") Cliente cliente) {

        Statement stmt = null;
        ResultSet rs;
        int r = -1;
        boolean ban = true;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (cliente.getCodigo().equals(null) || cliente.getCodigo().isEmpty() || cliente.getCodigo().equals("")) {
                r = -1;
            } else {
                if (miConexion != null) {

                    stmt = miConexion.createStatement();
                    String qry = "SELECT CAIAAA, CAINAM "
                            + " FROM S0646ED5.EFCP00D.IRFLCAI0  "
                            + "WHERE CAIAAA = '" + cliente.getCodigo() + "' ";

                    //System.out.println("Query " + qry);
                    rs = stmt.executeQuery(qry);
                    //System.out.println("Size " + rs.getRow());

                    while (rs.next()) {
                        //System.out.println("Size " + rs.getRow());
                        if (rs.getRow() == 0) {
                            ban = true;
                        } else {
                            ban = false;
                        }
                    }
                    //System.out.println("ban " + ban);
                    //System.out.println("Size " + rs.getRow());
                    if (ban) {
                        qry = "INSERT INTO S0646ED5.EFCP00D.IRFLCAI0(CAIAAA, CAICSU, CAISEX, CAIECI, CAICPR, CAICCA, CAICPA, CAINAM) VALUES('"
                                + cliente.getCodigo() + "', '" + cliente.getClase() + "', '" + cliente.getGenero() + "', '" + cliente.getEstadoCivil() + "', '" + cliente.getProvincia() + "', '" + cliente.getCanton() + "', '" + cliente.getParroquia() + "', '" + cliente.getNombre() + "')";
                        //System.out.println("Query " + qry);
                        r = stmt.executeUpdate(qry);
                    } else {
                        qry = "UPDATE S0646ED5.EFCP00D.IRFLCAI0 SET "
                                + "CAINAM = ' " + cliente.getNombre()
                                + "', CAICSU = '" + cliente.getClase()
                                + "', CAISEX = '" + cliente.getGenero()
                                + "', CAIECI = '" + cliente.getEstadoCivil()
                                + "', CAICPR = '" + cliente.getProvincia()
                                + "', CAICCA = '" + cliente.getCanton()
                                + "', CAICPA = '" + cliente.getParroquia()
                                + "' WHERE CAIAAA = '" + cliente.getCodigo() + "' ";
                        //System.out.println("Query " + qry);
                        r = stmt.executeUpdate(qry);
                    }
                }
            }

        } catch (Exception e) {
            r = -1;
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "getCliente")
    public Cliente getCliente(@WebParam(name = "codigo") String codigo) {

        Statement stmt = null;
        ResultSet rs;
        Cliente tmp = new Cliente();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT CAIAAA, CAICSU, CAISEX, CAIECI, CAICPR, CAICCA, CAICPA, CAINAM "
                        + " FROM S0646ED5.EFCP00D.IRFLCAI0  "
                        + "WHERE CAIAAA = '" + codigo + "' ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    tmp = new Cliente();
                    tmp.setCodigo(rs.getString(1).trim());
                    tmp.setClase(rs.getString(2).trim());
                    tmp.setGenero(rs.getString(3).trim());
                    tmp.setEstadoCivil(rs.getString(4).trim());
                    tmp.setProvincia(rs.getString(5).trim());
                    tmp.setCanton(rs.getString(6).trim());
                    tmp.setParroquia(rs.getString(7).trim());
                    tmp.setNombre(rs.getString(8).trim());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return tmp;
    }

    @WebMethod(operationName = "consultaNaturaleza")
    public String consultaNaturaleza(@WebParam(name = "cia") String cia, @WebParam(name = "century") String century, @WebParam(name = "year") String year, @WebParam(name = "mes") String mes, @WebParam(name = "tipo") String tipo, @WebParam(name = "prefijo") String prefijo, @WebParam(name = "documento") String documento) {

        Statement stmt = null;
        ResultSet rs;
        String r = "-1";

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT SDIFNX "
                        + " FROM S0646ED5.EFCP00D.IAPMOPID "
                        + " WHERE SDIABN = '" + cia + "' "
                        + " AND SDIC13 = " + century
                        + " AND SDIC14 = " + year
                        + " AND SDIC15 = " + mes
                        + " AND SDIC10 = '" + tipo + "' "
                        + " AND SDIC11 = '" + prefijo + "' "
                        + " AND SDIC12 = '" + documento + "' ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    //System.out.println("Size " + rs.getRow());
                    r = rs.getString(1);
                }
            }

        } catch (Exception e) {
            r = "-1";
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateNaturaleza")
    public int updateNaturaleza(@WebParam(name = "cia") String cia, @WebParam(name = "century") String century, @WebParam(name = "year") String year, @WebParam(name = "mes") String mes, @WebParam(name = "tipo") String tipo, @WebParam(name = "prefijo") String prefijo, @WebParam(name = "documento") String documento, @WebParam(name = "naturaleza") String naturaleza) {

        Statement stmt = null;
        int r = -1;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "UPDATE S0646ED5.EFCP00D.IAPMOPID SET "
                        + " SDIFNX = '" + naturaleza + "' "
                        + " WHERE SDIABN = '" + cia + "' "
                        + " AND SDIC13 = " + century
                        + " AND SDIC14 = " + year
                        + " AND SDIC15 = " + mes
                        + " AND SDIC10 = '" + tipo + "' "
                        + " AND SDIC11 = '" + prefijo + "' "
                        + " AND SDIC12 = '" + documento + "' ";
                r = stmt.executeUpdate(qry);
            }

        } catch (Exception e) {
            r = -1;
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "consultaRetencion")
    public String[] consultaRetencion(@WebParam(name = "cia") String cia, @WebParam(name = "tipo") String tipo, @WebParam(name = "prefijo") String prefijo, @WebParam(name = "documento") String documento, @WebParam(name = "sufijo") String sufijo) {

        Statement stmt = null;
        ResultSet rs;
        String[] r = new String[3];

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT VOAIR, VOSRET, VONRET "
                        + " FROM S0646ED5.EFCP00D.IAPVOU "
                        + " WHERE VOCIA = '" + cia + "' "
                        + " AND VOTIPO = '" + tipo + "' "
                        + " AND VOPREF = " + prefijo
                        + " AND VONRO = '" + documento + "' "
                        + " AND VOSUFI = " + sufijo;

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    //System.out.println("Size " + rs.getRow());
                    r[0] = rs.getString(1);
                    r[1] = rs.getString(2);
                    r[2] = rs.getString(3);
                }
            }

        } catch (Exception e) {
            r[0] = "-1";
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateRetencion")
    public int updateRetencion(@WebParam(name = "cia") String cia, @WebParam(name = "tipo") String tipo, @WebParam(name = "prefijo") String prefijo, @WebParam(name = "documento") String documento, @WebParam(name = "sufijo") String sufijo, @WebParam(name = "ir") String ir, @WebParam(name = "serie") String serie, @WebParam(name = "numero") String numero) {

        Statement stmt = null;
        int r = -1;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "UPDATE S0646ED5.EFCP00D.IAPVOU SET "
                        + " VOAIR = " + ir + ", "
                        + " VOSRET = '" + serie + "', "
                        + " VONRET = '" + numero + "' "
                        + " WHERE VOCIA = '" + cia + "' "
                        + " AND VOTIPO = '" + tipo + "' "
                        + " AND VOPREF = " + prefijo
                        + " AND VONRO = '" + documento + "' "
                        + " AND VOSUFI = " + sufijo;
                r = stmt.executeUpdate(qry);
            }

        } catch (Exception e) {
            r = -1;
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "getProvincia")
    public List<Provincia> getProvincia() {

        Statement stmt = null;
        ResultSet rs;
        Provincia tmp = new Provincia();
        lstProvincia = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT IAPPRO, IAPDES "
                        + " FROM S0646ED5.EFCP00D.IAPPAIS  "
                        + "WHERE IAPNIV = 1";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    tmp = new Provincia();
                    tmp.setCodigo(rs.getString(1).trim());
                    tmp.setNombre(rs.getString(2).trim());

                    lstProvincia.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstProvincia;
    }

    @WebMethod(operationName = "getCanton")
    public List<Canton> getCanton(@WebParam(name = "provincia") String provincia) {

        Statement stmt = null;
        ResultSet rs;
        Canton tmp = new Canton();
        lstCanton = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT IAPCAN, IAPDES "
                        + " FROM S0646ED5.EFCP00D.IAPPAIS  "
                        + "WHERE IAPNIV = 2 "
                        + "AND IAPPRO = '" + provincia + "'";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    tmp = new Canton();

                    tmp.setCodigoCanton(rs.getString(1).trim());
                    tmp.setNombreCanton(rs.getString(2).trim());

                    lstCanton.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstCanton;
    }

    @WebMethod(operationName = "getParroquia")
    public List<Parroquia> getParroquia(@WebParam(name = "provincia") String provincia, @WebParam(name = "canton") String canton) {

        Statement stmt = null;
        ResultSet rs;
        Parroquia tmp = new Parroquia();
        lstParroquia = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT IAPPAR, IAPDES "
                        + " FROM S0646ED5.EFCP00D.IAPPAIS  "
                        + "WHERE IAPNIV = 3 "
                        + "AND IAPPRO = '" + provincia
                        + "' AND IAPCAN = '" + canton + "' ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    tmp = new Parroquia();

                    tmp.setCodigoParroquia(rs.getString(1).trim());
                    tmp.setNombreParroquia(rs.getString(2).trim());

                    lstParroquia.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstParroquia;
    }

    @WebMethod(operationName = "getOrdenPedido")
    public List<OrdenPedido> getOrdenPedido(@WebParam(name = "tipoCliente") String tipoCliente, @WebParam(name = "fechaIni") String fechaIni, @WebParam(name = "fechaFin") String fechaFin) {

        Statement stmt = null;
        ResultSet rs;
        OrdenPedido tmp = new OrdenPedido();
        lstOrdenPedido = new ArrayList<OrdenPedido>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT CCPCIA, CCPPED, RTRIM(CCPPEU), CCPCLI, CCPTCL, CCPBOD, CCPFEC, RTRIM(CCPUCR), RTRIM(CCPUC2), CCPFE2 "
                        + " FROM S0646ED5.EFCP00D.CCFLPED2 "
                        + " WHERE CCPTCL = " + tipoCliente
                        + " AND CCPFEC BETWEEN '" + fechaIni + "' AND '" + fechaFin + "' "
                        + " GROUP BY CCPCIA,CCPPED,CCPPEU,CCPCLI,CCPTCL,CCPBOD,CCPFEC,CCPUCR,CCPUC2,CCPFE2";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);

                while (rs.next()) {
                    tmp = new OrdenPedido();
                    tmp.setCodigoCia(rs.getString(1));
                    tmp.setOrdenNumero(rs.getString(2));
                    tmp.setNumeroPedido(rs.getString(3));
                    tmp.setCliente(rs.getString(4));
                    tmp.setTipoCliente(rs.getString(5));
                    tmp.setBodega(rs.getString(6));
                    tmp.setFechaCreacion(rs.getString(7));
                    tmp.setUsuarioCrea(rs.getString(8));
                    tmp.setUsuarioAprueba(rs.getString(9));
                    tmp.setFechaAprobacion(rs.getString(10));

                    lstOrdenPedido.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstOrdenPedido = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstOrdenPedido;
    }

    @WebMethod(operationName = "getComprobantesRetencion")
    public List<ComprobantesRetencion> getComprobantesRetencion(@WebParam(name = "fechaIni") String fechaIni, @WebParam(name = "fechaFin") String fechaFin, @WebParam(name = "cia") String cia, @WebParam(name = "estado") String estado, @WebParam(name = "documento") String documento, @WebParam(name = "nombre") String nombre) {

        Statement stmt = null;
        ResultSet rs;
        ComprobantesRetencion tmp = new ComprobantesRetencion();
        lstComprobantes = new ArrayList<ComprobantesRetencion>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "";

                if (estado.equals("TODOS")) {
                    qry = " SELECT * "
                            + " FROM S0646ED5.EFCP00D.IARLTAL0 "
                            + " WHERE RTACLI = '" + cia + "' ";
                } else {
                    qry = " SELECT * "
                            + " FROM S0646ED5.EFCP00D.IARLTAL0 "
                            + " WHERE RTACLI = '" + cia + "' "
                            + " AND RTASTS = '" + estado + "' ";
                }

                if ((nombre != null) && (!nombre.equals(""))) {
                    qry = qry + " AND RTACOM LIKE '%" + nombre.toUpperCase() + "%' ";
                } else {
                    if ((documento != null) && (!documento.equals(""))) {
                        qry = qry + " AND RTANSX = '" + documento + "' ";
                    } else {
                        qry = qry + " AND RTAFEM BETWEEN " + fechaIni + " AND " + fechaFin;
                    }
                }

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);

                while (rs.next()) {
                    tmp = new ComprobantesRetencion();
                    tmp.setCiaEmisor(rs.getString(1).trim());
                    tmp.setNombreEmisor(rs.getString(2).trim());
                    tmp.setRucEmisor(rs.getString(3).trim());
                    tmp.setFechaDoc(rs.getString(4).trim());
                    tmp.setNombreRecibe(rs.getString(5).trim());
                    tmp.setContribuyente(rs.getString(6).trim());
                    tmp.setRucRecibe(rs.getString(7).trim());
                    tmp.setTipoDocumento(rs.getString(8).trim());
                    tmp.setNumeroComprobante(rs.getString(9).trim());
                    tmp.setNumeroAutorizacion(rs.getString(10).trim());
                    tmp.setClaveAutorizacion(rs.getString(11).trim());
                    tmp.setFechaAutorizacion(rs.getString(12).trim());
                    tmp.setIva(rs.getString(13).trim());
                    tmp.setIsd(rs.getString(14).trim());
                    tmp.setRenta(rs.getString(15).trim());
                    tmp.setTotal(rs.getString(16).trim());
                    tmp.setEstado(rs.getString(17).trim());
                    tmp.setFechaRegistro(rs.getString(18).trim());
                    tmp.setObservacionCarga(rs.getString(19).trim());
                    tmp.setUsuario(rs.getString(20).trim());
                    tmp.setUsuarioCarga(rs.getString(21).trim());
                    tmp.setFechaCreacion(rs.getString(22).trim());
                    tmp.setCodigoImpuesto(rs.getString(23).trim());
                    tmp.setCodigoRetencion(rs.getString(24).trim());
                    tmp.setBaseImponible(rs.getString(25).trim());
                    tmp.setPorcentajeRetenido(rs.getString(26).trim());
                    tmp.setValorRetenido(rs.getString(27).trim());
                    tmp.setCodigoSustento(rs.getString(28).trim());
                    tmp.setDocumentoSustento(rs.getString(29).trim());
                    tmp.setFechaSustento(rs.getString(30).trim());
                    tmp.setNumeroLinea(rs.getString(31).trim());
                    tmp.setFacturaInterna(rs.getString(32).trim());
                    tmp.setRadjCreado(rs.getString(33).trim());
                    tmp.setCodigoCliente(rs.getString(34).trim());
                    tmp.setObservacionDetalle(rs.getString(35).trim());

                    lstComprobantes.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstComprobantes = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstComprobantes;
    }

    @WebMethod(operationName = "getCias")
    public List<Cias> getCias() {

        Statement stmt = null;
        ResultSet rs;
        Cias tmp = new Cias();
        lstCias = new ArrayList<Cias>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT ABIABN, ABIB5I "
                        + " FROM S0646ED5.EFCP00D.IRFLCMP2 "
                        + " WHERE ABIB24 = 'A' ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);

                while (rs.next()) {
                    tmp = new Cias();
                    tmp.setCodigo(rs.getString(1));
                    tmp.setNombre(rs.getString(2).trim());

                    lstCias.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstCias = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstCias;
    }

    @WebMethod(operationName = "getCiasUsuario")
    public List<Cias> getCiasUsuario(@WebParam(name = "usuario") String usuario) {

        Statement stmt = null;
        ResultSet rs;
        Cias tmp = new Cias();
        lstCias = new ArrayList<Cias>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT ABIABN, ABIB5I "
                        + " FROM S0646ED5.EFCP00D.IRFLSCA1, S0646ED5.EFCP00D.IRFLCMP2 "
                        + " WHERE MYIABN = ABIABN "
                        + " AND ABIB24 = 'A' "
                        + " AND MYIBN0 = '" + usuario.toUpperCase() + "' ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);

                while (rs.next()) {
                    tmp = new Cias();
                    tmp.setCodigo(rs.getString(1));
                    tmp.setNombre(rs.getString(2).trim());

                    lstCias.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstCias = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstCias;
    }

    @WebMethod(operationName = "getTaxRetencionCliente")
    public List<TaxRetencionCliente> getTaxRetencionCliente(@WebParam(name = "cliente") String cliente, @WebParam(name = "cia") String cia, @WebParam(name = "tipoInvt") String tipoInvt) {

        Statement stmt = null;
        ResultSet rs;
        lstTaxRet = new ArrayList<TaxRetencionCliente>();
        TaxRetencionCliente tmp = new TaxRetencionCliente();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT * "
                        + " FROM S0646ED5.EFCP00D.IRFFCTR "
                        + " WHERE CTRAAA LIKE '%" + cliente + "%' "
                        + " AND CTRABN LIKE '%" + cia + "%' "
                        + " AND CTRTIN LIKE '%" + tipoInvt + "%' ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    tmp = new TaxRetencionCliente();
                    tmp.setCliente(rs.getString(1));
                    tmp.setCia(rs.getString(2));
                    tmp.setTipoInvt(rs.getString(3));
                    tmp.setTipoBienServicio(rs.getString(4));
                    tmp.setTaxRetFuente(rs.getDouble(5));
                    tmp.setTaxRetIva(rs.getDouble(6));
                    tmp.setEstado(rs.getString(7));

                    lstTaxRet.add(tmp);
                }
            }

        } catch (Exception e) {
            lstTaxRet = null;
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstTaxRet;
    }

    @WebMethod(operationName = "UpdateTaxRetencionCliente")
    public int UpdateTaxRetencionCliente(@WebParam(name = "data") TaxRetencionCliente data, @WebParam(name = "NewTipoInvt") String NewTipoInvt) {

        Statement stmt = null;
        ResultSet rs;
        int r = -1;
        boolean ban = true;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (data.getCliente().equals(null) || data.getCliente().isEmpty() || data.getCliente().equals("")) {
                r = -1;
            } else {
                if (miConexion != null) {

                    stmt = miConexion.createStatement();
                    String qry = "UPDATE S0646ED5.EFCP00D.IRFFCTR SET "
                            + " CTRTIN = '" + NewTipoInvt
                            + "', CTRTIP = '" + data.getTipoBienServicio()
                            + "', CTRPRF = " + data.getTaxRetFuente()
                            + ", CTRPRI = " + data.getTaxRetIva()
                            + ", CTRSTS = '" + data.getEstado()
                            + "' WHERE CTRAAA = '" + data.getCliente() + "' "
                            + " AND CTRABN = '" + data.getCia() + "' "
                            + " AND CTRTIN = '" + data.getTipoInvt() + "' ";
                    //System.out.println("Query " + qry);
                    r = stmt.executeUpdate(qry);
                }
            }

        } catch (Exception e) {
            r = -1;
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "insertTaxRetencionCliente")
    public int insertTaxRetencionCliente(@WebParam(name = "data") TaxRetencionCliente data) {

        Statement stmt = null;
        ResultSet rs;
        int r = -1;
        boolean ban = true;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (data.getCliente().equals(null) || data.getCliente().isEmpty() || data.getCliente().equals("")) {
                r = -1;
            } else {
                if (miConexion != null) {

                    stmt = miConexion.createStatement();

                    String qry = "INSERT INTO S0646ED5.EFCP00D.IRFFCTR VALUES('"
                            + data.getCliente() + "', '" + data.getCia() + "', '" + data.getTipoInvt() + "', '" + data.getTipoBienServicio() + "', " + data.getTaxRetFuente() + ", " + data.getTaxRetIva() + ", '" + data.getEstado() + "')";
                    //System.out.println("Query " + qry);
                    r = stmt.executeUpdate(qry);
                }
            }

        } catch (Exception e) {
            r = -1;
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "getClientes")
    public List<Cliente> getClientes() {

        Statement stmt = null;
        ResultSet rs;
        Cliente tmp = new Cliente();
        lstClientes = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT ACIAAA, ACIB5I "
                        + "FROM S0646ED5.EFCP00D.IRFLCUS0 ";

                rs = stmt.executeQuery(qry);

                //System.out.println("Query " + qry);
                while (rs.next()) {
                    tmp = new Cliente();
                    tmp.setCodigo(rs.getString(1));
                    tmp.setNombre(rs.getString(2).trim());

                    lstClientes.add(tmp);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstClientes = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstClientes;
    }

    @WebMethod(operationName = "getTipoInventario")
    public List<TipoInventario> getTipoInventario() {

        Statement stmt = null;
        ResultSet rs;
        TipoInventario tmp = new TipoInventario();
        lstTipoInvt = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT * "
                        + " FROM S0646ED5.EFCP00D.IRFLIVT2 "
                        + " ORDER BY 1 ";

                rs = stmt.executeQuery(qry);

                //System.out.println("Query " + qry);
                while (rs.next()) {
                    tmp = new TipoInventario();
                    tmp.setCodigo(rs.getString(1));
                    tmp.setNombre(rs.getString(2).trim());

                    lstTipoInvt.add(tmp);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstTipoInvt = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstTipoInvt;
    }

    @WebMethod(operationName = "getCountLog")
    public int getCountLog(@WebParam(name = "fechaIni") String fechaIni, @WebParam(name = "fechaFin") String fechaFin, @WebParam(name = "cia") String cia, @WebParam(name = "estado") String estado, @WebParam(name = "documento") String documento, @WebParam(name = "nombre") String nombre) {

        Statement stmt = null;
        ResultSet rs;
        int r = -1;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "";

                if (estado.equals("TODOS")) {
                    qry = " SELECT COUNT(*) "
                            + " FROM S0646ED5.EFCP00D.IARETCA "
                            + " WHERE RTACLI = '" + cia + "' ";
                } else {
                    qry = " SELECT COUNT(*) "
                            + " FROM S0646ED5.EFCP00D.IARETCA "
                            + " WHERE RTACLI = '" + cia + "' "
                            + " AND RTASTS = '" + estado + "' ";
                }

                if ((nombre != null) && (!nombre.equals(""))) {
                    qry = qry + " AND RTACOM LIKE '%" + nombre.toUpperCase() + "%' ";
                } else {
                    if ((documento != null) && (!documento.equals(""))) {
                        qry = qry + " AND RTADOC = '" + documento + "' ";
                    } else {
                        qry = qry + " AND RTAFEM BETWEEN " + fechaIni + " AND " + fechaFin;
                    }
                }

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);

                while (rs.next()) {
                    r = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            r = -1;
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "getCarteraInstitucional")
    public List<CarteraInstitucionalAS> getCarteraInstitucional() {

        Statement stmt = null;
        ResultSet rs;
        CarteraInstitucionalAS tmp = new CarteraInstitucionalAS();
        lstCartera = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = " SELECT HIICWN AS ORDEN_COMPRA,HIFB79 AS NUM_PEDIDO_INT, "
                        + "CASE HILFEN WHEN '0' THEN '' ELSE CONCAT('20', CONCAT(CONCAT(CONCAT(CONCAT(SUBSTR(SUBSTR(HILFEN, 2, LENGTH(RTRIM(HILFEN))), 1, 2), '/'), SUBSTR(HILFEN, 4, 2)), '/'), SUBSTR(HILFEN, 6, 2))) END AS FECHA_FACTURA, "
                        + "CASE HIFC5Y WHEN '0' THEN '' ELSE CONCAT('20', CONCAT(CONCAT(CONCAT(CONCAT(SUBSTR(SUBSTR(HIFC5Y, 2, LENGTH(RTRIM(HIFC5Y))), 1, 2), '/'), SUBSTR(HIFC5Y, 4, 2)), '/'), SUBSTR(HIFC5Y, 6, 2))) END AS FECHA_ORIGINAL_OC, "
                        + "CCNUME AS NUM_FAC_INTERNO, "
                        + "CONCAT(CONCAT(HIFEST, HIFPTO ),  HIFSEC) AS NUMFAC , "
                        + "CCDYY AS AÑO, "
                        + "CONCAT(REPEAT('0', 2 - LENGTH(TRIM(CCDMM))), CCDMM) AS MES, "
                        + "CONCAT(REPEAT('0', 2 - LENGTH(TRIM(CCDDD))), CCDDD) AS DIA, "
                        + "RTRIM(HIFATC) AS COD_PRODUCTO, HIFAA1 AS PRODUCTO, RTRIM(ACIAP4) AS RUC_CONTRATANTE, RTRIM(HIFB5I) AS NOMBRE_CONTRATANTE,ACIAAA AS COD_CLIENTE, RTRIM(ACIAP6) AS CIUDAD_CLIENTE, "
                        + "CCTOTC AS TOTAL_COBRAR, SUM(HIFCQV) AS VALOR_NETO, SUM(HIFCZM) AS CANTIDAD, C.CCAÑO, C.CCMES, HIFCWL AS TIPO, RTRIM(CCCOTN) AS INSTITUCION, RTRIM(HIFA2J) AS CLASE, CCVALO AS VALOR_CARTERA "
                        + "FROM S0646ED5.EFCP00D.IOPLHIF0 F, S0646ED5.EFCP00D.ESTFCXC C , S0646ED5.EFCP00D.IRFLCUS2 CL "
                        + "WHERE F.HIFABN = C.CCCIAC "
                        + "AND F.HIFDNM = C.CCCODC "
                        + "AND  F.HIFB74 = C.CCNUME "
                        + "AND F.HIFCWL = C.CCTIPO "
                        + "AND C.CCCODC= CL.ACIAAA "
                        + "AND C.CCAÑO >= SUBSTRING(CAST(YEAR(CURRENT_TIMESTAMP) AS VARCHAR(4)), 3, 2) "
                        + "AND C.CCMES = MONTH(CURRENT_TIMESTAMP) "
                        + "AND F.HIFABN ='002' "
                        + "AND HIFCWL IN ('DINV', 'CINV') "
                        + "AND CCCOTN IN ('IESS','MINISTERIO DE SALUD', 'OTRAS INSTIT. LICITACIONES') "
                        + "GROUP BY HIICWN, HILFEN, HIFB79, HIFC5Y, CCNUME, HIFEST, HIFPTO, HIFSEC, CCDYY, CCDMM, CCDDD, HIFATC ,HIFAA1, ACIAP4 ,HIFB5I,CCTOTC, ACIAAA, ACIAP6, HIFCWL, CCCOTN, HIFA2J, CCVALO,  C.CCAÑO, C.CCMES ";

                rs = stmt.executeQuery(qry);

                //System.out.println("Query " + qry);
                while (rs.next()) {
                    tmp = new CarteraInstitucionalAS();
                    tmp.setOC(rs.getString(1));
                    tmp.setNumeroPedido(rs.getString(2));
                    tmp.setFechaFactura(rs.getString(3));
                    tmp.setFechaOC(rs.getString(4));
                    tmp.setNumFactInterno(rs.getString(5));
                    tmp.setNumFacturaExterno(rs.getString(6));
                    tmp.setAnio(rs.getString(7));
                    tmp.setMes(rs.getString(8));
                    tmp.setDia(rs.getString(9));
                    tmp.setCodProducto(rs.getString(10));
                    tmp.setNombreProducto(rs.getString(11));
                    tmp.setRucContratante(rs.getString(12));
                    tmp.setNombreContratante(rs.getString(13));
                    tmp.setCodCliente(rs.getString(14));
                    tmp.setCiudadCliente(rs.getString(15));
                    tmp.setTotalCobrar(rs.getString(16));
                    tmp.setValorNeto(rs.getString(17));
                    tmp.setCantidad(rs.getInt(18));
                    tmp.setAnioCartera(rs.getString(19));
                    tmp.setMesCartera(rs.getString(20));
                    tmp.setTipo(rs.getString(21));
                    tmp.setInstitucion(rs.getString(22));
                    tmp.setClase(rs.getString(23));
                    tmp.setValorCartera(rs.getString(24));

                    lstCartera.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstCartera = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstCartera;
    }

    @WebMethod(operationName = "getDestruccion")
    public List<Destruccion> getDestruccion(@WebParam(name = "anio") String anio, @WebParam(name = "mes") String mes) {

        Statement stmt = null;
        ResultSet rs;
        Destruccion tmp = new Destruccion();
        lstDestruccion = new ArrayList<>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = " SELECT ASIATC AS CODIGO, APIAA1 AS DESCRIPCION, ASIAK2 AS LOTE, "
                        + " ASIA9G AS ANIO, ASIA9H AS MES, ASIA9K AS ANIO_EXPIRA, ASIA9L AS MES_EXPIRA, "
                        + " (SELECT SUM(GEIF49) "
                        + " FROM S0646ED5.EFCP00D.IFGLIHS3 "
                        + " WHERE GEIABN = ASIABN "
                        + " AND GEIBPB = H.GEIBPB "
                        + " AND GEIBPC = H.GEIBPC "
                        + " AND GEIF48 = 'TRF' "
                        + " AND GEIATC = ASIATC "
                        + " AND GEIAAY = ASIAAY "
                        + " AND GEIAK2 = ASIAK2 "
                        + " GROUP BY GEIATC, GEIAK2) AS SALDO "
                        + " FROM S0646ED5.EFCP00D.IFGFFGB, S0646ED5.EFCP00D.IRFLPRD2, S0646ED5.EFCP00D.IFGLIHS3 H "
                        + " WHERE ASIAA0 = APIAA0 "
                        + " AND ASIATD = APIATD "
                        + " AND ASIATC = APIATC "
                        + " AND GEIATC = ASIATC "
                        + " AND GEIAAY = ASIAAY "
                        + " AND GEIAK2 = ASIAK2 "
                        + " AND GEIBPB = " + anio
                        + " AND GEIBPC = " + mes
                        + " AND ASIAK6 > 0 "
                        + " AND ASIAAY = '035' "
                        + " AND GEIF48 = 'TRF' "
                        + " AND ASIABN IN ('001', '002') "
                        + " GROUP BY  ASIATC, APIAA1, ASIA9G, ASIA9H, ASIA9K, ASIA9L, ASIAK2, ASIABN, GEIBPB, GEIBPC, ASIAAY "
                        + " ORDER BY APIAA1 ";

                rs = stmt.executeQuery(qry);

                //System.out.println("Query " + qry);
                while (rs.next()) {
                    tmp = new Destruccion();
                    tmp.setCodigo(rs.getString(1).trim());
                    tmp.setDescripcion(rs.getString(2).trim());
                    tmp.setLote(rs.getString(3).trim());
                    tmp.setAnio(rs.getString(4));
                    tmp.setMes(rs.getString(5));
                    tmp.setAnioExpira(rs.getString(6));
                    tmp.setMesExpira(rs.getString(7));
                    tmp.setSaldo(rs.getString(8));

                    lstDestruccion.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstDestruccion = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstDestruccion;
    }

    @WebMethod(operationName = "getBackOrder")
    public String getBackOrder(@WebParam(name = "codigo") String codigo) {

        Statement stmt = null;
        ResultSet rs;
        String r = "0-0";

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                rs = stmt
                        .executeQuery("SELECT COALESCE(SUM(STIAMT), 0), COALESCE(SUM(STIAUB), 0)"
                                + "FROM S0646ED5.EFCP00D.IOPTSTS "
                                + "WHERE SUBSTRING(SUBSTRING(CAST(STIC5Y AS VARCHAR(7)), 2, 6), 1, 2) >= '17' "
                                + "AND (STIB74 = 0 OR STIB74 IS NULL) "
                                + "AND STIATC = '" + codigo + "' "
                                + "GROUP BY STIATC ");

                while (rs.next()) {
                    r = rs.getString(1) + '-' + rs.getString(2);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = "0-0";
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "getProductosPPIC")
    public List<Producto> getProductosPPIC() {

        Statement stmt = null;
        ResultSet rs;
        Producto tmp = new Producto();
        lstProducto = new ArrayList<Producto>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                rs = stmt
                        .executeQuery("SELECT RTRIM(APIATC) AS COD_PROD, RTRIM(APIAA1) AS DESC_PROD, APICWX AS PRESENTACION, "
                                + "CASE APIAPR WHEN '' THEN '0' ELSE APIAPR END AS FORMA_FARMACEUTICA, "
                                + "CASE APIAPV WHEN '' THEN '0' ELSE APIAPV END AS COD_UNIFICACION, "
                                + "CASE APIAPS WHEN '' THEN '0' ELSE APIAPS END AS FACTOR_UNIDAD, "
                                + "COALESCE(APIBE1, 0) AS LOTE_STD, "
                                + "CASE APIAJN WHEN 'N' THEN 'NO ESTERIL' WHEN 'E' THEN 'ESTERIL' WHEN 'I' THEN 'IMPORTADO' ELSE APIAJN END AS AREA, "
                                + "CASE RTRIM(APIAPW) WHEN '' THEN '0' ELSE RTRIM(APIAPW) END AS FACTOR_GRANEL, "
                                + "APIAA9 AS LOP "
                                + "FROM S0646ED5.EFCP00D.IRFLPRD2 "
                                + "WHERE APIAPP ='A' "
                                + "AND APICV4 IN ('06', '07') "
                                + "AND LEFT(APIATC, 1)= '3' "
                                + "ORDER BY APIAPR, APIAPV ");

                while (rs.next()) {
                    tmp = new Producto();
                    tmp.setCodProducto(rs.getString(1).trim());
                    tmp.setDescProducto(rs.getString(2).trim());
                    tmp.setPresentacion(rs.getString(3).trim());
                    tmp.setFormaFarmaceutica(rs.getString(4).trim());
                    tmp.setCodUnificacion(rs.getString(5).trim());
                    tmp.setFactorUnidad(rs.getString(6).trim());
                    tmp.setLoteStd(rs.getString(7).trim());
                    tmp.setArea(rs.getString(8).trim());
                    tmp.setFactorGranel(rs.getString(9).trim());
                    tmp.setLop(rs.getString(10).trim());

                    lstProducto.add(tmp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstProducto = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstProducto;
    }

    @WebMethod(operationName = "getStock")
    public String getStock(@WebParam(name = "codigo") String codigo) {

        Statement stmt = null;
        ResultSet rs;
        String r = "0.00";

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT SUM(ASIAK6) "
                        + "FROM S0646ED5.EFCP00D.IFGLFGB2 "
                        + "WHERE ASIABN IN ('002','003') "
                        + "AND ASIAAY IN ('030', '031', '034') "
                        + "AND ASIATC = '" + codigo
                        + "' GROUP BY ASIATC ");

                rs = stmt.executeQuery(qry);
                //System.out.println("Query " + qry);

                while (rs.next()) {
                    r = rs.getString(1).trim();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            r = "0.00";
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateBackOrder")
    public int updateBackOrder() {

        Statement stmt = null;

        ResultSet rs;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "DELETE FROM S0646ED5.EFCP00D.IOPTSTS ";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
                qry = "INSERT INTO S0646ED5.EFCP00D.IOPTSTS SELECT * FROM S0646ED5.EFCP00D.IOPSTS ";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
                qry = "INSERT INTO S0646ED5.EFCP00D.IOPTSTS(STIABN, STIB79, STIATC, STIAMT, STIC5Y, STIAUB) "
                        + "SELECT BZIABN, BZIB79, BZIATC, BZIAPG, AAIAL7, BZIB5N "
                        + "FROM S0646ED5.TRANSFLIB.QRYBO "
                        + "WHERE BZIABN = '002' "
                        + "AND APIAA9 <> '4' ";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "deleteBackOrder")
    public int deleteBackOrder() {

        Statement stmt = null;

        ResultSet rs;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "DELETE FROM S0646ED5.EFCP00D.IOPTSTS ";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateBackOrder1")
    public int updateBackOrder1() {

        Statement stmt = null;

        ResultSet rs;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "INSERT INTO S0646ED5.EFCP00D.IOPTSTS SELECT * FROM S0646ED5.EFCP00D.IOPSTS ";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateBackOrder2")
    public int updateBackOrder2() {

        Statement stmt = null;

        ResultSet rs;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "INSERT INTO S0646ED5.EFCP00D.IOPTSTS(STIABN, STIB79, STIATC, STIAMT, STIC5Y, STIAUB) "
                        + "SELECT BZIABN, BZIB79, BZIATC, BZIAPG, AAIAL7, BZIB5N "
                        + "FROM S0646ED5.TRANSFLIB.QRYBO "
                        + "WHERE BZIABN = '002' "
                        + "AND APIAA9 <> '4' ";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateProducto")
    public int updateProducto(@WebParam(name = "data") Producto data) {

        Statement stmt = null;

        ResultSet rs;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "UPDATE S0646ED5.EFCP00D.IRFLPRD2 SET "
                        + "APICWX = '" + data.getPresentacion() + "', "
                        + "APIAPR = '" + data.getFormaFarmaceutica() + "', "
                        + "APIAPV = '" + data.getCodUnificacion() + "', "
                        + "APIAPS = '" + data.getFactorUnidad() + "', "
                        + "APIBE1 = '" + data.getLoteStd() + "', "
                        + "APIAJN = '" + data.getArea() + "', "
                        + "APIAPW = '" + data.getFactorGranel() + "' "
                        + "WHERE RTRIM(APIATC) = '" + data.getCodProducto() + "'";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "getProducto")
    public Producto getProducto(@WebParam(name = "producto") String producto) {

        Statement stmt = null;
        ResultSet rs;
        Producto tmp = new Producto();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                rs = stmt
                        .executeQuery("SELECT RTRIM(APIATC) AS COD_PROD, RTRIM(APIAA1) AS DESC_PROD, APICWX AS PRESENTACION, "
                                + "CASE APIAPR WHEN '' THEN '0' ELSE APIAPR END AS FORMA_FARMACEUTICA, "
                                + "CASE APIAPV WHEN '' THEN '0' ELSE APIAPV END AS COD_UNIFICACION, "
                                + "CASE APIAPS WHEN '' THEN '0' ELSE APIAPS END AS FACTOR_UNIDAD, "
                                + "COALESCE(APIBE1, 0) AS LOTE_STD, "
                                + "CASE APIAJN WHEN 'N' THEN 'NO ESTERIL' WHEN 'E' THEN 'ESTERIL' WHEN 'I' THEN 'IMPORTADO' ELSE APIAJN END AS AREA, "
                                + "CASE RTRIM(APIAPW) WHEN '' THEN '0' ELSE RTRIM(APIAPW) END AS FACTOR_GRANEL, "
                                + "APIAA9 AS LOP "
                                + "FROM S0646ED5.EFCP00D.IRFLPRD2 "
                                + "WHERE RTRIM(APIATC) = '" + producto + "' "
                                + "AND APIAPP ='A' "
                                + "AND APICV4 IN ('06', '07') "
                                + "AND LEFT(APIATC, 1)= '3' ");

                while (rs.next()) {
                    tmp = new Producto();
                    tmp.setCodProducto(rs.getString(1).trim());
                    tmp.setDescProducto(rs.getString(2).trim());
                    tmp.setPresentacion(rs.getString(3).trim());
                    tmp.setFormaFarmaceutica(rs.getString(4).trim());
                    tmp.setCodUnificacion(rs.getString(5).trim());
                    tmp.setFactorUnidad(rs.getString(6).trim());
                    tmp.setLoteStd(rs.getString(7).trim());
                    tmp.setArea(rs.getString(8).trim());
                    tmp.setFactorGranel(rs.getString(9).trim());
                    tmp.setLop(rs.getString(10).trim());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            tmp = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return tmp;
    }

    @WebMethod(operationName = "getDocElectronicos")
    public List<Documentos> getDocElectronicos() {

        Statement stmt = null;
        ResultSet rs;
        Documentos tmp = new Documentos();
        lstDocs = new ArrayList<Documentos>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                rs = stmt
                        .executeQuery("SELECT DRERUC, DRETDO, DREDOC, DREFAU, DRENUA "
                                + "FROM S0646ED5.EFCP00D.IADOCCA "
                                + "WHERE DRESTS = 'SUBIDO' ");

                while (rs.next()) {
                    tmp = new Documentos();

                    tmp.setRuc(rs.getString(1).trim());
                    tmp.setTipoDoc(rs.getString(2).trim());
                    tmp.setNumSerie(rs.getString(3).trim());
                    tmp.setFechaAutorizacion(rs.getString(4).trim());
                    tmp.setNumAutorizacion(rs.getString(5).trim());

                    lstDocs.add(tmp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstDocs = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstDocs;
    }

    @WebMethod(operationName = "getCodProveedor")
    public String getCodProveedor(@WebParam(name = "ruc") String ruc) {

        Statement stmt = null;
        ResultSet rs;
        String r = "0";

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT IHIC0T "
                        + "FROM S0646ED5.EFCP00D.IRFLVND2  "
                        + "WHERE IHIAP5 = '" + ruc + "' "
                        + "AND IHIC0N = 'A' ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    r = rs.getString(1).trim();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = "-1";
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateObsDoc")
    public int updateObsDoc(@WebParam(name = "ruc") String ruc, @WebParam(name = "autorizacion") String autorizacion) {

        Statement stmt = null;

        ResultSet rs;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "UPDATE S0646ED5.EFCP00D.IADOCCA SET "
                        + "DREOBS = 'NO EXISTE PROVEEDOR' "
                        + "WHERE RTRIM(DRERUC) = '" + ruc + "' "
                        + "AND DRENUA = '" + autorizacion + "' ";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "insertAutorizacion")
    public int insertAutorizacion(@WebParam(name = "cliente") String cliente, @WebParam(name = "tipo") String tipo, @WebParam(name = "numSerie") String numSerie, @WebParam(name = "fecha") String fecha, @WebParam(name = "autorizacion") String autorizacion) {

        Statement stmt = null;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String[] num = numSerie.split("-");

                String qry = "INSERT INTO S0646ED5.EFCP00D.IRFAUT VALUES('"
                        + cliente + "', '" + tipo + "', '" + num[0] + num[1] + "', '" + num[2] + "', '" + num[2] + "', '" + fecha + "', '" + fecha + "', '" + autorizacion + "')";
                r = stmt.executeUpdate(qry);

                //System.out.println("Query " + qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "existeAutorizacion")
    public int existeAutorizacion(@WebParam(name = "codigo") String codigo, @WebParam(name = "autorizacion") String autorizacion) {

        Statement stmt = null;
        ResultSet rs;
        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT COUNT(*) "
                        + "FROM S0646ED5.EFCP00D.IRFAUT "
                        + "WHERE AUCODI = '" + codigo + "' "
                        + "AND AUNRO = '" + autorizacion + "' ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                //System.out.println("Size " + rs.getRow());

                while (rs.next()) {
                    r = Integer.parseInt(rs.getString(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateEstadoDoc")
    public int updateEstadoDoc(@WebParam(name = "ruc") String ruc, @WebParam(name = "autorizacion") String autorizacion) {

        Statement stmt = null;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "UPDATE S0646ED5.EFCP00D.IADOCCA SET "
                        + "DRESTS = 'PROCESADO OK', "
                        + "DREOBS = '' "
                        + "WHERE RTRIM(DRERUC) = '" + ruc + "' "
                        + "AND DRENUA = '" + autorizacion + "' ";

                //System.out.println("Query " + qry);
                r = stmt.executeUpdate(qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "updateEstadoExiste")
    public int updateEstadoExiste(@WebParam(name = "ruc") String ruc, @WebParam(name = "autorizacion") String autorizacion) {

        Statement stmt = null;

        int r = 0;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();

                String qry = "UPDATE S0646ED5.EFCP00D.IADOCCA SET "
                        + "DRESTS = 'EXISTE', "
                        + "DREOBS = '' "
                        + "WHERE RTRIM(DRERUC) = '" + ruc + "' "
                        + "AND DRENUA = '" + autorizacion + "' ";

                //System.out.println("Query " + qry);
                r = stmt.executeUpdate(qry);
            }

        } catch (Exception e) {
            e.printStackTrace();
            r = -1;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    @WebMethod(operationName = "getProveedorFaltante")
    public List<Proveedor> getProveedorFaltante() {

        Statement stmt = null;
        ResultSet rs;
        Proveedor tmp = new Proveedor();
        lstProv = new ArrayList<Proveedor>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT DRERUC, DRENOM "
                        + "FROM S0646ED5.EFCP00D.IADOCCA "
                        + "WHERE DRESTS = 'SUBIDO' "
                        + "AND DREOBS = 'NO EXISTE PROVEEDOR'";
                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);

                while (rs.next()) {
                    tmp = new Proveedor();

                    tmp.setRuc(rs.getString(1).trim());
                    tmp.setNombre(rs.getString(2).trim());

                    lstProv.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstProv = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstProv;
    }

    @WebMethod(operationName = "enabledDisabledUsers")
    public String enabledDisabledUsers(@WebParam(name = "usuario") String usuario, @WebParam(name = "estado") String estado) {

        String r = "";

        Properties tmpP = new Propiedades().getProperties();
        String server = tmpP.getProperty("Server");
        String userName = tmpP.getProperty("Usuario");
        String password = tmpP.getProperty("Clave");

        AS400 system = new AS400(server, userName, password);
        CommandCall command = new CommandCall(system);

        try {
            // Run the command.
            if (command.run("CHGUSRPRF USRPRF(" + usuario + ") STATUS(*" + estado + ")")) {
                System.out.print("Comando ejecutado con éxito");
            } else {
                System.out.print("Comando falló");
                r = "-1";
            }

            // If messages were produced from the command, print them
            AS400Message[] messagelist = command.getMessageList();
            if (messagelist.length > 0) {
                System.out.println("Mensajes del comando: ");
            }

            for (int i = 0; i < messagelist.length; i++) {
                System.out.print(messagelist[i].getID());
                System.out.println(messagelist[i].getText());
                r = messagelist[i].getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Comando " + command.getCommand() + " no se ejecutó");
            r = "-1";
        }

        return r;
    }
    
    @WebMethod(operationName = "executeCommand")
    public String[] executeCommand(@WebParam(name = "comando") String comando) {

        String[] r = new String[2];

        Properties tmpP = new Propiedades().getProperties();
        String server = tmpP.getProperty("Server");
        String userName = tmpP.getProperty("Usuario");
        String password = tmpP.getProperty("Clave");

        AS400 system = new AS400(server, userName, password);
        CommandCall command = new CommandCall(system);

        try {
            // Run the command.
            if (command.run(comando)) {
                System.out.print("Comando ejecutado con éxito");
            } else {
                System.out.print("Comando falló");
                r[0] = "-1";
                r[1] = "Comando falló";
            }
                        
            // If messages were produced from the command, print them
            AS400Message[] messagelist = command.getMessageList();
            if (messagelist.length > 0) {
                System.out.println("Mensajes del comando: ");
            }

            for (int i = 0; i < messagelist.length; i++) {
                System.out.print(messagelist[i].getID());
                System.out.println(messagelist[i].getText());
                
                r[0] = messagelist[i].getID();
                r[1] = messagelist[i].getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Comando " + command.getCommand() + " no se ejecutó");
            
            r[0] = "-1";
            r[1] = e.getMessage();
        }

        return r;
    }
    
    @WebMethod(operationName = "getFamilias")
    public List<Familias> getFamilias() {

       Statement stmt = null;
        ResultSet rs;
        Familias tmp = new Familias();
        lstFamilias = new ArrayList<Familias>();

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "SELECT DISTINCT(KAIA5E), KAIA5F "
                        + " FROM S0646ED5.EFCP00D.IRFLHPG2 "
                        + " WHERE KAIAA9 IN ('1', '4') "
                        + " AND KAIA5F NOT IN('AMEBIZOL SUSPENSION USADO 2012', 'BLENDAX FUTURA MAMA', 'BLENDAX SENSITIVE', 'LIPOMERZ', 'MUCOCID JARABE * NO USAR*', 'NEW HAIR', 'PARASIPACK SUSPENSION', 'PREDNISONA GEMINIS *NO USAR*') "
                        + " GROUP BY KAIA5E, KAIA5F "
                        + " ORDER BY KAIA5F ";

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);

                while (rs.next()) {
                    tmp = new Familias();
                    tmp.setCodigo(rs.getString(1).trim());
                    tmp.setNombre(rs.getString(2).trim());

                    lstFamilias.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstFamilias = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstFamilias;
    }
    
    @WebMethod(operationName = "getValorProductoTerminado")
    public String getValorProductoTerminado(@WebParam(name = "familia") String familia, @WebParam(name = "lop") String lop) {

        Statement stmt = null;
        ResultSet rs;
        String r = "0.00";
        String bodegas = "";

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                if (lop.equals("1")){
                    bodegas = "AND T01.ASIAAY IN ('030', '031', '038') ";
                }
                else{
                    bodegas = "AND T01.ASIAAY IN ('034') ";
                }
                    
                stmt = miConexion.createStatement();
                String qry = ("SELECT SUM(T04.JHIDCX * T01.ASIAK6) "
                        + "FROM S0646ED5.EFCP00D.IFGLFGB2 AS T01, S0646ED5.EFCP00D.IRFLPRD2 AS T02, S0646ED5.EFCP00D.IRFLWHS2 AS T03, S0646ED5.EFCP00D.IRFLPCS2 AS T04 "
                        + "WHERE T01.ASIAA0 = T02.APIAA0 AND  "
                        + "T01.ASIATC = T02.APIATC AND "
                        + "T01.ASIATD = T02.APIATD AND "
                        + "T01.ASIAAY = T03.AMIAAY AND "
                        + "T01.ASIAA0 = T04.JHIAA0 AND "
                        + "T01.ASIATC = T04.JHIATC AND "
                        + "T01.ASIATD = T04.JHIATD AND "
                        + "T01.ASIABN = T04.JHIABN AND "
                        + "T01.ASIAK6 <> 0 AND "
                        + "T01.ASIABN IN ('002') "
                        + bodegas
                        + "AND T02.APIAA9 = '" + lop + "'"
                        + "AND T02.APIA5E = '" + familia + "'"
                        + "AND T02.APICV4 IN ('06', '07') ");

                rs = stmt.executeQuery(qry);
                //System.out.println("Query " + qry);

                while (rs.next()) {
                    r = rs.getString(1).trim();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            r = "0.00";
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }
    
    @WebMethod(operationName = "getTotalProductoTerminado")
    public String getTotalProductoTerminado() {

        Statement stmt = null;
        ResultSet rs;
        String r = "0.00";

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT SUM(T04.JHIDCX * T01.ASIAK6) "
                        + "FROM S0646ED5.EFCP00D.IFGLFGB2 AS T01, S0646ED5.EFCP00D.IRFLPRD2 AS T02, S0646ED5.EFCP00D.IRFLWHS2 AS T03, S0646ED5.EFCP00D.IRFLPCS2 AS T04 "
                        + "WHERE T01.ASIAA0 = T02.APIAA0 AND  "
                        + "T01.ASIATC = T02.APIATC AND "
                        + "T01.ASIATD = T02.APIATD AND "
                        + "T01.ASIAAY = T03.AMIAAY AND "
                        + "T01.ASIAA0 = T04.JHIAA0 AND "
                        + "T01.ASIATC = T04.JHIATC AND "
                        + "T01.ASIATD = T04.JHIATD AND "
                        + "T01.ASIABN = T04.JHIABN AND "
                        + "T01.ASIAK6 <> 0 AND "
                        + "T01.ASIABN IN ('002') "
                        + "AND T01.ASIAAY IN ('030', '031', '034', '038') "
                        + "AND T02.APICV4 IN ('06', '07') ");

                rs = stmt.executeQuery(qry);
                //System.out.println("Query " + qry);

                while (rs.next()) {
                    r = rs.getString(1).trim();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            r = "0.00";
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }
    
    @WebMethod(operationName = "getCountDocs")
    public List<CountDocAS400> getCountDocs() {

        Statement stmt = null;
        ResultSet rs;
        CountDocAS400 tmp = new CountDocAS400();
        lstCountDoc = new ArrayList<CountDocAS400>();
        int mes = 0;
        
        java.util.Date fechaActual = new java.util.Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, -1); 
        
        mes = calendar.get(Calendar.MONTH) + 1;
        
        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT COUNT(*), CTIABN, CTIBB3 "
                        + " FROM S0646ED5.EFCP00D.IARLOPML "
                        + " WHERE CTIABN IN ('001', '002') "
                        + " AND CTIBQL = SUBSTR(" + calendar.get(Calendar.YEAR) + ", 3, 2) "
                        + " AND CTIBQM = " + mes
                        + " AND CTIBQN = " + calendar.get(Calendar.DAY_OF_MONTH)
                        + " AND CTIBB3 IN ('DINV', 'CINV', 'BCHK', 'RADJ', 'APVH', 'VADJ', 'VREV', 'PDDB') "
                        + " GROUP BY CTIABN, CTIBB3 ");

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                
                while (rs.next()) {
                    tmp = new CountDocAS400();
                    tmp.setCount(rs.getString(1).trim());
                    tmp.setCia(rs.getString(2).trim());
                    tmp.setDoc(rs.getString(3).trim());

                    lstCountDoc.add(tmp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstCountDoc = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstCountDoc;
    }
    
    @WebMethod(operationName = "getValorProductoTerminadoxProducto")
    public String[] getValorProductoTerminadoxProducto(@WebParam(name = "producto") String producto) {

        Statement stmt = null;
        ResultSet rs;
        String r[] = new String[2];

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {
                
                stmt = miConexion.createStatement();
                String qry = ("SELECT SUM(T04.JHIDCX * T01.ASIAK6), SUM(ASIAK6) "
                        + "FROM S0646ED5.EFCP00D.IFGLFGB2 AS T01, S0646ED5.EFCP00D.IRFLPRD2 AS T02, S0646ED5.EFCP00D.IRFLPCS2 AS T04 "
                        + "WHERE T01.ASIAA0 = T02.APIAA0 AND  "
                        + "T01.ASIATC = T02.APIATC AND "
                        + "T01.ASIATD = T02.APIATD AND "
                        + "T01.ASIAA0 = T04.JHIAA0 AND "
                        + "T01.ASIATC = T04.JHIATC AND "
                        + "T01.ASIATD = T04.JHIATD AND "
                        + "T01.ASIABN = T04.JHIABN AND "
                        + "T01.ASIAK6 <> 0 AND "
                        + "T01.ASIABN IN ('002') "
                        + "AND T01.ASIAAY IN ('030', '031', '038', '034') "
                        + "AND T02.APICV4 IN ('06', '07') "
                        + "AND ASIATC = '" + producto + "'");

                rs = stmt.executeQuery(qry);
                //System.out.println("Query " + qry);

                while (rs.next()) {
                    r[0] = rs.getString(1);
                    r[1] = rs.getString(2);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            r[0] = "-1";
            r[1] = e.getMessage();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }
    
    @WebMethod(operationName = "getFacturacionHistorica")
    public List<Facturacion> getFacturacionHistorica(@WebParam(name = "anio") String anio, @WebParam(name = "semestre") String semestre, @WebParam(name = "cia") String cia, @WebParam(name = "cliente") String cliente, @WebParam(name = "producto") String producto, @WebParam(name = "tipoDocumento") String tipoDocumento) {

        Statement stmt = null;
        ResultSet rs;
        Facturacion tmp = new Facturacion();
        lstFacturacion = new ArrayList<Facturacion>();
        String cad;
        
        try {

            miConexion = ConnectionAS400.GetConnection();
            
            if (semestre.equals("1"))
                cad = "<=";
            else
                cad = ">=";
                        

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT HIFABN, HIFDNM, HIFB5I, HIFAP6, HIFATC, HIFAA1, HIFCWL, HIFB74, HIFA88, HIFA89, HIFA9A, HIFCZM, HIFCQV, HIFA2J, HIFAK2, HIFSEC, HIICWN, HIFBPQ, HIFAQY "
                        + " FROM S0646ED5.EFCP00D.IOPLHIF0 "
                        + " WHERE HIFA88 = " + anio
                        + " AND HIFA89 " + cad + " 6 "
                        + " AND HIFABN = '" + cia + "' "
                        + " AND HIFDNM = '" + cliente + "' "
                        + " AND HIFATC = '" + producto + "'"
                        + " AND HIFCWL = UPPER('" + tipoDocumento + "')");

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                
                while (rs.next()) {
                    tmp = new Facturacion();
                    tmp.setCia(rs.getString(1).trim());
                    tmp.setCodCliente(rs.getString(2).trim());
                    tmp.setNomCliente(rs.getString(3).trim());
                    tmp.setCiudad(rs.getString(4).trim());
                    tmp.setCodProducto(rs.getString(5).trim());
                    tmp.setNomProducto(rs.getString(6).trim());
                    tmp.setTipDocumento(rs.getString(7).trim());
                    tmp.setNumDocumento(rs.getString(8).trim());
                    tmp.setAnioDocumento(rs.getString(9).trim());
                    tmp.setMesDocumento(rs.getString(10).trim());
                    tmp.setDiaDocumento(rs.getString(11).trim());
                    tmp.setCantidad(rs.getString(12).trim());
                    tmp.setValorNeto(rs.getString(13).trim());
                    tmp.setCodRazon(rs.getString(14).trim());
                    tmp.setLoteFacturado(rs.getString(15).trim());
                    tmp.setSecuencia(rs.getString(16).trim());
                    tmp.setOrdenCliente(rs.getString(17).trim());
                    tmp.setPrecioUnitarioNeto(rs.getString(18).trim());
                    tmp.setTipoVenta(rs.getString(19).trim());

                    lstFacturacion.add(tmp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstFacturacion = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstFacturacion;
    }
    
    @WebMethod(operationName = "getReservasXBodega")
    public List<Reservas> getReservasXBodega(@WebParam(name = "cia") String cia, @WebParam(name = "bodega") String bodega) {

        Statement stmt = null;
        ResultSet rs;
        Reservas tmp = new Reservas();
        lstReservas = new ArrayList<Reservas>();
        
        try {

            miConexion = ConnectionAS400.GetConnection();
            
            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT ASIABN, ASIATC, ASIAK2, ASIB6T "
                        + " FROM S0646ED5.EFCP00D.IFGLFGB0 "
                        + " WHERE ASIB6T > 0 " 
                        + " AND ASIAAY = '" + bodega + "' "
                        + " AND ASIABN = '" + cia + "' ");

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                
                while (rs.next()) {
                    tmp = new Reservas();
                    tmp.setCia(rs.getString(1).trim());
                    tmp.setProducto(rs.getString(2).trim());
                    tmp.setLote(rs.getString(3).trim());
                    tmp.setCantidad(rs.getString(4).trim());
                    
                    lstReservas.add(tmp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstReservas = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstReservas;
    }
    
    @WebMethod(operationName = "quitarReservas")
    public int quitarReservas(@WebParam(name = "cia") String cia, @WebParam(name = "bodega") String bodega) {

        Statement stmt = null;
        int r = -1;

        try {

            miConexion = ConnectionAS400.GetConnection();

            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = "UPDATE S0646ED5.EFCP00D.IFGLFGB0 SET "
                        + " ASIB6T = 0 "
                        + " WHERE ASIABN = '" + cia + "' "
                        + " AND ASIAAY = '" + bodega + "' "
                        + " AND ASIB6T > 0";
                
                r = stmt.executeUpdate(qry);
            }

        } catch (Exception e) {
            r = -1;
            e.printStackTrace();
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }
    
    @WebMethod(operationName = "getBodegas")
    public List<Bodegas> getBodegas() {

        Statement stmt = null;
        ResultSet rs;
        Bodegas tmp = new Bodegas();
        lstBodegas = new ArrayList<Bodegas>();
        
        try {

            miConexion = ConnectionAS400.GetConnection();
            
            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT AMIAAY, AMIB5I "
                        + " FROM S0646ED5.EFCP00D.IRFLWHS2 "
                        + " WHERE AMIA1L = 'A' " 
                        + " ORDER BY 2 ");

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                
                while (rs.next()) {
                    tmp = new Bodegas();
                    tmp.setCodigo(rs.getString(1).trim());
                    tmp.setNombre(rs.getString(2).trim());
                    
                    lstBodegas.add(tmp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            lstBodegas = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstBodegas;
    }
    
    @WebMethod(operationName = "getMultas")
    public List<Multas> getMultas() {

        Statement stmt = null;
        ResultSet rs;
        Multas tmp = new Multas();
        lstMultas = new ArrayList<Multas>();
        
        try {

            miConexion = ConnectionAS400.GetConnection();
            
            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT * "
                        + " FROM S0646ED5.EFCP00D.MULTA001 ");

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                
                while (rs.next()) {
                    tmp = new Multas();
                    tmp.setClase(rs.getString(1).trim());
                    tmp.setCodCliente(rs.getString(2).trim());
                    tmp.setNomCliente(rs.getString(3).trim());
                    tmp.setNumAjuste(rs.getString(4).trim());
                    tmp.setAnio(rs.getString(5).trim());
                    tmp.setMes(rs.getString(6).trim());
                    tmp.setDia(rs.getString(7).trim());
                    tmp.setFechaAjuste(rs.getString(8).trim());
                    tmp.setCodProducto(rs.getString(9).trim());
                    tmp.setNomProducto(rs.getString(10).trim());
                    tmp.setOrdenCompra(rs.getString(11).trim());
                    tmp.setFechaOC(rs.getString(12).trim());
                    tmp.setFacturaFinalInerna(rs.getString(13).trim());
                    tmp.setFacturaFinalExterna(rs.getString(14).trim());
                    tmp.setFechaFacturaFinal(rs.getString(15).trim());
                    tmp.setFacturaInicialInterna(rs.getString(16).trim());
                    tmp.setFacturaInicialExterna(rs.getString(17).trim());
                    tmp.setFechaFacturaInicial(rs.getString(18).trim());
                    tmp.setValorFactura(rs.getString(19).trim());
                    tmp.setValorMulta(rs.getString(20).trim());
                    tmp.setFechaPago(rs.getString(21).trim());
                    tmp.setDiasMulta(Integer.valueOf(rs.getString(22).trim()));
                    tmp.setCur(rs.getString(23).trim());
                    tmp.setFechaAceptacionCUR(rs.getString(24).trim());
                    
                    lstMultas.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstMultas = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstMultas;
    }
    
    @WebMethod(operationName = "getAreaPrincipal")
    public List<AreaPrincipal> getAreaPrincipal() {

        Statement stmt = null;
        ResultSet rs;
        AreaPrincipal tmp = new AreaPrincipal();
        lstArea = new ArrayList<AreaPrincipal>();
        
        try {

            miConexion = ConnectionAS400.GetConnection();
            
            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT DOIAG3, RTRIM(DOIAG4) "
                        + " FROM S0646ED5.EFCP00D.IRFLDEP2 "
                        + " WHERE DOIBUD = 0 "
                        + " ORDER BY 2");

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                
                while (rs.next()) {
                    tmp = new AreaPrincipal();
                    tmp.setCodigo(rs.getString(1).trim());
                    tmp.setNombre(rs.getString(2).trim());
                    
                    lstArea.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstArea = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstArea;
    }
    
    @WebMethod(operationName = "getResponsable")
    public List<Responsable> getResponsable() {

        Statement stmt = null;
        ResultSet rs;
        Responsable tmp = new Responsable();
        lstResponsable = new ArrayList<Responsable>();
        
        try {

            miConexion = ConnectionAS400.GetConnection();
            
            if (miConexion != null) {

                stmt = miConexion.createStatement();
                String qry = ("SELECT RTRIM(DBIAGQ), RTRIM(DBIAGT) CONCAT ' ' CONCAT RTRIM(DBIAGR) "
                        + " FROM S0646ED5.EFCP00D.IRFLEMP2 "
                        + " WHERE DBIAQD = 'RES' "
                        + " ORDER BY 2 ");

                //System.out.println("Query " + qry);
                rs = stmt.executeQuery(qry);
                
                while (rs.next()) {
                    tmp = new Responsable();
                    tmp.setCodigo(rs.getString(1).trim());
                    tmp.setNombre(rs.getString(2).trim());
                    
                    lstResponsable.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            lstResponsable = null;
        } finally {
            try {
                miConexion.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lstResponsable;
    }
}
