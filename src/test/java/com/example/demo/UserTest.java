package com.example.demo;

import com.example.demo.entidades.AppUser;
import com.example.demo.entidades.Rol;
import com.example.demo.repositorio.AppUserRepository;
import com.example.demo.repositorio.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserTest {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private RoleRepository roleRepository;
    @Test
    public void testAddRoleToUser(){
        AppUser appUser = new AppUser();
        appUser.setEmail("hola@gmail.com");
        appUser.setPassword("123456");
        appUser.setFirstName("Hola");
        appUser.setLastName("Mundo");

        Rol rol= roleRepository.findByName("User");
        appUser.addRole(rol);

        AppUser user= appUserRepository.save(appUser);
        assertThat(user.getRoles().size()).isEqualTo(1);
    }

    @Test
    public void testAddRoleToExistingUser(){
        AppUser appUser=appUserRepository.findById(1).get();
        Rol roluser= roleRepository.findByName("User");
        appUser.addRole(roluser);
        Rol roladmin = new Rol(2L);
        appUser.addRole(roladmin);
        AppUser user= appUserRepository.save(appUser);
        assertThat(user.getRoles().size()).isEqualTo(2);
    }
}
