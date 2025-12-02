package com.example.demo.repositorio;

import com.example.demo.entidades.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  AppUserRepository extends JpaRepository<AppUser, Integer> {
    @Query("SELECT u from AppUser u WHERE u.email = :email")
    public AppUser findByEmail(@Param("email") String email);

    @Query("UPDATE AppUser u SET u.failedattempt = ?1 WHERE u.email = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String email);

    @Query("UPDATE AppUser u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    public void updateEnabledStatus(@Param("id") Integer id,@Param("enabled") boolean enabled);

    @Query(value = "SELECT * FROM users WHERE users.enabled=true", nativeQuery = true)
    List<AppUser> findAllByActivo();
}
