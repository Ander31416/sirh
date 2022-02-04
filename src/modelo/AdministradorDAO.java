/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static java.lang.String.valueOf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import vista.FrmCrearCuenta;
import vista.FrmPrincipal;

/**
 *
 * @author ISABELLA CARMONA C
 */
public class AdministradorDAO {

    Conexion cn = new Conexion();

    Connection con; //Permite verificar la instruccion sql y ejecutarla
    PreparedStatement ps; //Objeto donde se carga el resultado de la consulta
    ResultSet rs; //Objeto que guarda el resultado de la consulta

    Administrador admin = new Administrador();

    public boolean cambiarPassword() {
        
        String newPassword;
        
        do{
            newPassword = JOptionPane.showInputDialog("Ingrese una nueva contraseña");            
            if(newPassword.equals("")){
                JOptionPane.showMessageDialog(null, "Porfavor, digite el campo de contraseña");
            }            
        }while(newPassword.equals(""));
        
        String sql = "UPDATE administrador SET password='" + newPassword + "' WHERE password='" + admin.getPassword() + "'";

        //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return false;
        }

        return true;
    }

    public boolean validarUsuario(String usuario, String contrasena) throws SQLException {

        String sql = "SELECT *FROM administrador";

        con = cn.getConnection();

        ps = con.prepareStatement(sql); //Se prepara el codigo sql
        rs = ps.executeQuery(); //Se ejecuta

        while (rs.next()) {
            if (rs.getString("user").trim().equals(usuario) && rs.getString("password").trim().equals(contrasena)) {
                FrmPrincipal fmenu = new FrmPrincipal();

                fmenu.setVisible(true);             
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    
    public boolean EditarValorUnitario(String valor, int y){
        
        String sql = "";
        
        switch (y) {
            case 1:
                sql = "UPDATE `administrador` SET `user` = '"+ valor +"' WHERE 1";
                break;
            case 2:
                sql = "UPDATE `administrador` SET `email` = '"+ valor +"' WHERE 1";
                break; 
            case 3:
                sql = "UPDATE `administrador` SET `id` = '"+ valor +"' WHERE 1";
                break;
            default:
                break;
        }
        
        try {
            ps = con.prepareStatement(sql); //Se prepara el codigo sql
            ps.executeUpdate(); //Se ejecuta
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public boolean EditarUsuario(String user, String email, String password) throws SQLException{
        
        String sql = "UPDATE administrador SET user = '"+ user + "', email = '"+ email +"'"
                + ", id = '"+ password +"' WHERE id = '"+ password +"'";
        
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Se prepara el codigo sql
            ps.executeUpdate(); //Se ejecuta
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public boolean EditarContraseña(String password) throws SQLException{
        
        String sql = "UPDATE administrador SET id = '"+ password +"' WHERE 1";
        
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Se prepara el codigo sql
            ps.executeUpdate(); //Se ejecuta
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public boolean CrearCuenta(String User, String Password, String Email) {
        
    //AgregarCuenta();
        String sql = "INSERT INTO administrador VALUES ('" + User + "', '" + Password + "', '"+ Email +"')";

    //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return false;
        }           

        return true;    
    }
    
    public void obtenerDatos() throws SQLException{
        String sql = "select * from administrador";
        
        //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }  
        while(rs.next()) {           
            admin.setUser(rs.getString("user"));
            admin.setPassword(rs.getString("id"));
            admin.setEmail(rs.getString("email"));
        }
    }
}
