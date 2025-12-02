package com.example.demo.repositorio;

import com.example.demo.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Rol, Long> {
    @Query("SELECT r from Rol r WHERE r.name = ?1")
    public Rol findByName(String name);

}
