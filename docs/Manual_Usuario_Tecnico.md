## `docs/Manual_Usuario_Tecnico.md`


**Manual Técnico**
**Sistema:** Java na Prática — CRUD de Contas Bancárias (Console)  
**Público:** Desenvolvedores, DevOps, avaliadores técnicos (DIO), e revisores de código.

---



## 📁 Estrutura do repositório (caminhos exatos)

<img width="982" height="1359" alt="Screenshot_20251020-192035" src="https://github.com/user-attachments/assets/81760750-cf78-4a7f-91f1-791badd66e21" />


---

---

## 🔧 Requisitos de desenvolvimento

- **Java JDK** 17+  
- **Maven** 3.8+ (ou Gradle se adaptar)  
- **MySQL Server** 8.0+  
- IDEs recomendadas: IntelliJ IDEA (recomendado), Eclipse ou VS Code (com extensões Java)  
- Sistema operacional: Windows / Linux / macOS

---

## 📦 Dependências (pom.xml)

Principais dependências:
- `mysql:mysql-connector-java:8.0.33` — driver JDBC para MySQL
- `org.junit.jupiter:junit-jupiter` — JUnit 5 para testes unitários

---

## 🧩 Banco de Dados

**Arquivo:** `sql/script_banco.sql`

Conteúdo (resumo):
```sql
CREATE DATABASE IF NOT EXISTS java_poo;
USE java_poo;

CREATE TABLE IF NOT EXISTS conta_bancaria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titular VARCHAR(100) NOT NULL,
    saldo DOUBLE NOT NULL
);

-- Inserts de exemplo
INSERT INTO conta_bancaria (titular, saldo) VALUES
('Sérgio Santos', 1200.00),
('Ana Costa', 800.50),
('Carlos Silva', 2500.75);



---
```

**Observações:**

Ajuste charset/collation se necessário (ex.: utf8mb4).

Para ambientes corporativos, crie usuário de aplicativo com permissões limitadas.



---

🔗 **Conexão com o banco**

Arquivo: src/main/java/br/com/santosdev/database/ConnectionFactory.java

Exemplo (padrão):

private static final String URL = "jdbc:mysql://localhost:3306/java_poo";
private static final String USER = "root";
private static final String PASSWORD = "admin";

**Boas práticas (recomendadas):**

Não hardcodear credenciais em produção.

Preferir variáveis de ambiente ou arquivo application.properties não versionado.

Exemplo usando variáveis de ambiente (sugestão de alteração):


String url = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/java_poo");
String user = System.getenv().getOrDefault("DB_USER", "root");
String pass = System.getenv().getOrDefault("DB_PASS", "admin");


---



🧱 **Explicação da arquitetura e responsabilidades**

**Model**

ContaBancaria.java — POJO com atributos id, titular, saldo. Contém getters/setters e toString().


**DAO** (Data Access Object)

ContaBancariaDAO.java — métodos:

criarConta(ContaBancaria conta) → INSERT

listarContas() → SELECT

atualizarConta(ContaBancaria conta) → UPDATE

deletarConta(int id) → DELETE


Usa PreparedStatement para prevenir SQL injection básico.

Gerenciamento de conexão com try-with-resources.


**Service**

ContaBancariaService.java — camada de regras de negócio:

Validações simples (ex.: saldo não negativo).

Converte entrada do Main em objetos do domínio e chama DAO.



**Main** (Interface)

Main.java — menu interativo no console:

Recebe input do usuário, chama service e exibe resultados.

Simples e síncrono — ideal para demonstração.




---

🧪 **Testes**

Local: src/test/java/.../ContaBancariaTest.java

JUnit 5 (@BeforeEach, @Test).

Testes focam em criação, leitura, atualização e comportamento do POJO.

Para testes integrados com MySQL, adicionar perfil/testcontainer (opcional).


Como rodar os testes:

mvn test


---

🛠️ **Como rodar localmente (passo a passo técnico)**

1. Preparar banco

Importar sql/script_banco.sql.

Verificar usuário, senha e host.



2. Ajustar ConnectionFactory (se necessário)

Caminho: src/main/java/br/com/santosdev/database/ConnectionFactory.java

Recomendado: usar variáveis de ambiente em vez de valores hardcoded.



3. Build

mvn clean install


4. Executar

mvn exec:java -Dexec.mainClass="br.com.santosdev.Main"


5. Rodar testes

mvn test




---

🔍 **Logs, erros comuns e soluções**

**Erro:** CommunicationsException: Communications link failure

**Causa:** MySQL não está rodando ou endereço incorreto.

**Solução:** Verifique systemctl status mysql (Linux), ou MySQL Workbench. Confirme URL, porta e host.


**Erro:** Access denied for user 'root'@'localhost'

**Causa:** Senha incorreta ou usuário sem permissão.

**Solução:** Alterar ConnectionFactory para as credenciais corretas ou criar usuário com GRANT apropriado:

CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'senha';
GRANT ALL PRIVILEGES ON java_poo.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;


**Erro:** Table 'java_poo.conta_bancaria' doesn't exist

**Causa:** Script SQL não executado ou banco errado.

**Solução:** Execute sql/script_banco.sql e confirme USE java_poo; SHOW TABLES;.


**Erro:** NoClassDefFoundError ou ClassNotFoundException ao executar via mvn exec

**Causa:** Dependência faltando ou plugin exec não configurado.

**Solução:** Assegure que mvn clean install seja executado com sucesso e que o pom.xml inclua dependências necessárias. Para execução direta de um JAR, empacote com mvn package e rode java -jar target/...jar.



---

🔐 **Segurança e produção**

Never commit credentials — remova credenciais do código antes de subir para repositório público.

**Para produção:**

Use um usuário MySQL com permissões mínimas.

Habilite TLS/SSL para conexões ao banco (se remoto).

Centralize segredos em cofre (Vault, AWS Secrets Manager etc.).

Configure backup automático do banco.




---

📈 **Melhorias futuras** (sugestões técnicas)

Adicionar API REST (Spring Boot) para expor operações CRUD via HTTP.

Adicionar autenticação (JWT) e autorização por papel.

Substituir console por UI (JavaFX ou frontend web).

Integrar CI/CD com GitHub Actions (build + testes).

Utilizar Testcontainers para testes de integração com MySQL isolado.

Containerizar com Docker e orquestrar via Docker Compose.



---

🔁 **Migração para Docker** (exemplo rápido)

docker-compose.yml sugerido (resumo):

version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: admin
      MYSQL_DATABASE: java_poo
    ports:
      - "3306:3306"
    volumes:
      - db_data:/var/lib/mysql

volumes:
  db_data:

Após docker-compose up -d, alterar ConnectionFactory para apontar para host localhost (ou mysql dentro da rede Docker-compose).


---

🔧 **Debugging e logs**

Adicione System.out.println() temporários nas camadas Service/DAO para rastrear fluxo em execução local.

Para projeto maior, adote SLF4J + Logback para logs configuráveis por nível (INFO/DEBUG/ERROR).



---

📚 **Referências úteis**

Oracle JDBC: https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/

MySQL JDBC driver: https://dev.mysql.com/downloads/connector/j/

JUnit 5: https://junit.org/junit5/



---

🧾 **Contato do autor**

**Sérgio Santos**
Repositório: https://github.com/Santosdevbjj/javaNaPraticaPOO
LinkedIn: (adicione seu link)



---

Fim do manual técnico.

---






