package org.example.shareddto.DTO.entity;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long usuarioId;
    private String contrasenia;
    private String nombre;
    private String apellido;
    private String celular;
    private String email;
    private double latitud;
    private double longitud;

    public UsuarioDTO(Long usuarioId, String contrasenia,String nombre, String apellido, String celular, String email, double latitud, double longitud) {
        this.usuarioId = usuarioId;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.email = email;
        this.latitud = latitud;
        this.longitud = longitud;
    }


}
