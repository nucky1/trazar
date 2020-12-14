/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.interfaces;

import ar.edu.unsl.backend.model.entities.Persona;

/**
 *
 * @author demig
 */
public interface IPersonaOperator {

    public void findPersona(String dni);

    public void save(Persona p);
    
}
