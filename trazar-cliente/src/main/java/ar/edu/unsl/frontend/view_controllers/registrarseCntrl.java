/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.services.LocalService;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.frontend.service_subscribers.RegistrarseServiceSubiscriber;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author demig
 */
public class registrarseCntrl extends ViewCntlr implements RegistrarseServiceSubiscriber {
    private Local miLocal = new Local();
    Usuario user = new Usuario();
    private boolean error;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private PasswordField txtContraseñaRep;
    @FXML
    private Label alertUser;
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
    private Button btnFinalizar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
// ================================== FXML methods ==================================

    @FXML
    private void checkNumberTel(KeyEvent event) {
        String s = event.getCharacter();
        if(s.length()!= 1)
            event.consume();
        char c = s.charAt(0);
        if((c<'0'||c>'9'))
            event.consume();
    }

    @FXML
    private void checkNumber(KeyEvent event) {
        String s = event.getCharacter();
        if(s.length()!= 1)
            event.consume();
        char c = s.charAt(0);
        if((c<'0'||c>'9'))
            event.consume();
    }

    @FXML
    private void cancelarOperacion(ActionEvent event) {
        this.getStage().close();
    }

    @FXML
    private void finalizarRegistro(ActionEvent event) {
        if(miLocal == null){
            miLocal = new Local();
        }
        if(!error && verificarPassword()){
            //Datos Local
            miLocal.setTelefono(txtTelefono.getText());
            miLocal.setCuit(txtcuit.getText());
            miLocal.setDireccion(txtDireccion.getText());
            miLocal.setNombre(txtNombre.getText());
            try {
                ((LocalService)this.getService(0)).insertLocal(miLocal);
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
// ================================= protected methods ===============================
 
    @Override
    protected void manualInitialize() {
        
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
        this.getPrevViewCntlr().cargarCosas(user.getUserName(),user.getPassword());
        this.getStage().close();
        CustomAlert alerta = new CustomAlert(Alert.AlertType.INFORMATION, "Exito", "Tu usuario fue creado con exito.");
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
        CustomAlert alerta = new CustomAlert(Alert.AlertType.ERROR, "Error", "Algo anduvo mal... \n No se pudo crear el usuario.");
        try {
            alerta.show();
            Thread.sleep(2000);
            alerta.close();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(registroFormCntrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void datosMiLocal(Local l) {
        
    }
    @Override
    public void insertUser(Local l){
        //Datos usuario
        Usuario user = new Usuario();
        user.setUserName(txtUsuario.getText());
        user.setPassword(txtContraseña.getText());
        user.setLocal(l);
        ((UserService)this.getService(1)).insert(user);
    }

    @Override
    public void cargarCosas(String... cosas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
