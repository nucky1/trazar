/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.service_subscribers;

import ar.edu.unsl.backend.model.entities.Persona;
import ar.edu.unsl.backend.model.entities.Registro;
import java.util.List;

/**
 *
 * @author demig
 */
public interface PersonaServiceSubscriber {
    void showPersona(Persona persona);
    void didntFind();
    void GuardarVisita(Persona persona);

    public void showExito(Registro body);

    public void error();
}
