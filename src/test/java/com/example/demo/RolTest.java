package com.example.demo;

import com.example.demo.entidades.Rol;
import com.example.demo.repositorio.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RolTest {
    @Autowired
    RoleRepository roleRepository;

    @Test
    public void testCreateRol(){
        Rol user= new Rol("user");
        Rol admin= new Rol("admin");
        roleRepository.saveAll(List.of(user,admin));
        List<Rol> rols = roleRepository.findAll();
        assertThat(rols.size()).isEqualTo(2);
    }
}
