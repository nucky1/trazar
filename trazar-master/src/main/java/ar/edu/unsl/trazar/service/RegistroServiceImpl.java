package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.repository.RegistroRepository;
import ar.edu.unsl.trazar.entity.Local;
import ar.edu.unsl.trazar.entity.Persona;
import ar.edu.unsl.trazar.entity.Registro;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Resource
    private RegistroRepository registroRepository;

    @Override
    public List<Persona> getPersonasByLocal(Integer id, Date fechaDesde,Date fechaHasta) {
        return registroRepository.findPersonasByLocalId(id,fechaDesde,fechaHasta);
    }

    @Override
    public List<Local> getLocalesByPersona(Integer id, Date fechaDesde, Date fechaHasta) {
        return registroRepository.findLocalByPersonaId(id,fechaDesde,fechaHasta);
    }

    @Override
    public Registro createRegistro(Registro registro) {
        return registroRepository.save(registro);
    }
}
