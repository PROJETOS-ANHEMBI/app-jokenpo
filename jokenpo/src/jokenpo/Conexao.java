package jokenpo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Conexao extends Remote {

    public boolean EscolherItem(int item) throws RemoteException;

}
