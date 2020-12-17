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
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.backend.util.Statics;
import ar.edu.unsl.frontend.service_subscribers.LocalServiceSubscriber;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;

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
    @FXML
    private Spinner<?> horaFrom;
    @FXML
    private Spinner<?> minFrom;
    @FXML
    private Spinner<?> horaTo;
    @FXML
    private Spinner<?> minTo;

   
// ================================== FXML methods ==================================
    private void controlHF(KeyEvent event) {
        String s = event.getCharacter();
        if(s.length()!= 1)
            event.consume();
        char c = s.charAt(0);
        if((c<'0'||c>'9'))
            event.consume();
    }
    @FXML
    private void controlHF(InputMethodEvent event) {
        System.out.println("asdasdasdasd");
    }
    @FXML
    private void controlMinF(KeyEvent event) {
        String s = event.getCharacter();
        if(s.length()!= 1)
            event.consume();
        char c = s.charAt(0);
        if((c<'0'||c>'9'))
            event.consume();
    }

    @FXML
    private void controlHT(KeyEvent event) {
        String s = event.getCharacter();
        if(s.length()!= 1)
            event.consume();
        char c = s.charAt(0);
        if((c<'0'||c>'9'))
            event.consume();
    }

    @FXML
    private void controlMinT(KeyEvent event) {
        String s = event.getCharacter();
        if(s.length()!= 1)
            event.consume();
        char c = s.charAt(0);
        if((c<'0'||c>'9'))
            event.consume();
    }
    @FXML
    private void openRegistroForm(ActionEvent event) {
        ViewCntlr v = this.createStage("Registrar visita", "registroForm", new PersonaService(),new RegistroService());
        v.manualInitialize();
        v.getStage().show();
    }

    @FXML
    private void modifylocal(ActionEvent event) {
        ViewCntlr v = this.createStage("Cambiar mis datos", "registroLocal", new LocalService(), new UserService());
        v.manualInitialize();
        v.getStage().show();
    }

    @FXML
    private void buscarVisitas(ActionEvent event) {
        String f= Statics.dateFormat(fromDate.getValue(), Integer.parseInt(horaFrom.getEditor().getText()),Integer.parseInt(minFrom.getEditor().getText()));
        String t= Statics.dateFormat(toDate.getValue(), Integer.parseInt(horaTo.getEditor().getText()),Integer.parseInt(minTo.getEditor().getText()) );
        System.out.println(f);
        System.out.println(t);
        ((RegistroService)this.getService(1)).buscarEntreFechas(f,t);
    }
// ================================= protected methods ===============================
    @Override
    protected void manualInitialize() {
        try
        {
            String f= Statics.dateFormat(fromDate.getValue(), 00,00);
            String t= Statics.dateFormat(toDate.getValue(), 23,59 );
            ((RegistroService)this.getService(1)).buscarEntreFechas(f,t);
            this.getStage().setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent e)
                {
                    volverLogin();
                }
        });
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    // ================================= private methods ==================================
    private void volverLogin(){
        ViewCntlr v = this.createStage("Login", "login", new UserService());
        v.manualInitialize();
        v.getStage().setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent e)
                {
                    new CustomAlert(Alert.AlertType.INFORMATION, "EXIT", "¿Estas seguro que desea salir?")
                            .customShow().ifPresent(response ->
                            {
                                if(response == ButtonType.OK)
                                {
                                    Platform.exit();
                                    System.exit(0);
                                }
                            });
                }
            });
        v.getStage().show();
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
        Spinner aux = new Spinner(00, 23, 13,1);
        Spinner aux2 = new Spinner(00, 23, 13,1);
        Spinner aux3 = new Spinner(00, 59, 00,1);
        Spinner aux4 = new Spinner(00, 59, 00,1);
        this.horaFrom.setValueFactory(aux.getValueFactory());
        this.horaFrom.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            int ent = 0;
            try{
                ent = Integer.parseInt(newValue);
                if(ent > 23 || ent < 0)
                    this.horaFrom.getEditor().setText(oldValue);
                else{
                    if(ent == 0){
                        this.horaFrom.getEditor().setText("00");
                    }
                }
            }catch(Exception e){
                this.horaFrom.getEditor().setText(oldValue);
            }
        });
        this.horaTo.setValueFactory(aux2.getValueFactory());
        this.horaTo.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            int ent = 0;
            try{
                ent = Integer.parseInt(newValue);
                if(ent > 23 || ent < 0)
                    this.horaTo.getEditor().setText(oldValue);
                else{
                    if(ent == 0){
                        this.horaTo.getEditor().setText("00");
                    }
                }
            }catch(Exception e){
                this.horaTo.getEditor().setText(oldValue);
            }
        });
        this.minFrom.setValueFactory(aux3.getValueFactory());
        this.minFrom.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            int ent = 0;
            try{
                ent = Integer.parseInt(newValue);
                if(ent > 59 || ent < 0)
                    this.minFrom.getEditor().setText(oldValue);
                else{
                    if(ent == 0){
                        this.minFrom.getEditor().setText("00");
                    }
                }
            }catch(Exception e){
                this.minFrom.getEditor().setText(oldValue);
            }
        });
        this.minTo.setValueFactory(aux4.getValueFactory());
        this.minTo.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            int ent = 0;
            try{
                ent = Integer.parseInt(newValue);
                if(ent > 50 || ent < 0){
                    this.minTo.getEditor().setText(oldValue);
                }else{
                    if(ent == 0){
                        this.minTo.getEditor().setText("00");
                    }
                }
            }catch(Exception e){
                this.minTo.getEditor().setText(oldValue);
            }
        });
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());
    }

    

    @Override
    public void showVisitas(List<Persona> lista) {
        this.loadData(this.VISITA_TABLE_NUMBER, lista);
    }

    @Override
    public void cargarCosas(String... cosas) {
        String f= Statics.dateFormat(fromDate.getValue(), Integer.parseInt(horaFrom.getEditor().getText()),Integer.parseInt(minFrom.getEditor().getText()));
        String t= Statics.dateFormat(toDate.getValue(), Integer.parseInt(horaTo.getEditor().getText()),Integer.parseInt(minTo.getEditor().getText()) );
        ((RegistroService)this.getService(1)).buscarEntreFechas(f,t);
    }
    
}
