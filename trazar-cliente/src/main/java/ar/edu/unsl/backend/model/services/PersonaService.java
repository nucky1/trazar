/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.services;

import ar.edu.unsl.backend.model.entities.Persona;
import ar.edu.unsl.backend.model.interfaces.IPersonaOperator;
import ar.edu.unsl.backend.model.persistence.PersonaOperatorRetrofit;
import javafx.concurrent.Task;

/**
 *
 * @author demig
 */
public class PersonaService extends Service {
    private IPersonaOperator operator;
    
    public PersonaService(){
        this.operator = new PersonaOperatorRetrofit(this);
    }

    public void findPersona(String dni) {
        
        Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                operator.findPersona(dni);
                return null;
            }
        };
        javafx.application.Platform.runLater(t);
    }

    public void insertarPersona(Persona p) {
        Task<Void> t = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                operator.save(p);
                return null;
            }
        };
        javafx.application.Platform.runLater(t);
    }
    
    
}
