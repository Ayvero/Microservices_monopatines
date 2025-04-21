package org.example.microserviciogateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.microserviciogateway.security.Jwt.JwtFilter;
import org.example.microserviciogateway.security.Jwt.TokenProvider;
import org.example.microserviciogateway.service.dto.LoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/authenticate" )
@RequiredArgsConstructor
public class JwtController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Operation(
            summary = "Autenticar usuario y obtener token JWT",
            description = "Este endpoint autentica a un usuario con su nombre de usuario y contraseña, " +
                    "y devuelve un token JWT para autenticar solicitudes subsecuentes."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticación exitosa y token generado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JWTToken.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Credenciales incorrectas",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    public ResponseEntity<JWTToken> authorize(
            @Parameter(
                    description = "Datos de inicio de sesión del usuario (nombre de usuario y contraseña)",
                    required = true,
                    schema = @Schema(implementation = LoginDTO.class)
            )
            @Valid @RequestBody LoginDTO request ) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate( authenticationToken );
        SecurityContextHolder.getContext().setAuthentication( authentication );
        final var jwt = tokenProvider.createToken( authentication );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add( JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt );
        return new ResponseEntity<>( new JWTToken( jwt ), httpHeaders, HttpStatus.OK );
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
