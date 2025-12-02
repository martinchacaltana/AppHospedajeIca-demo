package com.example.demo.repositorio;

import com.example.demo.entidades.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepositorio extends JpaRepository<com.example.demo.entidades.Habitacion, Long> {
    @Query(value = "SELECT * FROM habitacion WHERE habitacion.activo=true", nativeQuery = true)
    List<Habitacion> findAllByActivo();

    @Query(value = "SELECT * FROM habitacion WHERE habitacion.activo = true AND disponibilidad = true", nativeQuery = true)
    List<Habitacion> findAllByActivoAndDisponibilidad();

    @Query(value = "SELECT * FROM habitacion WHERE habitacion.IDHabitacion = :id AND habitacion.activo = true", nativeQuery = true)
    Optional<Habitacion> findByIdAAndActivo(@Param("id") long IDHabitacion);

    @Query("SELECT h FROM Habitacion h " +
            "JOIN TipoHabitacion th ON h.tipoHabitacion.id = th.id " +
            "WHERE th.descripcion LIKE %:descripcion% " +
            "AND h.activo = true " +
            "AND h.disponibilidad = true " +
            "AND h.id NOT IN (" +
            "    SELECT r.habitacion.id " +
            "    FROM Reserva r " +
            "    WHERE (r.fechaentrada <= :fechasalida AND r.fechasalida >= :fechaentrada)" +
            ")")
    List<Habitacion> encontrarHabitacionesDisponibles(
            @Param("descripcion") String descripcion,
            @Param("fechaentrada") LocalDate fechaEntrada,
            @Param("fechasalida") LocalDate fechaSalida);
}