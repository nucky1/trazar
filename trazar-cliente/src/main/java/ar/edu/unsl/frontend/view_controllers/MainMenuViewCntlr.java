package ar.edu.unsl.frontend.view_controllers;

import java.net.URL;
import java.util.List;
import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.services.UserService;
import javafx.scene.control.cell.PropertyValueFactory;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;

public class MainMenuViewCntlr extends TableViewCntlr implements UserServiceSubscriber
{
    private int USERS_TABLE_NUMBER;

    // ================================= FXML variables =================================
    @FXML private TableView<User> users;

    @FXML private TableColumn<User, Integer> id;
    @FXML private TableColumn<User, String> name;
    @FXML private TableColumn<User, String> userName;
    @FXML private TableColumn<User, String> email;
    @FXML private TableColumn<User, String> phone;
    @FXML private TableColumn<User, String> website;

    @FXML private TextField searchField;
    @FXML private TextField searchUser;
    @FXML private ImageView catButton;

    // ================================== FXML methods ==================================
    @FXML
    private void openUserDataButtonPressed()
    {
        if(this.users.getSelectionModel().getSelectedIndex() > -1)
        {
            this.showUser(this.users.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void selectUser(MouseEvent event)
    {
        if(event.getClickCount() == 2)
        {
            this.openUserDataButtonPressed();
        }
    }

    @FXML
    private void newUserButtonPressed()
    {
        this.createStage("User registration form", "userForm", new UserService()).getStage().show();
    }

    @FXML
    private void searchUserButtonPressed()
    {
        try
        {
            ((UserService)this.getService(0)).searchUser(this.searchUser.getText());   
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    // ================================= private methods =================================


    // ================================= protected methods ===============================
    @Override
    protected void manualInitialize()
    {
        try
        {
            ((UserService)this.getService(0)).searchUsers();   
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
    public void customTableViewInitialize(URL location, ResourceBundle resources) throws Exception
    {
        this.users.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        List<TableColumn> columns = new ArrayList<>();
        columns.add(this.id);
        columns.add(this.name);
        columns.add(this.userName);
        columns.add(this.email);
        columns.add(this.phone);
        columns.add(this.website);

        List<PropertyValueFactory> propertiesValues = new ArrayList<>();
        propertiesValues.add(new PropertyValueFactory<>("id"));
        propertiesValues.add(new PropertyValueFactory<>("name"));
        propertiesValues.add(new PropertyValueFactory<>("userName"));
        propertiesValues.add(new PropertyValueFactory<>("email"));
        propertiesValues.add(new PropertyValueFactory<>("phone"));
        propertiesValues.add(new PropertyValueFactory<>("website"));


        this.registerTable(this.users);
        this.USERS_TABLE_NUMBER = 0; // because is the first table registered.
        
        this.registerColumns(columns);
        this.registerPropertiesValues(propertiesValues);


        //Setting the filter binding to the text field
        
        this.searchField.textProperty().addListener((obs, oldValue, newValue) -> 
        {
            this.filterTable(this.USERS_TABLE_NUMBER, new Predicate<User>()
            {
                @Override
                public boolean test(User user)
                {
                    boolean ret;
                    if (/*newValue.isBlank() ||*/ String.valueOf(user.getId()).toUpperCase().contains(newValue.toUpperCase()) ||
                        (user.getName() != null && user.getName().toUpperCase().contains(newValue.toUpperCase())) ||
                        (user.getUserName() != null && user.getUserName().toUpperCase().contains(newValue.toUpperCase())) ||
                        (user.getEmail() != null && user.getEmail().toUpperCase().contains(newValue.toUpperCase())) ||
                        (user.getPhone() != null && user.getPhone().toUpperCase().contains(newValue.toUpperCase())) ||
                        (user.getWebsite() != null && user.getWebsite().toUpperCase().contains(newValue.toUpperCase())))
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
    public void showUser(User user)
    {
        new CustomAlert(AlertType.INFORMATION, "User registered", "User info:\n\n"+
        "id: "+user.getId()+"\nname: "+user.getName()+"\nuser name: "+user.getUserName()+"\ne-mail: "+user.getEmail()+
        "\nphone: "+user.getPhone()+"\nwebsite: "+user.getWebsite()).customShow();
    }

    @Override
    public void showUsers(List<User> users)
    {
        this.loadData(this.USERS_TABLE_NUMBER, users);
    }

    // ================================= service subscriber methods =================================

    @Override
    public void logueado(Usuario user) {
        
    }
}