package br.com.santosdev.exception;

/**
 * Exceção customizada para quando um recurso (Conta) não é encontrado.
 * Mapeada para HTTP 404.
 */
public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(Integer id) {
        super("Conta com ID " + id + " não foi encontrada.");
    }
}
