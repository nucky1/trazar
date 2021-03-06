package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario create(Usuario usuario);

    List<Usuario> getUsuarios();
    Usuario find(String userName);
    Usuario updateUsuario(Integer id,Usuario usuario);
    Usuario recuperarUsuario(String cuit);
}
