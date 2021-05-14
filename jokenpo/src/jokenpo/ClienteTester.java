package jokenpo;

import java.rmi.Naming;
import java.util.Scanner;
import jokenpo.Conexao;

// Classe para realização de testes de acesso ao servidor - arquivo temporario
public class ClienteTester {

    public static void main(String[] args) {

        try {

            // Cria a conexao com o Servidor
            Conexao conexao = (Conexao) Naming.lookup("rmi://LOCALHOST:1099/jokenpo");
            Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);

            System.out.print("Digite '1' para JxJ ou '2' para JxCPU: ");
            int escolha = sc2.nextInt();

            if (escolha == 1) {
                // JxJ -------------------------------------------------------------
                System.err.println("\nExemplo de Jogo JxJ");

                System.out.print("Cadastro do Jogador1: ");
                String jogador1 = sc.next();
                conexao.CadastrarJogadores(jogador1, 1);

                System.out.print("Cadastro do Jogador2: ");
                String jogador2 = sc.next();
                conexao.CadastrarJogadores(jogador2, 2);

                System.out.print("\nVez do Jogador1: ");
                int jogada1 = sc2.nextInt();

                conexao.Jogar(jogada1, 1);

                System.out.print("Vez do Jogador2: ");
                int jogada2 = sc2.nextInt();

                conexao.Jogar(jogada2, 2);

                System.out.println("\n" + conexao.DeterminarVencedorEReiniciarJogo() + "\n");
                // JxJ -------------------------------------------------------------
            } else {
                // JxCPU -----------------------------------------------------------
                System.err.println("\nExemplo de Jogo JxCPU");

                System.out.print("Cadastro do Jogador: ");
                String jogador = sc.next();
                conexao.CadastrarJogador(jogador);

                System.out.print("\nVez do Jogador: ");
                int jogada = sc2.nextInt();

                conexao.Jogar(jogada, 1);

                System.out.println("\n" + conexao.DeterminarVencedorEReiniciarJogo() + "\n");
                // JxCPU -----------------------------------------------------------
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
