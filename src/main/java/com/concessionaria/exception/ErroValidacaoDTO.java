package com.concessionaria.exception;

import java.util.List;

public record ErroValidacaoDTO(int status, List<ErroCampoDTO> erros) {
}