package com.example.demo.controladores;

import com.example.demo.entidades.AppUser;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/obtener-id-cliente")
    public ResponseEntity<Long>  obtenerIdCliente(@AuthenticationPrincipal UserDetails userDetails) {
        // Asume que tienes un servicio para obtener el cliente a partir del nombre de usuario
        AppUser cliente = userService.getByEmail(userDetails.getUsername());
        return new ResponseEntity<>((long) cliente.getId(), HttpStatus.OK);
    }
}
