package br.com.santosdev.service;

import br.com.santosdev.dao.ContaBancariaDAO;
import br.com.santosdev.model.ContaBancaria;
import br.com.santosdev.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de serviço - executa regras de negócio e orquestra operações.
 * Agora injetada com o DAO e com o RabbitMQ.
 */
@Service
public class ContaBancariaService {

    private final ContaBancariaDAO dao;
    private final RabbitTemplate rabbitTemplate; // Injeção do RabbitMQ

    // Injeção de Dependência por Construtor (Melhor Prática)
    public ContaBancariaService(ContaBancariaDAO dao, RabbitTemplate rabbitTemplate) {
        this.dao = dao;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ContaBancaria criar(ContaBancaria conta) {
        // Validação de Negócio
        if (conta.getSaldo() < 0) {
            // Em APIs REST, lançamos exceções para serem capturadas e retornar códigos HTTP 4xx
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo."); 
        }
        
        // Persiste no banco
        ContaBancaria novaConta = dao.save(conta);
        
        // Envio Assíncrono para o RabbitMQ (Microserviço)
        String mensagem = String.format("ID: %d, Titular: %s", novaConta.getId(), novaConta.getTitular());
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME, 
            RabbitMQConfig.ROUTING_KEY, 
            mensagem
        );
        
        return novaConta;
    }

    public List<ContaBancaria> listarTodos() {
        return dao.findAll(); // Método herdado do JpaRepository
    }

    public ContaBancaria buscarPorId(Integer id) {
        // Usa Optional (boa prática) e lança exceção se não encontrar (padrão REST 404)
        return dao.findById(id)
            .orElseThrow(() -> new RuntimeException("Conta ID " + id + " não encontrada."));
    }
    
    // Métodos atualizar e deletar usam dao.save(conta) e dao.deleteById(id)
}
