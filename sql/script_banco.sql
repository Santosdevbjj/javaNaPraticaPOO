-- ============================================================
-- Script de Criação do Banco de Dados Java na Prática POO
-- Autor: Sérgio Santos
-- Data: Outubro de 2025
-- Descrição: Estrutura inicial do banco para o CRUD Java
-- ============================================================

-- 1️⃣ Criar banco de dados
CREATE DATABASE IF NOT EXISTS java_poo;
USE java_poo;

-- 2️⃣ Criar tabela de contas bancárias
CREATE TABLE IF NOT EXISTS conta_bancaria (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- Identificador único
    titular VARCHAR(100) NOT NULL,      -- Nome do titular da conta
    saldo DOUBLE NOT NULL               -- Saldo atual
);

-- 3️⃣ Inserir alguns dados de exemplo
INSERT INTO conta_bancaria (titular, saldo) VALUES
('Sérgio Santos', 1200.00),
('Ana Costa', 800.50),
('Carlos Silva', 2500.75);

-- 4️⃣ Consultar os registros
SELECT * FROM conta_bancaria;

-- 5️⃣ Exemplo de atualização
-- UPDATE conta_bancaria SET saldo = 3000.00 WHERE id = 1;

-- 6️⃣ Exemplo de exclusão
-- DELETE FROM conta_bancaria WHERE id = 3;

-- ============================================================
-- 💡 Observações:
-- - O nome do banco deve coincidir com o usado em ConnectionFactory.java
--   jdbc:mysql://localhost:3306/java_poo
-- - Usuário padrão: root
-- - Senha padrão: admin (pode ser alterada)
-- ============================================================
