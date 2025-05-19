import java.io.File;

public class GerenciadorDeArquivos {

    public static boolean arquivoExiste(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        return arquivo.exists();
    }

    public static boolean deletarArquivo(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            return arquivo.delete();
        }
        return false;
    }
}