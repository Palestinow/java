import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private ServerSocket serverSocket;

    public Servidor(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
    }

    public void iniciar() throws IOException {
        System.out.println("Servidor iniciado e aguardando conexão...");
        try (Socket clienteSocket = serverSocket.accept();
             BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
             PrintWriter saida = new PrintWriter(clienteSocket.getOutputStream(), true)) {

            System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());

            String mensagem;
            while ((mensagem = entrada.readLine()) != null) {
                System.out.println("Mensagem recebida: " + mensagem);
                saida.println("Eco: " + mensagem);
            }
        }
    }

    public void parar() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }
}