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
import javax.swing.JOptionPane;
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
                        String x;
                        
                        do{
                            x = JOptionPane.showInputDialog(frmeditarperfil, "Digite de nuevo su nueva contraseña");
                            if(!frmeditarperfil.jPFNewPassword.getText().equals(x)){
                                JOptionPane.showMessageDialog(frmeditarperfil, "El campo se ha rellenado de forma"
                                     + " incorrecta");
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
