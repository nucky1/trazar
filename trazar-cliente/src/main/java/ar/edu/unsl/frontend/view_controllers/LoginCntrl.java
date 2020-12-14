/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.services.PersonaService;
import ar.edu.unsl.backend.model.services.RegistroService;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.backend.util.Statics;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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
    private void sendEmail(ActionEvent event) {
        System.out.println("No hago nada.");
        System.out.println("No ves que estoy chiquito!!");
    
    }
// ================================= protected methods ===============================
    
     @Override
    protected void manualInitialize() {
        
    }

// ================================= public methods ==================================
    public void init(Stage stage)
    {
         this.stage = stage;
        this.manualInitialize();
    }


   
    @Override
    public void customInitialize(URL location, ResourceBundle resources) throws Exception {
        
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
        Statics.getUser().setId_local(body.getId_local());
        this.stage.close();
        this.createStage("Local Main", "localMain", new PersonaService(), new RegistroService()).getStage().show();
    }
    
}
