package ar.edu.unsl.backend.model.persistence;

import java.util.List;
import com.mycompany.trazar.cliente.App;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import ar.edu.unsl.backend.model.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.impl.client.HttpClientBuilder;
import ar.edu.unsl.backend.model.services.UserService;
import org.apache.http.impl.client.CloseableHttpClient;
import ar.edu.unsl.backend.model.interfaces.IUserOperator;
import org.apache.http.client.methods.CloseableHttpResponse;

public class UserOperatorApache implements IUserOperator
{
    private final static int REQUEST_CONNECT_TIMEOUT_TOLERANCE = 20 * 1000;
    // private final static int REQUEST_READ_TIMEOUT_TOLERANCE = 5*1000;
    // private final static int REQUEST_WRITE_TIMEOUT_TOLERANCE = 5*1000;

    public final static String ID = "id";
    public final static String RESOURCE = "/users";
    public final static String SINGLE_RESOURCE = RESOURCE + "/" + ID;

    private static UserOperatorApache operator;

    private UserService userService;
    private CloseableHttpClient httpClient;

    public UserOperatorApache(UserService userService)
    {
        this.userService = userService;
        // CloseableHttpClient clienteHttp = HttpClients.createDefault();
        this.httpClient = HttpClientBuilder.create()
        .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(REQUEST_CONNECT_TIMEOUT_TOLERANCE).build())
        .build();
    }

    @Override
    public User insert(User user) throws Exception
    {
        User ret = null;
        
        ObjectMapper mapper = new ObjectMapper();

        String jsonUser = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);

        System.out.println(jsonUser);

        StringEntity postingString = new StringEntity(jsonUser);

        System.out.println("String entity = "+postingString);

        HttpPost httpPost = null;
        CloseableHttpResponse response = null;

        try
        {
            httpPost = new HttpPost(App.API_HOSTNAME + RESOURCE);
            httpPost.setEntity(postingString);
            httpPost.setHeader("Content-type", "application/json");

            System.out.println("request = "+httpPost);

            response = this.httpClient.execute(httpPost);

            System.out.println("response = "+response);
            if (200 <= response.getStatusLine().getStatusCode() && response.getStatusLine().getStatusCode() < 300)
            {
                HttpEntity entity = response.getEntity();

                System.out.println("http entity = "+response.getEntity());

                ret = mapper.readValue(EntityUtils.toString(entity), new TypeReference<User>(){});

                //Clean the steam
                EntityUtils.consume(entity);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            response.close();
            httpPost.releaseConnection();
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
        List<User> ret = null;

        HttpGet getHttp = null;
        CloseableHttpResponse response =  null;

        try
        {
            getHttp = new HttpGet(App.API_HOSTNAME + RESOURCE);
            response = this.httpClient.execute(getHttp);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode >= 200 && statusCode < 300)
            {
                HttpEntity entity = response.getEntity();
                ObjectMapper mapper = new ObjectMapper();
                //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ret = mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<User>>() {});

                //Clean the steam
                EntityUtils.consume(entity);
            }
            else
            {
                System.err.println("Error Inesperado STATUS: " + statusCode);
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            response.close();
            getHttp.releaseConnection();
        }

        return ret;
    }

    @Override
    public User find(Integer id) throws Exception
    {
        User ret = null;

        HttpGet getHttp = null;
        CloseableHttpResponse response =  null;

        try
        {
            getHttp = new HttpGet(App.API_HOSTNAME + RESOURCE + "/" + id);
            response = this.httpClient.execute(getHttp);

            int statusCode = response.getStatusLine().getStatusCode();

            if(statusCode >= 200 && statusCode < 300)
            {
                HttpEntity entity = response.getEntity();
                ObjectMapper mapper = new ObjectMapper();
                //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ret = mapper.readValue(EntityUtils.toString(entity), new TypeReference<User>() {});

                //Clean the steam
                EntityUtils.consume(entity);
            }
            else
            {
                System.err.println("Error Inesperado STATUS: " + statusCode);
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            response.close();
            getHttp.releaseConnection();
        }

        return ret;
    }

    @Override
    public User delete(Integer id) throws Exception
    {
        return null;
    } 
}