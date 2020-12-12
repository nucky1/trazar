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
    @GET(UserOperatorRetrofit.RESOURCE)
    Call<List<Local>> findAll();

    @GET(UserOperatorRetrofit.SINGLE_RESOURCE)
    Call<Local> find(@Path(UserOperatorRetrofit.ID) Integer id);

    @POST(UserOperatorRetrofit.RESOURCE)
    Call<Local> postUser(@Body Local local);
}
