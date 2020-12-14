/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.persistence;

import ar.edu.unsl.backend.model.interfaces.IRegistroOperator;
import ar.edu.unsl.backend.model.services.RegistroService;
import com.mycompany.trazar.cliente.App;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author demig
 */
public class RegistroOperatorRetrofit implements IRegistroOperator{
     private final static int REQUEST_CONNECT_TIMEOUT_TOLERANCE = 60;
    private final static int REQUEST_READ_TIMEOUT_TOLERANCE = 20;
    private final static int REQUEST_WRITE_TIMEOUT_TOLERANCE = 20;

    static UserOperatorRetrofit operator;

    private RegistroService registroService;
    private RegistroRepository registroRepository;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    public RegistroOperatorRetrofit(RegistroService registroService)
    {
        this.registroService = registroService;

        // HttpClient and Rest Client can be inyected for more decoupling
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_CONNECT_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .readTimeout(REQUEST_READ_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITE_TIMEOUT_TOLERANCE, TimeUnit.SECONDS).build();

        this.retrofit = new Retrofit.Builder().baseUrl(App.API_HOSTNAME).client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
                
        this.registroRepository = this.retrofit.create(RegistroRepository.class);
    }


    @Override
    public void consultarVisitas(String fechain, String fechaOut) {
        
    }
    
}
