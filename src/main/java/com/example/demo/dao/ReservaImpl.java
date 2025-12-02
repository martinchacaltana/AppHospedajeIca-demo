package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.entidades.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidades.Reserva;
import com.example.demo.repositorio.ReservaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ReservaImpl implements ServicioGenerico<Reserva>{
    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Override
    @Transactional
    public List<Reserva> listar() throws Exception {
        try {
            List<Reserva> lista = this.reservaRepositorio.findAllByActivo();
            return lista;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Reserva buscarporId(long id) throws Exception {
        try {
            Optional<Reserva> obj = this.reservaRepositorio.findById(id);
            return obj.get();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Reserva guardar(Reserva entity) throws Exception {
        try {
            return reservaRepositorio.save(entity);
        } catch (Exception e) {
            throw new Exception("Error al guardar la reserva: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Reserva actualizar(Reserva entity, long id) throws Exception {
        try {
            Optional<Reserva> obj = this.reservaRepositorio.findById(id);
            if (obj.isPresent()) {
                Reserva reserva = obj.get();
                reserva.setCliente(entity.getCliente());
                reserva.setHabitacion(entity.getHabitacion());
                reserva.setCantidadpersonas(entity.getCantidadpersonas());
                reserva.setFechaentrada(entity.getFechaentrada());
                reserva.setFechasalida(entity.getFechasalida());
                reserva.setEstadoreserva(entity.isEstadoreserva());
                return this.reservaRepositorio.save(reserva);
            }else{
                throw new Exception("Documento no encontrada con id: " + id);            }
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean eliminarporId(long id) throws Exception {
        try {
            Optional<Reserva> obj = this.reservaRepositorio.findById(id);
            if(!obj.isEmpty()){
                Reserva reserva = obj.get();
                reserva.setActivo(!reserva.isActivo());
                this.reservaRepositorio.save(reserva);
            }else{
                throw new Exception();
            }
            return true;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Reserva> obtenerReservasPorUsuario(Integer clienteid) {
        return reservaRepositorio.findByCliente_Id(clienteid);
    }
}
