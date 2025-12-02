package com.example.demo.config;

import com.example.demo.entidades.AppUser;
import com.example.demo.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccess extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        HospedajeIcaUserDetails userDetails = (HospedajeIcaUserDetails) authentication.getPrincipal();
        AppUser user = userDetails.getUser();
        System.out.println("customloginsuccess funciona");
        if(user.getFailedattempt() > 0){
            userService.resetFailedAttempts(user.getEmail());
        }
        session.setAttribute("idusuario", user.getId());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
