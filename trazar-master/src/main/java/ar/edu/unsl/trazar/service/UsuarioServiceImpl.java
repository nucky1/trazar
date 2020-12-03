package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.repository.UsuarioRepository;
import ar.edu.unsl.trazar.entity.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Resource
    UsuarioRepository usuarioRepository;

    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Usuario create(Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }
}
