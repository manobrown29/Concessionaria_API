package com.concessionaria.service;

import com.concessionaria.Repository.ClienteRepository;
import com.concessionaria.dto.ClienteRequestDTO;
import com.concessionaria.dto.ClienteResponseDTO;
import com.concessionaria.exception.RecursoDuplicadoException;
import com.concessionaria.exception.RecursoNaoEncontradoException;
import com.concessionaria.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteResponseDTO cadastrar(ClienteRequestDTO dto) {
        if (clienteRepository.existsByCpf(dto.cpf())) {
            throw new RecursoDuplicadoException("Já existe um cliente cadastrado com o CPF " + dto.cpf());
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());

        return toResponseDTO(clienteRepository.save(cliente));
    }

    public List<ClienteResponseDTO> listar() {
        return clienteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ClienteResponseDTO buscarPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: id " + id));
        return toResponseDTO(cliente);
    }

    public void remover(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado: id " + id);
        }
        clienteRepository.deleteById(id);
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail()
        );
    }
}
