# BDD - Foco no Comportamento e Integração do Sistema Completo
Funcionalidade: Gestão de Contas Bancárias (CRUD)

  Cenário: 1. Criar Conta com Sucesso e Validar Envio Assíncrono
    Dado que a aplicação está rodando (Docker-Compose)
    Quando eu envio uma requisição POST autenticada para "/api/contas" com o corpo:
      | titular | saldo |
      | John BDD | 2500.50 |
    Então o código de resposta deve ser 201
    E a resposta deve conter "John BDD"
    E a resposta deve conter "2500.50"
    E eu devo conseguir buscar a conta recém-criada por ID

  Cenário: 2. Falha de Segurança (Acesso Não Autorizado)
    Dado que a aplicação está rodando (Docker-Compose)
    Quando eu envio uma requisição GET não autenticada para "/api/contas"
    Então o código de resposta deve ser 401
    E a resposta de erro deve conter "Unauthorized"

  Cenário: 3. Falha de Validação (Regra de Negócio: Saldo Negativo)
    Dado que a aplicação está rodando (Docker-Compose)
    Quando eu envio uma requisição POST autenticada para "/api/contas" com o corpo:
      | titular | saldo |
      | Cliente Negativo | -1.00 |
    Então o código de resposta deve ser 400
    E a resposta de erro deve conter "O saldo não pode ser negativo"

  Cenário: 4. Falha de Recurso (Conta Não Encontrada)
    Dado que a aplicação está rodando (Docker-Compose)
    Quando eu envio uma requisição GET autenticada para "/api/contas/999999"
    Então o código de resposta deve ser 404
    E a resposta de erro deve conter "não foi encontrada"
