package com.mycompany.trazar.cliente;

import ar.edu.unsl.backend.model.services.LocalService;
import java.net.URL;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.backend.model.services.Service;
import ar.edu.unsl.backend.util.ExpressionChecker;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.frontend.view_controllers.LoginCntrl;
import ar.edu.unsl.frontend.view_controllers.ViewCntlr;
import java.net.MalformedURLException;

public class App extends Application
{
    public static final String GUIs_LOCATION = App.class.getResource("")+ "../../../../frontend/GUIs/";
    public static final String FILE_EXTENSION = ".fxml";
    //public static final String API_HOSTNAME = "http://localhost:8080";
    public static final String API_HOSTNAME = "http://35.184.148.44:8080";

    @Override
    public void start(final Stage stage) throws MalformedURLException, IOException
    {
            String fileName = "login";
            FXMLLoader fxmlLoader = new FXMLLoader(new URL(GUIs_LOCATION + fileName + FILE_EXTENSION));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Login");
            //stage.initStyle(StageStyle.UNDECORATED);
            ViewCntlr viewCtrller = fxmlLoader.getController();
            
            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent e)
                {
                    new CustomAlert(AlertType.INFORMATION, "EXIT", "¿Estas seguro que desea salir?")
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
            
            Service userService = new UserService();
            Service localService = new LocalService();
            viewCtrller.addService(userService);
            viewCtrller.addService(localService);
            userService.setServiceSubscriber(viewCtrller);
            localService.setServiceSubscriber(viewCtrller);
            userService.setExpressionChecker(ExpressionChecker.getExpressionChecker());
            
            stage.show();
            ((LoginCntrl)viewCtrller).init(stage);
    }

    public static void main( String[] args )
    {
        try{
        System.out.println(GUIs_LOCATION);
        launch();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
