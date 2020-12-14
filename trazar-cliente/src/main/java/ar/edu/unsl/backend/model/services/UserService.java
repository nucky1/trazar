package ar.edu.unsl.backend.model.services;

import java.util.List;
import ar.edu.unsl.backend.util.CustomAlert;
import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.interfaces.IUserOperator;
import ar.edu.unsl.backend.model.persistence.UserOperatorRetrofit;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;
import com.sun.jndi.dns.DnsContextFactory;
import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.concurrent.Task;

public class UserService extends Service
{
    private IUserOperator operator;

    public UserService()
    {
        this.operator = new UserOperatorRetrofit(this);
        //this.operator = new UserOperatorApache(this);
    }

    private boolean allFieldsOk(String name, String userName, String website, String email, String phone)
    {
        boolean ret = false;

        if(this.getExpressionChecker().composedName(name) && this.getExpressionChecker().userName(userName) &&
            this.getExpressionChecker().isEmail(email, false) && this.getExpressionChecker().onlyNumbers(phone, true))
            {
                ret = true;
            }

        return ret;
    }
    
    /*
    public void searchUsers() throws Exception
    {
        //CustomAlert customAlert = this.getServiceSubscriber().showProcessIsWorking("Wait a moment while the process is done.");
        
        Task<Void> task = new Task<>()
        {
            @Override
            protected Void call() throws Exception
            {
                List<User> users = operator.findAll();

                //getServiceSubscriber().closeProcessIsWorking(customAlert);
                if(users != null)
                {
                    ((UserServiceSubscriber)getServiceSubscriber()).showUsers(users);
                }
                else
                {
                    getServiceSubscriber().showNoResult("No users registered");
                }

                return null;
            }
        };
        
        Platform.runLater(task);
    }
    */

    
    public void searchUsers() throws Exception
    {
        List<User> users = this.operator.findAll();
        if(users != null)
        {
            //((UserServiceSubscriber)this.getServiceSubscriber()).showUsers(users);
        }
    }
    
    

    public void searchUser(String id) throws Exception
    {
        if(this.getExpressionChecker().onlyNumbers(id, false))
        {
            User user = this.operator.find(Integer.parseInt(id));
            
            if(user != null)
            {
                //((UserServiceSubscriber)this.getServiceSubscriber()).showUser(user);
            }
            
        }
    }

    public void registerUser(String name, String userName, String website, String email, String phone) throws Exception
    {
        CustomAlert customAlert = this.getServiceSubscriber().showProcessIsWorking("Wait a moment while the process is done.");

        if(allFieldsOk(name, userName, website, email, phone))
        {
            User newUser = new User();
            newUser.setName(name);
            newUser.setUserName(userName);
            newUser.setWebsite(website);
            newUser.setEmail(email);
            newUser.setPhone(phone);
/*
            Task<Void> task = new Task<>()
            {
                @Override
                protected Void call() throws Exception
                {
                    User user = operator.insert(newUser);

                    getServiceSubscriber().closeProcessIsWorking(customAlert);

                    if(user != null)
                    {
                        ((UserServiceSubscriber)getServiceSubscriber()).showUser(user);
                    }

                    return null;
                }
            };
            Platform.runLater(task);*/
        }
        else
        {
            getServiceSubscriber().closeProcessIsWorking(customAlert);
            this.getServiceSubscriber().showError("Some fields are in the wrong format or need to be completed");
        }
	}

    public void login(String username, String pass) throws Exception {
        CustomAlert customAlert = this.getServiceSubscriber().showProcessIsWorking("Wait a moment while the process is done.");
        
        Usuario user = new Usuario();
        user.setPassword(pass);
        user.setUserName(username);
        Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Usuario usuario = operator.login(user);
                getServiceSubscriber().closeProcessIsWorking(customAlert);
                return null;
            }
        };
        javafx.application.Platform.runLater(t);
        /*
        Usuario usuario = operator.login(user);
        getServiceSubscriber().closeProcessIsWorking(customAlert);
        if(usuario != null){
            ((UserServiceSubscriber)getServiceSubscriber()).logueado(usuario);
        }else{
            ((UserServiceSubscriber)getServiceSubscriber()).logueado(usuario);
            getServiceSubscriber().showError("Usuario y/o contraseña incorrecto.");
        }*/
    }

    public void pedirDatos(String username, String token) {
        Usuario user = new Usuario();
        user.setUserName(username);
        user.setToken(token);
        Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                operator.pedirDatos(user);
                return null;
            }
        };
        javafx.application.Platform.runLater(t);
    }
}