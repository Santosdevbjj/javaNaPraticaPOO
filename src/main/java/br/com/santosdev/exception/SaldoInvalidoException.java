package br.com.santosdev.exception;

/**
 * Exceção customizada para violação de regra de negócio (saldo negativo).
 * Mapeada para HTTP 400.
 */
public class SaldoInvalidoException extends RuntimeException {
    public SaldoInvalidoException(String message) {
        super(message);
    }
}
