package com.example.demo.entidades;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "habitacion")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private long IDHabitacion;

    @NotNull(message = "No puede estar vacio")
    @Max(value = 999, message = "Ingrese numero valido")
    private Short numeroHabitacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDTipoHabitacion", nullable = false)
    @JsonBackReference
    private TipoHabitacion tipoHabitacion;

    private boolean disponibilidad;
    @NotNull(message = "No puede estar vacio")
    private BigDecimal precio;

    private boolean activo = true;

    @OneToMany(mappedBy = "habitacion")
    @JsonIgnore
    private List<Reserva> reservas;
}