/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.services;

import ar.edu.unsl.backend.model.interfaces.IPersonaOperator;
import ar.edu.unsl.backend.model.persistence.PersonaOperatorRetrofit;

/**
 *
 * @author demig
 */
public class PersonaService extends Service {
    private IPersonaOperator operator;
    
    public PersonaService(){
        this.operator = new PersonaOperatorRetrofit(this);
    }
}
