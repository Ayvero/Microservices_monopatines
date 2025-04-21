package org.example.microserviciousuario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasenia;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre no puede ser vacío")
    private String nombre;

    @NotNull(message = "El apellido no puede ser nulo")
    @NotEmpty(message = "El apellido no puede ser vacío")
    private String apellido;
    private String celular;

    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String email;

    @Min(value = -90, message = "La latitud debe ser mayor o igual a -90")
    @Max(value = 90, message = "La latitud debe ser menor o igual a 90")
    private double latitud;

    @Min(value = -180, message = "La longitud debe ser mayor o igual a -180")
    @Max(value = 180, message = "La longitud debe ser menor o igual a 180")
    private double longitud;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    private List<Cuenta> cuentasApp = new ArrayList<>();


    public void agregarCuenta(Cuenta cuenta) {
        if (!this.cuentasApp.contains(cuenta)) {
            this.cuentasApp.add(cuenta);
            cuenta.getUsuarios().add(this);
        }
    }

}