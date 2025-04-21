package org.example.microserviciousuario.service;


import org.example.microserviciousuario.entity.Usuario;
import org.example.microserviciousuario.exception.EmailAlreadyExistsException;
import org.example.microserviciousuario.exception.InvalidEmailException;
import org.example.microserviciousuario.exception.UsuarioNotFoundException;
import org.example.microserviciousuario.foreinClient.MonopatinClient;
import org.example.microserviciousuario.repository.UsuarioRepository;

import org.example.microserviciousuario.service.mapper.UsuarioMapper;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.ReporteMonopatinesCercanosDTO;
import org.example.shareddto.DTO.entity.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MonopatinClient monopatinClient;
    @Autowired
    private UsuarioMapper usuarioMapper;


    //Crear Usuarios
    public List<Usuario> agregarUsuarios(List<UsuarioDTO> usuariosDTO) {
        List<Usuario> usuarios = usuariosDTO.stream()
                .map(usuarioMapper::mapToEntity)
                .collect(Collectors.toList());

        return usuarioRepository.saveAll(usuarios);
    }

    //Listar Usuarios
    public List<UsuarioDTO> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    //Crear Usuario
    public Usuario save(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getContrasenia() == null || usuarioDTO.getContrasenia().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        Usuario usuario = usuarioMapper.mapToEntity(usuarioDTO);
        return usuarioRepository.save(usuario);
    }

    //Buscar Usuario por Id
    public UsuarioDTO findById(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuarioMapper.mapToDTO(usuario);
    }

    //Modificar Usuario
    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
       Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

       if (usuarioDTO.getNombre() != null && !usuarioDTO.getNombre().trim().isEmpty()) {
            usuarioExistente.setNombre(usuarioDTO.getNombre());
        } else if (usuarioDTO.getNombre() != null && usuarioDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (usuarioDTO.getEmail() != null && !usuarioDTO.getEmail().trim().isEmpty()) {
            usuarioExistente.setEmail(usuarioDTO.getEmail());
        } else if (usuarioDTO.getEmail() != null && usuarioDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (usuarioDTO.getLatitud() != 0) {
            usuarioExistente.setLatitud(usuarioDTO.getLatitud());
        }
        if (usuarioDTO.getLongitud() != 0) {
            usuarioExistente.setLongitud(usuarioDTO.getLongitud());
        }
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);

        return usuarioMapper.mapToDTO(usuarioActualizado);
    }


    //Eliminar usuario
    public void delete(Long usuarioId) {
       Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    //Punto g- Listar monopatines cercanos
    public List<ReporteMonopatinesCercanosDTO> obtenerMonopatinesCercanos(Long usuarioId, double radio) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        double latitudUsuario = usuario.getLatitud();
        double longitudUsuario = usuario.getLongitud();

        ResponseEntity<List<MonopatinDTO>> response = monopatinClient.getAllMonopatines();

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<MonopatinDTO> todosLosMonopatines = response.getBody();
            List<ReporteMonopatinesCercanosDTO> monopatinesCercanos = new ArrayList<>();

            for (MonopatinDTO monopatin : todosLosMonopatines) {
                double distancia = calcularDistancia(latitudUsuario, longitudUsuario, monopatin.getLatitud(), monopatin.getLongitud());
                if (distancia <= radio) {
                    monopatinesCercanos.add(new ReporteMonopatinesCercanosDTO(
                            monopatin.getId(),
                            monopatin.getModelo(),
                            monopatin.getLatitud(),
                            monopatin.getLongitud()
                    ));
                }
            }
            return monopatinesCercanos;
        } else {
            throw new RuntimeException("Error al obtener la lista de monopatines.");
        }
    }


    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Devuelve la distancia en km
    }




}