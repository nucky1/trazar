/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.repositories;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.persistence.UserOperatorRetrofit;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *
 * @author demig
 */
public interface LocalRepository {

    @GET("/registro/local/{id}")
    Call<List<Registro>> getRegistros(@Path Integer id,
                               @Query("fechaDesde") Date fechaDesde,
                               @Query("fechaHasta") Date fechaHasta,
                               @Header("Authorization") String auth);

    @POST("/registro")
    Call<Resgistro> createRegistro(@Body Registro registro,@Header("Authorization") String auth);
}
