/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.persistence.wppRetrofit;
import ar.edu.unsl.backend.model.services.LocalService;
import ar.edu.unsl.backend.model.services.PersonaService;
import ar.edu.unsl.backend.model.services.RegistroService;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.backend.util.Statics;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

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
        String res = JOptionPane.showInputDialog(null, "Ingrese su número de CUIT", "Recuperar contraseña");
        if(this.getExpressionChecker().onlyNumbers(res, false)){
           ((LocalService)this.getService(0)).recuperarUserAndPass(res);
        }else{
            CustomAlert alerta = new CustomAlert(Alert.AlertType.ERROR, "Error", "El CUIT ingresado no es valido");
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
    private void registrarse(ActionEvent event) {
        ViewCntlr v = this.createStage("Registrarse", "registrarse", new LocalService(), new UserService());
        v.manualInitialize();
        v.getStage().show();
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


    @Override
    public void notificado() {
        CustomAlert alerta = new CustomAlert(Alert.AlertType.INFORMATION, "OK", "Se envio una nueva contraseña a su WhatsApp");
        try {
            alerta.show();
            Thread.sleep(2000);
            alerta.close();

        } catch (InterruptedException ex) {
            Logger.getLogger(registroFormCntrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void noNotificado() {
        CustomAlert alerta = new CustomAlert(Alert.AlertType.ERROR, "Error", "No se pudo recuperar la contraseña");
        try {
            alerta.show();
            Thread.sleep(2000);
            alerta.close();

        } catch (InterruptedException ex) {
            Logger.getLogger(registroFormCntrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificar(Usuario body) {
        new wppRetrofit(this).enviar(body.getLocal().getTelefono(), "1234",body.getUserName());
    }
}
