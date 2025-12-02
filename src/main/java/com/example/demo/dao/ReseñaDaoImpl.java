package com.example.demo.dao;

import com.example.demo.entidades.Reseña;
import com.example.demo.repositorio.ReseñaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReseñaDaoImpl {

    @Autowired
    private ReseñaRepositorio reseñaRepositorio;

    public List<Reseña> listar() {
        return reseñaRepositorio.findAll();
    }

    public Reseña guardar(Reseña reseña) {
        return reseñaRepositorio.save(reseña);
    }
}
