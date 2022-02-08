/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.String.valueOf;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import modelo.Administrador;
import modelo.AdministradorDAO;
//import modelo.Fabricante;
import vista.FrmCrearCuenta;
import vista.FrmInicio;
import vista.FrmPrincipal;

/**
 *
 * @author ISABELLA CARMONA C
 */
public class ControladorInicio implements ActionListener {

    FrmInicio frminicio;
    Administrador admin;
    AdministradorDAO admindao;
    //Fabricante fabric;

    public ControladorInicio(FrmInicio frminicio, Administrador admin, AdministradorDAO admindao) {
        this.frminicio = frminicio;
        this.admin = admin;
        this.admindao = admindao;

        //Escuchar los botones
        this.frminicio.jTxUser.addActionListener(this);
        this.frminicio.jPassword.addActionListener(this);
        this.frminicio.jBtInicioSesion.addActionListener(this);
        this.frminicio.jBtCambiarPw.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         
        if (e.getSource() == frminicio.jBtCambiarPw) {
            Administrador admin = new Administrador();
            AdministradorDAO admindao = new AdministradorDAO();
            
            FrmCrearCuenta form1 = new FrmCrearCuenta();
        
            ControladorCrearCuenta control1 = new ControladorCrearCuenta(form1, admin, admindao);
            form1.setVisible(true);                 
        }

        if (e.getSource() == frminicio.jBtInicioSesion) {

            String usuario = frminicio.jTxUser.getText();
            String contraseña = frminicio.jPassword.getText();

            try {
                if (admindao.validarUsuario(usuario, contraseña)) {                
                    FrmPrincipal fmenu = new FrmPrincipal();

                    fmenu.setVisible(true);
                } 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frminicio, "Usuario y/o Contraseña incorrecto(s)");
                Logger.getLogger(ControladorInicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
