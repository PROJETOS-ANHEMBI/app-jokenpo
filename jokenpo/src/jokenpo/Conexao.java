package jokenpo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Conexao extends Remote {

    public void CadastrarJogadores(String nome, int tipoJogador) throws RemoteException;
    
    public void CadastrarJogador(String nome) throws RemoteException;
    
    public void Jogar(int itemEscolhido, int tipoJogador) throws RemoteException;

    public String DeterminarVencedorEReiniciarJogo() throws RemoteException;
    
    
}
