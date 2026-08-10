package com.concessionaria.Controller;

import com.concessionaria.Repository.CarroRepository;
import com.concessionaria.exception.RecursoNaoEncontradoException;
import com.concessionaria.model.Carro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carros")
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;

    @PostMapping
    public ResponseEntity<Carro> cadastrar(@RequestBody Carro carro) {
        carro.setId(null);
        Carro salvo = carroRepository.save(carro);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Carro> listar() {
        return carroRepository.findAll();
    }

    @GetMapping("/{id}")
    public Carro buscarPorId(@PathVariable Integer id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Carro nao encontrado: id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        if (!carroRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Carro nao encontrado: id " + id);
        }
        carroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
