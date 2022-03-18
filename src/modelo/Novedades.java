/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;

/**
 *
 * @author ISABELLA CARMONA C
 */
public class Novedades {

    private int idNovedades;
    private String tipoid;
    private String tipoNovedad;
    private String descripcion;
    private Date fechaNovedadInicio;
    private Date fechaNovedadFin;
    private int idEmpleado;

    public Novedades() {
    }

    public Novedades(String tipoid, String tipoNovedad, String descripcion, java.sql.Date fechaNovedadInicio, java.sql.Date fechaNovedadFin, int idEmpleado) {
        this.tipoid = tipoid;
        this.tipoNovedad = tipoNovedad;
        this.descripcion = descripcion;
        this.fechaNovedadInicio = fechaNovedadInicio;
        this.fechaNovedadFin = fechaNovedadFin;
        this.idEmpleado = idEmpleado;
    }

    public int getIdNovedades() {
        return idNovedades;
    }

    public void setIdNovedades(int idNovedades) {
        this.idNovedades = idNovedades;
    }

    public String getTipoid() {
        return tipoid;
    }

    public void setTipoid(String tipoid) {
        this.tipoid = tipoid;
    }

    public String getTipoNovedad() {
        return tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaNovedadInicio() {
        return fechaNovedadInicio;
    }

    public void setFechaNovedadInicio(Date fechaNovedadInicio) {
        this.fechaNovedadInicio = fechaNovedadInicio;
    }

    public Date getFechaNovedadFin() {
        return fechaNovedadFin;
    }

    public void setFechaNovedadFin(Date fechaNovedadFin) {
        this.fechaNovedadFin = fechaNovedadFin;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
