import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SalvadorDePartida {

    private static final String ARQUIVO = "partida.dat";

    public static void salvar(Jogo jogo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(ARQUIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(jogo);
        }
    }
}