package com.example.demo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;

    @Column (unique = true, nullable = false)
    private String email;

    private String phone;
    private String address;
    private String password;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechanacimiento;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean enabled = true;

    @Column(name = "accountnonlocked", nullable = false, columnDefinition = "boolean default true")
    private boolean accountnonlocked = true;

    @Column(name = "failedattempt")
    private int failedattempt;

    @Column(name = "locktime")
    private Date locktime;

    // Nuevos campos migrados de Cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_documento")
    private Documento documento;

    private String numeroDocumento;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Reserva> reservas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id") )
    private Set<Rol> roles = new HashSet<>();

    public void addRole(Rol role) {
        this.roles.add(role);
    }
}