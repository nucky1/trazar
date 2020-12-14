/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.view_controllers;

import ar.edu.unsl.backend.model.entities.Persona;
import ar.edu.unsl.backend.model.services.LocalService;
import ar.edu.unsl.backend.model.services.PersonaService;
import ar.edu.unsl.backend.model.services.RegistroService;
import ar.edu.unsl.frontend.service_subscribers.LocalServiceSubscriber;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author demig
 */
public class localMainViewCntrl extends TableViewCntlr implements LocalServiceSubscriber {
    private int VISITA_TABLE_NUMBER;
    
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
    private TableView<Persona> personasTable;
    @FXML private TableColumn<Persona, Integer> id;
    @FXML private TableColumn<Persona, String> nombre;
    @FXML private TableColumn<Persona, String> apellido;
    @FXML private TableColumn<Persona, String> direccion;
    @FXML private TableColumn<Persona, String> telefono;
    @FXML private TableColumn<Persona, String> dni;

   
// ================================== FXML methods ==================================
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
        ((RegistroService)this.getService(1)).buscarEntreFechas(fromDate.getValue(),toDate.getValue());
    }
// ================================= protected methods ===============================
    @Override
    protected void manualInitialize() {
        try
        {
            fromDate.setValue(LocalDate.now());
            toDate.setValue(LocalDate.now());
            ((RegistroService)this.getService(1)).buscarEntreFechas(fromDate.getValue(),toDate.getValue());  
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    
    // ================================= public methods ==================================
    
    public void init()
    {
        this.manualInitialize();
    }   
    
    @Override
    public void customTableViewInitialize(URL location, ResourceBundle resources) throws Exception {
        this.personasTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        List<TableColumn> columns = new ArrayList<>();
        columns.add(this.id);
        columns.add(this.nombre);
        columns.add(this.apellido);
        columns.add(this.telefono);
        columns.add(this.direccion);
        columns.add(this.dni);
        List<PropertyValueFactory> propertiesValues = new ArrayList<>();
        propertiesValues.add(new PropertyValueFactory<>("id"));
        propertiesValues.add(new PropertyValueFactory<>("nombre"));
        propertiesValues.add(new PropertyValueFactory<>("apellido"));
        propertiesValues.add(new PropertyValueFactory<>("telefono"));
        propertiesValues.add(new PropertyValueFactory<>("direccion"));
        propertiesValues.add(new PropertyValueFactory<>("dni"));
        
        this.registerTable(personasTable);
        this.VISITA_TABLE_NUMBER = 0;
        
        this.registerColumns(columns);
        this.registerPropertiesValues(propertiesValues);
        
        this.txtFiltrar.textProperty().addListener((obs, oldValue, newValue) -> 
        {
            this.filterTable(this.VISITA_TABLE_NUMBER, new Predicate<Persona>()
            {
                @Override
                public boolean test(Persona persona)
                {
                    boolean ret;
                    if (/*newValue.isBlank() ||*/ String.valueOf(persona.getId()).toUpperCase().contains(newValue.toUpperCase()) ||
                        (persona.getNombre()!= null && persona.getNombre().toUpperCase().contains(newValue.toUpperCase())) ||
                        (persona.getApellido()!= null && persona.getApellido().toUpperCase().contains(newValue.toUpperCase())) ||
                        (persona.getTelefono()!= null && persona.getTelefono().toUpperCase().contains(newValue.toUpperCase())) ||
                        (persona.getDireccion() != null && persona.getDireccion().toUpperCase().contains(newValue.toUpperCase())) ||
                        (persona.getTelefono() != null && persona.getTelefono().toUpperCase().contains(newValue.toUpperCase())))
                    {
                        ret = true;
                    }
                    else
                    {
                        ret = false;
                    }
                    return ret;
                }
            });
        });
    }

    

    @Override
    public void showVisitas(List<Persona> lista) {
        this.loadData(this.VISITA_TABLE_NUMBER, lista);
    }

    
}
