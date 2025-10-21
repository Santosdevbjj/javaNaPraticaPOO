package br.com.santosdev.controller;

import br.com.santosdev.model.ContaBancaria;
import br.com.santosdev.service.ContaBancariaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller RESTful - Recebe requisições HTTP e delega ao Service.
 */
@RestController
@RequestMapping("/api/contas")
public class ContaBancariaController {

    private final ContaBancariaService service;
    
    public ContaBancariaController(ContaBancariaService service) {
        this.service = service;
    }

    // CREATE: POST http://localhost:8080/api/contas
    @PostMapping
    public ResponseEntity<ContaBancaria> criarConta(@RequestBody ContaBancaria conta) {
        ContaBancaria novaConta = service.criar(conta);
        // Retorna status 201 Created
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED); 
    }

    // READ ALL: GET http://localhost:8080/api/contas
    @GetMapping
    public List<ContaBancaria> listarContas() {
        return service.listarTodos(); // Retorna status 200 OK
    }
    
    // READ ONE: GET http://localhost:8080/api/contas/{id}
    @GetMapping("/{id}")
    public ContaBancaria buscarConta(@PathVariable Integer id) {
        return service.buscarPorId(id); // Retorna status 200 OK, ou 404 (via Exception Handler)
    }
}
