package com.example.demo.dao;

import com.example.demo.entidades.Habitacion;
import com.example.demo.repositorio.HabitacionRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitacionDaoImpl implements ServicioGenerico<Habitacion> {
    @Autowired
    private HabitacionRepositorio habitacionRepositorio;

    @Override
    @Transactional
    public List<Habitacion> listar() throws Exception {
        try {
            List<Habitacion> lista = this.habitacionRepositorio.findAll();
            return lista;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Habitacion buscarporId(long id) throws Exception {
        try {
            Optional<Habitacion> obj = this.habitacionRepositorio.findById(id);
            return obj.get();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Habitacion guardar(Habitacion entity) throws Exception {
        try {
            Habitacion habitacion = this.habitacionRepositorio.save(entity);
            return habitacion;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Habitacion actualizar(Habitacion entity, long id) throws Exception {
        try {
            Optional<Habitacion> obj = this.habitacionRepositorio.findById(id);
            if (obj.isPresent()) {
                Habitacion habitacion = obj.get();
                habitacion.setNumeroHabitacion(entity.getNumeroHabitacion());
                habitacion.setTipoHabitacion(entity.getTipoHabitacion());
                habitacion.setDisponibilidad(entity.isDisponibilidad());
                habitacion.setPrecio(entity.getPrecio());
                return this.habitacionRepositorio.save(habitacion);
            }else{
                throw new Exception("Habitacion no encontrada con id: " + id);            }
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean eliminarporId(long id) throws Exception {
        try {
            Optional<Habitacion> obj = this.habitacionRepositorio.findById(id);
            if(!obj.isEmpty()){
                Habitacion habitacion = obj.get();
                habitacion.setActivo(!habitacion.isActivo());
                this.habitacionRepositorio.save(habitacion);
            }else{
                throw new Exception();
            }
            return true;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public List<Habitacion> findAllByActivo() throws Exception {
        try {
            List<Habitacion> entities = this.habitacionRepositorio.findAllByActivo();
            return entities;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Transactional
    public Habitacion findByIdAndActivo(long id) throws Exception {
        try {
            Optional<Habitacion> opt = this.habitacionRepositorio.findByIdAAndActivo(id);
            return opt.get();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Transactional
    public List<Habitacion> encontrarHabitacionesDisponibles(String descripcion, LocalDate fechaEntrada,
                                                             LocalDate fechaSalida) throws Exception {
        try {
            return habitacionRepositorio.encontrarHabitacionesDisponibles(descripcion, fechaEntrada, fechaSalida);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
