package br.com.controlefinanceiro.services;

import br.com.controlefinanceiro.config.SecurityConfig;
import br.com.controlefinanceiro.model.security.Role;
import br.com.controlefinanceiro.model.security.User;
import br.com.controlefinanceiro.model.security.UserDetailsImpl;
import br.com.controlefinanceiro.model.security.dto.CreateUserDTO;
import br.com.controlefinanceiro.model.security.dto.JwtTokenDTO;
import br.com.controlefinanceiro.model.security.dto.LoginUserDTO;
import br.com.controlefinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    public void salvarUsuario(CreateUserDTO createUserDto) {
        User newUser = User.builder()
                .email(createUserDto.email())
                .password(securityConfig.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        userRepository.save(newUser);
    }

    public JwtTokenDTO autenticarUsuario(LoginUserDTO loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }
}
