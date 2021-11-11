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
    private String tipoNovedad;
    private String descripcion;
    private Date fechaNovedad;
    private Empleado idEmpleado;

    public Novedades() {
    }

    public Novedades(int idNovedades, String tipoNovedad, String descripcion, Date fechaNovedad, Empleado idEmpleado) {
        this.idNovedades = idNovedades;
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

    public Date getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(Date fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

}
