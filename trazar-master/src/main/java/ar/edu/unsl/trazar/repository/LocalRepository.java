package ar.edu.unsl.trazar.repository;

import ar.edu.unsl.trazar.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local,Integer> {

}
