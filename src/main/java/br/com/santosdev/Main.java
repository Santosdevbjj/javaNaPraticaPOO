package br.com.santosdev;

import br.com.santosdev.service.ContaBancariaService;
import java.util.Scanner;

/**
 * Classe principal com menu interativo em console.
 */
public class Main {
    public static void main(String[] args) {
        ContaBancariaService service = new ContaBancariaService();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== SISTEMA BANCÁRIO JAVA POO ===");
            System.out.println("1. Criar conta");
            System.out.println("2. Listar contas");
            System.out.println("3. Atualizar conta");
            System.out.println("4. Excluir conta");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome do titular: ");
                    sc.nextLine();
                    String nome = sc.nextLine();
                    System.out.print("Saldo inicial: ");
                    double saldo = sc.nextDouble();
                    service.criar(nome, saldo);
                }
                case 2 -> service.listar();
                case 3 -> {
                    System.out.print("ID da conta: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Novo titular: ");
                    String nome = sc.nextLine();
                    System.out.print("Novo saldo: ");
                    double saldo = sc.nextDouble();
                    service.atualizar(id, nome, saldo);
                }
                case 4 -> {
                    System.out.print("ID da conta a excluir: ");
                    int id = sc.nextInt();
                    service.deletar(id);
                }
                case 0 -> System.out.println("Saindo... Obrigado!");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        sc.close();
    }
}
