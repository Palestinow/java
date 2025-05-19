import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistroDeRanking {

    private static final String ARQUIVO = "ranking.txt";

    public static void salvarPontuacao(String nome, int pontuacao) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            // arquivo pode não existir ainda
        }
        boolean achou = false;
        for (int i = 0; i < linhas.size(); i++) {
            String[] partes = linhas.get(i).split(";");
            if (partes[0].equals(nome)) {
                int pont = Integer.parseInt(partes[1]);
                if (pontuacao > pont) {
                    linhas.set(i, nome + ";" + pontuacao);
                }
                achou = true;
                break;
            }
        }
        if (!achou) {
            linhas.add(nome + ";" + pontuacao);
        }
        linhas.sort((a, b) -> {
            int pa = Integer.parseInt(a.split(";")[1]);
            int pb = Integer.parseInt(b.split(";")[1]);
            return Integer.compare(pb, pa);
        });
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int posicaoNoRanking(String nome) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            int pos = 1;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes[0].equals(nome)) {
                    return pos;
                }
                pos++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void mostrarRanking() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            int pos = 1;
            System.out.println("Ranking:");
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                System.out.println(pos + ". " + partes[0] + " - " + partes[1]);
                pos++;
            }
        } catch (IOException e) {
            System.out.println("Nenhum ranking disponível.");
        }
    }
}