package jokenpo;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

public class Partida implements Conexao {

    // Construtor
    public Partida() throws RemoteException {
        super();
    }

    private String[] jogadores = new String[2]; // Grava o nome dos Jogadores
    private int[] jogo = new int[2]; // Grava as informações do Jogo, para computar o vencedor
    private boolean CPU; // Flag que informa se a CPU jogará ou não

    @Override // Realiza o Cadastro dos Jogadores, para um jogo JxJ
    public void CadastrarJogadores(String nome, int tipoJogador) {
        // Salva o nome dos Jogadores, com base na ordem de cadastro (Jogador1 e Jogador2)
        jogadores[tipoJogador - 1] = nome;
        CPU = false;
    }

    @Override // Realiza o Cadastro de um Jogador, para um jogo JxCPU
    public void CadastrarJogador(String nome) {
        // Salva o nome do Jogador, e salva a informação que a CPU também jogará
        jogadores[0] = nome;
        jogadores[1] = "CPU";
        CPU = true;
    }

    @Override // Método acessivel pela aplicação, para salvar a jogada dos Jogadores
    public void Jogar(int itemEscolhido, int tipoJogador) {

        // Este IF verifica se a CPU está jogando (CPU = "true" significa que é um jogo JxCPU)
        if (CPU) {
            // Salva a Jogada enviada pelo Jogador1, e realiza a jogada da CPU
            SalvarJogadaDoJogador(itemEscolhido, 1);
            SalvarJogadaDaCPU();
        } else {
            // Salva a jogada do Jogador1 e Jogador2, conforme parametros recebidos
            SalvarJogadaDoJogador(itemEscolhido, tipoJogador);
        }

    }

    // Salva as jogadas dos Jogadores, no controle "jogo"
    private void SalvarJogadaDoJogador(int itemEscolhido, int tipoJogador) {
        // Salva o item escolhido (itemEscolhido) pelo jogador (tipoJogador - Jogador1 ou Jogador2)
        jogo[tipoJogador - 1] = itemEscolhido;
    }

    // Salva as jogadas da CPU, no controle "jogo"
    private void SalvarJogadaDaCPU() {
        // Salva a jogada da CPU (feita de forma randomica)
        Random r = new Random();
        jogo[1] = r.nextInt(3) + 1;
    }

    @Override // Retorna a mensagem de Vitória ou Empate (o parametro = "true", finaliza o jogo)
    public String DeterminarVencedorEReiniciarJogo(boolean finalizarJogo) {

        // Retorna o vencedor (Jogador 1 ou Jogador2), caso todas as jogadas tiverem sido realizadas
        // "jogo[0] != 0 && jogo[1] != 0" - Significa que os 02 Jogadores realizaram suas jogadas
        if (jogo[0] != 0 && jogo[1] != 0) {

            // Utiliza um novo controle para os jogadores, caso o jogo seja encerrado
            String[] bckJogadores = new String[2];
            bckJogadores[0] = jogadores[0];
            bckJogadores[1] = jogadores[1];

            // Reinicia o controle do jogo para a próxima partida
            int[] bckJogo = new int[2];
            bckJogo[0] = jogo[0];
            bckJogo[1] = jogo[1];
            jogo = new int[2];

            // Se essa for a última partida
            if (finalizarJogo) {
                // Reinicia o controle dos jogadores
                jogadores = new String[2];
                // Reinicia o controle da CPU como Jogadora
                CPU = false;
            }

            // Determina o Vencedor (Jogador1 ou Jogador2)
            int vencedor;

            // Possibilidades de Vitória: | 1-3 | 3-2 | 2-1 | Pedra-Papel-Tesoura
            if (bckJogo[0] == 1 && bckJogo[1] == 3 || bckJogo[0] == 3 && bckJogo[1] == 2 || bckJogo[0] == 2 && bckJogo[1] == 1) {
                vencedor = 1;
            } else if (bckJogo[1] == 1 && bckJogo[0] == 3 || bckJogo[1] == 3 && bckJogo[0] == 2 || bckJogo[1] == 2 && bckJogo[0] == 1) {
                vencedor = 2;
            } else {

                String resultado = ("Empate:\n"
                        + "| O Jogador 1 (" + bckJogadores[0] + ") escolheu " + BuscarJogada(bckJogo, 1)
                        + " | O Jogador 2 (" + bckJogadores[1] + ") escolheu " + BuscarJogada(bckJogo, 2) + " |");

                if (finalizarJogo) {
                    System.out.println("\n> O Jogo entre " + bckJogadores[0] + " e " + bckJogadores[1] + " foi finalizado.\n\n");
                }

                return resultado;
            }

            String resultado = ("Vencedor: Jogador " + vencedor + " (" + bckJogadores[vencedor - 1] + ")\n"
                    + "| O Jogador 1 (" + bckJogadores[0] + ") escolheu " + BuscarJogada(bckJogo, 1)
                    + " | O Jogador 2 (" + bckJogadores[1] + ") escolheu " + BuscarJogada(bckJogo, 2) + " |");

            System.out.println(resultado);

            if (finalizarJogo) {
                System.out.println("\n> O Jogo entre " + bckJogadores[0] + " e " + bckJogadores[1] + " foi finalizado.\n\n");
            }

            return resultado;

        } else {
            // Retorna um valor nulo, caso algum jogador ainda precise realizar uma jogada
            return null;
        }

    }

    // Informa qual item cada jogador escolheu (Pedra, Papel ou Tesoura)
    private String BuscarJogada(int[] bckJogo, int tipoJogador) {
        // | 1 = PEDRA | 2 = PAPEL | 3 = TESOURA |
        switch (bckJogo[tipoJogador - 1]) {
            case 1:
                return "Pedra";
            case 2:
                return "Papel";
            default:
                return "Tesoura";
        }
    }

}
