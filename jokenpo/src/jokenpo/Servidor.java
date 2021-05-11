/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokenpo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    static ServerSocket serversocket;
    static Socket client_socket;
    static String msg;

    public static void main(String[] args) {

        try {
            serversocket = new ServerSocket(9600);
            System.out.println("--- Servidor Aberto ---");
        } catch (Exception e) {
            System.err.println("--- Erro ao Abrir o Servidor ---");
        }

        String texto = "";
        String resposta = "Resposta do Servidor:";
        new Servidor();

        while (0 < 1) {
            if (connect()) {

                texto = receive(client_socket);
                System.out.println(texto);      // fase de dados
                send(client_socket, resposta);

                try {
                    //client_socket.close();

                } catch (Exception e) {
                    System.err.println("--- Erro na Conexão: " + e.getMessage() + " ---");
                }
            }
        }
    }

    public static void send(Socket socket, String txt) {
        OutputStream out;
        try {
            out = socket.getOutputStream();
            out.write(txt.getBytes());
        } catch (Exception e) {
            System.err.println("Excecao no OutputStream");
        }
    }

    static boolean connect() {
        boolean ret;
        try {
            client_socket = serversocket.accept();  // fase de conexao
            ret = true;
        } catch (Exception e) {
            System.err.println("Nao fez conexao" + e.getMessage());
            ret = false;
        }
        return ret;
    }

    public static String receive(Socket socket) {
        InputStream in;
        int bt;
        byte btxt[];
        String txt = "";
        btxt = new byte[79];
        try {
            in = socket.getInputStream();
            bt = in.read(btxt);
            if (bt > 0) {
                txt = new String(btxt);

            }
        } catch (IOException e) {
            System.err.println("Excecao no InputStream: " + e);
        }
        return txt;
    }

}
