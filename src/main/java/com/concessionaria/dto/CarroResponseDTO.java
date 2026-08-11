package com.concessionaria.dto;

import com.concessionaria.model.StatusCarro;
import java.math.BigDecimal;

public record CarroResponseDTO(
        Integer id,
        String modelo,
        String marca,
        String cor,
        Integer anoFabricacao,
        Integer anoModelo,
        String placa,
        String chassi,
        Integer quilometragem,
        BigDecimal preco,
        StatusCarro status,
        ClienteResumoDTO cliente
) {
}
