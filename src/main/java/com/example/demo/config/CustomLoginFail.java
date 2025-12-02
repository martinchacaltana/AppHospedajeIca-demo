package com.example.demo.config;
import com.example.demo.entidades.AppUser;
import com.example.demo.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFail extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        AppUser user= userService.getByEmail(email);
        System.out.println("customloginfail funciona");
        if(user!=null){
            if(user.isEnabled() && user.isAccountnonlocked()){
                if(user.getFailedattempt() < UserService.MAX_FAILED_ATTEMPTS -1){
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.lock(user);
                    exception= new LockedException("Cuenta bloqueada por fallar 3 veces al iniciar sesion, se desbloqueara en 15 minutos");
                }
            }else if(!user.isAccountnonlocked()){
                if(userService.unlockWhenTimeExpired(user)){
                    exception = new LockedException("Cuenta desbloqueada. Por favor intente de nuevo ");
                }
            }
        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
