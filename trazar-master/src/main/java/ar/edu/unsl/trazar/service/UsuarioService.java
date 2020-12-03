package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario create(Usuario usuario);

    List<Usuario> getUsuarios();
}
