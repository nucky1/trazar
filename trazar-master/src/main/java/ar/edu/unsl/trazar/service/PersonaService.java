package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.entity.Persona;


public interface PersonaService {

    Persona createPersona(Persona persona);
    Persona getPersonaById(Integer id);
	Persona updatePersona(Integer id,Persona persona);
}
