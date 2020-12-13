/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.services.LocalService;
import ar.edu.unsl.backend.model.services.PersonaService;
import ar.edu.unsl.backend.model.services.RegistroService;
import ar.edu.unsl.frontend.service_subscribers.LocalServiceSubscriber;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author demig
 */
public class localMainViewCntrl extends TableViewCntlr implements LocalServiceSubscriber {

    @FXML
    private Button registerVisitButton;
    @FXML
    private Button btnModify;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TextField txtFiltrar;
    @FXML
    private Button btnBuscar;
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
    private void openRegistroForm(ActionEvent event) {
        this.createStage("Registrar visita", "registroForm", new PersonaService(),new RegistroService()).getStage().show();
    }

    @FXML
    private void modifylocal(ActionEvent event) {
        this.createStage("Cambiar mis datos", "registroLocal", new LocalService()).getStage().show();
    }

    @FXML
    private void buscarVisitas(ActionEvent event) {
        
        
    }

    @Override
    public void customTableViewInitialize(URL location, ResourceBundle resources) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void manualInitialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showLocal(Local local) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showLocales(List<Local> Locales) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
