package br.com.controlefinanceiro.model.security.dto;

import br.com.controlefinanceiro.model.enums.EnumRole;

public record CreateUserDTO(String email, String password, EnumRole role) {
}
