package ar.edu.unsl.trazar.controller;

import ar.edu.unsl.trazar.entity.Local;
import ar.edu.unsl.trazar.entity.Persona;
import ar.edu.unsl.trazar.entity.Registro;
import ar.edu.unsl.trazar.service.RegistroService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Resource
    RegistroService registroService;

    //Locales en los que estuvo una persona determinada fecha
    @GetMapping("/persona/{id}")
    public List<Local> getLocalesByPersona(@PathVariable Integer id,
                                           @RequestParam(name = "fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
                                           @RequestParam(name = "fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta){
        return registroService.getLocalesByPersona(id,fechaDesde,fechaHasta);
    }

    //Personas que estuvieron en un local determinada fecha
    @GetMapping("/local/{id}")
    public List<Persona> getPersonasByLocal(@PathVariable Integer id,
                                            @RequestParam(name = "fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
                                            @RequestParam(name = "fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta){
        return registroService.getPersonasByLocal(id,fechaDesde,fechaHasta);
    }

    @PostMapping
    public Registro createRegistro(@RequestBody Registro registro){
        return registroService.createRegistro(registro);
    }
}
