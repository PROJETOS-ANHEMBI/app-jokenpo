package jokenpo.cliente;

import jokenpo.model.Conexao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Scanner;
import javax.swing.Timer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppJokenpo extends javax.swing.JFrame {

    static Conexao conexao;
    static Timer timer;

    public AppJokenpo() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppJokenpo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppJokenpo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppJokenpo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppJokenpo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppJokenpo().setVisible(true);

                try {

                    // Pegando o registro 
                    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                    // Cria a conexao com o Servidor
                    conexao = (Conexao) registry.lookup("jokenpo");
                    
                    Scanner sc = new Scanner(System.in);
                    Scanner sc2 = new Scanner(System.in);

                    System.out.println("Digite: \n> '1' para JxJ \n> '2' para JxCPU \n> '3' para Teste real com 02 Jogadores");
                    int escolha = sc2.nextInt();

                    if (escolha == 1) {
                        // JxJ ---------------------------------------------------------
                        System.err.println("\nExemplo de Jogo JxJ");

                        System.out.print("Cadastro do Jogador1: ");
                        String jogador1 = sc.next();
                        conexao.CadastrarJogadores(jogador1);

                        System.out.print("Cadastro do Jogador2: ");
                        String jogador2 = sc.next();
                        conexao.CadastrarJogadores(jogador2);

                        System.out.print("\nVez do Jogador1: ");
                        int jogada1 = sc2.nextInt();

                        conexao.Jogar(jogada1, 1);

                        System.out.print("Vez do Jogador2: ");
                        int jogada2 = sc2.nextInt();

                        conexao.Jogar(jogada2, 2);

                        System.out.println("\n" + conexao.DeterminarVencedorEReiniciarJogo(false) + "\n");
                        // JxJ ---------------------------------------------------------
                    } else if (escolha == 2) {
                        // JxCPU -------------------------------------------------------
                        System.err.println("\nExemplo de Jogo JxCPU");

                        System.out.print("Cadastro do Jogador: ");
                        String jogador = sc.next();
                        conexao.CadastrarJogador(jogador);

                        System.out.print("\nVez do Jogador: ");
                        int jogada = sc2.nextInt();

                        conexao.Jogar(jogada, 1);

                        System.out.println("\n" + conexao.DeterminarVencedorEReiniciarJogo(true) + "\n");
                        System.out.println(conexao.DeterminarVencedorEReiniciarJogo(false));
                        // JxCPU -------------------------------------------------------
                    } else if (escolha == 3) {
                        // Teste real com 02 Jogadores ---------------------------------
                        System.err.println("\nExemplo de Teste real com 02 Jogadores");

                        System.out.print("PorFavor informe seu nome: ");
                        String jogador = sc.next();
                        conexao.CadastrarJogadores(jogador);

                        System.out.print("\nEscolha: '1'-Pedra ou '2'-Papel ou '3'-Tesoura: ");
                        int jogada = sc2.nextInt();

                        conexao.Jogar(jogada, 1);

                        // Timer que verifica o vencedor a cada 05 segundos
                        timer = new Timer(1000, VerificarVencedor);
                        timer.setRepeats(true);
                        timer.start();
                        // Timer que verifica o vencedor a cada 05 segundos
                        // JxCPU -------------------------------------------------------
                    }

                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }

            }
        });
    }

    static ActionListener VerificarVencedor = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            try {
                if (conexao.DeterminarVencedorEReiniciarJogo(true) != null) {
                    System.out.println("\n" + conexao.DeterminarVencedorEReiniciarJogo(true) + "\n");
                    timer.stop();
                } else {
                    System.out.println("Aguardando o outro Jogador escolher um item ...");
                }
            } catch (RemoteException e) {
                System.err.println("Erro: " + e);
            }
        }
    };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
