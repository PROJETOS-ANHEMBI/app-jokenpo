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

            // Pegando o registro 
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            // Cria a conexao com o Servidor
            conexao = (Conexao) registry.lookup("jokenpo");

            Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);
            String continuar = "s";
            Boolean primeiroJogo = true;
            Scanner sc3 = new Scanner(System.in);

            while (continuar.toLowerCase().equals(("s"))) {
                System.out.println("Digite: \n> '1' para JxCPU \n> '2' para Teste real com 02 Jogadores");
                int escolha = sc2.nextInt();

                if (escolha == 1) {
                    // JxCPU -------------------------------------------------------
                    System.err.println("\nExemplo de Jogo JxCPU");

                    

                    if (primeiroJogo) {
                        System.out.print("Cadastro do Jogador: ");
                        jogador = sc.next();

                        tipoJogador = conexao.CadastrarJogador(jogador);
                    }
                    if (tipoJogador != 0) {
                        System.out.print("Escolha: '1' para Pedra ou '2' para Papel ou '3' para Tesoura: ");
                        int jogada = sc2.nextInt();
                        conexao.Jogar(jogada, 1);
                    } else if (tipoJogador == 0 && primeiroJogo ) {
                        System.out.println("O Servidor está ocupado no momento!");
                        System.exit(0);
                    }
                    String vencedor = conexao.DeterminarVencedor();
                    System.out.println(vencedor);

                    // Timer que verifica o vencedor a cada 05 segundos
                    //timer = new Timer(3000, VerificarVencedor);
                    //timer.setRepeats(true);
                    //timer.start();
                    // Timer que verifica o vencedor a cada 05 segundos
                    // JxCPU -------------------------------------------------------
                } else if (escolha == 2) {
                    // Teste real com 02 Jogadores ---------------------------------
                    System.err.println("\nExemplo de Teste real com 02 Jogadores");
                    
                    if (primeiroJogo){

                    System.out.print("PorFavor informe seu nome: ");
                    jogador = sc.next();

                    tipoJogador = conexao.CadastrarJogadores(jogador);
                    }
                    if (tipoJogador != 0) {
                        System.out.print("Escolha: '1' para Pedra ou '2' para Papel ou '3' para Tesoura: ");
                        int jogada = sc2.nextInt();
                        conexao.Jogar(jogada, tipoJogador);
                    } else if (tipoJogador == 0 && primeiroJogo ) {
                        System.out.println("O Servidor está ocupado no momento!");
                        System.exit(0);

                    }
                    String vencedor = conexao.DeterminarVencedor();
                    System.out.println(vencedor);

                    // Timer que verifica o vencedor a cada 05 segundos
                    //timer = new Timer(3000, VerificarVencedor);
                    //timer.setRepeats(true);
                    //timer.start();
                    // Timer que verifica o vencedor a cada 05 segundos
                    // Teste real com 02 Jogadores -------------------------------------------------------
                }
                System.out.println("Placar: |" + vitorias + " Vitorias | " + derrotas + " Derrotas | " + empates + " Empates |");
                System.out.println("Deseja continuar?\n Digite 's' para continuar ou digite qualquer coisa para sair.");
                continuar = sc3.nextLine();
                if (continuar.toLowerCase().equals("s")) {
                    primeiroJogo = false;
                }

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
                String vencedor = conexao.DeterminarVencedor();
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
