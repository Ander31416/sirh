/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import java.io.*;
import static java.lang.String.valueOf;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    
    int y;
    Long longitud;
    String fecha;
    File Archivo;
    FileInputStream flujo;
    java.sql.Date fechasql;   
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos pdf y jpg","pdf","jpg"); 
    
    @Override
    public void actionPerformed(ActionEvent e) { 
        
        if(e.getSource() == frmnovedades.jDCFechaNov){
            
            //Capturar fecha
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            fecha = format.format(frmnovedades.jDCFechaNov.getDate());
            java.util.Date fechaN = null;                       
            try {
                fechaN = format.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
            }
            fechasql = new java.sql.Date(fechaN.getTime());
        }
        
        if(e.getSource() == frmnovedades.jBtGuardar){
                   
            String TipoNov = frmnovedades.jCbTipoNovedad.getSelectedItem().toString();
            int Cod = 0;
            if(!frmnovedades.jTFCodigo.getText().equals("")){
                Cod = parseInt(frmnovedades.jTFCodigo.getText());
            }
            String tipoDoc = frmnovedades.jCbIdentificacion.getSelectedItem().toString();
            int nroDoc = 0;
            if(!frmnovedades.jTFCedula.getText().equals("")){
                nroDoc = parseInt(frmnovedades.jTFCedula.getText());
            }
            String descripción = frmnovedades.jTADescription.getText();

            if(TipoNov.equals("[Seleccionar]") || tipoDoc.equals("[Seleccionar]") || 
               nroDoc == 0 || descripción.equals("") || Cod == 0){
                JOptionPane.showMessageDialog(frmnovedades, "Todos los campos deben ser insertados");
            }else{
                novedades = new Novedades(Cod, tipoDoc, TipoNov, descripción, fechasql, nroDoc);                                               
                
                try {
                    novedadesdao.GuardarNovedad(novedades);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
                }                
                JOptionPane.showMessageDialog(frmnovedades, "Novedad guardada con exito");
                LimpiarControles();
            }
        }
        
        if(e.getSource() == frmnovedades.jBtConsultar){           
                       
            if(!frmnovedades.jTFCodigo.getText().equals("")){ 
                int Cod = Integer.parseInt(frmnovedades.jTFCodigo.getText());
                try {
                    if(VerificarCodigo(novedadesdao.ConsultarNovedad(Cod))){
                        ConsultarNovedad(novedadesdao.ConsultarNovedad(Cod));
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
        
        if(e.getSource() == frmnovedades.jBtEditar){            
            
            if(frmnovedades.jTFCodigo.getText().equals("")){
                JOptionPane.showMessageDialog(frmnovedades, "No se ha digitado el código de la novedad que se "
                        + "desea editar");
            }else if(ValidarControlesLimpios()){
                int Cod = parseInt(frmnovedades.jTFCodigo.getText());
                JOptionPane.showMessageDialog(frmnovedades, "No se ha editado ningun dato en la novedad "
                        + Cod);
            }else{
                int x = JOptionPane.showConfirmDialog(frmnovedades, "¿Estas seguro de querer editar estos campos?", "Select an Option...",
			JOptionPane.YES_NO_OPTION); 
                
                if(x == 0){
                    try { 
                        int idNovedades = parseInt(frmnovedades.jTFCodigo.getText());
                        novedadesdao.EditarNovedad(idNovedades);
                        JOptionPane.showMessageDialog(frmnovedades, "La novedad se ha editado de"
                                + " forma correcta");
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }           
        }
        
        if(e.getSource() == frmnovedades.jBtEliminar){
            if(frmnovedades.jTFCodigo.getText().equals("")){
                JOptionPane.showMessageDialog(frmnovedades, "No se ha digitado el codigo de la novedad a eliminar");
            }else{
                int x = JOptionPane.showConfirmDialog(frmnovedades, "¿Estas seguro de querer eliminar esta novedad?", "Select an Option...",
				JOptionPane.YES_NO_OPTION);
                if(x == 0){                   
                    int cod = parseInt(frmnovedades.jTFCodigo.getText());
                    
                    try {
                        novedadesdao.EliminarNovedad(cod);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorNovedades.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(frmnovedades, "La novedad "+frmnovedades.jTFCodigo.getText()
                            + " ha sido eliminada");
                }
            }
        }
        
        if(e.getSource() == frmnovedades.jBtAñadirArchivos){           
            JFileChooser file = new JFileChooser();            
            file.setFileFilter(filter);
            
            int option = file.showOpenDialog(frmnovedades);
            if(option == JFileChooser.APPROVE_OPTION){  
                frmnovedades.jLabel1.setText(file.getSelectedFile().toString());
                
                Archivo = file.getSelectedFile();
                longitud = Archivo.length();

                try {
                    flujo = new FileInputStream(Archivo);
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
                
        return true;
    }
    
    public boolean ValidarControlesCompletos(){
        if( 
           frmnovedades.jCbTipoNovedad.getSelectedItem().equals("[Seleccionar]") ||
           frmnovedades.jCbIdentificacion.getSelectedItem().equals("[Seleccionar]") || 
           frmnovedades.jTFCedula.getText().equals("") ||
           frmnovedades.jTADescription.getText().equals("")
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
           y == 0                
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
    }
    
    public boolean VerificarCodigo(Novedades novedad) throws SQLException{
        return novedad.getIdNovedades() != 0;       
    }
}