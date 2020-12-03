package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.repository.UsuarioRepository;
import ar.edu.unsl.trazar.entity.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario= usuarioRepository.findByUserName(userName);
        return new User(usuario.getUserName(),usuario.getPassword(),new ArrayList<>());
    }
}
