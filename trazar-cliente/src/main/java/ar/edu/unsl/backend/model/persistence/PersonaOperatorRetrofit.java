/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.persistence;

import com.mycompany.trazar.cliente.App;
import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;
import retrofit2.converter.gson.GsonConverterFactory;
import ar.edu.unsl.backend.model.services.PersonaService;
import ar.edu.unsl.backend.model.interfaces.IPersonaOperator;
import ar.edu.unsl.backend.model.repositories.PersonaRepository;
/**
 *
 * @author demig
 */
public class PersonaOperatorRetrofit implements IPersonaOperator{
    private final static int REQUEST_CONNECT_TIMEOUT_TOLERANCE = 20;
    private final static int REQUEST_READ_TIMEOUT_TOLERANCE = 5;
    private final static int REQUEST_WRITE_TIMEOUT_TOLERANCE = 5;

    public final static String ID = "id";
    public final static String RESOURCE = "/persona";
    public final static String SINGLE_RESOURCE = RESOURCE + "/{" + ID + "}";

    static UserOperatorRetrofit operator;

    private PersonaService personaService;
    private PersonaRepository userRepository;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    public PersonaOperatorRetrofit(PersonaService personaService) {
        
    this.personaService = personaService;

        // HttpClient and Rest Client can be inyected for more decoupling
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_CONNECT_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .readTimeout(REQUEST_READ_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITE_TIMEOUT_TOLERANCE, TimeUnit.SECONDS).build();

        this.retrofit = new Retrofit.Builder().baseUrl(App.API_HOSTNAME).client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

        this.userRepository = this.retrofit.create(PersonaRepository.class);
    }
       
}
