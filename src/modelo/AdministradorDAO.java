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

    public boolean validarUsuario(String usuario, String contrasena) throws SQLException {

        String sql = "SELECT *FROM `administrador`";

        con = cn.getConnection();

        ps = con.prepareStatement(sql); //Se prepara el codigo sql
        rs = ps.executeQuery(); //Se ejecuta

        while (rs.next()) {
            if (rs.getString("user").trim().equals(usuario) && rs.getString("id").trim().equals(contrasena)) {                
                sql = "UPDATE `administrador` SET `Activate` = 'open' WHERE `id` = '"+ rs.getString("id") +"'";
        
                con = cn.getConnection();

                try {
                    ps = con.prepareStatement(sql); //Se prepara el codigo sql
                    ps.executeUpdate(); //Se ejecuta
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                                
                FrmPrincipal fmenu = new FrmPrincipal();

                fmenu.setVisible(true);             
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    
    public void EditarValorUnitario(Administrador admin, int y){

        String sql;
        
        switch (y) {
            case 1:
                sql = "UPDATE `administrador` SET `user` = '"+ admin.getUser() +"' WHERE Activate = 'open'";
                
                con = cn.getConnection();
                
                try {
                    ps = con.prepareStatement(sql); //Se prepara el codigo sql
                    ps.executeUpdate(); //Se ejecuta
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
                } 
                break;    
                
            case 2:
                sql = "UPDATE `administrador` SET `email` = '"+ admin.getEmail() +"' WHERE Activate = 'open'";
                
                con = cn.getConnection();
                
                try {
                    ps = con.prepareStatement(sql); //Se prepara el codigo sql
                    ps.executeUpdate(); //Se ejecuta
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
                } 
                break;
                
            case 3:
                sql = "UPDATE `administrador` SET `id` = '"+ admin.getPassword() +"' WHERE Activate = 'open'";
                
                con = cn.getConnection();
                
                try {
                    ps = con.prepareStatement(sql); //Se prepara el codigo sql
                    ps.executeUpdate(); //Se ejecuta
                } catch (SQLException ex) {
                    Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
                } 
                break;
        }
    }    

    public boolean EditarContraseña(String password) throws SQLException{
        
        String sql = "UPDATE `administrador` SET `id` = '"+ password +"' WHERE `Activate` = 'open'";
        
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
        String sql = "INSERT INTO administrador VALUES ('" + User + "', '" + Password + "', '"+ Email +"', 'open')";

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
    
    public void cerrarCuenta(){
        String sql = "UPDATE `administrador` SET `Activate`= '' WHERE `Activate`= 'open'";
        
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } 
    }
}
