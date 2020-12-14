package ar.edu.unsl.backend.model.repositories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
//import retrofit2.http.Query;
import ar.edu.unsl.backend.model.entities.Usuario;
import com.google.gson.JsonObject;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserRepository
{
    
    @POST("/login")
    Call<Response<Void>> login(@Body JsonObject login);

    @GET("/usuario?")
    Call<Usuario> pedirDatos(@Query("userName") String username,@Header("Authorization") String auth);
}