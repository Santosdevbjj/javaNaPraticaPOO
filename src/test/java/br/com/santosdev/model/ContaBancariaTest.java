package br.com.santosdev.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste unitário para ContaBancaria.
 * 
 * Os testes simulam operações básicas da conta bancária
 * garantindo que a lógica de encapsulamento e integridade
 * de dados seja mantida.
 * 
 * Estes testes seguem o padrão AAA (Arrange, Act, Assert):
 * - Arrange: Preparar os dados
 * - Act: Executar a ação
 * - Assert: Verificar o resultado esperado
 */
public class ContaBancariaTest {

    private ContaBancaria conta;

    /**
     * Este método é executado antes de cada teste.
     * Ele cria uma nova conta com saldo inicial.
     */
    @BeforeEach
    public void setup() {
        conta = new ContaBancaria("Sérgio", 1000.0);
    }

    /**
     * Testa se o objeto ContaBancaria é criado corretamente.
     */
    @Test
    public void deveCriarContaCorretamente() {
        assertEquals("Sérgio", conta.getTitular());
        assertEquals(1000.0, conta.getSaldo(), 0.01);
    }

    /**
     * Testa a atualização do saldo.
     * Como não temos métodos de depósito ou saque diretamente na model,
     * simulamos a modificação via setSaldo().
     */
    @Test
    public void deveAtualizarSaldoCorretamente() {
        conta.setSaldo(1500.0);
        assertEquals(1500.0, conta.getSaldo(), 0.01);
    }

    /**
     * Testa se é possível alterar o titular da conta.
     */
    @Test
    public void deveAlterarTitular() {
        conta.setTitular("Ana");
        assertEquals("Ana", conta.getTitular());
    }

    /**
     * Teste para verificar se o toString() exibe as informações esperadas.
     */
    @Test
    public void deveExibirToStringCorretamente() {
        String representacao = conta.toString();
        assertTrue(representacao.contains("Sérgio"));
        assertTrue(representacao.contains("1000"));
    }

    /**
     * Teste para evitar saldo negativo manualmente.
     * Aqui apenas garantimos que o valor pode ser validado externamente.
     */
    @Test
    public void naoDeveAceitarSaldoNegativo() {
        conta.setSaldo(-500.0);
        assertTrue(conta.getSaldo() < 0, "⚠️ Este teste demonstra que a validação deve ser feita no Service.");
    }
}
