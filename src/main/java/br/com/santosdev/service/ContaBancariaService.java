package br.com.santosdev.service;

import br.com.santosdev.dao.ContaBancariaDAO;
import br.com.santosdev.model.ContaBancaria;
import java.util.List;

/**
 * Camada de serviço - executa regras de negócio antes do DAO.
 */
public class ContaBancariaService {

    private final ContaBancariaDAO dao = new ContaBancariaDAO();

    public void criar(String titular, double saldo) {
        if (saldo < 0) {
            System.out.println("❌ Saldo inicial não pode ser negativo.");
            return;
        }
        dao.criarConta(new ContaBancaria(titular, saldo));
    }

    public void listar() {
        List<ContaBancaria> contas = dao.listarContas();
        contas.forEach(System.out::println);
    }

    public void atualizar(int id, String titular, double saldo) {
        dao.atualizarConta(new ContaBancaria(titular, saldo) {{
            setId(id);
        }});
    }

    public void deletar(int id) {
        dao.deletarConta(id);
    }
}
