/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.services;

import ar.edu.unsl.backend.model.entities.Registro;
import ar.edu.unsl.backend.model.interfaces.IRegistroOperator;
import ar.edu.unsl.backend.model.persistence.RegistroOperatorRetrofit;
import ar.edu.unsl.backend.util.Statics;
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
    public void buscarEntreFechas(String fechain, String fechaOut) {
        Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                operator.consultarVisitas(fechain, fechaOut);
                return null;
            }
        };
        javafx.application.Platform.runLater(t);
    }

    public void insertarRegistro(Registro r) {
        Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                operator.insertarRegistro(r);
                return null;
            }
        };
        javafx.application.Platform.runLater(t);
    }
    
}
