package br.com.santosdev.dao;

import br.com.santosdev.database.ConnectionFactory;
import br.com.santosdev.model.ContaBancaria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO - Data Access Object
 * Responsável por realizar operações CRUD no banco de dados MySQL.
 */
public class ContaBancariaDAO {

    // CREATE
    public void criarConta(ContaBancaria conta) {
        String sql = "INSERT INTO conta_bancaria (titular, saldo) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conta.getTitular());
            stmt.setDouble(2, conta.getSaldo());
            stmt.executeUpdate();
            System.out.println("✅ Conta criada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao criar conta: " + e.getMessage());
        }
    }

    // READ
    public List<ContaBancaria> listarContas() {
        List<ContaBancaria> contas = new ArrayList<>();
        String sql = "SELECT * FROM conta_bancaria";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ContaBancaria conta = new ContaBancaria();
                conta.setId(rs.getInt("id"));
                conta.setTitular(rs.getString("titular"));
                conta.setSaldo(rs.getDouble("saldo"));
                contas.add(conta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar contas: " + e.getMessage());
        }
        return contas;
    }

    // UPDATE
    public void atualizarConta(ContaBancaria conta) {
        String sql = "UPDATE conta_bancaria SET titular = ?, saldo = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conta.getTitular());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setInt(3, conta.getId());
            stmt.executeUpdate();
            System.out.println("✅ Conta atualizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar conta: " + e.getMessage());
        }
    }

    // DELETE
    public void deletarConta(int id) {
        String sql = "DELETE FROM conta_bancaria WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Conta removida com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao excluir conta: " + e.getMessage());
        }
    }
}
