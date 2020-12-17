package ar.edu.unsl.frontend.service_subscribers;

import ar.edu.unsl.backend.model.entities.Usuario;

public interface UserServiceSubscriber
{

    public void logueado(String user);

    public void iniciarPantalla(Usuario body);

    public void usuarioInexistente();

    public void notificado();

    public void noNotificado();

    public void notificar(Usuario body);

}