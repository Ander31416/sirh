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
        this.frminicio.jBtCrearCuenta.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         
        if (e.getSource() == frminicio.jBtCrearCuenta) {
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

    
    /*
    public void SendEmail(){
        Properties propiedad = new Properties();
        propiedad.setProperty("mail smtp host", "smtp gmail com");
        propiedad.setProperty("mail.smtp.starttla.enable", "true");
        propiedad.setProperty("mail smtp port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        Session sesion = Session.getDefaultInstance(propiedad);
        Random claseRandom = new Random();
        int code = 1000 + claseRandom.nextInt(10000 - 1000);
            
            
        String Email = admin.getEmail();
        String Emailfabricante = fabric.getEmailfabricante();
        String Passwordfabricante = fabric.getPasswordfabricante();
        String asunto = "envio de código de verificación";
        String mensaje = "Código de verificación:\n\n" + code;
        MimeMessage mail = new MimeMessage(sesion);
            
        try {
            mail.setFrom(new InternetAddress (Emailfabricante));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (Email));
            mail.setSubject(asunto);
            mail.setText(mensaje);
                
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(Emailfabricante, Passwordfabricante);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();
                
        } catch (MessagingException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String encryptionEmail = "", x = "";
        for(int i = 0; i<Email.length(); i++){
            if(i>1 && !(encryptionEmail.charAt(i) == '@' && x.equals(""))){
                encryptionEmail += "*";
            }else{
                if(i<=1){
                    encryptionEmail += Email.charAt(i);
                }else{
                    encryptionEmail += Email.charAt(i);
                    x = " ";
                }
            }
        }
        int a = 0;
        do{
            String Inputcode = JOptionPane.showInputDialog(null, "Se ha enviado un código de verificación a "
                + encryptionEmail + "\n\nDigite el código:");
                
            if(Inputcode.equals(valueOf(code))){
                admindao.cambiarPassword();
                a++;
            }else{
                JOptionPane.showMessageDialog(null, "ERROR. Codigo ingresado sin exito");
            }
        }while(a == 0);        
    }*/
}

