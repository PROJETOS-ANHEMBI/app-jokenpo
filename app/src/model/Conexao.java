package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Conexao extends Remote {

    public int CadastrarJogadores(String nome) throws RemoteException;

    public int CadastrarJogador(String nome) throws RemoteException;

    public void Jogar(int itemEscolhido, int tipoJogador) throws RemoteException;

    public String[] DeterminarVencedor() throws RemoteException;
    
    public void EncerrarPartida() throws RemoteException;

}
