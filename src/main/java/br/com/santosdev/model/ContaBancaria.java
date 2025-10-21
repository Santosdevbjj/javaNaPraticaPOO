package br.com.santosdev.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin; // NOVO
import jakarta.validation.constraints.NotBlank;   // NOVO
import jakarta.validation.constraints.NotNull;    // NOVO
import java.math.BigDecimal; 

/**
 * Representa uma conta bancária com anotações JPA e Bean Validation.
 */
@Entity
@Table(name = "conta_bancaria")
public class ContaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "O titular da conta não pode ser vazio.")
    private String titular;
    
    @NotNull(message = "O saldo é obrigatório.")
    @DecimalMin(value = "0.00", message = "O saldo não pode ser negativo.") // Garante que o saldo seja >= 0
    private BigDecimal saldo; 

    public ContaBancaria() {
        this.saldo = BigDecimal.ZERO;
    }

    public ContaBancaria(String titular, BigDecimal saldo) {
        this.titular = titular;
        this.saldo = saldo != null ? saldo : BigDecimal.ZERO;
    }

    // ... Getters e Setters ...
    // (Permanecem inalterados, usando BigDecimal)
    
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
