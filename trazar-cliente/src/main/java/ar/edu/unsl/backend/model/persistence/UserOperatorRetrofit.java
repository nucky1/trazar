package ar.edu.unsl.backend.model.persistence;

import java.util.List;
import retrofit2.Call;
import com.mycompany.trazar.cliente.App;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import java.io.IOException;
import okhttp3.OkHttpClient;
import javafx.application.Platform;
import java.util.concurrent.TimeUnit;
import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;
import retrofit2.converter.gson.GsonConverterFactory;
import ar.edu.unsl.backend.model.services.UserService;
import ar.edu.unsl.backend.model.interfaces.IUserOperator;
import ar.edu.unsl.backend.model.repositories.UserRepository;
import ar.edu.unsl.frontend.service_subscribers.UserServiceSubscriber;
import com.google.gson.JsonObject;

public class UserOperatorRetrofit implements IUserOperator
{
    private final static int REQUEST_CONNECT_TIMEOUT_TOLERANCE = 60;
    private final static int REQUEST_READ_TIMEOUT_TOLERANCE = 20;
    private final static int REQUEST_WRITE_TIMEOUT_TOLERANCE = 20;

    public final static String ID = "id";
    public final static String RESOURCE = "/usuario";
    public final static String SINGLE_RESOURCE = RESOURCE + "/{" + ID + "}";

    static UserOperatorRetrofit operator;

    private UserService userService;
    private UserRepository userRepository;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    public UserOperatorRetrofit(UserService userService)
    {
        this.userService = userService;

        // HttpClient and Rest Client can be inyected for more decoupling
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_CONNECT_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .readTimeout(REQUEST_READ_TIMEOUT_TOLERANCE, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITE_TIMEOUT_TOLERANCE, TimeUnit.SECONDS).build();

        this.retrofit = new Retrofit.Builder().baseUrl(App.API_HOSTNAME).client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
                
        this.userRepository = this.retrofit.create(UserRepository.class);
    }

    @Override
    public User insert(User user) throws Exception
    {
        User ret = null;
        Response<User> response = null;
        try
        {
            response = this.userRepository.postUser(user).execute();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }

        if(response != null && response.isSuccessful())
        {
            ret = response.body();
        }
        else
        {
            this.userService.getServiceSubscriber().showError("User registration error. Body:\n"+response.errorBody().toString());
        }
        
        return ret;
    }

    @Override
    public List<User> insertMany(List<User> list) throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User update(User entity) throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> findAll() throws Exception
    {
        this.userRepository.findAll().enqueue
        (
            new Callback<List<User>>()
            {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response)
                {
                    if(response.isSuccessful())
                    {
                       // ((UserServiceSubscriber)userService.getServiceSubscriber()).showUsers(response.body());
                    }
                        else
                    {
                        userService.getServiceSubscriber().showError("Cannot obtain a users list", response.errorBody().toString(), new Exception("Error response"));
                    }
                };
                
                @Override
                public void onFailure(Call<List<User>> call, Throwable throwable)
                {
                    userService.getServiceSubscriber().showError("Find all user request fail", null, new Exception(throwable));
                };
            }
        );
        return null;
    }

    @Override
    public User find(Integer id) throws Exception
    {
        this.userRepository.find(id).enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response)
            {
                if(response.isSuccessful())
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            //((UserServiceSubscriber)userService.getServiceSubscriber()).showUser(response.body());
                        } 
                    });
                }
                else
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            userService.getServiceSubscriber().showError("Cannot obtain user "+id, response.errorBody().toString(), new Exception("Error response"));
                        } 
                    });
                }
            };
            
            @Override
            public void onFailure(Call<User> call, Throwable throwable)
            {
                userService.getServiceSubscriber().showError("find user request fail", null, new Exception(throwable));
            };
        });
        return null;
    }

    @Override
    public User delete(Integer id) throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Usuario login(Usuario user) throws Exception {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", user.getUserName());
        jsonObject.addProperty("password", user.getPassword());
        this.userRepository.login(jsonObject).enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((UserServiceSubscriber)userService.getServiceSubscriber()).logueado(rspns.headers().get("Authorization"));
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            userService.getServiceSubscriber().showError("Cannot obtain user ", rspns.errorBody().toString(), new Exception("Error response"));
                        } 
                    });
                }
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable thrwbl) {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userService.getServiceSubscriber().showError("login request fail", null, new Exception(thrwbl));
                    } 
                });
            }
        });
        return null;
    }

    @Override
    public void pedirDatos(Usuario user) {
        
        this.userRepository.pedirDatos(user.getUserName(),user.getToken()).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call call, Response rspns) {
                if(rspns.code()== 200)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            ((UserServiceSubscriber)userService.getServiceSubscriber()).iniciarPantalla((Usuario) rspns.body());
                        } 
                    });
                }else{
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            userService.getServiceSubscriber().showError("No trae la info del local", rspns.errorBody().toString(), new Exception("Error response"));
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
                        userService.getServiceSubscriber().showError("info request fail", null, new Exception(thrwbl));
                    } 
                });
            }
        });
    }
}