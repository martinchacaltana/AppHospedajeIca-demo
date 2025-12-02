package com.example.demo.repositorio;

import com.example.demo.entidades.Reseña;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReseñaRepositorio extends JpaRepository<Reseña, Long> {
}
