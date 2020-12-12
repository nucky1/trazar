/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.services.LocalService;
import ar.edu.unsl.frontend.service_subscribers.LocalServiceSubscriber;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author demig
 */
public class localFormViewCntrl extends ViewCntlr implements LocalServiceSubscriber {

    @FXML
    private Button registerVisitButton;
    @FXML
    private Button ModifyButton;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TextField searchText;
    @FXML
    private TableView<?> personasTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifylocal(ActionEvent event) {
    }

    @Override
    protected void manualInitialize() {
        try
        {
            ((LocalService)this.getService(0)).searchVisitas();   
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public void customInitialize(URL location, ResourceBundle resources) throws Exception {
        
    }

    @Override
    public void showLocal(Local local) {
        
    }

    @Override
    public void showLocales(List<Local> Locales) {
        
    }
    
}
