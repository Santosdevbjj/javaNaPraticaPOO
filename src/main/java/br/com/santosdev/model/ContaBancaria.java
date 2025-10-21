package br.com.santosdev.model;

import jakarta.persistence.*; // Import para as anotações JPA

/**
 * Representa uma conta bancária. Agora é uma Entidade JPA.
 */
@Entity
@Table(name = "conta_bancaria")
public class ContaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String titular;
    private double saldo; // Sugestão: Usar BigDecimal em produção

    public ContaBancaria() {}

    public ContaBancaria(String titular, double saldo) {
        this.titular = titular;
        this.saldo = saldo;
    }

    // Getters e Setters (Mantidos)

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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "ContaBancaria [id=" + id + ", titular='" + titular + "', saldo=" + saldo + "]";
    }
}
