package jokenpo;

import java.rmi.Naming;
import jokenpo.Conexao;

// Classe para realização de testes de acesso ao servidor - arquivo temporario
public class ClienteTester {

    public static void main(String[] args) {

        try {
            
            // Cria a conexao com o Servidor
            Conexao conexao = (Conexao) Naming.lookup("rmi://LOCALHOST:1099/jokenpo");

            // Utiliza uma das funções do Servidor para teste
            if(conexao.EscolherItem(4)){
                System.out.println("Sucesso :)");
            } else {
                System.out.println("Falha :/");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
