package com.example.demo.controladores;

import com.example.demo.dao.ReservaImpl;
import com.example.demo.dao.TipoHabitacionDaoImpl;
import com.example.demo.entidades.Reserva;
import com.example.demo.repositorio.ReservaRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ControladorPrincipal {

    @Autowired
    private TipoHabitacionDaoImpl tipoHabitacionDaoImpl;
    @Autowired
    private ReservaImpl reserva;
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private ReservaImpl reservaImpl;

    @GetMapping("/")
    public String index(Model model) {
        try {
            model.addAttribute("tipohabitacion", this.tipoHabitacionDaoImpl.findAllByActivo());
            return "index";
        } catch (Exception e) {
            return "";
        }

    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    /*@GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "register";
    }*/

    @GetMapping("/service")
    public String service() {
        return "service";
    }

    @GetMapping("/room")
    public String room() {
        return "room";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/booking")
    public String booking() {
        return "booking";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/team")
    public String team() {
        return "team";
    }

    @GetMapping("/testimonial")
    public String testimonial() {
        return "testimonial";
    }

    @GetMapping("/misreservas")
    public String reservasxUser(Model model,HttpSession session) {
        Integer idUsuario = (Integer) session.getAttribute("idusuario");
        if (idUsuario != null) {
            List<Reserva> reservas = reservaImpl.obtenerReservasPorUsuario(idUsuario);
            System.out.println("Usuario ID: " + idUsuario);
            System.out.println("Reservas encontradas: " + reservas.size());
            for (Reserva r : reservas) {
                System.out.println("Reserva ID: " + r.getIDReserva());
            }
            model.addAttribute("reservas", reservas);
        }
        System.out.println("probando");
        model.addAttribute("sesion", idUsuario);
        return "misreservas";
    }
}