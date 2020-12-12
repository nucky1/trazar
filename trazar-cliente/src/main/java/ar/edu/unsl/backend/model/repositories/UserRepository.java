package ar.edu.unsl.backend.model.repositories;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Body;
//import retrofit2.http.Query;
import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.persistence.UserOperatorRetrofit;

public interface UserRepository
{
    @GET(UserOperatorRetrofit.RESOURCE)
    Call<List<User>> findAll();

    @GET(UserOperatorRetrofit.SINGLE_RESOURCE)
    Call<User> find(@Path(UserOperatorRetrofit.ID) Integer id);

    @POST(UserOperatorRetrofit.RESOURCE)
    Call<User> postUser(@Body User user);
    
    @POST("/login")
    Call<Usuario> login(@Body Usuario user);
}