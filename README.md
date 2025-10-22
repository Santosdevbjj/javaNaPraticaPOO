🚀 Microsserviço Bancário POO - Da Teoria à Produção (DIO Article Project)
| Status | Versão | Arquitetura | Testes |
|---|---|---|---|
| ✅ Completo | 1.0-SNAPSHOT | Microsserviço Spring Boot | TDD (JUnit/Mockito) & BDD (Cucumber/RestAssured) |

---

Este projeto é o produto final do artigo "Fundamentos de Java na Prática: Orientação a Objetos para Pensar como Engenheiro de Software", desenvolvido para a competição de artigos da DIO. Ele demonstra como os princípios de Orientação a Objetos (OO) e Engenharia de Software evoluem de um conceito simples para uma arquitetura de Microsserviço de Nível de Produção.
🌟 Destaques de Arquitetura e Conexão com OO
O projeto transcende o CRUD básico, implementando componentes de produção que reforçam conceitos avançados de OO e SOLID:

---

| Conceito do Artigo (Teoria) | Implementação no Projeto (Prática) | Foco Principal |
|---|---|---|
| Encapsulamento & Bean Validation | Uso de @DecimalMin e GlobalExceptionHandler. | A integridade do estado do objeto (saldo em ContaBancaria) é garantida na fronteira da API. |
| Colaboração & Acoplamento Baixo | RabbitMQ (Mensageria Assíncrona). | O envio de notificações é desacoplado do serviço principal, um exemplo prático do princípio de Colaboração. |
| Herança & Polimorfismo | ContaBancariaDAO estendendo JpaRepository. | Utiliza polimorfismo, onde o Spring Data implementa o contrato da Interface. |
| Exceções & Programação Defensiva | Exceções de Domínio e GlobalExceptionHandler. | Garante que erros de negócio (HTTP 400) e recursos (HTTP 404) sejam tratados de forma profissional e previsível. |
| Precisão Financeira | Uso de java.math.BigDecimal no Java e DECIMAL(19, 2) no MySQL. | Proteção contra erros de ponto flutuante, aderente às boas práticas financeiras. |
💻 Tecnologia Utilizada (Tech Stack)
| Categoria | Tecnologia | Versão | Função |
|---|---|---|---|
| Core | Java | 17 | Linguagem principal. |
| Framework | Spring Boot | 3.2.0 | Simplificação e inicialização do Microsserviço. |
| Persistência | Spring Data JPA / Hibernate | 3.2.0 | ORM e gerenciamento de dados. |
| Banco de Dados | MySQL | 8.0 (via Docker) | Armazenamento persistente de dados. |
| Mensageria | RabbitMQ | 3.12 (via Docker) | Broker de mensagens para comunicação assíncrona. |
| Segurança | Spring Security | 3.2.0 | Autenticação (HTTP Basic) e Autorização. |
| Container | Docker / Docker Compose | Últimas | Ambiente de execução isolado e orquestração de serviços. |
| Testes TDD | JUnit 5 / Mockito | 5 / 5 | Testes unitários e de colaboração de classes. |
| Testes BDD | Cucumber / RestAssured | 7 | Testes de sistema (API End-to-End) e comportamento. |
🛠️ Requisitos de Software e Hardware
O único requisito obrigatório para rodar o projeto é o Docker.
Software
| Software | Versão Mínima | Finalidade |
|---|---|---|
| Docker Engine | 20.10+ | Para construir a aplicação e rodar a stack completa. |
| Docker Compose | 3.8+ | Para orquestrar os 3 containers (App, DB, MQ). |
| Postman | Última | Para enviar comandos à API e usar a coleção de testes BDD. |

---

Hardware
 * RAM: Mínimo de 4 GB livres (para o Docker e os três containers).
🚀 Configuração e Execução (O Modo Docker)
O projeto é projetado para ser iniciado com um único comando, abstraindo a configuração de Java, MySQL e RabbitMQ.
Passo 1: Clone o Repositório
git clone https://github.com/Santosdevbjj/javaNaPraticaPOO.git
cd javaNaPraticaPOO

Passo 2: Inicialize a Stack Completa
Na pasta raiz do projeto, execute o Docker Compose. Ele fará o build da imagem Java e iniciará os três serviços em background.
docker-compose up --build -d

| Componente | Porta Local | Credenciais Padrão |
|---|---|---|
| API REST | 8080 | Basic Auth: user / password |
| MySQL | 3306 | Root/App User (configurado no Compose) |
| RabbitMQ Management | 15672 | guest / guest |
Passo 3: Verificação de Saúde (Health Check)
Aguarde 30 segundos após a execução e verifique se a API está no ar:
http://localhost:8080/actuator/health

Passo 4: Teste a API (via Postman/BDD)
 * Abra o Postman.
 * Importe a coleção de testes: api-tests/javaNaPraticaPOO-Collection.json.
 * Execute a coleção para validar o fluxo CRUD, Segurança (401), e Validação (400).
Passo 5: Parada dos Containers
Para desligar todos os serviços e remover os containers:
docker-compose down

📂 Estrutura Detalhada do Projeto
A estrutura de pastas segue o padrão Spring Boot, com separação clara por responsabilidades:
| Caminho do Arquivo/Pasta | Descrição Didática | Princípios OO/Técnicos |
|---|---|---|
| / (Raiz) | Arquivos de execução e build do ambiente. |  |
| docker-compose.yml | Orquestra a rede de containers (App, MySQL, RabbitMQ). | DevOps, Colaboração. |
| Dockerfile | Define como a aplicação Java é construída e executada dentro do container. | Imutabilidade, Abstração. |
| pom.xml | Define as dependências da aplicação e as ferramentas de teste BDD/TDD. | Gerenciamento de Dependências. |
| sql/ | Script de inicialização do banco. |  |
| script_banco.sql | Cria o banco java_poo e a tabela conta_bancaria usando o tipo DECIMAL(19, 2) para precisão. | Modelagem de Dados. |
| api-tests/ | Documentação e testes de API. |  |
| javaNaPraticaPOO-Collection.json | Coleção Postman com cenários BDD prontos para uso. | BDD, Contrato de API. |
| src/main/java/br/com/santosdev/... | Código Fonte Java. |  |
| .../model/ContaBancaria.java | Entidade JPA. O objeto central do domínio, usa BigDecimal e anotações de Bean Validation. | Classe, Estado, Encapsulamento. |
| .../dao/ContaBancariaDAO.java | Interface de Persistência. Estende JpaRepository, eliminando código boilerplate de acesso a dados. | Herança, Polimorfismo. |
| .../service/ContaBancariaService.java | Regras de Negócio. Contém a lógica de CRUD e a Colaboração com o RabbitMQ. | Responsabilidade, SOLID (SRP). |
| .../controller/ | Controlador REST. Recebe requisições e lida com a fronteira HTTP. | Fronteira, I/O. |
| .../config/SecurityConfig.java | Configura o Spring Security (Basic Auth: user/password). | Segurança. |
| .../controller/handler/GlobalExceptionHandler.java | Trata exceções de validação (400 Bad Request) e recursos (404 Not Found). | Programação Defensiva. |
| src/test/java/br/com/santosdev/... | Suíte de Testes Automatizados. |  |
| .../model/ContaBancariaTest.java | Testes TDD. Foca na integridade do BigDecimal na Model. | TDD, JUnit 5. |
| .../service/ContaBancariaServiceTest.java | Testes TDD com Mocks. Testa a lógica do Service, simulando o DAO e o RabbitMQ via Mockito. | TDD, Mocking, Colaboração. |
| .../ContaBancariaBDDRunner.java | Classe que inicializa o Cucumber para rodar o BDD. | BDD. |
| .../steps/ContaBancariaSteps.java | Implementação da lógica BDD, usando RestAssured para fazer chamadas HTTP reais. | BDD, Teste de Sistema. |
| src/test/resources/features/ | Arquivos .feature com cenários Gherkin (Dado, Quando, Então). | BDD, Requisitos de Negócio. |
🧪 Estratégia de Testes (TDD vs. BDD)
O projeto usa TDD e BDD para garantir tanto a qualidade do código interno quanto o comportamento do sistema.
1. TDD (Desenvolvimento Orientado a Testes)
Foca no design interno e na lógica de classes isoladas. O ciclo Red-Green-Refactor é aplicado em:
 * Model: Garantir que o BigDecimal funcione como esperado.
 * Service: Garantir que as regras de negócio e a colaboração com o RabbitMQ (usando Mockito) funcionem corretamente.
2. BDD (Desenvolvimento Orientado a Comportamento)
Foca no comportamento do sistema completo a partir da perspectiva do usuário/cliente da API.
 * Os cenários Gherkin testam a integração do Spring Security, Bean Validation e a persistência no MySQL.
 * É o nível de teste que valida o valor de negócio entregue ao cliente.
🤝 Contato e Referências
 * Autor: Sérgio Santos
 * Repositório: https://github.com/Santosdevbjj/javaNaPraticaPOO
 * Referência Acadêmica: Fundamentos de Java na Prática: Orientação a Objetos para Pensar como Engenheiro de Software (Artigo DIO).
