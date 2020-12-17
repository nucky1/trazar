/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.persistence;

import ar.edu.unsl.backend.model.entities.Persona;
import ar.edu.unsl.backend.model.entities.Registro;
import ar.edu.unsl.backend.model.interfaces.IRegistroOperator;
import ar.edu.unsl.backend.model.repositories.RegistroRepository;
import ar.edu.unsl.backend.model.services.RegistroService;
import ar.edu.unsl.backend.util.Statics;
import ar.edu.unsl.frontend.service_subscribers.LocalServiceSubscriber;
import ar.edu.unsl.frontend.service_subscribers.PersonaServiceSubscriber;
import com.mycompany.trazar.cliente.App;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        this.registroRepository.getRegistros(Statics.getUser().getId_local(), fechain, fechaOut, Statics.getUser().getToken()).enqueue(new Callback<List<Persona>>() {
            @Override
            public void onResponse(Call<List<Persona>> call, Response<List<Persona>> rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((LocalServiceSubscriber)registroService.getServiceSubscriber()).showVisitas(rspns.body());
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            System.out.println("asdasdasd");
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Persona>> call, Throwable thrwbl) {
                Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            registroService.getServiceSubscriber().showError("get visitas fail", null, new Exception(thrwbl));
                        }  
                    });
            }
        });
    }

    @Override
    public void insertarRegistro(Registro r) {
        this.registroRepository.createRegistro(r, Statics.getUser().getToken()).enqueue(new Callback<Registro>() {
            @Override
            public void onResponse(Call<Registro> call, Response<Registro> rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((PersonaServiceSubscriber)registroService.getServiceSubscriber()).showExito(rspns.body());
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((PersonaServiceSubscriber)registroService.getServiceSubscriber()).error();
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call<Registro> call, Throwable thrwbl) {
                Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            registroService.getServiceSubscriber().showError("get visitas fail", null, new Exception(thrwbl));
                        }  
                    });
            }
        });
    }
    
}
