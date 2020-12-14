/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.entities.Persona;
import ar.edu.unsl.backend.model.entities.Registro;
import ar.edu.unsl.backend.model.services.PersonaService;
import ar.edu.unsl.backend.model.services.RegistroService;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.backend.util.Statics;
import ar.edu.unsl.frontend.service_subscribers.PersonaServiceSubscriber;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author demig
 */
public class registroFormCntrl extends ViewCntlr implements PersonaServiceSubscriber {

    private int idPersona = -1;
    private LocalDate fechaActual = LocalDate.now();
    @FXML
    private Button btnRegistrar;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private Label lblFecha;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private Label lblAlerta;
    @FXML
    private Button btnBuscar;

// ================================== FXML methods ==================================

    @FXML
    private void registrarVisita(ActionEvent event) {
        Persona p = new Persona();
        p.setApellido(txtApellido.getText());
        p.setNombre(txtNombre.getText());
        p.setDireccion(txtDireccion.getText());
        p.setId(idPersona);
        p.setTelefono(txtTelefono.getText());
        p.setNumeroDocumento(txtDocumento.getText());
        ((PersonaService)this.getService(0)).insertarPersona(p);
    }

    @FXML
    private void buscarPersona(ActionEvent event) {
        lblAlerta.setOpacity(0);
        ((PersonaService)this.getService(0)).findPersona(this.txtDocumento.getText());
    }
// ================================= protected methods ===============================
    @Override
    protected void manualInitialize() {
        fechaActual = LocalDate.now();
        lblFecha.setText(fechaActual.toString());
    }
// ================================= private methods ==================================
    private void setCampos(boolean flag){
        txtApellido.setEditable(flag);
        txtNombre.setEditable(flag);
        txtDireccion.setEditable(flag);
        txtTelefono.setEditable(flag);
        if(flag == true){
            txtApellido.setText("");
            txtNombre.setText("");
            txtDireccion.setText("");
            txtTelefono.setText(""); 
        }
    }
    // ================================= public methods ==================================
    public void init()
    {
        this.manualInitialize();
    }   
    
    @Override
    public void customInitialize(URL location, ResourceBundle resources) throws Exception {
       
    }

    @Override
    public void showPersona(Persona persona) {
        idPersona = persona.getId();
        txtApellido.setText(persona.getApellido());
        txtDireccion.setText(persona.getDireccion());
        txtNombre.setText(persona.getNombre());
        txtTelefono.setText(persona.getTelefono());
        setCampos(false);
    }
    
    @Override
    public void didntFind() {
        lblAlerta.setOpacity(1);
        setCampos(true);
    }

    @Override
    public void GuardarVisita(Persona persona) {
        Registro r = new Registro();
        Local l = new Local();
        l.setId(Statics.getUser().getId_local());
        r.setPersona(persona);
        r.setLocal(l);
        String s = Statics.dateFormat(fechaActual);
        r.setFecha(s);
        ((RegistroService)this.getService(1)).insertarRegistro(r);
    }

    @Override
    public void showExito(Registro body) {
        this.getStage().close();
        CustomAlert alerta = new CustomAlert(Alert.AlertType.INFORMATION, "Exito", "Se registro la visita con exito");
        try {
            alerta.show();
            Thread.sleep(2000);
            alerta.close();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(registroFormCntrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void error() {
        this.getStage().close();
        CustomAlert alerta = new CustomAlert(Alert.AlertType.ERROR, "Error", "Algo anduvo mal... \n No se pudo registrar la visita");
        try {
            alerta.show();
            Thread.sleep(2000);
            alerta.close();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(registroFormCntrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
