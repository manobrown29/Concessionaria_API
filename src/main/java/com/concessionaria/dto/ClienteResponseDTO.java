package com.concessionaria.dto;


public record ClienteResponseDTO(
        Integer id,
        String nome,
        String cpf,
        String telefone,
        String email
) {
}
