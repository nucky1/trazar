/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.frontend.service_subscribers;

import ar.edu.unsl.backend.model.entities.Local;

/**
 *
 * @author demig
 */
public interface RegistrarseServiceSubiscriber {
    
    void usuarioLibre();
    void usuarioOcupado();
    void exito();
    void error();
    void datosMiLocal(Local l);
    void insertUser(Local l);
}
