# 📘 Manual do Usuário Leigo
**Sistema:** Java na Prática — CRUD de Contas Bancárias (Console)  
**Objetivo:** Guiar usuários sem conhecimento técnico a executar e usar o sistema pelo terminal.

---

## 🟢 Visão geral (em uma frase)
Este sistema permite **criar, listar, atualizar e excluir** contas bancárias através de um **menu simples no terminal**. Os dados são armazenados localmente em um banco de dados MySQL.

---

## ✅ O que você precisa (pré-requisitos mínimos)

Antes de começar, confirme que:

- Um computador com Windows, macOS ou Linux.
- Java JDK 17 (ou superior) instalado.
  - Verifique com: `java -version`
- MySQL Server instalado e em execução (localmente ou servidor na mesma rede).
  - Você pode usar o MySQL Workbench ou phpMyAdmin para ver o banco.
- Arquivos do projeto na pasta local (clone do repositório):

---

git clone https://github.com/Santosdevbjj/javaNaPraticaPOO.git cd javaNaPraticaPOO


---

Se você não sabe como instalar Java ou MySQL, peça para um amigo técnico ou envie por e-mail que eu forneço links de instalação básicos.

---

## ⚙️ Configuração inicial (passo a passo simples)

1. **Importar o script do banco**
 - Abra o MySQL Workbench (ou outro cliente).
 - Execute o arquivo `sql/script_banco.sql` (localizado na pasta `sql/` do repositório).
 - Esse script cria o banco de dados `java_poo` e a tabela `conta_bancaria` com dados de exemplo.

2. **Verificar credenciais do banco**
 - Por padrão, o projeto usa:
   - Usuário: `root`
   - Senha: `admin`
   - Banco: `java_poo`
 - Essas credenciais estão no arquivo `src/main/java/br/com/santosdev/database/ConnectionFactory.java`.
 - Se o seu MySQL usa outra senha, peça a um técnico para alterar o arquivo ou me avise que eu explico como.

---

## ▶️ Como executar o sistema (modo usuário)

1. Abra o terminal/Prompt de comando.
2. Navegue até a pasta do projeto:

---

cd caminho/para/javaNaPraticaPOO

3. Compile e execute (com Maven):

mvn clean install mvn exec:java -Dexec.mainClass="br.com.santosdev.Main"

4. Você verá o menu em texto no terminal:

=== SISTEMA BANCÁRIO JAVA POO ===

1. Criar conta


2. Listar contas


3. Atualizar conta


4. Excluir conta


5. Sair


---


## 📝 Como usar o menu (passo a passo)

### 1) Criar conta
- Digite `1` e pressione Enter.
- Informe o **nome do titular** (ex.: Maria Silva) e pressione Enter.
- Informe o **saldo inicial** (ex.: `1000.50`) e pressione Enter.
- Mensagem esperada: `✅ Conta criada com sucesso!`

### 2) Listar contas
- Digite `2` e pressione Enter.
- O sistema exibirá todas as contas cadastradas com `id`, `titular` e `saldo`.

### 3) Atualizar conta
- Digite `3` e pressione Enter.
- Informe o **ID** da conta (veja na lista) e pressione Enter.
- Digite o **novo nome** e o **novo saldo** quando solicitado.
- Mensagem esperada: `✅ Conta atualizada com sucesso!`

### 4) Excluir conta
- Digite `4` e pressione Enter.
- Informe o **ID** da conta que deseja excluir.
- Mensagem esperada: `✅ Conta removida com sucesso!`

### 0) Sair
- Digite `0` para encerrar o programa.

---

## ⚠️ Mensagens comuns e como agir

- `Erro ao conectar ao banco: <mensagem>`  
- Verifique se o MySQL está rodando e se usuário/senha estão corretos.
- Abra o Workbench e tente logar com as mesmas credenciais.

- `Opção inválida!`  
- Digite apenas números conforme o menu (0 a 4).

- `Conta criada com sucesso!`  
- Tudo ok — os dados foram inseridos no banco.

---

## 🔍 Onde os dados são guardados?
Os registros ficam no banco MySQL `java_poo`, tabela `conta_bancaria`.  
Você pode ver os dados no MySQL Workbench ou executar:
```sql
USE java_poo;
SELECT * FROM conta_bancaria;

---
```


❓ **Perguntas Frequentes (FAQ)**

P: Posso usar o sistema sem instalar MySQL?
R: Não — os dados são persistidos no MySQL. Para uso temporário, posso gerar versão que usa arquivo local (se quiser).

P: Tenho medo de quebrar algo ao testar. Posso apagar o banco?
R: Sim, você pode recriar o banco com o script sql/script_banco.sql sempre que precisar.

P: Preciso de internet?
R: Não — apenas se estiver clonando o repositório pela primeira vez. O sistema roda localmente.



---



👩‍💻 Se precisar de ajuda

Se ficar travado em algum passo, me envie:

Captura de tela do erro no terminal, ou

A mensagem de erro exibida, que eu oriento o conserto.


---

