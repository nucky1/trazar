/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.service_subscribers;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.entities.Persona;
import ar.edu.unsl.backend.model.entities.Usuario;
import java.util.List;

/**
 *
 * @author demig
 */
public interface AdminServiceSubscriber {
    void showUser(Usuario usuario);
    void showLocales(List<Local> Locales);
    void showUsers(List<Usuario> Locales);
    void showPersona(Persona persona);
    void showPersonas(List<Persona> personas);
}
