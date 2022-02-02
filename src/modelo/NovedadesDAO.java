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
    
    public boolean GuardarNovedad(Novedades novedad, FileInputStream flujo, long longitud) throws SQLException{
        String sql = "insert into novedades values ('?', '?', '?', '?', '?','?')";
        
        ps.setInt(1, novedad.getIdNovedades());
        ps.setString(2, novedad.getDescripcion());
        ps.setDate(3, novedad.getFechaNovedad());
        ps.setInt(4, novedad.getIdEmpleado());
        ps.setString(5, novedad.getTipoNovedad());
        ps.setBlob(6, flujo, longitud);
        
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
        
        String sql = "select * from Novedades where idNovedades = "+ idNovedades;
        
        //Conectarse a la base de datos
        con =cn.getConnection(); // Establece la conexión
        ps = con.prepareStatement(sql); // Se prepara el código sql
        rs = ps.executeQuery(); 
        while(rs.next()){
            novedad.setIdNovedades(rs.getInt("IdNovedades"));
            novedad.setTipoNovedad(rs.getString("tipoNovedad"));
            novedad.setDescripcion(rs.getString("Descripcion"));
            novedad.setFechaNovedad(rs.getDate("FechaNovedad"));
            novedad.setTipoid(rs.getString("TipoDocument")); 
            novedad.setIdEmpleado(rs.getInt("IdEmpleado"));
        }
        return novedad;   
    }
    
    public boolean EditarNovedad (int idNovedades) throws SQLException{
    
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";
        
        String TipoNov = frmnovedades.jCbTipoNovedad.getSelectedItem().toString();
        String Cod = frmnovedades.jTFCodigo.getText();
        String tipoDoc = frmnovedades.jCbIdentificacion.getSelectedItem().toString();      
        String nroDoc = frmnovedades.jTFCedula.getText();
        String descripción = frmnovedades.jTADescription.getText();
        
        if(!TipoNov.equals("[seleccionar]")){
            
            sql1 = "UPDATE TipoNovedad SET = "+ TipoNov + " WHERE id = "+ idNovedades;
            
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
        if(!Cod.equals("")){
            sql2 = "UPDATE TipoNovedad SET = "+ Cod + " WHERE id = "+ idNovedades;
            
            //Conectarse a la base de datos
            con = cn.getConnection();

            try {
                ps = con.prepareStatement(sql2); //Envia la instruccion en comando sql
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
            sql3 = "UPDATE TipoNovedad SET = "+ tipoDoc + " WHERE id = "+ idNovedades;
            
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
            sql4 = "UPDATE TipoNovedad SET = "+ nroDoc + " WHERE id = "+ idNovedades;
            
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
            sql5 = "UPDATE TipoNovedad SET = "+ descripción + " WHERE id = "+ idNovedades;
            
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
        
        String sql = "DELETE * from Novedades where idNovedades = "+ idNovedades;
        
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
