package br.com.santosdev.service;

import br.com.santosdev.config.RabbitMQConfig;
import br.com.santosdev.dao.ContaBancariaDAO;
import br.com.santosdev.exception.ContaNaoEncontradaException;
import br.com.santosdev.model.ContaBancaria;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de serviço - Foco 100% na lógica de negócio e orquestração.
 * As validações simples foram movidas para o Bean Validation (@DecimalMin).
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
        // A validação de saldo negativo e titular vazio foi removida daqui!
        // Ela agora é tratada automaticamente pelo @Valid e @DecimalMin/NotBlank.
        
        ContaBancaria novaConta = dao.save(conta);
        
        // Envio Assíncrono
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

    // ATUALIZAÇÃO
    public ContaBancaria atualizar(Integer id, ContaBancaria contaAtualizada) {
        ContaBancaria contaExistente = dao.findById(id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
        
        // A validação de saldo negativo (DecimalMin) acontece no Controller.
        
        contaExistente.setTitular(contaAtualizada.getTitular());
        contaExistente.setSaldo(contaAtualizada.getSaldo()); 

        return dao.save(contaExistente);
    }
    
    // EXCLUSÃO
    public void deletar(Integer id) {
        dao.findById(id)
            .orElseThrow(() -> new ContaNaoEncontradaException(id));
            
        dao.deleteById(id);
    }
}
