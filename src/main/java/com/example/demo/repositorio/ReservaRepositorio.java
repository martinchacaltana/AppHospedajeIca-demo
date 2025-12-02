package com.example.demo.repositorio;

import java.util.List;

import com.example.demo.entidades.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Reserva;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    @Query(value = "SELECT * FROM reserva WHERE reserva.activo=true", nativeQuery = true)
    List<Reserva> findAllByActivo();

    List<Reserva> findByCliente_Id(Integer clienteId);
}
