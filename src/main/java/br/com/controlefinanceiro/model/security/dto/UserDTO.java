package br.com.controlefinanceiro.model.security.dto;

import br.com.controlefinanceiro.model.security.Role;

import java.util.List;

public record UserDTO(Long id, String email, List<Role> roles) {
}
