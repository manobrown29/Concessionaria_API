package com.concessionaria.Controller;

import com.concessionaria.dto.CarroRequestDTO;
import com.concessionaria.dto.CarroResponseDTO;
import com.concessionaria.service.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Carros", description = "Cadastro, consulta e remoção de veículos do estoque")
@RestController
@RequestMapping("/carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @Operation(
            summary = "Cadastra um carro",
            description = "Cadastra um carro novo ou seminovo. O carro sempre nasce com status DISPONIVEL e sem cliente vinculado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Carro cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ano fora da faixa, preço menor ou igual a zero, chassi/placa em formato errado, etc.)"),
            @ApiResponse(responseCode = "409", description = "Já existe um carro cadastrado com o mesmo chassi ou placa")
    })
    @PostMapping
    public ResponseEntity<CarroResponseDTO> cadastrar(@Valid @RequestBody CarroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carroService.cadastrar(dto));
    }

    @Operation(
            summary = "Busca carros no estoque",
            description = "Retorna todos os carros. Pode ser filtrado por cor e/ou ano de fabricação; se nenhum filtro for informado, lista todos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro de filtro inválido")
    })
    @GetMapping
    public List<CarroResponseDTO> listar(
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) Integer anoFabricacao) {
        return carroService.listar(cor, anoFabricacao);
    }

    @Operation(summary = "Busca um carro por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carro encontrado"),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    })
    @GetMapping("/{id}")
    public CarroResponseDTO buscarPorId(@PathVariable Integer id) {
        return carroService.buscarPorId(id);
    }

    @Operation(summary = "Remove um carro do estoque")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Carro removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        carroService.remover(id);
        return ResponseEntity.noContent().build();
    }
}