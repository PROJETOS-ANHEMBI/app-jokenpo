package jokenpo;

import java.rmi.RemoteException;

public class Mensagem implements Conexao {

    public Mensagem() throws RemoteException {
        super();
    }

    @Override
    public boolean EscolherItem(int item) throws RemoteException {
        try {
            if (item == 1) {
                System.out.println("Pedra");
            } else if (item == 2) {
                System.out.println("Papel");
            } else if (item == 3) {
                System.out.println("Tesoura");
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String nomeJogador;
    private boolean finalizar;
    private int item;
    private int vitorias;

}
