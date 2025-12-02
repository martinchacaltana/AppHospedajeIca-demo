package com.example.demo.config;

import com.example.demo.entidades.AppUser;
import com.example.demo.entidades.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class HospedajeIcaUserDetails implements UserDetails {

    private AppUser user;

    public HospedajeIcaUserDetails(AppUser user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Rol> rol= user.getRoles();
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(Rol role:rol){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountnonlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullname() {
        return capitalize(this.user.getFirstName()) + " " + capitalize(this.user.getLastName());
    }

    private String capitalize(String string){
        if(string==null || string.isEmpty()){
            return string;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public AppUser getUser() {
        return this.user;
    }
}
