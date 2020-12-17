package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.entity.Local;
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

    @Resource
    LocalService localService;

    @Override
    public Usuario create(Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

	@Override
	public Usuario find(String userName) {
		return usuarioRepository.findByUserName(userName);
	}
	@Override
    public Usuario updateUsuario(Integer id,Usuario usuario) {
        Usuario usuarioAux=usuarioRepository.findById(id).orElse(null);
        if(usuarioAux!=null){
            usuarioAux.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
            usuarioAux.setUserName(usuario.getUserName());
            usuarioAux.setLocal(usuario.getLocal());
            usuarioAux=usuarioRepository.save(usuarioAux);
        }
        return usuarioAux;
    }

    @Override
    public Usuario recuperarUsuario(String cuit) {
        Local local=localService.getByCuit(cuit);
        Usuario nuevoUsuario=null;
        if (local!=null){
            nuevoUsuario=local.getUsuario();
            nuevoUsuario.setPassword(bCryptPasswordEncoder.encode("1234"));
            nuevoUsuario=usuarioRepository.save(nuevoUsuario);
        }
        return nuevoUsuario;
    }


}
