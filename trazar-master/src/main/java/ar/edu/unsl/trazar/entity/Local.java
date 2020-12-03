package ar.edu.unsl.trazar.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "locales")
@Getter
@Setter
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String telefono;
    private String direccion;

}
