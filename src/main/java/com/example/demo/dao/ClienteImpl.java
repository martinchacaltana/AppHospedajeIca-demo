package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.entidades.AppUser;
import com.example.demo.repositorio.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ClienteImpl implements ServicioGenerico<AppUser>{
    @Autowired 
    private AppUserRepository appUserRepository;

    @Override
    @Transactional
    public List<AppUser> listar() throws Exception {
        try {
            return this.appUserRepository.findAllByActivo();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public AppUser buscarporId(long id) throws Exception {
        try {
            Optional<AppUser> obj = this.appUserRepository.findById((int) id);
            return obj.get();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public AppUser guardar(AppUser entity) throws Exception {
        try {
            AppUser cliente = this.appUserRepository.save(entity);
            return cliente;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public AppUser actualizar(AppUser entity, long id) throws Exception {
        try {
            Optional<AppUser> obj = this.appUserRepository.findById((int) id);
            if (obj.isPresent()) {
                AppUser cliente = obj.get();
                cliente.setFirstName(entity.getFirstName());
                cliente.setLastName(entity.getLastName());
                cliente.setEmail(entity.getEmail());
                cliente.setDocumento(entity.getDocumento());
                cliente.setNumeroDocumento(entity.getNumeroDocumento());
                cliente.setFechanacimiento(entity.getFechanacimiento());
                cliente.setPhone(entity.getPhone());
                // No actualizamos password aqui por seguridad, usualmente se hace en otro metodo
                return this.appUserRepository.save(cliente);
            }else{
                throw new Exception("Usuario no encontrado con id: " + id);            }
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean eliminarporId(long id) throws Exception {
        try {
            Optional<AppUser> obj = this.appUserRepository.findById((int) id);
            if(!obj.isEmpty()){
                AppUser cliente = obj.get();
                cliente.setEnabled(!cliente.isEnabled());
                this.appUserRepository.save(cliente);
            }else{
                throw new Exception();
            }
            return true;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}