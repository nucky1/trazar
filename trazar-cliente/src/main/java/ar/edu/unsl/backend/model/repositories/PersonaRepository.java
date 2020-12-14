/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.repositories;

/**
 *
 * @author demig
 */
public interface PersonaRepository {

    @POST("/persona")
    Call<Persona> createPersona(@Body Persona persona,@Header("Authorization") String auth);

    @GET("/persona?")
    Call<Persona> getPersonaByDni(@Query("dni") String dni,@Header("Authorization") String auth);
}
