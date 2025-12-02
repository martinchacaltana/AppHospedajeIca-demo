package com.example.demo.controladores;

import com.example.demo.dao.HabitacionDaoImpl;
import com.example.demo.dao.ReservaImpl;
import com.example.demo.entidades.AppUser;
import com.example.demo.entidades.Habitacion;
import com.example.demo.entidades.Reserva;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ControladorAjax {
    @Autowired
    private HabitacionDaoImpl habitacionDaoImpl;

    @Autowired
    private ReservaImpl reservaimpl;
    @Autowired
    private UserService userService;
    @Autowired
    private View error;

    @GetMapping("/habitaciones-disponibles")
    public List<Habitacion> obtenerHabitacionesDisponibles(
            @RequestParam String descripcion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida) throws Exception {
        return habitacionDaoImpl.encontrarHabitacionesDisponibles(descripcion, fechaEntrada, fechaSalida);
    }

    @PostMapping("/reservar-habitacion")
    public Reserva reservarHabitacion(@Validated @RequestBody Reserva reserva, Errors errors) throws Exception {
        System.out.println(errors.toString());
        System.out.println(reserva);
        System.out.println("Datos de reserva recibidos:");
        System.out.println("Fecha Entrada: " + reserva.getFechaentrada());
        System.out.println("Fecha Salida: " + reserva.getFechasalida());
        System.out.println("Cantidad Personas: " + reserva.getCantidadpersonas());
        System.out.println("Habitacion ID: " + reserva.getHabitacion());
        System.out.println("Cliente: " + reserva.getCliente());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            String username=((((UserDetails) principal).getUsername()));
            AppUser user = userService.findbyUsername(username);
            reserva.setCliente(user);
        }else{
            throw new Exception("Usuario no autenticado");
        }
        if (reserva.getFechaentrada() == null || reserva.getFechasalida() == null || reserva.getCantidadpersonas() == null) {
            throw new Exception("Campos obligatorios faltantes");
        }
        return reservaimpl.guardar(reserva);
    }
}
