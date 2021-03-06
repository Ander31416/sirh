/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Empleado;
import modelo.EmpleadoDAO;
import vista.FrmEmpleados;

/**
 *
 * @author SALA404-406
 */
public class ControladorEmpleado implements ActionListener {

    FrmEmpleados frmempleados;
    Empleado empleado;
    EmpleadoDAO empleadodao;

    public ControladorEmpleado(FrmEmpleados frmempleados, Empleado empleado, EmpleadoDAO empleadodao) {
        this.frmempleados = frmempleados;
        this.empleado = empleado;
        this.empleadodao = empleadodao;

        this.frmempleados.jBtAgregar.addActionListener(this);
        this.frmempleados.jBtModificar.addActionListener(this);
        this.frmempleados.jBtEliminar.addActionListener(this);
        this.frmempleados.jBtConsultar.addActionListener(this);
        this.frmempleados.jBtNuevo.addActionListener(this);
        this.frmempleados.jBtSalir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmempleados.jBtAgregar) {

            String tipoID = (String) frmempleados.jCbTipoID.getSelectedItem();
            String cedula = frmempleados.jTxNumeroID.getText();
            String nombres = frmempleados.jTxNombres.getText();
            String apellidos = frmempleados.jTxApellidos.getText();

            //Capturar fecha
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = format.format(frmempleados.jDtFechaNacimiento.getDate());
            java.util.Date fechaN = null;
            try {
                fechaN = format.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            java.sql.Date fechasql = new java.sql.Date(fechaN.getTime());

            String telefono = frmempleados.jTxTelefono.getText();
            String direccion = frmempleados.jTxDireccion.getText();
            String rh = (String) frmempleados.jCbRH.getSelectedItem();
            String eps = (String) frmempleados.jCbEPS.getSelectedItem();
            String arl = (String) frmempleados.jCbARL.getSelectedItem();

            empleado = new Empleado(tipoID, cedula, nombres, apellidos, fechasql, telefono, direccion, rh, eps, arl);

            /*if (empleadodao.guardarDatos(empleado)) {
                limpiarControles();
                JOptionPane.showMessageDialog(frmvideop, "Producto registrado exitosamente");
            } else {
                JOptionPane.showMessageDialog(frmvideop, "Error al registrar el producto");
            }*/
        }
    }

}
