/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.util;

import ar.edu.unsl.backend.model.entities.Usuario;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author demig
 */
public class Statics {
    private static Usuario user = null;
    static SimpleDateFormat plantilla = new SimpleDateFormat("yyyy-MM-dd");
    
    public static String dateFormat(LocalDate date, int hora, int min){
        String fecha = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        fecha += " "+hora+":"+min+":00.0";
        return fecha;
//Locale locale = new Locale("us", "US");
        
        //DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        //return plantilla.format(date);
    }
    public static Usuario getUser() {
        return user;
    }

    public static void setUser(Usuario user) {
        Statics.user = user;
    }
    
}
