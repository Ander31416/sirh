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
    private Date fechaNovedad;
    private int idEmpleado;

    public Novedades() {
    }

    public Novedades(String tipoid, String tipoNovedad, String descripcion, java.sql.Date fechaNovedad, int idEmpleado) {
        this.tipoid = tipoid;
        this.tipoNovedad = tipoNovedad;
        this.descripcion = descripcion;
        this.fechaNovedad = fechaNovedad;
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

    public java.util.Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(java.sql.Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

}
