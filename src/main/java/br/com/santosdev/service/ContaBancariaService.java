package br.com.santosdev.service;

import br.com.santosdev.config.RabbitMQConfig;
import br.com.santosdev.dao.ContaBancariaDAO;
import br.com.santosdev.exception.ContaNaoEncontradaException;
import br.com.santosdev.exception.SaldoInvalidoException;
import br.com.santosdev.model.ContaBancaria;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Camada de serviço - executa regras de negócio e orquestra operações.
 */
@Service
public class ContaBancariaService {

    private final ContaBancariaDAO dao;
    private final RabbitTemplate rabbitTemplate;

    public ContaBancariaService(ContaBancariaDAO dao, RabbitTemplate rabbitTemplate) {
        this.dao = dao;
        this.rabbitTemplate = rabbitTemplate;
    }

    // CRIAÇÃO
    public ContaBancaria criar(ContaBancaria conta) {
        // Regra de Negócio: Saldo não pode ser negativo (comparação com BigDecimal.ZERO)
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInvalidoException("Saldo inicial não pode ser negativo."); 
        }
        
        ContaBancaria novaConta = dao.save(conta);
        
        // Envio Assíncrono para o RabbitMQ
        String mensagem = String.format("ID: %d, Titular: %s", novaConta.getId(), novaConta.getTitular());
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME, 
            RabbitMQConfig.ROUTING_KEY, 
            mensagem
        );
        
        return novaConta;
    }

    // LISTAGEM
    public List<ContaBancaria> listarTodos() {
        return dao.findAll();
    }

    // BUSCA POR ID
    public ContaBancaria buscarPorId(Integer id) {
        return dao.findById(id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
    }

    // ATUALIZAÇÃO (Implementação completa do CRUD)
    public ContaBancaria atualizar(Integer id, ContaBancaria contaAtualizada) {
        // 1. Verifica se existe (lança 404 se não)
        ContaBancaria contaExistente = dao.findById(id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
        
        // 2. Valida o novo saldo
        if (contaAtualizada.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInvalidoException("O saldo atualizado não pode ser negativo.");
        }
        
        // 3. Aplica as mudanças
        contaExistente.setTitular(contaAtualizada.getTitular());
        contaExistente.setSaldo(contaAtualizada.getSaldo()); 

        // 4. Salva (JPA entende que é um UPDATE porque o ID já existe)
        return dao.save(contaExistente);
    }
    
    // EXCLUSÃO (Implementação completa do CRUD)
    public void deletar(Integer id) {
        // Garante que o recurso existe antes de deletar
        dao.findById(id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
            
        dao.deleteById(id);
    }
}
