/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.repositories;

import ar.edu.unsl.backend.model.entities.Persona;
import ar.edu.unsl.backend.model.entities.Registro;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * @author demig
 */
public interface RegistroRepository {

    @GET("/trazar/registro/local/{id}")
    Call<List<Persona>> getRegistros(@Path("id") Integer id,
                               @Query("fechaDesde") String fechaDesde,
                               @Query("fechaHasta") String fechaHasta,
                               @Header("Authorization") String auth);

    @POST("/trazar/registro")
    Call<Registro> createRegistro(@Body Registro registro,@Header("Authorization") String auth);
}
