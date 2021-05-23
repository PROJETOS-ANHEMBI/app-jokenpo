package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javax.swing.Timer;
import model.Conexao;

public class App {

    static Conexao conexao;
    static Timer timer;
    static int segundosDeEspera;
    static int tipoJogador;
    static int vitorias, derrotas, empates;
    static String jogador;

    public static void main(String args[]) {

        try {

            // Inicia o servidor
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            conexao = (Conexao) registry.lookup("jokenpo");

            // Criando Scanners necessários
            Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);
            Scanner sc3 = new Scanner(System.in);
            String continuar = "s";

            // Cadastro de Jogador
            System.out.println("--- Cadastro do Jogador:");
            System.out.print("Digite seu Nome: ");
            jogador = sc.next();

            // Escolha do Modo de Jogo
            System.out.println("\n--- Modo de Jogo:");
            System.out.println("> '1' para JOGADOR x CPU\n> '2' para JOGADOR x JOGADOR");
            System.out.print("Escolha o Modo de Jogo: ");
            int escolha = sc2.nextInt();

            // Cadastra o Jogador com base no Modo de Jogo
            if (escolha == 1) {
                tipoJogador = conexao.CadastrarJogador(jogador);
            } else {
                tipoJogador = conexao.CadastrarJogadores(jogador);
            }

            // Execução do Jogo
            while (continuar.toLowerCase().equals(("s"))) {

                if (escolha == 1) {
                    // JOGADOR x CPU -------------------------------------------
                    System.err.println("\n--- JOGADOR x CPU");

                    if (tipoJogador != 0) {
                        System.out.print("Escolha: '1' para Pedra ou '2' para Papel ou '3' para Tesoura: ");
                        int jogada = sc2.nextInt();
                        conexao.Jogar(jogada, 1);
                    } else {
                        System.out.println("O Servidor está ocupado no momento!");
                        System.exit(0);
                    }

                    String[] vencedor = conexao.DeterminarVencedor();
                    if (vencedor[1] != null) {
                        System.out.println(vencedor[0] + "\nO Vencedor foi: " + vencedor[1]);
                        if (vencedor[2].equals(String.valueOf(tipoJogador))) {
                            vitorias++;
                        } else {
                            derrotas++;
                        }
                    } else {
                        empates++;
                        System.out.println(vencedor[0]);
                    }

                    // Timer que verifica o vencedor a cada 05 segundos
                    timer = new Timer(3000, VerificarVencedor);
                    timer.setRepeats(true);
                    timer.start();
                    // Timer que verifica o vencedor a cada 05 segundos

                } else if (escolha == 2) {
                    // JOGADOR x JOGADOR ---------------------------------------
                    System.err.println("\n--- JOGADOR x JOGADOR");

                    if (tipoJogador != 0) {
                        System.out.print("Escolha: '1' para Pedra ou '2' para Papel ou '3' para Tesoura: ");
                        int jogada = sc2.nextInt();
                        conexao.Jogar(jogada, tipoJogador);
                    } else {
                        System.out.println("O Servidor está ocupado no momento!");
                        System.exit(0);
                    }

                    String[] vencedor = conexao.DeterminarVencedor();
                    if (vencedor[1] != null) {
                        System.out.println(vencedor[0] + "\nO Vencedor foi: " + vencedor[1]);
                        if (vencedor[2].equals(tipoJogador)) {
                            vitorias++;
                        } else {
                            derrotas++;
                        }
                    } else {
                        System.out.println(vencedor[0]);
                    }

                    // Timer que verifica o vencedor a cada 05 segundos
                    timer = new Timer(3000, VerificarVencedor);
                    timer.setRepeats(true);
                    timer.start();
                    // Timer que verifica o vencedor a cada 05 segundos

                }
                System.out.println("\n--- PLACAR: |" + vitorias + " Vitorias | " + derrotas + " Derrotas | " + empates + " Empates |");
                System.out.println("\nDeseja continuar jogando " + jogador + "? Digite 's' para continuar ou digite qualquer coisa para sair.");
                continuar = sc3.nextLine();

            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

    static ActionListener VerificarVencedor = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            try {

                segundosDeEspera += 3;

                /*
                //Exemplo de Finalização de uma Partida em Execução ------------
                if (segundosDeEspera == 9) {
                    conexao.EncerrarPartida();
                }
                //Exemplo de Finalização de uma Partida em Execução ------------
                 */
                String[] vencedor = conexao.DeterminarVencedor();
                if (vencedor != null) {
                    if (vencedor.equals("Partida Finalizada")) {
                        System.out.println(vencedor);
                        timer.stop();
                    } else {
                        System.out.println("\n" + vencedor + "\n");
                        timer.stop();
                    }
                } else {
                    System.out.println("Aguardando o outro Jogador ...");
                }
            } catch (RemoteException e) {
                System.err.println("Erro: " + e);
            }
        }
    };
}
