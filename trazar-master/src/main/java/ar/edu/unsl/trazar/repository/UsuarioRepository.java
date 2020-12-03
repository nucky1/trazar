package ar.edu.unsl.trazar.repository;

import ar.edu.unsl.trazar.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Usuario findByUserName(String userName);
}
