/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.persistence;

import ar.edu.unsl.backend.model.entities.Local;
import ar.edu.unsl.backend.model.entities.Usuario;
import ar.edu.unsl.backend.model.interfaces.ILocalOperator;
import ar.edu.unsl.backend.model.repositories.LocalRepository;
import ar.edu.unsl.backend.model.services.LocalService;
import ar.edu.unsl.backend.util.Statics;
import ar.edu.unsl.frontend.service_subscribers.RegistrarseServiceSubiscriber;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;
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
public class LocalOperatorRetrofit implements ILocalOperator{

    private final static int REQUEST_CONNECT_TIMEOUT_TOLERANCE = 20;
    private final static int REQUEST_READ_TIMEOUT_TOLERANCE = 5;
    private final static int REQUEST_WRITE_TIMEOUT_TOLERANCE = 5;

    public final static String ID = "id";
    public final static String RESOURCE = "/local";
    public final static String SINGLE_RESOURCE = RESOURCE + "/{" + ID + "}";

    static UserOperatorRetrofit operator;

    private LocalService localService;
    private LocalRepository localRepository;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    
    public LocalOperatorRetrofit(LocalService localService)
    {
        this.localService = localService;

        // HttpClient and Rest Client can be inyected for more decoupling
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_CONNECT_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .readTimeout(REQUEST_READ_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITE_TIMEOUT_TOLERANCE, TimeUnit.SECONDS).build();

        this.retrofit = new Retrofit.Builder().baseUrl(App.API_HOSTNAME).client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofit.baseUrl();
        this.localRepository = this.retrofit.create(LocalRepository.class);
    }
    
    @Override
    public Local find(Integer id) throws Exception {
        this.localRepository.find(id, Statics.getUser().getToken()).enqueue(new Callback<Local>() {
            @Override
            public void onResponse(Call<Local> call, Response<Local> rspns) {
                
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            System.out.println(rspns.body());
                            System.out.println(localService);
                            System.out.println(localService.getServiceSubscriber());
                            
                            ((RegistrarseServiceSubiscriber)localService.getServiceSubscriber()).datosMiLocal(rspns.body());
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            localService.getServiceSubscriber().showError("F: No se pudo obtener los datos del local");
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call<Local> call, Throwable thrwbl) {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        localService.getServiceSubscriber().showError("F: Al buscar datos local(onFailure)", null, new Exception(thrwbl));
                    } 
                });
            }
        });
        return null;
    }

    @Override
    public List<Local> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Local delete(Integer id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Local l) {
        this.localRepository.updateLocal(Statics.getUser().getId_local(), l, Statics.getUser().getToken()).enqueue(new Callback<Local>() {
            @Override
            public void onResponse(Call<Local> call, Response<Local> rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((RegistrarseServiceSubiscriber)localService.getServiceSubscriber()).exito();
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((RegistrarseServiceSubiscriber)localService.getServiceSubscriber()).error();
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call<Local> call, Throwable thrwbl) {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        localService.getServiceSubscriber().showError("F: Al guardar local (onFailure)", null, new Exception(thrwbl));
                    } 
                });
            }
        });
    }

    @Override
    public void insert(Local miLocal) {
        this.localRepository.insert(miLocal).enqueue(new Callback<Local>() {
            @Override
            public void onResponse(Call<Local> call, Response<Local> rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((RegistrarseServiceSubiscriber)localService.getServiceSubscriber()).insertUser(rspns.body());
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            System.out.println("asdasdasd");
                            ((RegistrarseServiceSubiscriber)localService.getServiceSubscriber()).error();
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call<Local> call, Throwable thrwbl) {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        localService.getServiceSubscriber().showError("F: Al guardar local (onFailure)", null, new Exception(thrwbl));
                    } 
                });
            }
        });
    }

    @Override
    public void getUserByCUIT(String cuit) {
        System.out.println(cuit);
        this.localRepository.recUser(cuit).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((UserServiceSubscriber)localService.getServiceSubscriber()).notificar(rspns.body());
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((UserServiceSubscriber)localService.getServiceSubscriber()).noNotificado();
                        } 
                    });
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable thrwbl) {
               Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        localService.getServiceSubscriber().showError("F: Al guardar local (onFailure)", null, new Exception(thrwbl));
                    } 
                }); 
            }
        });
    }
    
}
