package app;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.Timer;
import model.Conexao;

public class App {

    // Variaveis para controle das partidas
    static Conexao conexao;
    static Timer timer;
    static String continuar = "s";
    static boolean vencedorVerificado;
    static int segundosDeEspera;

    // Variaveis para controle do jogador   
    static String jogador;
    static int tipoJogador;
    static int vitorias, derrotas, empates;

    // Criando Scanners necessários
    static Scanner sc = new Scanner(System.in);
    static Scanner sc2 = new Scanner(System.in);
    static Scanner sc3 = new Scanner(System.in);

    public static void main(String args[]) throws RemoteException {

        try {

            // Inicia o servidor
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            conexao = (Conexao) registry.lookup("jokenpo");

            // Inicializando variavés com valores iniciais
            continuar = "s";
            vencedorVerificado = true;

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

                if (escolha == 1 && vencedorVerificado) {
                    // JOGADOR x CPU -------------------------------------------
                    System.out.println("\n--- JOGADOR x CPU");
                    vencedorVerificado = false;

                    if (tipoJogador != 0) {
                        System.out.print("Escolha: '1' para Pedra ou '2' para Papel ou '3' para Tesoura: ");
                        int jogada = sc2.nextInt();
                        conexao.Jogar(jogada, 1);
                    } else {
                        System.out.println("O Servidor está ocupado no momento!");
                        System.exit(0);
                    }

                    // Timer que verifica o vencedor a cada 03 segundos
                    timer = new Timer();
                    timer.schedule(new TaskVerificarVencedor(), 0, 3000);
                    // Timer que verifica o vencedor a cada 03 segundost

                } else if (escolha == 2 && vencedorVerificado) {
                    // JOGADOR x JOGADOR ---------------------------------------
                    System.out.println("\n--- JOGADOR x JOGADOR");
                    vencedorVerificado = false;

                    if (tipoJogador != 0) {
                        System.out.print("Escolha: '1' para Pedra ou '2' para Papel ou '3' para Tesoura: ");
                        int jogada = sc2.nextInt();
                        conexao.Jogar(jogada, tipoJogador);
                    } else {
                        System.out.println("O Servidor está ocupado no momento!");
                        System.exit(0);
                    }

                    // Timer que verifica o vencedor a cada 03 segundos
                    timer = new Timer();
                    timer.schedule(new TaskVerificarVencedor(), 0, 3000);
                    // Timer que verifica o vencedor a cada 03 segundost

                }

            }

            conexao.EncerrarPartida();

        } catch (Exception e) {
            conexao.EncerrarPartida();
            System.out.println("Erro: " + e.getMessage());
        }

    }

    // Metodo para verificar o vencedor em partidas JxJ ou JxCPU
    static void VerificarVencedor(String[] vencedor) {
        try {
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

            System.out.println("\n--- PLACAR: | " + vitorias + " Vitorias | " + derrotas + " Derrotas | " + empates + " Empates |");
            System.out.println("\nDeseja continuar jogando " + jogador + "? Digite 's' para continuar ou digite qualquer coisa para sair.");
            Scanner scanner = new Scanner(System.in);
            continuar = scanner.nextLine();
            vencedorVerificado = true;
            timer.cancel();

        } catch (Exception e) {
            System.err.println("Erro: " + e);
        }
    }

    // Classe utilizada para verificar o vencedor a cada 03 segundos com timer
    static class TaskVerificarVencedor extends TimerTask {

        public void run() {
            try {
                segundosDeEspera += 3;
                if (segundosDeEspera == 120) {
                    conexao.EncerrarPartida();
                    System.err.println("A partida foi finalizada devido demora de 02 minutos do outro Jogador!");
                    conexao.EncerrarPartida();
                    System.exit(0);
                }

                String[] vencedor = conexao.DeterminarVencedor();
                if (vencedor != null) {
                    VerificarVencedor(vencedor);
                } else {
                    System.out.println("Aguardando o outro jogador ...");
                }

            } catch (RemoteException e) {
                System.err.println("Erro: " + e);
            }
        }
    }

}
