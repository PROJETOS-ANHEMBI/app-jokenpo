package jokenpo.model;

import java.rmi.RemoteException;
import java.util.Random;

public class Partida implements Conexao {

    // Construtor
    public Partida() throws RemoteException {
        super();
    }

    private String[] jogadores = new String[2]; // Grava o nome dos Jogadores
    private int[] jogo = new int[2]; // Grava as informações do Jogo, para computar o vencedor
    private boolean CPU; // Flag que informa se a CPU jogará ou não
    private int vencedorDeterminado; // contador que informa se os 02 jogadores foram informados do resultado da partida

    @Override // Realiza o Cadastro dos Jogadores, para um jogo JxJ
    public int CadastrarJogadores(String nome) {
        // Salva o nome dos Jogadores, com base na ordem de cadastro (Jogador1 e Jogador2)
        CPU = false;

        if (jogadores[0] == null || jogadores[0] == "") {
            jogadores[0] = nome;
            return 1;
        } else if (jogadores[1] == null || jogadores[0] == "") {
            jogadores[1] = nome;
            return 2;
        } else {
            return 0;
        }

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
    public String DeterminarVencedor() {

        // Retorna o vencedor (Jogador 1 ou Jogador2), caso todas as jogadas tiverem sido realizadas
        // "jogo[0] != 0 && jogo[1] != 0" - Significa que os 02 Jogadores realizaram suas jogadas
        if (jogo[0] != 0 && jogo[1] != 0) {

            // Controle do jogo, para informar ao Jogador2, o resultado da partida
            int bckJogo[] = jogo;

            // Verifica se os 02 jogadores já visualizaram o resultado
            vencedorDeterminado++;
            if ((!CPU && vencedorDeterminado == 2) || (CPU && vencedorDeterminado == 1)) {
                // Reinicia o controle do jogo para a próxima partida
                jogo = new int[2];
                vencedorDeterminado = 0;
            }

            // Determina o Vencedor (Jogador1 ou Jogador2)
            int vencedor;

            // Possibilidades de Vitória: | 1-3 | 3-2 | 2-1 | Pedra-Papel-Tesoura
            if (bckJogo[0] == 1 && bckJogo[1] == 3 || bckJogo[0] == 3 && bckJogo[1] == 2 || bckJogo[0] == 2 && bckJogo[1] == 1) {
                vencedor = 1;
            } else if (bckJogo[1] == 1 && bckJogo[0] == 3 || bckJogo[1] == 3 && bckJogo[0] == 2 || bckJogo[1] == 2 && bckJogo[0] == 1) {
                vencedor = 2;
            } else {
                return ("Empate:\n"
                        + "| O Jogador 1 (" + jogadores[0] + ") escolheu " + BuscarJogada(bckJogo, 1)
                        + " | O Jogador 2 (" + jogadores[1] + ") escolheu " + BuscarJogada(bckJogo, 2) + " |");
            }

            return ("Vencedor: Jogador " + vencedor + " - " + jogadores[vencedor - 1] + "\n"
                    + "| " + jogadores[0] + " escolheu " + BuscarJogada(bckJogo, 1)
                    + " | " + jogadores[1] + " escolheu " + BuscarJogada(bckJogo, 2) + " |");

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

    public void SairDaPartida() {

    }
    
}
