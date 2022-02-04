/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import modelo.Novedades;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import vista.FrmNovedades;

/**
 *
 * @author 57322
 */
public class NovedadesDAO {
    
    Conexion cn = new Conexion();

    Connection con; //Permite verificar la instruccion sql y ejecutarla
    PreparedStatement ps; //Objeto donde se carga el resultado de la consulta
    ResultSet rs; //Objeto que guarda el resultado de la consulta

    Novedades novedad = new Novedades();
    FrmNovedades frmnovedades;
    
    public boolean GuardarNovedad(Novedades novedad) throws SQLException{
        String sql = "INSERT INTO `novedades`(`Id_Novedades`, `Descripcion`, `tipoDocumento`, `tipoNovedad`,`Id_Empleado`) "
                + "VALUES ('"+novedad.getIdNovedades()+"','"+novedad.getDescripcion()+"','"+novedad.getTipoid()+
                "','"+novedad.getTipoNovedad()+"','"+novedad.getIdEmpleado()+"')";
        
        /*
        ps.setInt(1, novedad.getIdNovedades());
        ps.setString(2, novedad.getDescripcion());
        ps.setDate(3, novedad.getfechaNovedad());
        ps.setBlob(4, flujo, longitud);
        ps.setInt(5, novedad.getIdEmpleado());
        ps.setString(6, novedad.getTipoid());
        ps.setString(7, novedad.getTipoNovedad()); */
        
        //ps.setBlob(1, flujo, longitud);
        
        //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return false;
        }
        
        return true;
    }
    
    public Novedades ConsultarNovedad(int idNovedades) throws SQLException{
        
        String sql = "select * from `novedades` where `Id_Novedades` = '"+ idNovedades +"'";
        
        //Conectarse a la base de datos
        con =cn.getConnection(); // Establece la conexión
        ps = con.prepareStatement(sql); // Se prepara el código sql
        rs = ps.executeQuery(); 
        while(rs.next()){           
            novedad.setIdNovedades(rs.getInt("Id_Novedades"));
            novedad.setTipoNovedad(rs.getString("tipoNovedad"));
            novedad.setDescripcion(rs.getString("Descripcion"));
            novedad.setFechaNovedad(rs.getDate("Fecha_Novedad"));
            novedad.setTipoid(rs.getString("tipoDocumento")); 
            novedad.setIdEmpleado(rs.getInt("Id_Empleado"));
        }
        return novedad;   
    }
    
    public boolean EditarNovedad (int idNovedades) throws SQLException{
    
        String sql1 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";
        
        
        String TipoNov = frmnovedades.jCbTipoNovedad.getSelectedItem().toString();
        String tipoDoc = frmnovedades.jCbIdentificacion.getSelectedItem().toString();      
        String nroDoc = frmnovedades.jTFCedula.getText();
        String descripción = frmnovedades.jTADescription.getText();
        
       
        if(!TipoNov.equals("[seleccionar]")){
            
            sql1 = "UPDATE `novedades` SET `tipoNovedad` = '"+ TipoNov + "' WHERE `Id_Novedades` = '"+ idNovedades +"'";
            
            //Conectarse a la base de datos
            con = cn.getConnection();

            try {
                ps = con.prepareStatement(sql1); //Envia la instruccion en comando sql
                ps.executeUpdate(); //Ejecuta la instruccion
            } catch (SQLException ex) {
                //Muestra el error en caso de haberlo
                Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                return false;
            }        
 
            return true;
        }
        if(!tipoDoc.equals("[seleccionar]")){
            sql3 = "UPDATE `novedades` SET `tipoDocumento` = '"+ tipoDoc + "' WHERE `Id_Novedades` = '"+ idNovedades +"'";
            
            //Conectarse a la base de datos
            con = cn.getConnection();

            try {
                ps = con.prepareStatement(sql3); //Envia la instruccion en comando sql
                ps.executeUpdate(); //Ejecuta la instruccion
            } catch (SQLException ex) {
                //Muestra el error en caso de haberlo
                Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                return false;
            } 
        }
        if(!nroDoc.equals("")){
            sql4 = "UPDATE `novedades` SET `Id_Empleado` = '"+ nroDoc + "' WHERE `Id_Novedades` = '"+ idNovedades +"'";
            
            //Conectarse a la base de datos
            con = cn.getConnection();

            try {
                ps = con.prepareStatement(sql4); //Envia la instruccion en comando sql
                ps.executeUpdate(); //Ejecuta la instruccion
            } catch (SQLException ex) {
                //Muestra el error en caso de haberlo
                Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                return false;
            } 
        }
        if(!descripción.equals("")){
            sql5 = "UPDATE `novedades` SET `Descripcion` = '"+ descripción + "' WHERE `Id_Novedades` = '"+ idNovedades +"'";
            
            //Conectarse a la base de datos
            con = cn.getConnection();

            try {
                ps = con.prepareStatement(sql5); //Envia la instruccion en comando sql
                ps.executeUpdate(); //Ejecuta la instruccion
            } catch (SQLException ex) {
                //Muestra el error en caso de haberlo
                Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
                return false;
            } 
        }
        
        return true;
    }
    
    public boolean EliminarNovedad(int idNovedades) throws SQLException{
        
        String sql = "DELETE from novedades where `Id_Novedades` = '"+ idNovedades +"'";
        
        //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return false;
        }    
        
        return true;
    }
    
}
