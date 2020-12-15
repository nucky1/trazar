/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.services.LocalService;
import ar.edu.unsl.backend.model.services.RegistroService;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.backend.util.Statics;
import java.awt.Color;
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
import javafx.scene.paint.Paint;
import ar.edu.unsl.frontend.service_subscribers.RegistrarseServiceSubiscriber;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

/**
 * FXML Controller class
 *
 * @author demig
 */
public class registrarLocalCntrl extends ViewCntlr implements RegistrarseServiceSubiscriber {

    private Local miLocal = new Local();
    private boolean error;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContraseña;
    @FXML
    private TextField txtContraseñaRep;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtcuit;
    @FXML
    private Button btnCancel;
    @FXML
    private Label alertUser;
    // ================================== FXML methods ==================================
    @FXML
    private Button btnguardarUsuario;
    @FXML
    private Button btnGuardarLocal;

    @FXML
    private void cancelarOperacion(ActionEvent event) {
        this.getStage().close();
    }
    @FXML
    private void updateUser(ActionEvent event) {
        if(miLocal == null){
            miLocal = new Local();
        }
        if(!error && verificarPassword()){
            
            Usuario user = new Usuario();
            user.setUserName(txtUsuario.getText());
            user.setPassword(txtContraseña.getText());
            user.setId(Statics.getUser().getId());
            try {
                ((UserService)this.getService(1)).update(user);
            } catch (Exception ex) {
                System.out.println(this.getService(1).getClass().toString());
                Logger.getLogger(registrarLocalCntrl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            CustomAlert alerta = new CustomAlert(Alert.AlertType.ERROR, "Error", "Por favor revise que las contraseñas coincidan y el usuario este disponible");
            try {
                alerta.show();
                Thread.sleep(2000);
                alerta.close();

            } catch (InterruptedException ex) {
                Logger.getLogger(registroFormCntrl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void updateLocal(ActionEvent event) {
        if(miLocal == null){
            miLocal = new Local();
        }
            miLocal.setId(Statics.getUser().getId_local());
            miLocal.setTelefono(txtTelefono.getText());
            miLocal.setCuit(txtcuit.getText());
            miLocal.setDireccion(txtDireccion.getText());
            miLocal.setNombre(txtNombre.getText());
            try {
                ((LocalService)this.getService(0)).update(miLocal);
            } catch (Exception ex) {
                Logger.getLogger(registrarLocalCntrl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
 
// ================================= protected methods ===============================
    @Override
    protected void manualInitialize() {
        try
        {
            ((LocalService)this.getService(0)).findById();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
// ================================= private methods ==================================
    private boolean verificarPassword(){
        return txtContraseña.getText().equals(txtContraseñaRep.getText());
    }
    
// ================================= public methods ==================================
    public void init()
    {
        this.manualInitialize();
    }   

    @Override
    public void usuarioLibre() {
        error = false;
        alertUser.setText("Nombre de usuario disponible");
        alertUser.setTextFill(Paint.valueOf("#008f39"));
        alertUser.setOpacity(1);
    }

    @Override
    public void usuarioOcupado() {
        error = true;
        alertUser.setText("Nombre de usuario NO disponible");
        alertUser.setTextFill(Paint.valueOf("#ff0000"));
        alertUser.setOpacity(1);
    }

    @Override
    public void exito() {
        this.getStage().close();
        CustomAlert alerta = new CustomAlert(Alert.AlertType.INFORMATION, "Exito", "Los cambios se guardaron con exito");
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
        CustomAlert alerta = new CustomAlert(Alert.AlertType.ERROR, "Error", "Algo anduvo mal... \n No se pudo guardar los datos");
        try {
            alerta.show();
            Thread.sleep(2000);
            alerta.close();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(registroFormCntrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void customInitialize(URL location, ResourceBundle resources) throws Exception {
        txtUsuario.focusedProperty().addListener((obs, oldVal, newVal) -> 
        {
            try {
                if(!newVal)
                    ((UserService)this.getService(1)).searchUser(txtUsuario.getText());
            } catch (Exception ex) {
                Logger.getLogger(registrarLocalCntrl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        txtContraseña.focusedProperty().addListener((obs, oldVal, newVal) -> 
        {
            if(!newVal){
                if(!verificarPassword()){
                    txtContraseña.setStyle("-fx-text-box-border: #ff0000 ; -fx-focus-color: #ff0000;");
                    txtContraseñaRep.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                }else{
                    txtContraseña.setStyle("");
                    txtContraseñaRep.setStyle("");
                }
            }
                
            
        });
        txtContraseñaRep.focusedProperty().addListener((obs, oldVal, newVal) -> 
        {
            if(!newVal){
                if(!verificarPassword()){
                    txtContraseña.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                    txtContraseñaRep.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: #ff0000;");
                }else{
                    txtContraseña.setStyle("");
                    txtContraseñaRep.setStyle("");
                }
            }
               
            
        });
    }

    @Override
    public void datosMiLocal(Local l) {
       miLocal = l;
       System.out.println(miLocal.getNombre());
       txtNombre.setText(miLocal.getNombre());
       txtDireccion.setText(miLocal.getDireccion());
       txtTelefono.setText(miLocal.getTelefono());
       txtcuit.setText(miLocal.getCuit());
       
    }


    
}
