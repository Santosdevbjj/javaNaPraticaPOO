🚀 Microsserviço Bancário POO - Da Teoria à Produção

Este projeto é o produto final e prático do artigo "Fundamentos de Java na Prática: Orientação a Objetos para Pensar como Engenheiro de Software", desenvolvido para a competição de artigos da DIO.
Ele evolui um conceito inicial de Orientação a Objetos (OO) em Java para uma arquitetura de Microsserviço de Nível de Produção, aplicando os princípios mais avançados de Engenharia de Software, incluindo Spring Boot, JPA, Docker, RabbitMQ e Segurança.

---

🌟 Destaques de Arquitetura e Conexão com a Teoria de OO
O projeto demonstra a transição dos conceitos de Orientação a Objetos para a Arquitetura Orientada a Serviços (SOA/Microsserviços), implementando:
| Conceito do Artigo (Teoria) | Implementação no Projeto (Prática) | Foco Principal |
|---|---|---|
| Classes, Estado e Comportamento | br.com.santosdev.model.ContaBancaria.java | Define o objeto central do sistema, utilizando BigDecimal para garantir a precisão financeira. |
| Encapsulamento | Atributos private em ContaBancaria.java e a remoção da lógica de validação do Service. | A validação (@DecimalMin) garante que a integridade do estado do objeto seja mantida desde a fronteira (Controller). |
| Modelagem CRC (Responsabilidades) | Separação estrita em camadas (Controller, Service, DAO). | O Controller lida com HTTP, o Service lida com Regras de Negócio e o DAO lida com Persistência (SRP - Single Responsibility Principle). |
| Colaboração e Acoplamento Baixo | RabbitMQ (Broker de Mensagens). | A criação de uma conta envia um evento assíncrono para o NotificacaoListener, garantindo que o Microsserviço de Notificação seja totalmente desacoplado do serviço bancário. |
| Interfaces (Herança e Polimorfismo) | br.com.santosdev.dao.ContaBancariaDAO.java estendendo JpaRepository. | Usa Polimorfismo e Acoplamento Abstrato, onde o Spring Data implementa o contrato de uma interface, eliminando o código boilerplate (JDBC). |
| Exceções em Java e Programação Defensiva | br.com.santosdev.controller.handler.GlobalExceptionHandler.java. | Garante que erros de negócio (HTTP 400 - Saldo Inválido) e erros de recurso (HTTP 404 - Conta Não Encontrada) sejam tratados profissionalmente na fronteira da API. |
🛠️ Requisitos de Software e Hardware
Para rodar o projeto, você precisará ter o ambiente de conteinerização configurado.
| Software | Versão Mínima | Finalidade |
|---|---|---|
| Docker | 20.10+ | Para construir e gerenciar os containers (Java App, MySQL, RabbitMQ). |
| Docker Compose | 3.8+ | Para orquestrar toda a arquitetura com um único comando. |
| Java JDK | 17 (ou superior) | Para compilar o código (necessário apenas para a fase de build no container). |
| Maven | 3.6+ | Gerenciador de dependências (necessário apenas para o Dockerfile). |
| Postman | Última | Para testar a API REST (inclui a coleção de testes). |
Requisitos de Hardware: 4 GB de RAM livre (para Docker e containers).
📂 Estrutura de Pastas e Explicação de Arquivos
A estrutura de pastas reflete as melhores práticas do Spring Boot e a separação de responsabilidades (CRC).
| Caminho do Arquivo/Pasta | Tipo | Funcionalidade e Princípio OO |
|---|---|---|
| Dockerfile | Raiz | Define as etapas para construir o container Java App. Abstrai o ambiente de execução. |
| docker-compose.yml | Raiz | Orquestra e conecta os 3 containers (app, mysql, rabbitmq). |
| pom.xml | Raiz | Define as dependências da aplicação (JPA, Web, Security, Actuator, RabbitMQ). |
| api-tests/ | Pasta | Contém o arquivo javaNaPraticaPOO-Collection.json para testes imediatos (Postman). |
| src/main/resources/application.properties | Config. | Configuração de Conexão (MySQL) e exposição do Actuator (Monitoramento). |
| src/main/java/br/com/santosdev/ | Pacote | Raiz do código fonte. |
| .../config/SecurityConfig.java | Classe | NOVO: Implementa o Spring Security (Autenticação HTTP Basic) para proteger os endpoints. |
| .../controller/ | Pacote | Camada de Entrada/Saída (I/O). Lida com requisições HTTP e usa @Valid para ativar o Bean Validation. |
| .../dao/ContaBancariaDAO.java | Interface | Camada de Persistência. Estende JpaRepository, eliminando o código de acesso a dados (Acoplamento Abstrato). |
| .../exception/ | Pacote | NOVO: Exceções customizadas (ContaNaoEncontradaException, etc.) para um tratamento de erro limpo. |
| .../model/ContaBancaria.java | Classe | Classe e Estado. Entidade JPA com anotações de Bean Validation (@DecimalMin) para aplicar regras de Encapsulamento. |
| .../service/ContaBancariaService.java | Classe | Camada de Responsabilidade e Lógica de Negócio. Orquestra o DAO e a colaboração assíncrona com o RabbitMQ. |
⚙️ Como Rodar o Projeto
O projeto é configurado para ser executado integralmente via Docker.
1. Build e Inicialização
Na pasta raiz do projeto, execute o comando único que fará o download das imagens (MySQL, RabbitMQ), construirá a imagem Java (Dockerfile) e iniciará os três serviços em segundo plano (-d).
docker-compose up --build -d

2. Acessos Importantes
| Serviço | URL | Credenciais |
|---|---|---|
| API REST | http://localhost:8080/api/contas | Basic Auth: user / password |
| Actuator | http://localhost:8080/actuator/health | Sem autenticação (configurado no SecurityConfig) |
| RabbitMQ Management | http://localhost:15672 | guest / guest |
3. Executando os Testes (Postman)
Devido à implementação do Spring Security, todas as requisições à /api/contas exigem autenticação.
 * Instale o Postman e clique em Importar o arquivo api-tests/javaNaPraticaPOO-Collection.json.
 * A coleção está pré-configurada com a autenticação Basic Auth (user/password) em todas as requisições.
 * Execute a coleção na ordem para testar o CRUD, a criação de eventos assíncronos no RabbitMQ e os tratamentos de erro (400 e 404).
4. Parada dos Containers
Para desligar todos os serviços e remover os containers:
docker-compose down

💡 Próximos Passos (Evolução)
Para levar o projeto ao nível Enterprise, as próximas implementações seriam:
 * Centralização de Configuração: Uso de Spring Cloud Config para gerenciar application.properties externamente.
 * Externalização do Listener: Mover o NotificacaoListener.java para um projeto Spring Boot separado, transformando-o em um microsserviço dedicado (desacoplamento total).
 * Métricas: Integrar o Actuator com Prometheus e Grafana para visualização real-time do desempenho do microsserviço.
Este projeto representa o estado da arte do desenvolvimento com Java e Orientação a Objetos no contexto de sistemas distribuídos.
