package ar.edu.unsl.frontend.view_controllers;

import java.net.URL;
import java.util.List;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import ar.edu.unsl.backend.util.CustomAlert;
import javafx.scene.control.Alert.AlertType;
import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;

public class UserFormViewCntlr extends ViewCntlr implements UserServiceSubscriber
{
    // ================================= FXML variables =================================
    @FXML private Label invalidName;
    @FXML private TextField name;
    @FXML private Label invalidUserName;
    @FXML private TextField userName;
    @FXML private Label invalidWebsite;
    @FXML private TextField website;
    @FXML private DatePicker birthdate;
    @FXML private TextField email;
    @FXML private Label invalidEmail;
    @FXML private TextField phone;
    @FXML private Label invalidPhone;
    @FXML private Label invalidLeaderId;

    // ================================= FXML methods =================================
    @FXML
    private void nameCheck()
    {
        if(this.getExpressionChecker().composedName(this.name.getText()))
        {
            this.invalidName.setVisible(false);
        }
        else
        {
            this.invalidName.setText("Invalid format");
            this.invalidName.setVisible(true);
        }  
    }

    @FXML
    private void userNameCheck()
    {
        if(this.getExpressionChecker().userName(this.userName.getText()))
        {
            this.invalidUserName.setVisible(false);
        }
        else
        {
            this.invalidUserName.setText("Invalid format");
            this.invalidUserName.setVisible(true);
        }
    }

    @FXML
    private void emailCheck()
    {
        if(this.getExpressionChecker().isEmail(this.email.getText(), true))
        {
            this.invalidEmail.setVisible(false);
        }
        else
        {
            this.invalidEmail.setText("Invalid format");
            this.invalidEmail.setVisible(true);
        }  
    }

    @FXML
    private void phoneCheck()
    {
        if(this.getExpressionChecker().onlyNumbers(this.phone.getText(), true))
        {
            this.invalidPhone.setVisible(false);
        }
        else
        {
            this.invalidPhone.setText("Invalid format");
            this.invalidPhone.setVisible(true);
        }  
    }

    @FXML
    private void registerButtonPressed() throws Exception
    {
        ((UserService)this.getService(0)).registerUser(this.name.getText(), this.userName.getText(),
                this.website.getText(), this.email.getText(), this.phone.getText());
    }
    // ================================= private methods =================================


    // ================================= protected methods ===============================
    @Override
    protected void manualInitialize()
    {
        
    }

    // ================================= public methods ==================================
    @Override
    public void customInitialize(URL location, ResourceBundle resources) throws Exception
    {
        // TODO Auto-generated method stub

    }

    // ================================= service subscriber methods ==================================

    


    @Override
    public void logueado(String user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciarPantalla(Usuario body) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}