📘 **Manual do Usuário Leigo**
Sistema: Java na Prática — Microsserviço Bancário (API REST)
Objetivo: Guiar usuários sem conhecimento técnico a executar o sistema (via Docker) e a testar suas funcionalidades (via Postman).
🟢 Visão geral (em uma frase)
Este sistema é um serviço web seguro que permite criar, listar, atualizar e excluir contas bancárias. Ele roda em um ambiente isolado (Docker) e é acessado por requisições HTTP, utilizando um aplicativo chamado Postman para enviar os comandos.
✅ O que você precisa (pré-requisitos mínimos)
Você não precisa mais instalar Java ou MySQL diretamente! O sistema usa Docker para rodar todos os componentes automaticamente.
 * Um computador com Windows, macOS ou Linux.
 * Docker e Docker Compose instalados e funcionando.
   * O Docker é a ferramenta que executa o sistema isoladamente.
 * Postman instalado.
   * O Postman é o aplicativo que você usará para enviar comandos (requisições) à API.
 * Arquivos do projeto na pasta local (clone do repositório):
<!-- end list -->
git clone https://github.com/Santosdevbjj/javaNaPraticaPOO.git
cd javaNaPraticaPOO

⚙️ Configuração e Execução (O Novo Passo a Passo)
A execução do seu sistema é agora feita em um único comando que inicia 3 componentes: A Aplicação Java, o Banco de Dados MySQL e o Broker de Mensagens RabbitMQ.
 * Abra o terminal/Prompt de Comando na pasta raiz do projeto (javaNaPraticaPOO).
 * Execute o sistema:
   docker-compose up --build -d

   Espera-se: Este comando pode demorar alguns minutos na primeira vez, pois ele baixa as imagens (MySQL/RabbitMQ) e compila o código Java.
 * Verifique se está no ar:
   Aguarde 30 segundos e verifique se o serviço está saudável, acessando o link (não precisa de login):
   http://localhost:8080/actuator/health
   Resultado esperado: Uma página ou texto JSON com {"status":"UP"}.
▶️ Como usar o sistema (via API REST)
Você não usa mais números no terminal; você envia comandos HTTP usando o Postman.
Passo 1: Importar a Coleção de Testes (Recomendado)
O repositório já inclui um arquivo com todos os testes prontos.
 * Abra o Postman.
 * Clique no botão "Import" (Importar).
 * Selecione a opção "File" e escolha o arquivo: api-tests/javaNaPraticaPOO-Collection.json
 * Uma nova pasta chamada "JavaNaPraticaPOO - Banking API" aparecerá na sua área de trabalho.
Passo 2: O Detalhe Importante — Segurança
O sistema agora é seguro (Spring Security). Para usar qualquer função, você deve fornecer um login e senha em cada comando:
| Credencial | Valor |
|---|---|
| Usuário (Username): | user |
| Senha (Password): | password |
(A coleção Postman que você importou já inclui essas credenciais em todos os testes.)
Passo 3: Executar o Fluxo de Testes (CRUD)
O jeito mais fácil de usar o sistema é rodar a sequência de testes prontos:
 * No Postman, clique nos três pontos (...) ao lado do nome da Coleção "JavaNaPraticaPOO - Banking API".
 * Clique em "Run collection" (Executar coleção).
 * Clique em "Run" na tela de execução.
A coleção executa os seguintes comandos para você:
| Ação | Comando HTTP (Método) | Função no Projeto |
|---|---|---|
| 1. Criar Conta (Sucesso) | POST /api/contas | Cria uma nova conta. |
| 2. Listar Contas | GET /api/contas | Exibe todas as contas. |
| 3. Atualizar Conta | PUT /api/contas/{id} | Altera o saldo/titular. |
| 4. Excluir Conta | DELETE /api/contas/{id} | Remove o registro. |
| 5. Testes de Erro | POST/GET com dados inválidos. | Testa o tratamento de erros (400 Bad Request) e segurança (401 Unauthorized). |
⚠️ Solução de Problemas Comuns (FAQ)
| Mensagem/Problema | O que significa (Causa) | Como Agir (Solução) |
|---|---|---|
| Connection Refused ou Could not send request | O sistema Java ou o Docker não está rodando. | Verifique no terminal: docker ps. Deve ver os containers java-app, mysql-db e rabbitmq. Se não estiverem, repita o docker-compose up --build -d. |
| HTTP 401 Unauthorized | Você esqueceu de incluir o login e senha. | Sempre use a autenticação básica (user/password) nas requisições da API. Se estiver usando a coleção Postman, verifique se a aba "Authorization" está configurada. |
| HTTP 400 Bad Request | Você violou uma regra de negócio ou formato. | Isso significa que a validação está funcionando! Exemplo: Você tentou criar uma conta com saldo negativo ou sem nome (@DecimalMin e @NotBlank). |
| HTTP 404 Not Found | O ID da conta não existe. | Isso significa que a gestão de exceções está funcionando! |
🔴 Como Parar o Sistema
Para desligar todos os containers e liberar as portas do seu computador:
 * Abra o terminal na pasta raiz.
 * Execute:
   docker-compose down

👩‍💻 Se precisar de ajuda
Se o sistema não subir após o docker-compose up, envie uma captura de tela do terminal após executar os seguintes comandos:
 * docker-compose up (o resultado da execução).
 * docker ps (para ver quais containers subiram).
