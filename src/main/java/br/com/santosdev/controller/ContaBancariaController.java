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

    // 1. CREATE: POST /api/contas
    @PostMapping
    public ResponseEntity<ContaBancaria> criarConta(@RequestBody ContaBancaria conta) {
        ContaBancaria novaConta = service.criar(conta);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED); // Status 201
    }

    // 2. READ ALL: GET /api/contas
    @GetMapping
    public List<ContaBancaria> listarContas() {
        return service.listarTodos(); // Status 200
    }
    
    // 3. READ ONE: GET /api/contas/{id}
    @GetMapping("/{id}")
    public ContaBancaria buscarConta(@PathVariable Integer id) {
        // Lança ContaNaoEncontradaException, que é mapeada para 404 pelo ControllerAdvice
        return service.buscarPorId(id); 
    }
    
    // 4. UPDATE: PUT /api/contas/{id}
    @PutMapping("/{id}")
    public ContaBancaria atualizarConta(@PathVariable Integer id, @RequestBody ContaBancaria conta) {
        // Lança 404 se não encontrar ou 400 se o saldo for inválido
        return service.atualizar(id, conta);
    }
    
    // 5. DELETE: DELETE /api/contas/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status 204: Sucesso sem conteúdo de retorno
    public void deletarConta(@PathVariable Integer id) {
        service.deletar(id);
    }
}
