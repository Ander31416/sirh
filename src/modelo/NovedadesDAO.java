/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.String.valueOf;
import java.sql.Blob;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
        String sql = "UPDATE `novedades` SET `Descripcion`='"+novedad.getDescripcion()+"',"
                + "`Fecha_Novedad_Inicial`='"+novedad.getFechaNovedadInicio()+"',`Fecha_Novedad_Final`='"+novedad.getFechaNovedadFin()+"',`Id_Empleado`='"+novedad.getIdEmpleado()+"',"
                + "`tipoNovedad`='"+novedad.getTipoNovedad()+"',`tipoDocumento`='"+novedad.getTipoid()+"' WHERE `Id_Novedades` = '"+ ObtenerID().getIdNovedades() +"'";
       
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
        con = cn.getConnection(); // Establece la conexión
        ps = con.prepareStatement(sql); // Se prepara el código sql
        rs = ps.executeQuery(); 
        while(rs.next()){           
            novedad.setIdNovedades(rs.getInt("Id_Novedades"));
            novedad.setTipoNovedad(rs.getString("tipoNovedad"));
            novedad.setDescripcion(rs.getString("Descripcion"));
            novedad.setFechaNovedadInicio(rs.getDate("Fecha_Novedad_Inicial"));
            novedad.setFechaNovedadFin(rs.getDate("Fecha_Novedad_Final"));
            novedad.setTipoid(rs.getString("tipoDocumento")); 
            novedad.setIdEmpleado(rs.getInt("Id_Empleado"));
        }
        return novedad;   
    }
    
    public boolean EditarNovedad (int Cod, String TipoNov, String descripción) throws SQLException{
    
        String sql = "";      
       
        if(!TipoNov.equals("[Seleccionar]")){            
            sql = "UPDATE `novedades` SET `tipoNovedad` = '"+ TipoNov + "' WHERE `Id_Novedades` = '"+ Cod +"'";
            
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
        }
        if(!descripción.equals("")){
            sql = "UPDATE `novedades` SET `Descripcion` = '"+ descripción + "' WHERE `Id_Novedades` = '"+ Cod +"'";
            
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
        }  
        return true;
    }
    
    public boolean EditarFechaInicio(Date fechasql, int Cod) {
        
            String sql = "UPDATE `novedades` SET `Fecha_Novedad_Inicial` = '"+ fechasql + "' WHERE `Id_Novedades` = '"+ Cod +"'";
            
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
    
    public boolean EditarFechaFin(Date fechasql, int Cod) {
        
            String sql = "UPDATE `novedades` SET `Fecha_Novedad_Final` = '"+ fechasql + "' WHERE `Id_Novedades` = '"+ Cod +"'";
            
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
    
    public void EditarArchivo(int Id_Novedades) throws SQLException{

                String sql = "SELECT * FROM novedades WHERE Descripcion is null";
                
                //Conectarse a la base de datos
                con = cn.getConnection();
                
                try {
                    ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
                    rs = ps.executeQuery(); //Ejecuta la instruccion
                } catch (SQLException ex) {
                    //Muestra el error en caso de haberlo
                    Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);                
                }    
                
                Blob Archivo = null;

                while(rs.next()){ 
                   Archivo = rs.getBlob("ArchivoNovedad");
                }
                
                sql = "UPDATE `novedades` SET `ArchivoNovedad`= '"+Archivo+"' WHERE Id_Novedades = '"+Id_Novedades+"'";
                
                try {
                    ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
                    ps.executeUpdate(); //Ejecuta la instruccion
                } catch (SQLException ex) {
                    //Muestra el error en caso de haberlo
                    Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);              
                }
                
                sql = "DELETE from `novedades` where Descripcion is null";

                try {
                    ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
                    ps.executeUpdate(); //Ejecuta la instruccion
                } catch (SQLException ex) {
                //Muestra el error en caso de haberlo
                    Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);
                }    
            
    }
    
    public boolean EliminarNovedad(int idNovedades) throws SQLException{
        
        String sql = "DELETE from `novedades` where `Id_Novedades` = '"+ idNovedades +"'";
        
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
    
    public void GuardarFecha(Date fecha){
        String sql = "INSERT INTO `novedades`(`Fecha_Novedad`) VALUES ('"+ fecha +"')";
        
        //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }    
    }
    
    public Date ConsultarFecha() throws SQLException{
        String sql = "SELECT `Fecha_Novedad` FROM `novedades` WHERE `Id_Novedades` = ''";
        
        //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } 
        
        return rs.getDate("Fecha_Novedad");
    }
    public int ContarFilas() throws SQLException{
        String sql = "SELECT COUNT(Id_Novedades) FROM `novedades` WHERE 1";
        
        //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } 
        int x = 0;
        while(rs.next()){
            x = rs.getInt("COUNT(Id_Novedades)");
        }       
        return x;
    }    
    
    public void DescargarArchivo(int Id_Novedades) throws SQLException, IOException{

        String sql = "SELECT * FROM `novedades` WHERE `Id_Novedades` = '"+Id_Novedades+"';";
        
        ps = con.prepareStatement(sql);
        ps.executeQuery();
        while(rs.next()) {
            Blob doc = rs.getBlob("`ArchivoNovedad`"); 
            InputStream is = doc.getBinaryStream();
            
            File file = new File("Documento_novedad_"+Id_Novedades);
            BufferedInputStream in = new BufferedInputStream(is);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            
            byte[] bytes = new byte[8000];
            
            int len = 0;
            
            while((len = in.read(bytes)) > 0){
                out.write(bytes, 0, len);
            }
            
            out.flush();
            out.close();
            in.close();
        } 
    }
     
    public void EliminarFecha(){
        String sql = "DELETE from `novedades` where `Id_Novedades` = ''";
        
        //Conectarse a la base de datos
        con = cn.getConnection();

        try {
            ps = con.prepareStatement(sql); //Envia la instruccion en comando sql
            ps.executeUpdate(); //Ejecuta la instruccion
        } catch (SQLException ex) {
            //Muestra el error en caso de haberlo
            Logger.getLogger(NovedadesDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }    
    }
    
    public Novedades ObtenerID() throws SQLException{
        
        String sql = "select * from `novedades` where `Descripcion` is null";
        
        //Conectarse a la base de datos
        con = cn.getConnection(); // Establece la conexión
        ps = con.prepareStatement(sql); // Se prepara el código sql
        rs = ps.executeQuery(); 
        while(rs.next()){           
            novedad.setIdNovedades(rs.getInt("Id_Novedades"));
            novedad.setTipoNovedad(rs.getString("tipoNovedad"));
            novedad.setDescripcion(rs.getString("Descripcion"));
            novedad.setFechaNovedadInicio(rs.getDate("Fecha_Novedad_Inicial"));
            novedad.setFechaNovedadFin(rs.getDate("Fecha_Novedad_Final"));
            novedad.setTipoid(rs.getString("tipoDocumento")); 
            novedad.setIdEmpleado(rs.getInt("Id_Empleado"));
        }
        return novedad;
    }
}
