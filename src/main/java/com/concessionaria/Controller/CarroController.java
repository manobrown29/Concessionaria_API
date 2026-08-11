package com.concessionaria.Controller;

import com.concessionaria.dto.CarroRequestDTO;
import com.concessionaria.dto.CarroResponseDTO;
import com.concessionaria.service.CarroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @PostMapping
    public ResponseEntity<CarroResponseDTO> cadastrar(@Valid @RequestBody CarroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carroService.cadastrar(dto));
    }

    @GetMapping
    public List<CarroResponseDTO> listar() {
        return carroService.listar();
    }

    @GetMapping("/{id}")
    public CarroResponseDTO buscarPorId(@PathVariable Integer id) {
        return carroService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        carroService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
