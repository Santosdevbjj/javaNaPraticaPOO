package br.com.santosdev.dao;

import br.com.santosdev.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * O Spring Data JPA cria a implementação do CRUD (Create, Read, Update, Delete)
 * automaticamente, eliminando o código boilerplate de JDBC que estava aqui.
 * * Herda métodos como: save(), findById(), findAll(), deleteById().
 */
@Repository
public interface ContaBancariaDAO extends JpaRepository<ContaBancaria, Integer> {
    // Não é necessário escrever nada, a funcionalidade já é herdada.
}
