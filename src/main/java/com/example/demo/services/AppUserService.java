package com.example.demo.services;


import com.example.demo.config.HospedajeIcaUserDetails;
import com.example.demo.entidades.AppUser;
import com.example.demo.repositorio.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    private AppUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = repo.findByEmail(email);

        if (appUser == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return new HospedajeIcaUserDetails(appUser);

    }
}
