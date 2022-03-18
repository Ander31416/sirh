/*/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import java.io.*;
import static java.lang.String.valueOf;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Conexion;
import modelo.Novedades;
import modelo.NovedadesDAO;
import vista.FrmNovedades;

/**
 *
 * @author 57322
 */
public class ControladorNovedades implements ActionListener {
    
    FrmNovedades frmnovedades;
    Novedades novedades;
    NovedadesDAO novedadesdao;

    public ControladorNovedades() {
    } 

    public ControladorNovedades(FrmNovedades frmnovedades, Novedades novedades, NovedadesDAO novedadesdao) {
        this.frmnovedades = frmnovedades;
        this.novedades = novedades;
        this.novedadesdao = novedadesdao;
        
        this.frmnovedades.jBtAñadirArchivos.addActionListener(this);
        this.frmnovedades.jBtConsultar.addActionListener(this);
        this.frmnovedades.jBtEditar.addActionListener(this);
        this.frmnovedades.jBtEliminar.addActionListener(this);
        this.frmnovedades.jBtGuardar.addActionListener(this);
    }
    
    //Predeterminados de archivos
    Long longitud;
    File Archivo;
    FileInputStream flujo;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos pdf y jpg","pdf","jpg");

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == frmnovedades.jBtGuardar){
                   
            String TipoNov = frmnovedades.jCbTipoNovedad.getSelectedItem().toString();
            String tipoDoc = frmnovedades.jCbIdentificacion.getSelectedItem().toString();
            int nroDoc = 0;
            if(!frmnovedades.jTFCedula.getText().equals("")){
                nroDoc = parseInt(frmnovedades.jTFCedula.getText());
            }
            String descripción = frmnovedades.jTADescription.getText();
            
            java.sql.Date fechaInicsql = AdquirirFecha(frmnovedades.jDCFechaNovInic.getDate());
            java.sql.Date fechaFinsql = AdquirirFecha(frmnovedades.jDCFechaNovFin.getDate());

            if(!frmnovedades.jTFCodigo.getText().equals("")){
                JOptionPane.showMessageDialog(frmnovedades, "En caso de querer guardar una novedad el campo 'código'"
                        + "\nno debe ser rellenado");
            }else if(!ValidarControlesCompletos()){
                JOptionPane.showMessageDialog(frmnovedades, "Todos los campos (menos el código de la novedad)"
                        + "\ndeben ser insertados");
            }else{               
                novedades = new Novedades(tipoDoc, TipoNov, descripción, fechaInicsql, fechaFinsql, nroDoc);                                               
                
                try {
                    int x = novedadesdao.ObtenerID().getIdNovedades();
                    novedadesdao.GuardarNovedad(novedades); 
                                        
                    JOptionPane.showMessageDialog(frmnovedades, "Novedad guardada con exito\n\nCódigo: "+ x);
                } catch (SQLException ex) {                    
                    Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);                    
                }  
                //novedadesdao.EliminarFecha();
                LimpiarControles();
            }
        }
        
        if(e.getSource() == frmnovedades.jBtConsultar){           
                       
            if(!frmnovedades.jTFCodigo.getText().equals("")){ 
                int Cod = Integer.parseInt(frmnovedades.jTFCodigo.getText());
                try {
                    if(VerificarCodigo(novedadesdao.ConsultarNovedad(Cod))){
                        ConsultarNovedad(novedadesdao.ConsultarNovedad(Cod));
                        novedadesdao.DescargarArchivo(Cod);
                    }else{
                        JOptionPane.showMessageDialog(frmnovedades, "Este código de novedad"
                               + " no se encuentra registrado");
                    }                    
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);                   

                } catch (IOException ex) {
                    Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(frmnovedades, "Ingrese el codigo de la novedad");
            }
        }
        
        if(e.getSource() == frmnovedades.jBtEditar){ 
      
            if(frmnovedades.jTFCodigo.getText().equals("")){
                JOptionPane.showMessageDialog(frmnovedades, "No se ha digitado el código de la novedad que se "
                        + "desea editar");
            }else if(ValidarControlesLimpios()){
                int Cod = parseInt(frmnovedades.jTFCodigo.getText());
                JOptionPane.showMessageDialog(frmnovedades, "No se ha editado ningun dato en la novedad "
                        + Cod);
            }else if(!frmnovedades.jTFCedula.getText().equals("") || !frmnovedades.jCbIdentificacion.getSelectedItem().toString().equals("[Seleccionar]")){
                        JOptionPane.showMessageDialog(frmnovedades, "El tipo y número de documento no deben ser editados");
            }else{
                int x = JOptionPane.showConfirmDialog(frmnovedades, "¿Estas seguro de querer editar estos campos?", "Select an Option...",
			JOptionPane.YES_NO_OPTION); 
                
                if(x == 0){
                    String TipoNov = frmnovedades.jCbTipoNovedad.getSelectedItem().toString();
                    int Cod = 0;
                    if(!frmnovedades.jTFCodigo.getText().equals("")){
                        Cod = parseInt(frmnovedades.jTFCodigo.getText());
                    }
                    String descripción = frmnovedades.jTADescription.getText();
                    int y = 0, z = 0, a = 0; //Variables que se 'inicializaran' si se edita su respectivo valor
                    AdquirirFecha(frmnovedades.jDCFechaNovInic.getDate());
                    
                    //Identificar si el usuario va a editar la fecha inicial
                    if(frmnovedades.jDCFechaNovInic.getDate() != null){
                        y++; 
                    }
                    
                    //Identificar si el usuario va a editar la fecha final
                    if(frmnovedades.jDCFechaNovFin.getDate() != null){
                        a++; 
                    }
                    
                    //Identificar si el usuario va a editar el documento
                    if(frmnovedades.jLabel12.getText() != null){
                        z++; 
                    }

                    try {
                        novedadesdao.EditarNovedad(Cod, TipoNov, descripción);
                        
                        //Condicional - ¿Se editará la fecha inicial?
                        if(y == 1){
                            try{
                                novedadesdao.EditarFechaInicio(AdquirirFecha(frmnovedades.jDCFechaNovInic.getDate()), Cod);
                            }catch(Exception f){
                                JOptionPane.showMessageDialog(frmnovedades, "La fecha inicial se ha escrito de"
                                  + " forma incorrecta");
                            }
                        }
                        //Condicional - ¿Se editará la fecha Final?
                        if(a == 1){
                            try{
                                novedadesdao.EditarFechaFin(AdquirirFecha(frmnovedades.jDCFechaNovFin.getDate()), Cod);
                            }catch(Exception f){
                                JOptionPane.showMessageDialog(frmnovedades, "La fecha se ha escrito de"
                                  + " forma incorrecta");
                            }
                        }
                        //Condicional - ¿Se editará el documento?
                        if(z == 1){
                            novedadesdao.EditarArchivo(Cod);
                        }
                        JOptionPane.showMessageDialog(frmnovedades, "La novedad se ha editado de"
                                  + " forma correcta");
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }           
        }
        
        if(e.getSource() == frmnovedades.jBtEliminar){
            
            if(!frmnovedades.jTFCodigo.getText().equals("")){ 
                int Cod = Integer.parseInt(frmnovedades.jTFCodigo.getText());
                try {
                    if(VerificarCodigo(novedadesdao.ConsultarNovedad(Cod))){
                        int x = JOptionPane.showConfirmDialog(frmnovedades, "¿Estas seguro de querer eliminar esta novedad?", "Select an Option...",
                                JOptionPane.YES_NO_OPTION);
                        if(x == 0){
                            novedadesdao.EliminarNovedad(Cod);
                            
                            JOptionPane.showMessageDialog(frmnovedades, "Novedad eliminada con exito");
                            LimpiarControles();
                        }
                    }else{
                        JOptionPane.showMessageDialog(frmnovedades, "Este código de novedad"
                               + " no se encuentra registrado");
                    }                    
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);                   

                }
            }else{
                JOptionPane.showMessageDialog(frmnovedades, "Ingrese el codigo de la novedad");
            }
        }
        
        if(e.getSource() == frmnovedades.jBtAñadirArchivos){           
            JFileChooser file = new JFileChooser();            
            file.setFileFilter(filter);
            
            int option = file.showOpenDialog(frmnovedades);
            if(option == JFileChooser.APPROVE_OPTION){  
                frmnovedades.jLabel12.setText(file.getSelectedFile().toString());
                
                Archivo = file.getSelectedFile();
                longitud = Archivo.length();

                try {
                    flujo = new FileInputStream(Archivo);
                    
                    Conexion cn = new Conexion();

                    Connection con; //Permite verificar la instruccion sql y ejecutarla
                    PreparedStatement ps ; //Objeto donde se carga el resultado de la consulta
                    ResultSet rs; //Objeto que guarda el resultado de la consulta
                    
                    String sql = "INSERT INTO `novedades`(`ArchivoNovedad`) VALUES (?)";
                            
                    con = cn.getConnection();

                    try {
                        ps = con.prepareStatement(sql); //Se prepara el codigo sql
                        ps.setBlob(1, flujo, longitud);
                        
                        ps.executeUpdate(); //Se ejecuta                          
                    } catch (Exception m) {
                        JOptionPane.showMessageDialog(frmnovedades, "Error. El archivo es demasiado grande");
                    }
                                       
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
                }               
            }
        }       
    }  
    
    public boolean LimpiarControles(){
        
        frmnovedades.jCbTipoNovedad.setSelectedItem("[Seleccionar]");
        frmnovedades.jTFCedula.setText(null);
        frmnovedades.jCbIdentificacion.setSelectedItem("[Seleccionar]");      
        frmnovedades.jTFCodigo.setText(null);
        frmnovedades.jTADescription.setText(null);
        frmnovedades.jDCFechaNovInic.setDate(null);
        frmnovedades.jDCFechaNovFin.setDate(null);
        frmnovedades.jLabel12.setText(null);
                
        return true;
    }
    
    public boolean ValidarControlesCompletos(){
        if( 
           frmnovedades.jCbTipoNovedad.getSelectedItem().equals("[Seleccionar]") ||
           frmnovedades.jCbIdentificacion.getSelectedItem().equals("[Seleccionar]") || 
           frmnovedades.jTFCedula.getText().equals("") ||
           frmnovedades.jTADescription.getText().equals("") ||
           frmnovedades.jLabel12.getText().equals("")
           ){
            return false;
        }
        return true;
    }  
    
    public boolean ValidarControlesLimpios(){
        if( 
           frmnovedades.jCbTipoNovedad.getSelectedItem().equals("[Seleccionar]") &&
           frmnovedades.jCbIdentificacion.getSelectedItem().equals("[Seleccionar]") && 
           frmnovedades.jTFCedula.getText().equals("") &&
           frmnovedades.jTADescription.getText().equals("") &&   
           frmnovedades.jLabel12.getText().equals("") &&
           frmnovedades.jDCFechaNovInic.getDate() == null &&
           frmnovedades.jDCFechaNovFin.getDate() == null
           ){
            return true;
        }
        return false;
    }
    
    public void ConsultarNovedad(Novedades novedad){
        frmnovedades.jCbTipoNovedad.setSelectedItem(novedad.getTipoNovedad());
        frmnovedades.jTFCedula.setText(valueOf(novedad.getIdEmpleado()));
        frmnovedades.jCbIdentificacion.setSelectedItem(novedad.getTipoid());      
        frmnovedades.jTFCodigo.setText(valueOf(novedad.getIdNovedades()));
        frmnovedades.jTADescription.setText(novedad.getDescripcion());
        frmnovedades.jDCFechaNovInic.setDate(novedad.getFechaNovedadInicio());
    }
    
    public boolean VerificarCodigo(Novedades novedad) throws SQLException{
        return novedad.getIdNovedades() != 0;       
    }
    
    public java.sql.Date AdquirirFecha(java.util.Date Fecha) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date fechasql;
        String fecha = "";
        int x = 0;
        
        try{
            fecha = format.format(Fecha);
        }catch(Exception e){
           x++; 
        }
            
        if(x == 1){                    
            fecha = "0001-01-01";
            java.util.Date fechaN = null;
            try {
                fechaN = format.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
            }       
            fechasql = new java.sql.Date(fechaN.getTime());
        }else{           
            //Capturar fecha  
            fecha = format.format(Fecha);
            java.util.Date fechaN = null;
            try {
                fechaN = format.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
            }       
            fechasql = new java.sql.Date(fechaN.getTime());
        }
        return fechasql;
    }
}
