import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;

    public Cliente(String endereco, int porta) throws IOException {
        socket = new Socket(endereco, porta);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        saida = new PrintWriter(socket.getOutputStream(), true);
    }

    public void enviarMensagem(String mensagem) {
        saida.println(mensagem);
    }

    public String receberMensagem() throws IOException {
        return entrada.readLine();
    }

    public void fechar() throws IOException {
        entrada.close();
        saida.close();
        socket.close();
    }
}