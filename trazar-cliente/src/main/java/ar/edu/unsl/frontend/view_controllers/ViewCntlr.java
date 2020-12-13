package ar.edu.unsl.frontend.view_controllers;

import java.net.URL;
import com.mycompany.trazar.cliente.App;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import ar.edu.unsl.backend.util.CustomAlert;
import javafx.scene.control.Alert.AlertType;
import ar.edu.unsl.backend.util.ExpressionChecker;
import ar.edu.unsl.backend.model.services.Service;
import ar.edu.unsl.frontend.service_subscribers.ServiceSubscriber;
import javafx.scene.layout.VBox;

public abstract class ViewCntlr implements Initializable, ServiceSubscriber
{
    private Stage stage;
    private List<Service> services;
    private ViewCntlr prevViewCntlr;
    private ExpressionChecker expressionChecker;

    // ================================= Getters && setters =================================
    protected void setPrevViewCntlr(ViewCntlr prevViewCntlr)
    {
        this.prevViewCntlr = prevViewCntlr;
    }

    protected ViewCntlr getPrevViewCntlr()
    {
        return this.prevViewCntlr;
    }

    protected void setStage(Stage stage)
    {
        this.stage = stage;
    }

    protected Stage getStage()
    {
        return this.stage;
    }

    // ==================================== FXML methods ====================================
    @FXML
    private void backButtonPressed()
    {
        this.stage.close();
    }

    // =================================== private methods ==================================


    // ================================= protected methods ==================================
    /**
     * This method is supposed to be used when some code needs
     * to be separated from the initialize (javafx.fxml.Initializable)
     * that execute automatically.
     */
    protected abstract void manualInitialize();

    protected ViewCntlr createStage(String title, String sceneName, Service ... services)
    {
        String fileName = sceneName;
        FXMLLoader fxmlLoader = null;
        Scene scene = null;
        try
        {
            fxmlLoader = new FXMLLoader(new URL(App.GUIs_LOCATION+fileName+App.FILE_EXTENSION));
            VBox root = new VBox();
            fxmlLoader.setRoot(root);
            scene = new Scene(fxmlLoader.load());
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        
        ViewCntlr viewCtrller = fxmlLoader.getController();
        viewCtrller.setStage(new Stage());
        viewCtrller.getStage().setResizable(false);
        viewCtrller.getStage().setScene(scene);
        viewCtrller.getStage().setTitle(title);
        viewCtrller.setPrevViewCntlr(this);
        this.services.get(0).toString();
        for(Service service: services)
        {
            viewCtrller.addService(service);
            service.setServiceSubscriber(viewCtrller);
            service.setExpressionChecker(this.expressionChecker);
        }

        return viewCtrller;
    }

    protected ExpressionChecker getExpressionChecker()
    {
        return this.expressionChecker;
    }

    protected void setExpressionChecker(ExpressionChecker expressionChecker)
    {
        this.expressionChecker = expressionChecker;
    }

    // =================================== public methods ===================================
    /**
     * This method will be called automatically before the view is displayed.
     * Use this method to initialize the controller.
     */
    public abstract void customInitialize(URL location, ResourceBundle resources) throws Exception;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try 
        {
            this.services = new ArrayList<>();
            this.expressionChecker = ExpressionChecker.getExpressionChecker();
            this.customInitialize(url, rb);    
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }   
    }

    // ================================= service subscriber methods =================================
    @Override
    public Service getService(int location)
    {
        return this.services.get(location);
    }

    @Override
    public int addService(Service service)
    {
        if(this.services == null)
            this.services = new ArrayList<>();
        this.services.add(service);
        return this.services.size()-1;
    }

    @Override
    public CustomAlert showProcessIsWorking(String message)
    {
        CustomAlert customAlert = new CustomAlert(AlertType.NONE, "PROCESANDO", message);
        customAlert.customShow();
        return customAlert; 
    }

    @Override
    public void closeProcessIsWorking(CustomAlert customAlert)
    {
        customAlert.customClose();
    }

    @Override
    public void showSucces(String message)
    {
        new CustomAlert(AlertType.INFORMATION, "EXITO", message).customShow();
    }

    @Override
    public void showError(String message)
    {
        new CustomAlert(AlertType.ERROR, "ERROR", message).customShow();
    }

    @Override
    public void showError(String message, String description, Exception exception)
    {
        new CustomAlert(AlertType.ERROR, "ERROR", message, description, exception).customShow();
    }

    @Override
    public void showNoResult(String message)
    {
        
    }
}