package org.example.microserviciousuario;

import org.example.microserviciousuario.entity.Usuario;
import org.example.microserviciousuario.foreinClient.MonopatinClient;
import org.example.microserviciousuario.repository.UsuarioRepository;
import org.example.microserviciousuario.service.UsuarioService;
import org.example.microserviciousuario.service.mapper.UsuarioMapper;
import org.example.shareddto.DTO.ReporteMonopatinesCercanosDTO;
import org.example.shareddto.DTO.entity.MonopatinDTO;
import org.example.shareddto.DTO.entity.UsuarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private MonopatinClient monopatinClient;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgregarUsuarios() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                1L,               // usuarioId
                "password123",     // contrasenia
                "Juan",           // nombre
                "Pérez",          // apellido
                "123456789",      // celular
                "juan@example.com", // email
                10.0,             // latitud
                20.0              // longitud
        );
        Usuario usuario = new Usuario();
            usuario.setNombre("Juan");
            usuario.setEmail("juan@example.com");
            usuario.setLatitud(10.0);
            usuario.setLongitud(20.0);
            usuario.setContrasenia("password");
        when(usuarioMapper.mapToEntity(usuarioDTO)).thenReturn(usuario);
        when(usuarioRepository.saveAll(anyList())).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.agregarUsuarios(List.of(usuarioDTO));

        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        verify(usuarioRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFindAll() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setEmail("juan@example.com");
        usuario.setLatitud(10.0);
        usuario.setLongitud(20.0);
        usuario.setContrasenia("password");
       UsuarioDTO usuarioDTO = new UsuarioDTO(
               1L,               // usuarioId
               "password123",     // contrasenia
               "Juan",           // nombre
               "Pérez",          // apellido
               "123456789",      // celular
               "juan@example.com", // email
               10.0,             // latitud
               20.0              // longitud
        );
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioMapper.mapToDTO(usuario)).thenReturn(usuarioDTO);

        List<UsuarioDTO> usuarios = usuarioService.findAll();

        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Usuario usuario = new Usuario();
            usuario.setNombre("Juan");
            usuario.setEmail("juan@example.com");
            usuario.setLatitud(10.0);
            usuario.setLongitud(20.0);
            usuario.setContrasenia("password");
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                1L,               // usuarioId
                "password123",     // contrasenia
                "Juan",           // nombre
                "Pérez",          // apellido
                "123456789",      // celular
                "juan@example.com", // email
                10.0,             // latitud
                20.0              // longitud
        );
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.mapToDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.findById(1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_UsuarioNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> usuarioService.findById(1L));

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerMonopatinesCercanos() {
        Usuario usuario = new Usuario();
            usuario.setNombre("Juan");
            usuario.setEmail("juan@example.com");
            usuario.setLatitud(10.0);
            usuario.setLongitud(20.0);
            usuario.setContrasenia("password");
        MonopatinDTO monopatinDTO1 = new MonopatinDTO();
            monopatinDTO1.setId(1L);
            monopatinDTO1.setModelo("Modelo A");
            monopatinDTO1.setLatitud(-34.603722);
            monopatinDTO1.setLongitud(-58.381592);
        MonopatinDTO monopatinDTO2 = new MonopatinDTO();
            monopatinDTO2.setId(2L);                  // ID del monopatín
            monopatinDTO2.setModelo("Modelo b");      // Modelo del monopatín
            monopatinDTO2.setLatitud(-54.603722);     // Latitud
            monopatinDTO2.setLongitud(-58.381592);    // Longitud
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(monopatinClient.getAllMonopatines()).thenReturn(ResponseEntity.ok(Arrays.asList(monopatinDTO1, monopatinDTO2)));

        List<ReporteMonopatinesCercanosDTO> resultado = usuarioService.obtenerMonopatinesCercanos(1L, 5.0);

        assertEquals(1, resultado.size());
        assertEquals("Modelo1", resultado.get(0).getModelo());
        verify(monopatinClient, times(1)).getAllMonopatines();
    }
}
