package com.example.demo.controladores;

import com.example.demo.dao.HabitacionDaoImpl;
import com.example.demo.dao.TipoHabitacionDaoImpl;
import com.example.demo.entidades.Habitacion;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class    ControladorHabitacion {
    @Autowired
    private HabitacionDaoImpl servicioHabitacion;
    @Autowired
    private TipoHabitacionDaoImpl servicioTipoHabitacion;

    @GetMapping("/habitacion")
    public String habitacion(Model model) {
        try {
            List<Habitacion> habitaciones=this.servicioHabitacion.findAllByActivo();
            model.addAttribute("habitaciones", habitaciones);
            return "habitacion";
        }catch (Exception e) {
            return "";
        }
    }
    @GetMapping("/habitacion/form/{id}")
    public String nuevoHabitacion(Model model,@PathVariable("id")long id) {
        try {
            model.addAttribute("tipohabitacion",this.servicioTipoHabitacion.findAllByActivo());
            if(id==0){
                model.addAttribute("habitacion", new Habitacion());
            }else{
                model.addAttribute("habitacion",this.servicioHabitacion.buscarporId(id));
            }
            return "crear_habitacion";
        }catch (Exception e) {
            return "";
        }
    }
    @PostMapping("/habitacion/form/{id}")
    public String guardarHabitacion(@Valid @ModelAttribute("habitacion") Habitacion habitacion,BindingResult result,Model model,@PathVariable("id")long id){
        try {
            model.addAttribute("tipohabitacion", this.servicioTipoHabitacion.findAllByActivo());
            if (result.hasErrors()) {
                return "crear_habitacion";
            }
            if(id==0){
                this.servicioHabitacion.guardar(habitacion);
            }else{
                this.servicioHabitacion.actualizar(habitacion,id);
            }
            return "redirect:/habitacion";
        }catch (Exception e) {
            return "";
        }
    }
    @GetMapping("/habitacion/{id}")
    public String eliminarHabitacion(@PathVariable Long id) {
        try {
            this.servicioHabitacion.eliminarporId(id);
            return "redirect:/habitacion";
        }catch (Exception e) {
            return "";
        }
    }
}
