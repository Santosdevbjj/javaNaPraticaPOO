package br.com.santosdev.model;

/**
 * Representa uma conta bancária com operações básicas de CRUD.
 */
public class ContaBancaria {

    private int id;
    private String titular;
    private double saldo;

    public ContaBancaria() {}

    public ContaBancaria(String titular, double saldo) {
        this.titular = titular;
        this.saldo = saldo;
    }

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
