package org.example.microserviciousuario.service.mapper;
import org.example.microserviciousuario.entity.Usuario;
import org.example.shareddto.DTO.entity.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario mapToEntity(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usuarioDTO.getUsuarioId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCelular(usuarioDTO.getCelular());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setLatitud(usuarioDTO.getLatitud());
        usuario.setLongitud(usuarioDTO.getLongitud());
        usuario.setContrasenia(usuarioDTO.getContrasenia());

        return usuario;
    }

    public UsuarioDTO mapToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO(
                usuario.getUsuarioId(),
                usuario.getContrasenia(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCelular(),
                usuario.getEmail(),
                usuario.getLatitud(),
                usuario.getLongitud()
        );
    }
}
