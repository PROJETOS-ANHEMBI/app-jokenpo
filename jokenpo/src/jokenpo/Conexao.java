package jokenpo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Conexao extends Remote {

    public boolean CadastrarJogadores(String nome) throws RemoteException;
    
    public void CadastrarJogador(String nome) throws RemoteException;
    
    public void Jogar(int itemEscolhido, int tipoJogador) throws RemoteException;

    public String DeterminarVencedorEReiniciarJogo(boolean finalizarJogo) throws RemoteException;
    
    
}
