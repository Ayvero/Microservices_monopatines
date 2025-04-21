package org.example.microserviciousuario.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.microserviciousuario.entity.Usuario;
import org.example.microserviciousuario.service.UsuarioService;
import org.example.shareddto.DTO.ReporteMonopatinesCercanosDTO;
import org.example.shareddto.DTO.entity.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @Operation(summary = "Crear múltiples usuarios", description = "Este endpoint permite crear varios usuarios a la vez.")
    @ApiResponse(responseCode = "201", description = "Usuarios creados exitosamente.")
    @PostMapping("/crearUsuarios")
    public ResponseEntity<List<Usuario>> crearUsuarios(@RequestBody List<UsuarioDTO> usuariosDTO) {
        List<Usuario> usuariosCreado = usuarioService.agregarUsuarios(usuariosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariosCreado);
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Este endpoint devuelve una lista de todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente.")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios() {
        List<UsuarioDTO> usuariosDTO = usuarioService.findAll();
        return ResponseEntity.ok(usuariosDTO); // Devuelve la lista de usuarios
    }

    @Operation(summary = "Crear usuario", description = "Este endpoint permite crear un nuevo usuario.")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente.")
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario nuevoUsuario = usuarioService.save(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @Operation(summary = "Obtener usuario por ID", description = "Este endpoint devuelve los detalles de un usuario utilizando su ID.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Long id) {
        UsuarioDTO suarioDTO = usuarioService.findById(id);
        return new ResponseEntity<>(suarioDTO, HttpStatus.OK);
    }


    @Operation(summary = "Actualizar usuario", description = "Este endpoint permite actualizar los detalles de un usuario existente.")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioActualizado = usuarioService.update(id, usuarioDTO);
        return usuarioActualizado != null ? ResponseEntity.ok(usuarioActualizado) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar usuario", description = "Este endpoint permite eliminar un usuario utilizando su ID.")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente.")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

   //Punto g -Endpoint para listar monopatines cercanos
   @Operation(summary = "Obtener monopatines cercanos", description = "Este endpoint permite obtener una lista de monopatines cercanos a un usuario en un radio determinado.")
   @ApiResponse(responseCode = "200", description = "Lista de monopatines cercanos obtenida exitosamente.")
   @ApiResponse(responseCode = "400", description = "Parámetro de radio inválido.")
   @GetMapping("/{usuarioId}/monopatines-cercanos")
   public ResponseEntity<List<ReporteMonopatinesCercanosDTO>> obtenerMonopatinesCercanos(
           @PathVariable Long usuarioId,
           @RequestParam(defaultValue = "5") double radio) { // Radio por defecto de 5 km

       List<ReporteMonopatinesCercanosDTO> monopatinesCercanos = usuarioService.obtenerMonopatinesCercanos(usuarioId, radio);
       return ResponseEntity.ok(monopatinesCercanos);
   }
}
