package org.example.microserviciogateway.service;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.microserviciogateway.entity.User;
import org.example.microserviciogateway.repository.AuthorityRepository;
import org.example.microserviciogateway.repository.UserRepository;
import org.example.microserviciogateway.service.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public long saveUser( UserDTO request ) {
        final var user = new User( request.getUsername() );
        user.setPassword( passwordEncoder.encode( request.getPassword() ) );
        final var roles =  this.authorityRepository.findAllById( request.getAuthorities() );
        user.setAuthorities( roles );
        final var result = this.userRepository.save( user );
        return result.getId();
    }
}
