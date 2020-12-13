/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.util;

import ar.edu.unsl.backend.model.entities.Usuario;

/**
 *
 * @author demig
 */
public class Statics {
    private static Usuario user = null;

    public static Usuario getUser() {
        return user;
    }

    public static void setUser(Usuario user) {
        Statics.user = user;
    }
    
}
