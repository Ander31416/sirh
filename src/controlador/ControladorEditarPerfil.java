/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import modelo.Administrador;
import modelo.AdministradorDAO;
import vista.FrmEditarPerfil;

/**
 *
 * @author 57322
 */
public class ControladorEditarPerfil implements ActionListener{
    
    FrmEditarPerfil frmeditarperfil;
    Administrador admin;
    AdministradorDAO admindao;

    public ControladorEditarPerfil() {
    }

    public ControladorEditarPerfil(FrmEditarPerfil frmEditarPerfil, Administrador admin, AdministradorDAO admindao) {
        this.frmeditarperfil = frmEditarPerfil;
        this.admin = admin;
        this.admindao = admindao;
        
        this.frmeditarperfil.jBtEditar.addActionListener(this);
        this.frmeditarperfil.jBtSalir.addActionListener(this);
    }
      
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource() == frmeditarperfil.jBtEditar){                        
            
            int y = 0;
            int a = 0, b = 0, c = 0;
            
                if(!frmeditarperfil.jTxNewUser.getText().equals("")){
                    y++; 
                    a = 1;
                }
                if(!frmeditarperfil.jTxNewEmail.getText().equals("")){
                    y++;
                    b = 2;
                }
                if(!frmeditarperfil.jPFNewPassword.getText().equals("")){                    
                    y++;
                    c = 3;
                }
                if(y != 0){
                    int z = JOptionPane.showConfirmDialog(frmeditarperfil, "¿Estas seguro de querer editar estos campos?", "Select an Option...",
			       JOptionPane.YES_NO_OPTION);
                                          
                    if(c == 3 && z == 0){
                        String x = null;
                        
                        do{
                            // Creamos el panel que contendrá los componentes Label y Password
                            JPanel panel = new JPanel();
                            JLabel label = new JLabel("ingrese de nuevo su nueva contraseña\n");
                            // Definimos el largo de la casilla para la contraseña
                            JPasswordField passwordField = new JPasswordField(15);
                            // Agregamos los componentes al panel
                            panel.add(label);
                            panel.add(passwordField);

                            // Definimos el texto de las opciones para aceptar o cancelar
                            String[] options = new String[]{"Aceptar", "Cancelar"};

                           // Agregamos el panel y las opciones al dialogo
                           int option = JOptionPane.showOptionDialog(null, panel, "ingrese de nuevo su nueva contraseña",
                           JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                           null, options, options[1]);

                           if(option == 0){
                                x = passwordField.getText(); 
                                if(!frmeditarperfil.jPFNewPassword.getText().equals(x)){
                                JOptionPane.showMessageDialog(frmeditarperfil, "El campo se ha rellenado de forma"
                                     + " incorrecta");
                                }
                           }else {
                                panel.setVisible(false);
                           }
                            
                        }while(!frmeditarperfil.jPFNewPassword.getText().equals(x));
                    }
                    
                    String user = frmeditarperfil.jTxNewUser.getText();
                    String email = frmeditarperfil.jTxNewEmail.getText();
                    String password = frmeditarperfil.jPFNewPassword.getText();
                    
                    admin = new Administrador(user, email, password);
                    
                    if(z == 0){
                        if(a == 1){
                            admindao.EditarValorUnitario(admin, a);
                        }
                        if(b == 2){
                            admindao.EditarValorUnitario(admin, b);
                        }
                        if(c == 3){
                            admindao.EditarValorUnitario(admin, c);
                        }
                    }
                
            } 
        }
        
        if(e.getSource() == frmeditarperfil.jBtSalir){
            int respuesta = JOptionPane.showConfirmDialog(frmeditarperfil, "¿Esta seguro de querer salir de la plataforma?", "Fin productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (respuesta == JOptionPane.YES_OPTION){
                frmeditarperfil.setVisible(false);
            }
        }
    }        
}
