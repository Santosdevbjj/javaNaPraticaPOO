# ☕ Microsserviço Bancário POO — Da Teoria à Produção

> Este projeto complementa meu artigo que participou da na 37ª Competição de Artigos da DIO


[![Java](https://img.shields.io/badge/Java-17-red?logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)](https://www.docker.com/)
[![JUnit5](https://img.shields.io/badge/JUnit_5-TDD-orange?logo=java)](https://junit.org/junit5/)
[![Cucumber](https://img.shields.io/badge/Cucumber-BDD-green?logo=cucumber)](https://cucumber.io/)
[![License](https://img.shields.io/badge/license-MIT-lightgrey)](LICENSE)

---

## 1. 🧩 Problema de Negócio

A maioria dos cursos e tutoriais ensina POO através de exemplos artificiais: `Cachorro extends Animal`, `Carro.acelerar()`. Conceitos isolados, sem contexto real, que o desenvolvedor memoriza mas não sabe aplicar quando encontra um problema de verdade no trabalho.

O resultado prático é um dev que conhece a teoria mas **não pensa como engenheiro de software**:

- Cria classes sem encapsulamento real, expondo estado interno sem proteção;
- Não escreve testes porque "funciona na minha máquina";
- Acumula acoplamento até que qualquer mudança quebra o sistema inteiro;
- Usa `double` para calcular dinheiro e não entende por que os centavos somem.

> **O que este projeto resolve:** demonstrar, através de um microsserviço bancário funcional e completo, como POO, TDD, BDD e SOLID se aplicam juntos em código de produção — não como conceitos separados, mas como uma forma de pensar integrada.

Este repositório é o **produto final do artigo** *"Java na Prática: OO para Pensar como Engenheiro de Software"*, desenvolvido para a 37ª Competição de Artigos da DIO.

---

## 2. 📌 Contexto

O domínio bancário foi escolhido intencionalmente porque ele **força decisões técnicas reais**:

- Dinheiro não pode ser representado com `double` — exige `BigDecimal` e `DECIMAL(19,2)` no banco;
- Saldo insuficiente não é uma condição de erro genérica — é uma regra de negócio que precisa de exceção própria com mensagem clara;
- Operações financeiras não podem ser parcialmente executadas — atomicidade com `@Transactional` é obrigatória;
- Eventos como criação de conta e depósitos precisam ser comunicados a outros sistemas de forma assíncrona, sem acoplamento direto — justificativa real para o RabbitMQ.

A aplicação implementa um microsserviço de contas bancárias com operações de **criação, depósito, saque e consulta**, protegida por autenticação Basic Auth via Spring Security, com todos os eventos publicados em filas RabbitMQ e toda a lógica coberta por testes TDD e BDD.

---

## 3. 📐 Premissas da Solução

As seguintes decisões foram tomadas como premissas de arquitetura e não foram negociadas:

- **`BigDecimal` é inegociável** para qualquer valor monetário — `double` e `float` têm erros de ponto flutuante que são inaceitáveis em sistemas financeiros;
- Todo método que altera o estado de uma conta bancária é **transacional** (`@Transactional`) — não existe estado parcialmente atualizado;
- Nenhum teste acessa o banco de dados real — os testes unitários usam Mockito para isolar dependências. O banco só é tocado nos testes de integração;
- Exceções de negócio têm classes próprias (`ContaNaoEncontradaException`, `SaldoInsuficienteException`) — nada de lançar `RuntimeException` genérica com string no construtor;
- A API segue REST com os verbos HTTP corretos e retorna códigos de status semânticos (400 para validação, 404 para recurso não encontrado, 201 para criação).

---

## 4. ⚙️ Estratégia da Solução

A construção seguiu a ordem que qualquer engenheiro de software deveria usar: **domínio primeiro, infraestrutura depois**.

**Passo 1 — Modelagem do domínio com encapsulamento real**
A entidade `ContaBancaria` não é um POJO anêmico. Ela possui validações via Bean Validation, rejeita saldo negativo no construtor e protege seu estado através de métodos com semântica de negócio (`depositar`, `sacar`), não apenas getters e setters.

**Passo 2 — TDD antes de qualquer implementação de serviço**
Seguindo o ciclo Red → Green → Refactor: os testes em `ContaBancariaTest.java` e `ContaBancariaServiceTest.java` foram escritos antes das implementações. Mockito foi usado para simular o DAO e o publisher RabbitMQ, isolando a lógica de negócio de qualquer dependência externa.

**Passo 3 — Camada de serviço com SOLID**
`ContaBancariaService` tem uma única responsabilidade. A comunicação com RabbitMQ é delegada a um componente separado (`ContaEventPublisher`), respeitando o Princípio da Responsabilidade Única e o Princípio da Inversão de Dependência.

**Passo 4 — API REST com tratamento centralizado de erros**
`GlobalExceptionHandler` com `@RestControllerAdvice` intercepta todas as exceções de negócio e retorna respostas padronizadas — sem `try/catch` espalhado pelos controllers.

**Passo 5 — Mensageria assíncrona com RabbitMQ**
Eventos de domínio (criação de conta, depósito, saque) são publicados na fila `conta_eventos` de forma assíncrona. Isso desacopla o microsserviço de qualquer consumidor futuro.

**Passo 6 — BDD com Cucumber e RestAssured**
Cenários escritos em Gherkin (`.feature`) descrevem o comportamento esperado da API do ponto de vista do negócio. `ContaBancariaSteps.java` traduz esses cenários em chamadas HTTP reais via RestAssured.

**Passo 7 — Containerização com Docker Compose**
A stack completa (App + MySQL + RabbitMQ) sobe com um único comando. Sem configuração manual de banco, sem "funciona na minha máquina".

---

## 5. 💡 Insights Técnicos

Os aprendizados mais valiosos vieram das decisões que pareciam pequenas mas tinham consequências grandes:

- **`BigDecimal` muda a forma de pensar o domínio.** Não é só trocar `double` por `BigDecimal` — você precisa definir escala, modo de arredondamento e como comparar valores. A decisão de usar `DECIMAL(19,2)` no MySQL foi consequência direta de entender que o banco precisa ter a mesma precisão que o Java.

- **TDD não é sobre testes — é sobre design.** Quando você escreve o teste antes da implementação, você é obrigado a pensar na interface da sua classe antes de pensar no seu funcionamento interno. Foi escrevendo os testes do `ContaBancariaService` que ficou claro que o publisher RabbitMQ precisava ser uma dependência injetada, não instanciada dentro do serviço.

- **Exceções de negócio com `@ResponseStatus` eliminam o `GlobalExceptionHandler` para casos simples.** A combinação de exceções bem nomeadas com o handler centralizado resultou em controllers com zero lógica de erro — eles só orquestram o caminho feliz.

- **Cucumber força a conversa entre técnico e negócio.** Escrever cenários em Gherkin obriga você a pensar no comportamento do sistema como um usuário o veria, não como um desenvolvedor. "Quando envio POST com saldo -100.00, então o sistema retorna 400" é uma especificação que qualquer pessoa entende — e que também é um teste automatizado.

- **RabbitMQ em Docker tem armadilha de timing.** O Spring Boot sobe mais rápido que o RabbitMQ. Sem um `depends_on` com `condition: service_healthy` no `docker-compose.yml`, a aplicação tenta conectar ao broker antes que ele esteja pronto e falha na inicialização.

---

## 6. 📊 Resultados

**Conceitos de POO aplicados com rastreabilidade:**

| Conceito | Onde está no código | Benefício concreto |
|---|---|---|
| Encapsulamento | `ContaBancaria` — validações e métodos de negócio | Estado protegido, impossível criar conta com saldo negativo |
| Polimorfismo | `ContaBancariaDAO extends JpaRepository` | CRUD sem boilerplate, extensível sem modificação |
| Programação Defensiva | `GlobalExceptionHandler` + Bean Validation | API nunca retorna stack trace para o cliente |
| Precisão Financeira | `BigDecimal` + `DECIMAL(19,2)` | Zero erros de arredondamento em operações monetárias |
| Baixo Acoplamento | RabbitMQ como intermediário de eventos | Microsserviço não conhece os consumidores dos seus eventos |
| TDD | JUnit 5 + Mockito | Regressões detectadas antes de subir para produção |
| BDD | Cucumber + RestAssured | Comportamento da API validado como especificação executável |

**Fluxo de TDD aplicado:**
```
[RED]     → Escreva um teste que falha (o comportamento ainda não existe)
[GREEN]   → Implemente o mínimo para o teste passar
[REFACTOR]→ Melhore o código sem quebrar nenhum teste
```

**Exemplo de cenário BDD (Gherkin):**
```gherkin
Funcionalidade: Criação de Conta Bancária
  Cenário: Saldo Inicial Negativo
    Dado que o endpoint /api/contas está disponível
    Quando envio POST com saldo -100.00
    Então o sistema retorna 400 e mensagem "Saldo não pode ser negativo"
```

**Cobertura de testes:**

| Tipo | Framework | Arquivo | O que valida |
|---|---|---|---|
| TDD — Unidade | JUnit 5 | `ContaBancariaTest.java` | Depósitos, saques e validações da entidade |
| TDD — Serviço | Mockito | `ContaBancariaServiceTest.java` | Lógica de negócio isolada do banco e do broker |
| BDD — Comportamento | Cucumber + RestAssured | `ContaBancariaSteps.java` + `.feature` | Fluxos end-to-end da API como o negócio enxerga |

**Arquitetura UML:**

<img width="946" height="1543" alt="UML Simplificado" src="https://github.com/user-attachments/assets/271d9d3c-8635-4b18-8deb-7712dd8b3730" />

**Estrutura de pastas:**

<img width="908" height="1666" alt="Estrutura de Pastas" src="https://github.com/user-attachments/assets/d1d3202c-a22e-4662-86b4-82c537031560" />

---

## 7. 🚀 Próximos Passos

A base está sólida para evoluir este microsserviço em direção a um sistema bancário real:

- [ ] **Autenticação JWT** — substituir Basic Auth por tokens JWT com refresh token, eliminando a necessidade de enviar credenciais em toda requisição;
- [ ] **Saga Pattern** — implementar transações distribuídas entre microsserviços usando o padrão Saga com RabbitMQ para garantir consistência eventual;
- [ ] **Transferência entre contas** — nova operação de negócio que exige atomicidade em duas contas simultaneamente, testando os limites do `@Transactional` em operações compostas;
- [ ] **Cobertura de testes com JaCoCo** — gerar relatório de cobertura de código e definir threshold mínimo de 80% no pipeline;
- [ ] **Observabilidade** — adicionar métricas com Micrometer + Prometheus e rastreamento distribuído com Zipkin;
- [ ] **Rate Limiting** — proteger os endpoints contra abuso com Spring Cloud Gateway ou Resilience4j.

---

## 🛠️ Tecnologias Utilizadas

| Categoria | Tecnologia | Versão | Função no projeto |
|---|---|---|---|
| Linguagem | Java | 17 | Lógica de negócio e testes |
| Framework | Spring Boot | 3.2.0 | Estrutura do microsserviço |
| Persistência | Spring Data JPA / Hibernate | 3.2.0 | ORM e acesso a dados |
| Banco de Dados | MySQL | 8.0 | Armazenamento de contas |
| Mensageria | RabbitMQ | 3.12 | Comunicação assíncrona de eventos |
| Segurança | Spring Security | 3.2.0 | Autenticação Basic Auth |
| Containerização | Docker / Docker Compose | Latest | Stack completa em um comando |
| Testes Unitários | JUnit 5 + Mockito | 5 | TDD com isolamento de dependências |
| Testes de Sistema | Cucumber + RestAssured | 7 | BDD end-to-end |

---

## 🔧 Como Executar Localmente

**Pré-requisitos:** Docker e Docker Compose instalados.

**1. Clone o repositório**
```bash
git clone https://github.com/Santosdevbjj/javaNaPraticaPOO.git
cd javaNaPraticaPOO
```

**2. Suba toda a stack (App + MySQL + RabbitMQ)**
```bash
docker-compose up --build -d
```

**3. Verifique se a aplicação está no ar**
```
http://localhost:8080/actuator/health
```

**4. Teste a API via Postman**
```
Importe: api-tests/javaNaPraticaPOO-Collection.json
```

**5. Acompanhe os logs**
```bash
docker-compose logs -f app
```

**6. Encerre os containers**
```bash
docker-compose down
```

---

## 📘 Documentação Complementar

| Manual | Descrição | Caminho |
|---|---|---|
| Manual do Usuário | Execução via Docker e uso do Postman | `docs/Manual_Usuario_Leigo.md` |
| Manual Técnico | Arquitetura, debugging e decisões de design | `docs/Manual_Usuario_Tecnico.md` |

---

## 🔗 Artigo na DIO

Este repositório é o código que acompanha o artigo **"Java na Prática: OO para Pensar como Engenheiro de Software"**, publicado na 37ª Competição de Artigos da DIO.

O artigo explica o *porquê* de cada decisão técnica implementada aqui — da escolha do `BigDecimal` até a arquitetura de eventos com RabbitMQ.

---

## 📚 Aprendizados

Este projeto consolidou algo que eu entendia teoricamente mas só aprendi de verdade na prática: **POO, TDD e SOLID não são técnicas separadas — são a mesma forma de pensar aplicada em camadas diferentes**.

Quando você encapsula bem, o teste fica fácil de escrever. Quando o teste é fácil de escrever, o design está correto. Quando o design está correto, adicionar o RabbitMQ não quebra nada porque as dependências já estavam invertidas.

O maior erro que cometi foi tentar escrever código de produção antes de ter os testes. Quando voltei para o ciclo TDD correto — teste primeiro, implementação depois — o design emergiu naturalmente, sem precisar refatorar uma arquitetura ruim depois.

Se fosse recomeçar, começaria pela escrita dos cenários BDD em Gherkin, antes de qualquer linha de código Java. Definir o comportamento esperado da API na linguagem do negócio força uma clareza que nenhum diagrama de classes consegue provocar.

---

## 📚 Referências

- Bloch, Joshua. *Effective Java*. Addison-Wesley, 2018.
- Martin, Robert C. *Clean Code*. Prentice Hall, 2008.
- Gamma et al. *Design Patterns*. Addison-Wesley, 1994.
- [Oracle Java SE 17 Docs](https://docs.oracle.com/en/java/javase/17/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

---

## 👤 Autor

**Sérgio Santos**

Analista de sistemas com experiência em desenvolvimento, suporte técnico e automação de processos. Pesquisa e aplica engenharia de software com foco em boas práticas, testes e arquitetura.

[![Portfólio](https://img.shields.io/badge/Portfólio-Sérgio_Santos-111827?style=for-the-badge&logo=githubpages&logoColor=00eaff)](https://portfoliosantossergio.vercel.app)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Sérgio_Santos-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/santossergioluiz)

---

> *"O mercado de trabalho não contrata quem usa ferramentas — contrata quem resolve problemas com elas."*
> — Meigarom Lopes
