package br.com.santosdev.model;

import jakarta.persistence.*;
import java.math.BigDecimal; // Importação essencial para precisão

/**
 * Representa uma conta bancária. Agora é uma Entidade JPA com BigDecimal.
 */
@Entity
@Table(name = "conta_bancaria")
public class ContaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String titular;
    
    // Usando BigDecimal para precisão em valores monetários
    private BigDecimal saldo; 

    public ContaBancaria() {
        this.saldo = BigDecimal.ZERO; // Inicializa com ZERO para evitar NullPointer
    }

    public ContaBancaria(String titular, BigDecimal saldo) {
        this.titular = titular;
        this.saldo = saldo != null ? saldo : BigDecimal.ZERO;
    }

    // --- Getters e Setters Atualizados para BigDecimal ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "ContaBancaria [id=" + id + ", titular='" + titular + "', saldo=" + saldo + "]";
    }
}
