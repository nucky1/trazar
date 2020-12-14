package ar.edu.unsl.backend.model.interfaces;

import ar.edu.unsl.backend.model.entities.User;
import ar.edu.unsl.backend.model.entities.Usuario;

public interface IUserOperator extends CrudOperator<User>
{
    User find(Integer id) throws Exception;
    Usuario login(Usuario user) throws Exception;

    User delete(Integer id) throws Exception;

    public void pedirDatos(Usuario user);

    public void verificarUsuario(String userName);
}