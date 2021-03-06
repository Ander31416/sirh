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
import vista.FrmCrearCuenta;
import vista.FrmPrincipal;

/**
 *
 * @author ALBERTO CHICA
 */
public class ControladorCrearCuenta implements ActionListener{
    
    FrmCrearCuenta frmCrearCuenta = new FrmCrearCuenta();
    Administrador admin = new Administrador();
    AdministradorDAO admindao = new AdministradorDAO();

    public ControladorCrearCuenta(FrmCrearCuenta frmCrearCuenta, Administrador admin, AdministradorDAO admindao) {
        this.admin = admin;
        this.admindao = admindao;
        this.frmCrearCuenta = frmCrearCuenta;
        
        //Escuchar los botones
        this.frmCrearCuenta.jTxNewUser.addActionListener(this);
        this.frmCrearCuenta.jPFNewPassword.addActionListener(this);
        this.frmCrearCuenta.jTxNewEmail.addActionListener(this);
        this.frmCrearCuenta.jPFNewRepeatPassword.addActionListener(this);
        this.frmCrearCuenta.jBtContinuar.addActionListener(this);
        this.frmCrearCuenta.jBtSalir.addActionListener(this);        
    }   
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmCrearCuenta.jBtContinuar) {
            
            String User = frmCrearCuenta.jTxNewUser.getText();
            String Password = frmCrearCuenta.jPFNewPassword.getText();
            String Email = frmCrearCuenta.jTxNewEmail.getText();
            String RepeatPassword = frmCrearCuenta.jPFNewRepeatPassword.getText();
            
            if(User.equals("") || Password.equals("") || Email.equals("") || RepeatPassword.equals("")){
                JOptionPane.showMessageDialog(frmCrearCuenta,"Porfavor digite todos los campos");
            }else if(Password.equals(RepeatPassword) == false){
                JOptionPane.showMessageDialog(frmCrearCuenta,"Los campos 'contrase??a' y 'repetir "
                    + "contrase??a' deben ser digitados de manera identica.");
            }else{
                admindao.CrearCuenta(User, Password, Email);
                JOptionPane.showMessageDialog(frmCrearCuenta, "Cuenta creada con exito");

                FrmPrincipal abrir = new FrmPrincipal();
                abrir.setVisible(true);
            }
        }    
        
        if (e.getSource() == frmCrearCuenta.jBtSalir) {
            int respuesta = JOptionPane.showConfirmDialog(null, "??Esta seguro de querer salir de la plataforma?", "Fin productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (respuesta == JOptionPane.YES_OPTION){
                frmCrearCuenta.setVisible(false);
            }
        }
    }
}
