package br.com.santosdev.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD - Teste Unitário da Model: Testa o objeto ContaBancaria isoladamente.
 * Foca no Encapsulamento, Estado e uso correto do BigDecimal.
 */
public class ContaBancariaTest {

    private ContaBancaria conta;
    private final BigDecimal SALDO_INICIAL = new BigDecimal("1000.00");

    @BeforeEach
    public void setup() {
        conta = new ContaBancaria("Sérgio", SALDO_INICIAL);
    }

    @Test
    void deveCriarContaCorretamente() {
        // Assert: Compara BigDecimal para garantir precisão
        assertEquals("Sérgio", conta.getTitular());
        assertTrue(conta.getSaldo().compareTo(SALDO_INICIAL) == 0, 
                   "O saldo inicial deve ser 1000.00.");
    }

    @Test
    void deveAtualizarSaldoCorretamente() {
        // Arrange
        BigDecimal novoSaldo = new BigDecimal("1500.55");
        
        // Act
        conta.setSaldo(novoSaldo);
        
        // Assert
        assertTrue(conta.getSaldo().compareTo(novoSaldo) == 0, 
                   "O saldo deve ser atualizado para o novo valor.");
    }

    @Test
    void deveAlterarTitular() {
        // Act
        conta.setTitular("Ana Carolina");
        
        // Assert
        assertEquals("Ana Carolina", conta.getTitular());
    }
    
    // Este teste apenas confirma que a Model em si não impede valores negativos, 
    // delegando essa responsabilidade ao Bean Validation no Controller.
    @Test
    void modelNaoImpedeSaldoNegativoViaSetter() {
        // Arrange
        BigDecimal saldoNegativo = new BigDecimal("-500.00");
        
        // Act
        conta.setSaldo(saldoNegativo);
        
        // Assert
        assertTrue(conta.getSaldo().compareTo(BigDecimal.ZERO) < 0, 
                   "A validação de saldo negativo é uma Responsabilidade do Controller/Service.");
    }
}
