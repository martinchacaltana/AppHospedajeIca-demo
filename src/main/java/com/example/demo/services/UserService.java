package com.example.demo.services;

import com.example.demo.entidades.AppUser;
import com.example.demo.entidades.Rol;
import com.example.demo.repositorio.AppUserRepository;
import com.example.demo.repositorio.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class UserService {
    public static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 2 * 60 * 1000;

    @Autowired
    private AppUserRepository repo;
    @Autowired
    private RoleRepository roleRepository;

    public void increaseFailedAttempts(AppUser user) {
        int newFailAttempts = user.getFailedattempt() + 1;
        repo.updateFailedAttempts(newFailAttempts, user.getEmail());
        System.out.println("Intentos fallidos incrementados para el usuario: " + user.getEmail());
    }

    public void resetFailedAttempts(String email) {
        repo.updateFailedAttempts(0, email);
        System.out.println("Intentos fallidos reseteados para el usuario: " + email);
    }

    public void lock(AppUser user) {
        user.setAccountnonlocked(false);
        user.setLocktime(new Date());
        repo.save(user);
        System.out.println("Usuario bloqueado: " + user.getEmail());
    }

    public boolean unlockWhenTimeExpired(AppUser user) {
        long lockTimeInMillis = user.getLocktime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountnonlocked(true);
            user.setLocktime(null);
            user.setFailedattempt(0);

            repo.save(user);

            return true;
        }

        return false;
    }

    public AppUser getByEmail(String email) {
        return repo.findByEmail(email);
    }
    public AppUser findbyUsername(String username) {
        return repo.findByEmail(username);
    }
}
