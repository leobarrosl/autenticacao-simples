package com.leobarrosl.auth.models.dto;

public record RegisterDTO(String senha, String nome, String sobrenome, String email, String telefone) {

    public boolean isValid() {
        return this.senha().length() >= 8 &&
                !this.nome().isEmpty() &&
                !this.sobrenome().isEmpty() &&
                !this.email().isEmpty() &&
                !this.telefone().isEmpty();
    }
}
