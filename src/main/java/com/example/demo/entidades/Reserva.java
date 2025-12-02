package com.example.demo.entidades;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long IDReserva;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", nullable = false)
    private AppUser cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDHabitacion", nullable = false)
    private Habitacion habitacion;

    @Positive(message = "Ingrese cantidad de personas correcta")
    @NotNull(message = "No se puede dejar en blanco")
    private Short cantidadpersonas;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "No puede dejarse en blanco")
    @FutureOrPresent(message = "Ingrese fecha valida")
    private LocalDate fechaentrada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "No puede dejarse en blanco")
    @Future(message = "Ingrese fecha valida")
    private  LocalDate fechasalida;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date fechaCreacion;

    private boolean estadoreserva;

    private boolean activo = true;
}
