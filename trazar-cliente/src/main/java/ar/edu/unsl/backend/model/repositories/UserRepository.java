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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserRepository
{
    
    @POST("/trazar/login")
    Call<Response<Void>> login(@Body JsonObject login);
    
    @POST("/trazar/usuario")
    Call<Usuario> insertar(@Body Usuario user);
    
    @GET("/trazar/usuario?")
    Call<Usuario> pedirDatos(@Query("userName") String username,@Header("Authorization") String auth);
    
    @PUT("/trazar/usuario/{id}")
   Call<Usuario> update(@Path("id") Integer id,@Body Usuario user,@Header("Authorization") String token);
    
    @POST("/instance206960/message?")
    Call<Response<String>> enviar(@Query("token") String token,@Body JsonObject body);
}