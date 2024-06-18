package br.com.controlefinanceiro.controller;


import br.com.controlefinanceiro.model.security.dto.CreateUserDTO;
import br.com.controlefinanceiro.model.security.dto.JwtTokenDTO;
import br.com.controlefinanceiro.model.security.dto.LoginUserDTO;
import br.com.controlefinanceiro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUsuario(@RequestBody LoginUserDTO loginUserDto) {
        JwtTokenDTO token = userService.autenticarUsuario(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> salvarUsuario(@RequestBody CreateUserDTO createUserDto) {
        userService.salvarUsuario(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
