/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.repositories;

import ar.edu.unsl.backend.model.entities.Local;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 * @author demig
 */
public interface LocalRepository {

    @PUT("/local/{id}")
    Call<Local> updateLocal(@Path("id") Integer id,@Body Local local,@Header("Authorization") String auth);

    @GET("/local/{id}")
    Call<Local> find(@Path("id") Integer id,@Header("Authorization") String auth);

}
