package br.com.santosdev.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal; // Importação essencial
import java.math.RoundingMode; // Importação para precisão

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste unitário para ContaBancaria.
 * * Atualizada para usar BigDecimal, garantindo precisão financeira.
 * O foco é testar a integridade do estado da Model, e não as regras
 * de negócio (que agora estão no Controller/Service com Bean Validation).
 */
public class ContaBancariaTest {

    private ContaBancaria conta;
    private final BigDecimal SALDO_INICIAL = new BigDecimal("1000.00");
    private final BigDecimal DELTA = new BigDecimal("0.001"); // Margem de erro para BigDecimal

    /**
     * Prepara uma nova conta antes de cada teste.
     */
    @BeforeEach
    public void setup() {
        // CORREÇÃO: Usando BigDecimal no construtor
        conta = new ContaBancaria("Sérgio", SALDO_INICIAL);
    }

    /**
     * Testa se o objeto ContaBancaria é criado corretamente.
     */
    @Test
    public void deveCriarContaCorretamente() {
        // Assert: Verifica se o titular e o saldo foram definidos
        assertEquals("Sérgio", conta.getTitular());
        
        // CORREÇÃO: Usando assertEquals com BigDecimal para precisão total
        assertTrue(conta.getSaldo().compareTo(SALDO_INICIAL) == 0, "O saldo deve ser igual ao inicial.");
    }

    /**
     * Testa a atualização do saldo via setter.
     * OBS: Em um modelo OO ideal, teríamos métodos 'depositar' e 'sacar'.
     */
    @Test
    public void deveAtualizarSaldoCorretamente() {
        // Arrange
        BigDecimal novoSaldo = new BigDecimal("1500.55");
        
        // Act
        conta.setSaldo(novoSaldo);
        
        // Assert
        assertTrue(conta.getSaldo().compareTo(novoSaldo) == 0, "O saldo deve ser atualizado para o novo valor.");
    }

    /**
     * Testa se é possível alterar o titular da conta.
     */
    @Test
    public void deveAlterarTitular() {
        // Act
        conta.setTitular("Ana Carolina");
        
        // Assert
        assertEquals("Ana Carolina", conta.getTitular());
    }

    /**
     * Teste para verificar se o toString() exibe as informações esperadas.
     */
    @Test
    public void deveExibirToStringCorretamente() {
        // Act
        String representacao = conta.toString();
        
        // Assert
        assertTrue(representacao.contains("Sérgio"));
        // Verifica se o valor com precisão está na String
        assertTrue(representacao.contains(SALDO_INICIAL.toPlainString())); 
    }
    
    /**
     * Teste de comportamento inválido na Model: 
     * A Model aceita qualquer valor, mas a validação ocorre no Controller (Bean Validation).
     * Este teste apenas confirma que a Model em si não impede o valor.
     */
    @Test
    public void modelNaoImpedeSaldoNegativoViaSetter() {
        // Arrange
        BigDecimal saldoNegativo = new BigDecimal("-500.00");
        
        // Act
        conta.setSaldo(saldoNegativo);
        
        // Assert: Confirma que o saldo é negativo, provando que a validação não está aqui.
        assertTrue(conta.getSaldo().compareTo(BigDecimal.ZERO) < 0, 
                   "O Model não deve impedir o saldo negativo, a validação deve ser feita no Controller/Service.");
    }
}
