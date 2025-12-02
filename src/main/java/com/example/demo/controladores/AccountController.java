package com.example.demo.controladores;

import com.example.demo.entidades.AppUser;
import com.example.demo.entidades.RegisterDto;
import com.example.demo.entidades.Rol;
import com.example.demo.repositorio.AppUserRepository;
import com.example.demo.repositorio.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AccountController {

    @Autowired
    private AppUserRepository repo;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/registro")
    public String registro(Model model){
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/registro")
    public String registro(
            Model model,
            @Valid @ModelAttribute RegisterDto registerDto,
            BindingResult result
    ){

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
            result.addError(
                    new FieldError("registerDto","confirmPassword"
                            , "Password and Confirm Password do not match")
            );
        }

        AppUser appUser = repo.findByEmail(registerDto.getEmail());
        if (appUser != null){
            result.addError(
                    new FieldError("registerDto", "email"
                            , "Email address is alredy used")
            );
        }

        if (result.hasErrors()){
            return "register";
        }

        try{
            //Created new Account
            var bCryptEncoder = new BCryptPasswordEncoder();

            AppUser newUser = new AppUser();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setCreatedAt(new Date());
            newUser.setFechanacimiento(registerDto.getFechanacimiento());
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
            Rol rol=roleRepository.findByName("User");
            newUser.addRole(rol);
            repo.save(newUser);

            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("Success", true);

        }
        catch (Exception ex){
            result.addError(
                    new FieldError("registerDto", "firstName"
                            , ex.getMessage())
            );
        }
        return "register";
    }
}
