package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.entity.Local;
import ar.edu.unsl.trazar.entity.Usuario;

import java.util.List;

public interface LocalService {

    Local createLocal(Local local);
    Local getLocalById(Integer id);
    List<Local> getLocales();
    Local updateLocal(Integer id,Local local);
    Local getByCuit(String cuit);
}
