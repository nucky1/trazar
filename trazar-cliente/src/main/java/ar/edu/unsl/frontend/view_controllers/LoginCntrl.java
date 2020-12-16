/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.services.LocalService;
import ar.edu.unsl.backend.model.services.PersonaService;
import ar.edu.unsl.backend.model.services.RegistroService;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.backend.util.Statics;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author demig
 */
public class LoginCntrl extends ViewCntlr implements UserServiceSubscriber {
    private Stage stage;
// ================================= FXML variables =================================
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button iniciar;
    @FXML
    private Hyperlink forgetPass;
    @FXML
    private Hyperlink registrarse;
    @FXML
    private Label alertError;
    
// ================================== FXML methods ==================================

    @FXML
    private void loguear(ActionEvent event) {
        try{
            ((UserService)this.getService(0)).login(this.username.getText(),this.password.getText());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void sendWpp(ActionEvent event) {
        
    }

    @FXML
    private void registrarse(ActionEvent event) {
        this.createStage("Registrarse", "registrarse", new LocalService(), new UserService()).getStage().show();
    }
    
// ================================= protected methods ===============================
    
     @Override
    protected void manualInitialize() {
        this.stage = this.getStage();
    }

// ================================= public methods ==================================
    public void init(Stage stage)
    {
        this.stage = stage;
    }


   
    @Override
    public void customInitialize(URL location, ResourceBundle resources) throws Exception {
        username.textProperty().addListener((observable) -> {
            alertError.setOpacity(0);
        });
    }
    @Override
    public void logueado(String token) {
        Usuario user = new Usuario();
        user.setToken(token);
        Statics.setUser(user);
        ((UserService)this.getService(0)).pedirDatos(username.getText(),token);
    }   

    @Override
    public void iniciarPantalla(Usuario body) {
        Statics.getUser().setLocal(body.getLocal());
        Statics.getUser().setId(body.getId());
        this.stage.close();
        ViewCntlr s = this.createStage("Local Main", "localMain", new PersonaService(), new RegistroService());
        s.manualInitialize();
        s.getStage().show();
    }

    @Override
    public void usuarioInexistente() {
        alertError.setOpacity(1);
    }
    @Override
    public void cargarCosas(String ... cosas){
        username.setText(cosas[0]);
        password.setText(cosas[1]);
    }
}
