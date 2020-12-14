/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.services;

import ar.edu.unsl.backend.model.entities.Usuario;
import java.time.LocalDate;
import javafx.concurrent.Task;

/**
 *
 * @author demig
 */
public class RegistroService extends Service{
    private IRegistroOperator operator;

    public RegistroService()
    {
        this.operator = new RegistroOperatorRetrofit(this);
        //this.operator = new UserOperatorApache(this);
    }
    public void buscarEntreFechas(LocalDate value, LocalDate value0) {
        Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Usuario usuario = operator.login(user);
                getServiceSubscriber().closeProcessIsWorking(customAlert);
                return null;
            }
        };
        javafx.application.Platform.runLater(t);
    }
    
}
