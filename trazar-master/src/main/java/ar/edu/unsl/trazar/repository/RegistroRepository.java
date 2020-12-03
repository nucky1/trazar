package ar.edu.unsl.trazar.repository;

import ar.edu.unsl.trazar.entity.Local;
import ar.edu.unsl.trazar.entity.Persona;
import ar.edu.unsl.trazar.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro,Integer> {

    @Query("SELECT r.local FROM Registro r WHERE r.persona.id=:id AND r.fecha BETWEEN :fechaDesde AND :fechaHasta")
    List<Local> findLocalByPersonaId(@Param("id") Integer id,
                                     @Param("fechaDesde")Date fechaDesde,
                                     @Param("fechaHasta")Date fechaHasta);

    @Query("select r.persona from Registro r where r.local.id=:id AND r.fecha BETWEEN :fechaDesde AND :fechaHasta")
    List<Persona> findPersonasByLocalId(@Param("id") Integer id,
                                        @Param("fechaDesde")Date fechaDesde,
                                        @Param("fechaHasta")Date fechaHasta);



}
