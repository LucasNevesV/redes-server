package Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private Socket client;
    private int jogada;
    private int jogadaCliente;
    private ObjectOutputStream outObject;
    private ObjectInputStream inObject;

    // [Inicia conexao com cliente]
    public void iniciandoServidor(RoundResult roundResult) throws IOException, ClassNotFoundException {
            new Thread() {

                @Override
                public void run() {
                    int i = 0;

                    while(true) {
                        try {
                            if(i == 0){
                                // [aqui eh criada a conexao com o cliente]
                                ServerSocket slisten = new ServerSocket(16868);
                                System.out.println("Aguardando Conexao...");
                                client = slisten.accept();
                                outObject = new ObjectOutputStream(client.getOutputStream());
                                inObject = new ObjectInputStream(client.getInputStream());
                                Main.setStatus(Status.GAME);
                                i++;
                            }

                            // [Se houver algum ganhador encerra thread e conexao]
                            if(roundResult.getPoints() == 3 || roundResult.getClientPoints() == 3){
                                try {
                                    client.close();
                                    this.interrupt();
                                    break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                              System.out.println("lendo cliente...");
                              jogadaCliente = inObject.read();
                            System.out.println("jogada cliente: " + jogadaCliente);
                              // [caso ja existam as duas jogadas feitas]
                            if(jogadaCliente > 0 && jogada > 0){
                                Main.setStatus(Status.ROUNDRESULT);
                                roundResult.setButtonsOn(false);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

//                    try {
//                        client.close();
//                        System.out.println("Ja deu boy");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }
            }.start();

    }

    // [Getters e setters]
    public int getJogadaCliente() {
        return jogadaCliente;
    }

    public void setJogadaCliente(int jogadaCliente) {
        this.jogadaCliente = jogadaCliente;
    }

    public int getJogada() {
        return jogada;
    }

    public void setJogada(int jogada) {
        this.jogada = jogada;
    }

    public ObjectOutputStream getOutObject() {
        return outObject;
    }
}
