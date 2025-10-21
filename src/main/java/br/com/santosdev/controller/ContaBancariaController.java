package br.com.santosdev.controller;

import br.com.santosdev.model.ContaBancaria;
import br.com.santosdev.service.ContaBancariaService;
import jakarta.validation.Valid; // NOVO: Importação para Bean Validation
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller RESTful. Usa @Valid para ativar a validação automática (Bean Validation).
 */
@RestController
@RequestMapping("/api/contas")
public class ContaBancariaController {

    private final ContaBancariaService service;
    
    public ContaBancariaController(ContaBancariaService service) {
        this.service = service;
    }

    // 1. CREATE: POST /api/contas
    @PostMapping
    public ResponseEntity<ContaBancaria> criarConta(@Valid @RequestBody ContaBancaria conta) { // NOVO: @Valid
        ContaBancaria novaConta = service.criar(conta);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED); 
    }

    // 2. READ ALL: GET /api/contas
    @GetMapping
    public List<ContaBancaria> listarContas() {
        return service.listarTodos();
    }
    
    // 3. READ ONE: GET /api/contas/{id}
    @GetMapping("/{id}")
    public ContaBancaria buscarConta(@PathVariable Integer id) {
        return service.buscarPorId(id); 
    }
    
    // 4. UPDATE: PUT /api/contas/{id}
    @PutMapping("/{id}")
    public ContaBancaria atualizarConta(@PathVariable Integer id, @Valid @RequestBody ContaBancaria conta) { // NOVO: @Valid
        return service.atualizar(id, conta);
    }
    
    // 5. DELETE: DELETE /api/contas/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarConta(@PathVariable Integer id) {
        service.deletar(id);
    }
}
