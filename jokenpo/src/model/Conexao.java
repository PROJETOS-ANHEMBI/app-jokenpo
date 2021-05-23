package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Conexao extends Remote {

    // Realiza o Cadastro do Jogador1 e Jogador2 - para um jogo JxJ
    // Retorna um valor "inteiro", que corresponde a ordem de cadastro dos Jogadores
    // Quando retorar zero "0", significa que o servidor já está em uso por outros Jogadores
    public int CadastrarJogadores(String nome) throws RemoteException;

    // Realiza o Cadastro do Jogador que jogará contra a CPU
    // Retorna um valor "inteiro", que corresponde a ordem de cadastro dos Jogadores
    // Quando retorar zero "0", significa que o servidor já está em uso por outros Jogadores
    public int CadastrarJogador(String nome) throws RemoteException;

    // Salva o item (Pedra/Papel/Tesoura) que um Jogador escolher
    // void - sem retorno
    public void Jogar(int itemEscolhido, int tipoJogador) throws RemoteException;

    // Verifica o resultado do Jogo, e determina quem foi o Vencedor em jogos JxJ e/ou JxCPU
    // Retorna um valor "string", que contém as informações do Jogo e Vencedor
    public String DeterminarVencedor() throws RemoteException;

    // Finaliza uma partida, e remove os 02 Jogadores da sessão
    // void - sem retorno
    public void EncerrarPartida() throws RemoteException;

}
