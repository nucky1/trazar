package ar.edu.unsl.frontend.service_subscribers;

import java.util.List;
import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;

public interface UserServiceSubscriber
{

    public void logueado(String user);

    public void iniciarPantalla(Usuario body);

}