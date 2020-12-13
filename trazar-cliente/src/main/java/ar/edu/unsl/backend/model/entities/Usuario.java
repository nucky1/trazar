package ar.edu.unsl.backend.model.entities;

public class Usuario {

    private Integer id;
    private String userName;
    private String password;
    private String tipo;
    private String token;
    private Integer local;

    public Integer getId_local() {
        return local;
    }

    public void setId_local(Integer local) {
        this.local = local;
    }

    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    
    
}
