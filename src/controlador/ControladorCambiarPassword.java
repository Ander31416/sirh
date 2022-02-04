/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Administrador;
import modelo.AdministradorDAO;
import vista.FrmCambiarPassword;
import vista.FrmCrearCuenta;
import vista.FrmPrincipal;

/**
 *
 * @author ALBERTO CHICA
 */
public class ControladorCambiarPassword implements ActionListener{
    
    FrmCambiarPassword FrmCambiarPassword = new FrmCambiarPassword();
    Administrador admin = new Administrador();
    AdministradorDAO admindao = new AdministradorDAO();

    public ControladorCambiarPassword(FrmCambiarPassword FrmCambiarPassword, Administrador admin, AdministradorDAO admindao) {
        this.admin = admin;
        this.admindao = admindao;
        this.FrmCambiarPassword = FrmCambiarPassword;
        
        //Escuchar los botones
        this.FrmCambiarPassword.jBtEditar.addActionListener(this);
        this.FrmCambiarPassword.jBtSalir.addActionListener(this);       
    }   
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == FrmCambiarPassword.jBtEditar) {
            
            if (e.getSource() == FrmCambiarPassword.jBtEditar) {
            if(!FrmCambiarPassword.jTxRepetirContraseña.getText().equals(FrmCambiarPassword.jTxNuevaContraseña.getText())
                    ){
               JOptionPane.showMessageDialog(FrmCambiarPassword, "Los campos contraseña y repetir contraseña"
                       + " deben ser digitados de manera identica");
            }else if(FrmCambiarPassword.jTxRepetirContraseña.getText().equals("")){
                JOptionPane.showMessageDialog(FrmCambiarPassword, "Los campos no deben estar"
                        + " vacios");
            }else{
                try {
                    admindao.EditarContraseña(FrmCambiarPassword.jTxRepetirContraseña.getText());
                    JOptionPane.showMessageDialog(FrmCambiarPassword, "La contraseña se ha editado"
                        + " de manera correcta");
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorCrearCuenta.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
            }
        }    
        
        if (e.getSource() == FrmCambiarPassword.jBtSalir) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de querer salir de la plataforma?", "Fin productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (respuesta == JOptionPane.YES_OPTION){
                FrmCambiarPassword.setVisible(false);
            }
        }
    }
}
