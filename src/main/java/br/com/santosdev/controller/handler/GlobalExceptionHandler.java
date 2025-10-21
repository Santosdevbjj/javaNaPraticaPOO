package br.com.santosdev.controller.handler;

import br.com.santosdev.exception.ContaNaoEncontradaException;
import br.com.santosdev.exception.SaldoInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * @ControllerAdvice: Componente global para tratar exceções
 * lançadas por qualquer Controller no projeto e retornar códigos HTTP corretos.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Trata exceções de Regra de Negócio (ex: Saldo < 0)
    @ExceptionHandler(SaldoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleSaldoInvalido(SaldoInvalidoException ex) {
        return new ResponseEntity<>(
            Map.of("status", "400", "erro", "Erro de Negócio", "mensagem", ex.getMessage()),
            HttpStatus.BAD_REQUEST // HTTP 400
        );
    }

    // Trata exceções de recurso não encontrado
    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleContaNaoEncontrada(ContaNaoEncontradaException ex) {
        return new ResponseEntity<>(
            Map.of("status", "404", "erro", "Recurso Não Encontrado", "mensagem", ex.getMessage()),
            HttpStatus.NOT_FOUND // HTTP 404
        );
    }
    
    // Tratamento genérico para outras exceções não mapeadas (Opcional, mas recomendado)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeral(Exception ex) {
        return new ResponseEntity<>(
            Map.of("status", "500", "erro", "Erro Interno do Servidor", "mensagem", "Ocorreu um erro inesperado: " + ex.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR // HTTP 500
        );
    }
}
