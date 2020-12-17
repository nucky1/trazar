/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.repositories;

import ar.edu.unsl.backend.model.entities.Persona;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 * @author demig
 */
public interface PersonaRepository {

    @POST("/trazar/persona")
    Call<Persona> createPersona(@Body Persona persona,@Header("Authorization") String auth);

    @GET("/trazar/persona?")
    Call<Persona> getPersonaByDni(@Query("dni") String dni,@Header("Authorization") String auth);
}
