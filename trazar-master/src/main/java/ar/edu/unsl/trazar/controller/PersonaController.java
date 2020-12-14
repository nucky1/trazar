package ar.edu.unsl.trazar.controller;

import ar.edu.unsl.trazar.entity.Persona;
import ar.edu.unsl.trazar.service.PersonaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Resource
    private PersonaService personaService;

    @PostMapping
    Persona createPersona(@RequestBody Persona persona){
        return personaService.createPersona(persona);
    }

    @GetMapping("/{id}")
    Persona getPersonaById(@PathVariable Integer id){
        return personaService.getPersonaById(id);
    }
    
  	@RequestMapping(value = "/{personaId}", method = RequestMethod.PUT)
  	public Persona updatePersona(@PathVariable("personaId") Integer personaId, @RequestBody Persona persona) {
  		return personaService.updatePersona(personaId, persona);
  	}

  	@GetMapping
    public Persona getPersonaByDni(@RequestParam(name = "dni") String dni){
        return personaService.getPersonaByDni(dni);
    }
}
