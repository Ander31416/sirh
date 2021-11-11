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
           
            if(validarCamposCompletos(TipoNov,Cod,TipoDoc,nroDoc,descripción) != true){
                JOptionPane.showMessageDialog(frmnovedades, "Todos los campos deben ser insertados");
            }else{
                novedades = new Novedades(Cod, TipoNov, descripción, Date fechaNovedad, nroDoc);
                
                novedadesdao.GuardarNovedad(novedades);
                LimpiarControles();
                JOptionPane.showMessageDialog(frmnovedades, "Novedad guardada con exito");
            }
        }
        
        if(e.getSource() == frmnovedades.jBtConsultar){
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
