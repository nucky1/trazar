/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.services.UserService;
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

/**
 * FXML Controller class
 *
 * @author demig
 */
public class LoginCntrl extends ViewCntlr implements UserServiceSubscriber {
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
    public void init()
    {
        this.manualInitialize();
    }
    @Override
    public void showUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showUsers(List<User> users) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    @Override
    public void customInitialize(URL location, ResourceBundle resources) throws Exception {
        
    }

    @Override
    public void logueado(Usuario user) {
        if(user.getTipo().equals("LOCAL")){
            this.createStage("Local Main", "localMain", new UserService()).getStage().show();
        }else{
           this.createStage("Persona Main", "personaMain", new UserService()).getStage().show(); 
        }
    }
    
}
