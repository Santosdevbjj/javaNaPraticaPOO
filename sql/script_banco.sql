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
    -- CORREÇÃO CRÍTICA: Usando DECIMAL para precisão financeira,
    -- aderente ao java.math.BigDecimal do modelo Java.
    saldo DECIMAL(19, 2) NOT NULL       -- Saldo atual com precisão exata
);

-- 3️⃣ Inserir alguns dados de exemplo
INSERT INTO conta_bancaria (titular, saldo) VALUES
('Sérgio Santos', 1200.00),
('Ana Costa', 800.50),
('Carlos Silva', 2500.75);

-- 4️⃣ Consultar os registros
SELECT * FROM conta_bancaria;

-- ============================================================
-- 💡 Observações para o Microsserviço:
-- - Este script será executado automaticamente pelo Docker Compose
--   no container MySQL.
-- - As credenciais são lidas a partir do docker-compose.yml e application.properties.
-- ============================================================
