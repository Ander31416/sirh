/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import java.sql.Date;
import javax.swing.JOptionPane;
import modelo.Empleado;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == frmnovedades.jBtGuardar){
                    
            String TipoNov = frmnovedades.jCtipoNovedad.getSelectedItem().toString();
            int Cod = parseInt(frmnovedades.jTFCedula.getText());
            String TipoDoc = frmnovedades.jCBIdentificación.getSelectedItem().toString();      
            int nroDoc = parseInt(frmnovedades.jTFCedula.getText());
            String descripción = frmnovedades.jTADescription.getText();
            
            //Capturar fecha
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = format.format(frmnovedades.jCFechaNovedad.getDate());
            java.util.Date fechaN = null;
            try {
                fechaN = format.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            java.sql.Date fechasql = new java.sql.Date(fechaN.getTime());
            
           
            if(validarCamposCompletos(TipoNov,Cod,TipoDoc,nroDoc,descripción) != true){
                JOptionPane.showMessageDialog(frmnovedades, "Todos los campos deben ser insertados");
            }else{
                novedades = new Novedades(Cod, TipoNov, descripción, fecha, tipoDoc, nroDoc);
                
                novedadesdao.GuardarNovedad(novedades);
                LimpiarControles();
                JOptionPane.showMessageDialog(frmnovedades, "Novedad guardada con exito");
            }
        }
        
        if(e.getSource() == frmnovedades.jBtConsultar){
            if(!frmnovedades.jTFCedula.getText().equals("")){
                
            }
        }
    }  
    
    public boolean validarCamposCompletos(String TipoNov, int Cod, String TipoDoc, int nroDoc, String descripción){       
        if(TipoNov == "[seleccionar]"||TipoDoc == "[seleccionar]"||nroDoc == 0||descripción.isEmpty()){
            return false;
        }        
        return true;
    }
    
    public boolean LimpiarControles(){
        
        frmnovedades.jCtipoNovedad.setSelectedItem("[seleccionar]");
        frmnovedades.jTFCedula.setText(null);
        frmnovedades.jCBIdentificación.setSelectedItem("[seleccionar]");      
        frmnovedades.jTFCedula.setText(null);
        frmnovedades.jTADescription.setText(null);
        
        return true;
    }
}
