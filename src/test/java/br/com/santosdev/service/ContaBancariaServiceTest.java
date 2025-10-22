package br.com.santosdev.service;

import br.com.santosdev.config.RabbitMQConfig;
import br.com.santosdev.dao.ContaBancariaDAO;
import br.com.santosdev.exception.ContaNaoEncontradaException;
import br.com.santosdev.model.ContaBancaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TDD - Teste de Colaboração/Unidade do Service: 
 * Foca nas Regras de Negócio e nas chamadas a dependências (DAO, RabbitMQ).
 */
@ExtendWith(MockitoExtension.class)
public class ContaBancariaServiceTest {

    @Mock // Mockito: Objeto simulado do DAO
    private ContaBancariaDAO dao;

    @Mock // Mockito: Objeto simulado do RabbitMQ
    private RabbitTemplate rabbitTemplate;

    @InjectMocks // Mockito: Cria a instância do Service e injeta os Mocks acima
    private ContaBancariaService service;

    private ContaBancaria conta;
    private final Integer ID_EXISTENTE = 1;

    @BeforeEach
    public void setup() {
        conta = new ContaBancaria("Cliente Teste Service", new BigDecimal("1000.00"));
        conta.setId(ID_EXISTENTE);
    }

    @Test
    void deveCriarContaESalvarNoBancoEEnviarParaRabbitMQ() {
        // Arrange: Quando o DAO salvar, retorna a conta com o ID
        when(dao.save(any(ContaBancaria.class))).thenReturn(conta);

        // Act
        ContaBancaria resultado = service.criar(conta);

        // Assert
        assertNotNull(resultado);
        // Garante que o método save do DAO foi chamado UMA VEZ
        verify(dao, times(1)).save(conta);
        
        // Garante que o evento para o RabbitMQ foi enviado UMA VEZ
        verify(rabbitTemplate, times(1)).convertAndSend(
            eq(RabbitMQConfig.EXCHANGE_NAME),
            eq(RabbitMQConfig.ROUTING_KEY),
            anyString() // Verifica se alguma string foi enviada
        );
    }

    @Test
    void deveBuscarContaPorIdComSucesso() {
        // Arrange
        when(dao.findById(ID_EXISTENTE)).thenReturn(Optional.of(conta));

        // Act
        ContaBancaria resultado = service.buscarPorId(ID_EXISTENTE);

        // Assert
        assertNotNull(resultado);
        assertEquals(ID_EXISTENTE, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarContaInexistente() {
        // Arrange
        Integer ID_INEXISTENTE = 999;
        when(dao.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());

        // Assert: Garante que a exceção de negócio correta é lançada
        assertThrows(ContaNaoEncontradaException.class, () -> {
            service.buscarPorId(ID_INEXISTENTE);
        });
    }

    @Test
    void deveDeletarContaExistente() {
        // Arrange
        when(dao.findById(ID_EXISTENTE)).thenReturn(Optional.of(conta));
        // Não é necessário mockar o deleteById, apenas garantir que ele foi chamado

        // Act
        service.deletar(ID_EXISTENTE);

        // Assert: Garante que o método deleteById do DAO foi chamado UMA VEZ
        verify(dao, times(1)).deleteById(ID_EXISTENTE);
    }
}
