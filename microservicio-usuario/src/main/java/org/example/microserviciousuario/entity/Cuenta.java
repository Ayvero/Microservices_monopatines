package org.example.microserviciousuario.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;

    @NotNull(message = "La fecha de alta no puede ser nula")
  //  @FutureOrPresent(message = "La fecha de alta debe ser actual o futura")
    private LocalDateTime fechaAlta;

    @NotNull(message = "El saldo no puede ser nulo")
    @Min(value = 0, message = "El saldo debe ser mayor o igual a 0")
    private Double saldo;

    @NotEmpty(message = "El ID de Mercado Pago no puede estar vacío")
    private String idMercadoPago;

    private boolean anulada;

    @ManyToMany
    @JoinTable(
            name = "usuario_cuenta",
            joinColumns = @JoinColumn(name = "cuenta_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();


    public void agregarUsuario(Usuario usuario) {
        if (!this.usuarios.contains(usuario)) {
            this.usuarios.add(usuario);
            usuario.getCuentasApp().add(this);
        }
    }
}
