package org.example.microserviciogateway.config;


import org.example.microserviciogateway.security.AuthotityConstant;
import org.example.microserviciogateway.security.Jwt.JwtFilter;
import org.example.microserviciogateway.security.Jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http ) throws Exception {
        http
                .csrf( AbstractHttpConfigurer::disable );
        http
                .sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
       http
                .securityMatcher("/api/**" )
                .authorizeHttpRequests( authz -> authz
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll() // Login abierto
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Login abierto
                        // Usuarios
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/{usuarioId}/monopatines-cercanos").hasAuthority(AuthotityConstant._USER) // Solo USER puede ver monopatines cercanos
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").hasAuthority(AuthotityConstant._ADMIN) // Eliminar usuario solo para ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/crearUsuarios").hasAuthority(AuthotityConstant._ADMIN) // Crear múltiples usuarios solo para ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/").hasAuthority(AuthotityConstant._ADMIN) // ADMIN ve todos los usuarios

                        .requestMatchers("/api/usuarios/**").hasAnyAuthority(AuthotityConstant._USER, AuthotityConstant._ADMIN) // Ver perfil propio(USER-ADMIN)

                        // **Cuentas**
                        .requestMatchers(HttpMethod.PUT, "/api/cuentas/{id}/descontar").hasAuthority(AuthotityConstant._ADMIN) // Solo los administradores pueden descontar saldo de cualquier cuenta.
                        .requestMatchers(HttpMethod.PUT, "/api/cuentas/{cuentaId}/anular").hasAuthority(AuthotityConstant._ADMIN) // Solo los administradores pueden anular cuentas.

                        .requestMatchers( "/api/cuentas/**").hasAnyAuthority(AuthotityConstant._USER, AuthotityConstant._ADMIN) // Ambos, usuarios y administradores

                        // **Administradores**
                        .requestMatchers("/api/administrador/**").hasAuthority(AuthotityConstant._ADMIN) // Endpoints de administrador (ajustes, tarifas, mantenimiento, etc.)


                        // **Monopatines** -
                        .requestMatchers(HttpMethod.POST, "/api/monopatines").hasAuthority(AuthotityConstant._ADMIN) // Crear monopatín
                        .requestMatchers(HttpMethod.PUT, "/api/monopatines/{id}").hasAuthority(AuthotityConstant._ADMIN) // Modificar monopatín
                        .requestMatchers(HttpMethod.DELETE, "/api/monopatines/{id}").hasAuthority(AuthotityConstant._ADMIN) // Eliminar monopatín

                        .requestMatchers("/api/monopatines/**").hasAnyAuthority(AuthotityConstant._USER, AuthotityConstant._ADMIN)

                        // **Viajes** -
                        .requestMatchers(HttpMethod.DELETE, "/api/viajes/{id}").hasAuthority(AuthotityConstant._ADMIN) // Eliminar viaje

                        .requestMatchers( "/api/viajes/**").hasAnyAuthority(AuthotityConstant._USER, AuthotityConstant._ADMIN) // USER-ADMIN

                        // **Pausas** -
                        .requestMatchers("/api/pausas/**").hasAnyAuthority(AuthotityConstant._USER, AuthotityConstant._ADMIN) // Crear pausa

                        // **Mantenimiento** -
                        .requestMatchers( "/api/mantenimiento/**").hasAuthority(AuthotityConstant._MANTEN) //

                        // **Administrador**
                        .requestMatchers("/api/tarifas/**").hasAuthority(AuthotityConstant._ADMIN) //


                        // **Paradas** - Reglas para paradas (solo ADMIN)
                        .requestMatchers("/api/paradas/**").hasAuthority(AuthotityConstant._ADMIN) //

                        .anyRequest().authenticated()
                )
                .httpBasic( Customizer.withDefaults() )
                .addFilterBefore( new JwtFilter( this.tokenProvider ), UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

}
