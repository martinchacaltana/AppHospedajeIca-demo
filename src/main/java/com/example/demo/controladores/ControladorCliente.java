package com.example.demo.controladores;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entidades.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dao.ClienteImpl;
import com.example.demo.dao.DocumentoImpl;

import jakarta.validation.Valid;

@Controller
public class ControladorCliente {
    @Autowired
    private ClienteImpl clienteImpl;
    @Autowired
    private DocumentoImpl documentoImpl;

    @GetMapping("/cliente")
    public String cliente(Model model) {
        try {
            List<AppUser> clientes = clienteImpl.listar();
            LocalDate today = LocalDate.now();
            List<AppUser> clientesCumpleanios = clientes.stream()
                    .filter(cliente -> cliente.getFechanacimiento() != null && cliente.getFechanacimiento().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate().getDayOfMonth() == today.getDayOfMonth() &&
                            cliente.getFechanacimiento().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate().getMonth() == today.getMonth())
                    .collect(Collectors.toList());

            model.addAttribute("clientes", clientes);
            model.addAttribute("clientesCumpleanios", clientesCumpleanios);
            return "cliente";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/cliente/form/{id}")
    public String nuevoCliente(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("documento", this.documentoImpl.listar());
            if (id == 0) {
                model.addAttribute("cliente", new AppUser());
            } else {
                model.addAttribute("cliente", this.clienteImpl.buscarporId(id));
            }
            return "crear_cliente";
        } catch (Exception e) {
            return "";
        }
    }

    @PostMapping("/cliente/form/{id}")
    public String guardarCliente(@Valid @ModelAttribute("cliente") AppUser cliente, BindingResult result, Model model,
            @PathVariable("id") long id) {
        try {
            model.addAttribute("documento", this.documentoImpl.listar());
            if (result.hasErrors()) {
                return "crear_cliente";
            }
            if (id == 0) {
                this.clienteImpl.guardar(cliente);
            } else {
                this.clienteImpl.actualizar(cliente, id);
            }
            return "redirect:/cliente";
        } catch (Exception e) {
            return "";
        }
    }

    @GetMapping("/cliente/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        try {
            this.clienteImpl.eliminarporId(id);
            return "redirect:/cliente";
        } catch (Exception e) {
            return "";
        }
    }
}