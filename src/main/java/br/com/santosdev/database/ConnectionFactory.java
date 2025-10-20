package br.com.santosdev.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por criar e gerenciar conexões com o banco de dados MySQL.
 */
public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://localhost:3306/java_poo";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    /**
     * Retorna uma nova conexão com o banco de dados MySQL.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
