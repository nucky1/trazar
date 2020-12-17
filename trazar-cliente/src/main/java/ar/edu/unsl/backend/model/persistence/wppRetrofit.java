/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.unsl.backend.model.persistence;

import ar.edu.unsl.backend.model.repositories.UserRepository;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;
import com.google.gson.JsonObject;
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
public class wppRetrofit {
        private final static int REQUEST_CONNECT_TIMEOUT_TOLERANCE = 60;
    private final static int REQUEST_READ_TIMEOUT_TOLERANCE = 20;
    private final static int REQUEST_WRITE_TIMEOUT_TOLERANCE = 20;
    
    private UserServiceSubscriber userService;
    private UserRepository userRepository;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    public wppRetrofit(UserServiceSubscriber userService)
    {
        this.userService = userService;

        // HttpClient and Rest Client can be inyected for more decoupling
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_CONNECT_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .readTimeout(REQUEST_READ_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITE_TIMEOUT_TOLERANCE, TimeUnit.SECONDS).build();

        this.retrofit = new Retrofit.Builder().baseUrl("https://eu4.chat-api.com").client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        this.userRepository = this.retrofit.create(UserRepository.class);
        
    }
    public void enviar(String phone,String password,String user){
        JsonObject js = new JsonObject();
        js.addProperty("phone", "+54"+phone);
        js.addProperty("body", "Recuperación de contraseña: \n "
                + "Su usuario es: "+user+"\n"
                +"Su contraseña es:"+password+"\n"
                + "Si usted no realizo esta acción, alguien le quiere robar la contraseña y sabe su CUIT.");
        System.out.println(phone);
        this.userRepository.enviar("jrj6dgs1no36dr4q",js).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response rspns) {
                
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            userService.notificado();
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            userService.noNotificado();
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call call, Throwable thrwbl) {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userService.noNotificado();
                    } 
                });
            }
        });
    }
}
