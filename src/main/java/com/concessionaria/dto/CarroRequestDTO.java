package com.concessionaria.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CarroRequestDTO(

        @NotBlank(message = "Modelo é obrigatório")
        String modelo,

        @NotBlank(message = "Marca é obrigatória")
        String marca,

        @NotBlank(message = "Cor é obrigatória")
        String cor,

        @NotNull(message = "Ano de fabricação é obrigatório")
        @Min(value = 1950, message = "Ano de fabricação inválido")
        @Max(value = 2026, message = "deve ser menor ou igual a 2026")
        Integer anoFabricacao,

        @NotNull(message = "Ano do modelo é obrigatório")
        @Min(value = 1950, message = "Ano do modelo inválido")
        @Max(value = 2027, message = "deve ser menor ou igual a 2027")
        Integer anoModelo,

        String placa,

        @NotBlank(message = "Chassi é obrigatório")
        @Size(min = 17, max = 17, message = "Chassi deve ter exatamente 17 caracteres")
        String chassi,

        @NotNull(message = "Quilometragem é obrigatória")
        @PositiveOrZero(message = "Quilometragem não pode ser negativa")
        Integer quilometragem,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "deve ser maior que 0")
        BigDecimal preco
) {
}
