package servidor;

import model.Partida;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {

    public static void main(String[] args) {
        Registry registro = null;
        try {
            Partida obj = new Partida();
            Remote remote = UnicastRemoteObject.exportObject(obj, 0);
            registro = LocateRegistry.createRegistry(1099);
            registro.rebind("jokenpo", remote);
            // Informa que o Servidor foi iniciado
            System.err.println("--- Jokenpô Iniciado");
        } catch (Exception e) {
            System.err.println("--- Erro na Inicialização do Jokenpô: " + e.getMessage());
        }

    }
}
