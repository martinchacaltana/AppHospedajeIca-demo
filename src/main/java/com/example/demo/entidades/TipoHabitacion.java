package com.example.demo.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="TipoHabitacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long IDTipoHabitacion;
    @NotEmpty(message = "No puede estar vacio")
    private String descripcion;
    @NotNull(message = "No puede estar vacio")
    @Positive(message = "Ingrese cantidad correcta")
    private Short CantidadCamas;
    @NotNull(message = "No puede estar vacio")
    @Positive(message = "Ingrese maximo de personas correcto")
    private Short MaximoPersonas;
    private boolean activo=true;

    @OneToMany(mappedBy = "tipoHabitacion")
    @JsonManagedReference
    private List<Habitacion> habitaciones;

}