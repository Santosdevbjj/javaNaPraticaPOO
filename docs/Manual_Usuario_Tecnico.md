📘 **Manual Técnico**
Sistema: Java na Prática — Microsserviço Bancário POO (API REST)
Público: Desenvolvedores, Engenheiros de Software, DevOps e Avaliadores Técnicos.
🏗️ Arquitetura e Estrutura do Repositório
O projeto adota uma arquitetura de Microsserviços e a estrutura de diretórios Domain-Driven Design (DDD) com separação estrita de camadas (Controller, Service, DAO).
| Arquivo/Pasta | Tipo | Descrição e Finalidade |
|---|---|---|
| pom.xml | Config | Gerenciamento de dependências (Spring Web, JPA, Security, RabbitMQ, Validation) e ferramentas de Teste (Cucumber, RestAssured). |
| Dockerfile | Build | Instruções para construir a imagem Docker da aplicação Java (Java 17 + Maven Build + JRE para execução). |
| docker-compose.yml | Orquestração | Define e conecta os três serviços da arquitetura: java-app, mysql-db, e rabbitmq-broker. |
| api-tests/ | Testes BDD | Contém o arquivo javaNaPraticaPOO-Collection.json (Coleção Postman), utilizado para testes manuais e como documentação da API. |
| src/main/resources/ | Config | application.properties para configurações de JPA, porta 8080 e RabbitMQ. |
| src/main/java/.../model/ | Domínio | Classes de Entidade (ContaBancaria.java). Utiliza BigDecimal e Bean Validation. |
| src/main/java/.../dao/ | Persistência | Interfaces de Acesso a Dados (DAO). Extende JpaRepository (Spring Data JPA). |
| src/main/java/.../service/ | Negócio | Camada de Regra de Negócio e Colaboração (chama DAO e RabbitTemplate). |
| src/main/java/.../controller/ | Fronteira | Lida com requisições HTTP, ativa Bean Validation (@Valid) e usa o Service. |
| src/main/java/.../config/ | Configuração | SecurityConfig.java (Spring Security), RabbitMQConfig.java. |
| src/main/java/.../exception/ | Exceções | Exceções de Domínio (ContaNaoEncontradaException). |
| src/main/java/.../controller/handler/ | Erros | GlobalExceptionHandler.java (Controller Advice) para tratamento de erros padronizado (404, 400). |
| src/test/java/.../ | Testes TDD/BDD | Contém a suíte completa de testes. |
🔧 Requisitos de Desenvolvimento
| Ferramenta | Versão | Uso |
|---|---|---|
| Docker Engine | 20.10+ | Ambiente de execução para toda a stack (App, DB, Messaging). |
| Docker Compose | 3.8+ | Orquestração da stack de 3 serviços. |
| Java JDK | 17+ | Linguagem de desenvolvimento. |
| Maven | 3.6+ | Gerenciamento de dependências e build (dentro do Dockerfile). |
| Postman | Última | Cliente manual para testar a API REST (BDD). |
📦 Dependências (pom.xml)
As dependências refletem a arquitetura de produção e a estratégia de testes TDD/BDD:
| Grupo ID | Artifact ID | Finalidade Técnica |
|---|---|---|
| org.springframework.boot | spring-boot-starter-web | API REST e Servidor Tomcat embutido. |
| org.springframework.boot | spring-boot-starter-data-jpa | Persistência ORM (JPA/Hibernate). |
| mysql | mysql-connector-j | Driver de conexão com MySQL. |
| org.springframework.boot | spring-boot-starter-amqp | Cliente para RabbitMQ (Mensageria Assíncrona). |
| org.springframework.boot | spring-boot-starter-security | Autenticação HTTP Basic e proteção de endpoints. |
| org.springframework.boot | spring-boot-starter-actuator | Monitoramento e Health Check da aplicação. |
| org.springframework.boot | spring-boot-starter-validation | Bean Validation para regras de domínio (@DecimalMin, @NotNull). |
| org.springframework.boot | spring-boot-starter-test | TDD Core (JUnit 5, Mockito). |
| io.cucumber | cucumber-junit, cucumber-spring | BDD Core e integração com o contexto Spring. |
| io.rest-assured | rest-assured | Cliente HTTP robusto para testes de integração BDD. |
🧱 Arquitetura e Responsabilidades (OO e SOLID)
| Camada | Responsabilidade (CRC) | Detalhes de Implementação |
|---|---|---|
| Controller | Lida com Requisição/Resposta (HTTP I/O). | Ativa a Validação (@Valid) e delega ao Service. Usa Global Exception Handler para 400/404. |
| Service | Regras de Negócio, Transações, Colaboração. | Implementa o princípio SRP (Single Responsibility Principle): Orquestra o DAO e o RabbitTemplate. |
| DAO | Persistência de Dados. | Utiliza Spring Data JPA (Acoplamento Abstrato) – Não há código SQL ou JDBC manual. |
| SecurityConfig | Segurança (AOP). | Configura o Filtro de Segurança para exigir Basic Auth (user/password) em todos os endpoints, exceto /actuator/**. |
| RabbitMQ | Comunicação Assíncrona. | O método criar() no Service envia uma mensagem para o broker, desacoplando a lógica de notificação do serviço principal. |
🧪 Estratégia de Testes (TDD e BDD)
O projeto emprega uma estratégia de testes em três níveis:
1. TDD (Testes Unitários e de Integração)
| Local | Foco | Ferramentas |
|---|---|---|
| .../model/ContaBancariaTest.java | Teste do Estado e Encapsulamento do POJO, validando o uso do BigDecimal. | JUnit 5. |
| .../service/ContaBancariaServiceTest.java | Teste de Responsabilidade e Colaboração. | Mockito para simular (mockar) o ContaBancariaDAO e o RabbitTemplate, garantindo o isolamento da lógica. |
Como rodar TDD: mvn test
2. BDD (Testes de Sistema/Comportamento)
| Local | Foco | Ferramentas |
|---|---|---|
| src/test/resources/features/*.feature | Definição dos Requisitos de Negócio (Gherkin: Dado/Quando/Então). | Cucumber. |
| .../steps/ContaBancariaSteps.java | Teste da API completa (End-to-End). | RestAssured para fazer chamadas HTTP reais (POST, GET), validando o ciclo completo (Controller -> Service -> JPA -> MySQL). |
Como rodar BDD: Execute a classe ContaBancariaBDDRunner.java via IDE ou Maven.
🛠️ Execução e Debugging
O ambiente de execução é totalmente containerizado.
1. Inicialização da Stack
 * Verifique se o Docker está em execução.
 * Na raiz do projeto, execute o comando de orquestração:
   docker-compose up --build -d

   (Este comando constrói a imagem Java, inicia a aplicação, o MySQL e o RabbitMQ, interligando a rede Docker.)
2. Debugging da Aplicação (Live Debugging)
Para debug em tempo real:
 * Modifique o docker-compose.yml para expor a porta de debug (ex: 8080:8080 e 5005:5005).
 * Altere a seção command do serviço app para incluir o comando JVM de debug:
   command: java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar /app/app.jar

 * Reexecute: docker-compose up --build -d.
 * Conecte sua IDE (IntelliJ/Eclipse) na porta 5005 (localhost:5005) com breakpoints.
3. Logs e Monitoramento
 * Logs: Para visualizar os logs de todos os serviços em tempo real:
   docker-compose logs -f

 * Actuator: Para verificar a saúde da aplicação:
   http://localhost:8080/actuator/health
4. Parada da Stack
Para parar e remover todos os containers:
docker-compose down

🔐 Segurança e Boas Práticas
 * Credenciais Hardcoded Removidas: Credenciais de MySQL (root/admin) e RabbitMQ (guest/guest) são definidas APENAS no docker-compose.yml (variáveis de ambiente), e o código Java as lê via application.properties (que aponta para as variáveis de ambiente do container).
 * Controle de Acesso: Implementação obrigatória de Spring Security (Autenticação Básica) para proteger os endpoints de API.
 * Validação na Fronteira: O Bean Validation atua na camada Controller, garantindo que a integridade do domínio seja mantida antes que o dado chegue ao Service ou DAO.
 * Precisão Financeira: Uso de java.math.BigDecimal para o atributo saldo (essencial para evitar erros de ponto flutuante).
📈 Melhorias Futuras
 * Desacoplamento Completo do Listener: Mover a lógica de consumo de RabbitMQ (o Listener) para um microsserviço dedicado (External Service), garantindo um acoplamento mais fraco.
 * CI/CD: Integrar o mvn clean install e a execução dos testes BDD/TDD no GitHub Actions/GitLab CI.
 * Monitoramento Avançado: Integrar o Actuator com Prometheus e Grafana para visualização de métricas em tempo real.
 * Testcontainers: Adotar Testcontainers para os testes de integração DAO, garantindo que o MySQL seja iniciado e destruído automaticamente por cada execução de teste.
 * Autenticação JWT: Migrar do Basic Auth para JSON Web Tokens (JWT) para um modelo de segurança sem estado.
