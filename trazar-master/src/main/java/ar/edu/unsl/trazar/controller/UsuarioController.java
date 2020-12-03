package ar.edu.unsl.trazar.controller;

import ar.edu.unsl.trazar.entity.Usuario;
import ar.edu.unsl.trazar.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Resource
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario){
        return usuarioService.create(usuario);
    }

    @GetMapping
    public List<Usuario> getUsuarios(){
        return usuarioService.getUsuarios();
    }
}
