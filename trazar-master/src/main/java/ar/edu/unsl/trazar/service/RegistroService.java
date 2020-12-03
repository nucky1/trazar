package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.entity.Local;
import ar.edu.unsl.trazar.entity.Persona;
import ar.edu.unsl.trazar.entity.Registro;

import java.util.Date;
import java.util.List;

public interface RegistroService {

    List<Local> getLocalesByPersona(Integer id, Date fechaDesde,Date fechaHasta);

    List<Persona> getPersonasByLocal(Integer id, Date fechaDesde,Date fechaHasta);

    Registro createRegistro(Registro registro);
}
