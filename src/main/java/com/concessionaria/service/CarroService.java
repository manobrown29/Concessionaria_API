package com.concessionaria.service;

import com.concessionaria.Repository.CarroRepository;
import com.concessionaria.dto.CarroRequestDTO;
import com.concessionaria.dto.CarroResponseDTO;
import com.concessionaria.dto.ClienteResumoDTO;
import com.concessionaria.exception.RecursoDuplicadoException;
import com.concessionaria.exception.RecursoNaoEncontradoException;
import com.concessionaria.model.Carro;
import com.concessionaria.model.StatusCarro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public CarroResponseDTO cadastrar(CarroRequestDTO dto) {
        if (carroRepository.existsByChassi(dto.chassi())) {
            throw new RecursoDuplicadoException("Já existe um carro cadastrado com o chassi " + dto.chassi());
        }
        if (dto.placa() != null && carroRepository.existsByPlaca(dto.placa())) {
            throw new RecursoDuplicadoException("Já existe um carro cadastrado com a placa " + dto.placa());
        }

        Carro carro = new Carro();
        carro.setModelo(dto.modelo());
        carro.setMarca(dto.marca());
        carro.setCor(dto.cor());
        carro.setAnoFabricacao(dto.anoFabricacao());
        carro.setAnoModelo(dto.anoModelo());
        carro.setPlaca(dto.placa());
        carro.setChassi(dto.chassi());
        carro.setQuilometragem(dto.quilometragem());
        carro.setPreco(dto.preco());
        carro.setStatus(StatusCarro.DISPONIVEL);
        carro.setCliente(null);

        return toResponseDTO(carroRepository.save(carro));
    }

    // Sem filtro nenhum -> lista tudo. Com cor e/ou anoFabricacao -> filtra.
    public List<CarroResponseDTO> listar(String cor, Integer anoFabricacao) {
        return carroRepository.buscarComFiltro(cor, anoFabricacao).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CarroResponseDTO buscarPorId(Integer id) {
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Carro não encontrado: id " + id));
        return toResponseDTO(carro);
    }

    public void remover(Integer id) {
        if (!carroRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Carro não encontrado: id " + id);
        }
        carroRepository.deleteById(id);
    }

    private CarroResponseDTO toResponseDTO(Carro carro) {
        ClienteResumoDTO clienteResumo = carro.getCliente() == null
                ? null
                : new ClienteResumoDTO(carro.getCliente().getId(), carro.getCliente().getNome());

        return new CarroResponseDTO(
                carro.getId(),
                carro.getModelo(),
                carro.getMarca(),
                carro.getCor(),
                carro.getAnoFabricacao(),
                carro.getAnoModelo(),
                carro.getPlaca(),
                carro.getChassi(),
                carro.getQuilometragem(),
                carro.getPreco(),
                carro.getStatus(),
                clienteResumo
        );
    }
}