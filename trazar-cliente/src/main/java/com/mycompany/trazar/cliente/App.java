package com.mycompany.trazar.cliente;

import java.net.URL;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;
//import javafx.stage.StageStyle;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.backend.model.services.Service;
import ar.edu.unsl.backend.util.ExpressionChecker;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.frontend.view_controllers.ViewCntlr;
import ar.edu.unsl.frontend.view_controllers.MainMenuViewCntlr;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application
{
    public static final String GUIs_LOCATION = App.class.getResource("")+ "../../../../frontend/GUIs/";
    public static final String FILE_EXTENSION = ".fxml";
    public static final String API_HOSTNAME = "http://jsonplaceholder.typicode.com";

    @Override
    public void start(final Stage stage) throws MalformedURLException, IOException
    {
            String fileName = "login";
            FXMLLoader fxmlLoader = new FXMLLoader(new URL(GUIs_LOCATION + fileName + FILE_EXTENSION));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Main Menu");
            //stage.initStyle(StageStyle.UNDECORATED);
            ViewCntlr viewCtrller = fxmlLoader.getController();
            
            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent e)
                {
                    new CustomAlert(AlertType.INFORMATION, "EXIT", "Do you want to close this system?")
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
            viewCtrller.addService(userService);
            userService.setServiceSubscriber(viewCtrller);
            userService.setExpressionChecker(ExpressionChecker.getExpressionChecker());
            
            stage.show();
            
            ((MainMenuViewCntlr)viewCtrller).init();
    }

    public static void main( String[] args )
    {
        try{
        System.out.println(GUIs_LOCATION);
            
        }catch(Exception e){
            
        }
        launch();
    }
}
