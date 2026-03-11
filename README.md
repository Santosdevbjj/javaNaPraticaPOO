# 🚀 Microsserviço Bancário POO — Da Teoria à Produção  
**Um estudo aplicado de Orientação a Objetos, TDD e BDD em Java com Spring Boot**

[![Java](https://img.shields.io/badge/Java-17-red?logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)](https://www.docker.com/)
[![JUnit5](https://img.shields.io/badge/JUnit_5-TDD-orange?logo=java)](https://junit.org/junit5/)
[![Cucumber](https://img.shields.io/badge/Cucumber-BDD-green?logo=cucumber)](https://cucumber.io/)
[![License](https://img.shields.io/badge/license-MIT-lightgrey)](LICENSE)

---

## 📚 Sumário
1. [Sobre o Projeto](#-sobre-o-projeto)
2. [Arquitetura e Conceitos de Engenharia](#-arquitetura-e-conceitos-de-engenharia)
3. [Tecnologias Utilizadas](#-tecnologias-utilizadas)
4. [Requisitos de Software e Hardware](#-requisitos-de-software-e-hardware)
5. [Estrutura de Pastas e Arquivos](#-estrutura-de-pastas-e-arquivos)
6. [UML Simplificado](#-uml-simplificado)
7. [Execução e Uso (Modo Docker)](#-execução-e-uso-modo-docker)
8. [Testes TDD e BDD](#-testes-tdd-e-bdd)
9. [Manuais do Projeto](#-manuais-do-projeto)
10. [Logs de Execução e Depuração](#-logs-de-execução-e-depuração)
11. [Conexão com o Artigo DIO](#-conexão-com-o-artigo-dio)
12. [Autor e Referências](#-autor-e-referências)

---

## 🧭 Sobre o Projeto
Este microsserviço é o **produto final do artigo**   
• 🎓 *“Java na Prática: OO para Pensar como Engenheiro de Software”*,  
desenvolvido por **Sérgio Santos** para a 37° competição de artigos da DIO.

Ele demonstra como aplicar princípios de:
- **Programação Orientada a Objetos (POO)**  
- **Test Driven Development (TDD)**  
- **Behavior Driven Development (BDD)**  
- **Princípios SOLID**  
- **Boas práticas de Engenharia de Software**

em um **microsserviço bancário real**, construído com **Spring Boot + Docker + MySQL + RabbitMQ**.

---

## 🧱 Arquitetura e Conceitos de Engenharia
| Conceito (Artigo) | Implementação no Projeto | Benefício |
|-------------------|--------------------------|------------|
| Encapsulamento | `ContaBancaria` usa Bean Validation e exceções personalizadas. | Protege o estado do domínio. |
| Colaboração (Baixo Acoplamento) | RabbitMQ envia eventos assíncronos. | Promove escalabilidade e desacoplamento. |
| Polimorfismo | `ContaBancariaDAO` estende `JpaRepository`. | Código enxuto e genérico. |
| Programação Defensiva | `GlobalExceptionHandler` + validações. | Resiliência e previsibilidade. |
| Precisão Financeira | `BigDecimal` e `DECIMAL(19,2)` no MySQL. | Evita erros monetários. |
| TDD | Testes com `JUnit5` e `Mockito`. | Garante integridade do código. |
| BDD | Testes Cucumber + RestAssured. | Valida comportamento do sistema. |

---

## 💻 Tecnologias Utilizadas
| Categoria | Tecnologia | Versão | Finalidade |
|------------|-------------|---------|-------------|
| Linguagem | Java | 17 | Lógica de Negócio e Testes |
| Framework | Spring Boot | 3.2.0 | Estrutura do Microsserviço |
| Persistência | Spring Data JPA / Hibernate | 3.2.0 | ORM e Acesso a Dados |
| Banco de Dados | MySQL | 8.0 | Armazenamento de Contas |
| Mensageria | RabbitMQ | 3.12 | Comunicação Assíncrona |
| Segurança | Spring Security | 3.2.0 | Autenticação (Basic Auth) |
| Containerização | Docker / Docker Compose | Latest | Empacotamento e Orquestração |
| Testes Unitários | JUnit 5 / Mockito | 5 / 5 | TDD |
| Testes de Sistema | Cucumber / RestAssured | 7 | BDD |

---

## 🧰 Requisitos de Software e Hardware

### 💾 Software
| Ferramenta | Versão Mínima | Função |
|-------------|----------------|---------|
| Docker Engine | 20.10+ | Execução de containers |
| Docker Compose | 3.8+ | Orquestração (App, DB, MQ) |
| Postman | Última | Teste da API e cenários BDD |
| Git | 2.25+ | Clonagem e versionamento |

### ⚙️ Hardware
- CPU: Dual Core (mínimo)
- RAM: **4 GB livres**
- Armazenamento: **500 MB**
- Conexão de Internet (para pull de imagens Docker)

---

## 📂 Estrutura de Pastas e Arquivos 


<img width="908" height="1666" alt="Screenshot_20251024-022933" src="https://github.com/user-attachments/assets/d1d3202c-a22e-4662-86b4-82c537031560" />



---


## 🧩 UML Simplificado (Arquitetura de Classes) 



<img width="946" height="1543" alt="Screenshot_20251022-192314" src="https://github.com/user-attachments/assets/271d9d3c-8635-4b18-8deb-7712dd8b3730" />





---



 1️⃣ **Clone o repositório**
git clone https://github.com/Santosdevbjj/javaNaPraticaPOO.git
cd javaNaPraticaPOO

 2️⃣ **Suba toda a stack (App + MySQL + RabbitMQ)**
docker-compose up --build -d

 3️⃣ **Verifique se está no ar**
http://localhost:8080/actuator/health

 4️⃣ **Teste via Postman**
Importe api-tests/javaNaPraticaPOO-Collection.json

 5️⃣ **Parar containers**
docker-compose down



---


🧪 **Testes TDD e BDD**

**Tipo**	**Framework**	**Arquivo	Descrição**


🧩 TDD (Unidade)	JUnit 5	ContaBancariaTest.java	Valida depósitos e saques

🧩 TDD (Serviço)	Mockito	ContaBancariaServiceTest.java	Simula DAO e mensageria

🧩 BDD (Comportamento)	Cucumber + RestAssured	ContaBancariaSteps.java + .feature	Testes end-to-end com Gherkin


**Fluxo de TDD — Red → Green → Refactor**


[RED] ➜ Crie um teste que falha
[GREEN] ➜ Faça o código mínimo passar
[REFACTOR] ➜ Melhore sem quebrar testes

---

**Fluxo de BDD (Gherkin)**

Funcionalidade: Criação de Conta Bancária
  Cenário: Saldo Inicial Negativo
    Dado que o endpoint /api/contas está disponível
    Quando envio POST com saldo -100.00
    Então o sistema retorna 400 e mensagem "Saldo não pode ser negativo"


---


📘 **Manuais do Projeto**

Tipo	Descrição	Caminho

👥 Manual do Usuário Leigo	Execução via Docker e uso do Postman.	**docs/Manual_Usuario_Leigo.md**


👨‍💻 Manual Técnico	Estrutura de código, debugging e arquitetura.	**docs/Manual_Usuario_Tecnico.md**



---


🧾 **Logs de Execução e Depuração**

Durante a execução do microsserviço, os logs são gravados no console Docker.

**Exemplo de log:**

• [INFO] Conta criada com sucesso - ID: 101

• [INFO] Mensagem publicada em RabbitMQ - Fila: conta_eventos

• [WARN] Tentativa de saque com saldo insuficiente - Conta: 101

• [ERROR] ContaNaoEncontradaException: Conta inexistente


---


🔗 **Conexão com o Artigo DIO**

📖 **Este repositório complementa o artigo:**

- **“Java na Prática: OO para Pensar como Engenheiro de Software”**


🔗 **Leia o artigo completo na DIO**
O projeto traduz em código real os conceitos de:

**Encapsulamento**

**Polimorfismo**

**Herança**

**TDD**

**BDD**

**SOLID**

**Programação Defensiva**





---

👤 **Autor e Referências**


**Autor:** Sérgio Santos



**Principais Referências:**

Bloch, Joshua. Effective Java. Addison-Wesley, 2018.

Martin, Robert C. Clean Code. Prentice Hall, 2008.

Gamma et al. Design Patterns. Addison-Wesley, 1994.

Oracle Java SE 17 Docs

JUnit 5 User Guide

Cucumber Docs

Stack Overflow Developer Survey 2024



---

•  “Testes não são o fim — são o início da engenharia de software madura.”



---
**Leia o artigo completo na plataforma da DIO:** https://web.dio.me/articles/java-na-pratica-oo-para-pensar-como-engenheiro-de-software-c470bf0a1c88?back=/articles


---
**Contato:**



[![Portfólio Sérgio Santos](https://img.shields.io/badge/Portfólio-Sérgio_Santos-111827?style=for-the-badge&logo=githubpages&logoColor=00eaff)](https://portfoliosantossergio.vercel.app)

[![LinkedIn Sérgio Santos](https://img.shields.io/badge/LinkedIn-Sérgio_Santos-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/santossergioluiz)


---


