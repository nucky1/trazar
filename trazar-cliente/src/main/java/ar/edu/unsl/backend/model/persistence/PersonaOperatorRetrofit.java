/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.persistence;

import ar.edu.unsl.backend.model.entities.Persona;
import com.mycompany.trazar.cliente.App;
import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;
import retrofit2.converter.gson.GsonConverterFactory;
import ar.edu.unsl.backend.model.services.PersonaService;
import ar.edu.unsl.backend.model.interfaces.IPersonaOperator;
import ar.edu.unsl.backend.model.repositories.PersonaRepository;
import ar.edu.unsl.backend.util.Statics;
import ar.edu.unsl.frontend.service_subscribers.PersonaServiceSubscriber;
import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private PersonaRepository personaRepository;
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

        this.personaRepository = this.retrofit.create(PersonaRepository.class);
    }

    @Override
    public void findPersona(String dni) {
        this.personaRepository.getPersonaByDni(dni, Statics.getUser().getToken()).enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((PersonaServiceSubscriber)personaService.getServiceSubscriber()).showPersona(rspns.body());
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((PersonaServiceSubscriber)personaService.getServiceSubscriber()).didntFind();
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable thrwbl) {
                Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            personaService.getServiceSubscriber().showError("get visitas fail", null, new Exception(thrwbl));
                        }  
                    });
            }
        });
    }

    @Override
    public void save(Persona p) {
        this.personaRepository.createPersona(p, Statics.getUser().getToken()).enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((PersonaServiceSubscriber)personaService.getServiceSubscriber()).GuardarVisita(rspns.body());
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            personaService.getServiceSubscriber().showError("Save persona fail");
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable thrwbl) {
               Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            personaService.getServiceSubscriber().showError("get visitas fail", null, new Exception(thrwbl));
                        }  
                    });
            }
        });
    }
       
}
