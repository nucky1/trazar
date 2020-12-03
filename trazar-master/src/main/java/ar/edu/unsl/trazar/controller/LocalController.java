package ar.edu.unsl.trazar.controller;

import ar.edu.unsl.trazar.entity.Local;
import ar.edu.unsl.trazar.service.LocalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/local")
public class LocalController {

    @Resource
    private LocalService localService;

    @PostMapping
    public Local createLocal(@RequestBody Local local){
        return localService.createLocal(local);
    }

    @GetMapping("/{id}")
    public Local getLocalById(@PathVariable("id") Integer id){
        return localService.getLocalById(id);
    }

    @GetMapping
    public List<Local> getLocales(){
        return localService.getLocales();
    }

}
