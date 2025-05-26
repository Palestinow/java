import java.io.*;
import java.util.*;

public class Ranking {
    private static final String ARQUIVO_RANKING = "ranking.txt";
    private final List<Jogador> jogadores;

    public Ranking() {
        jogadores = new ArrayList<>();
        carregarRanking();
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
        jogadores.sort((j1, j2) -> Integer.compare(j2.getPontuacao(), j1.getPontuacao()));
        salvarRanking();
    }

    public int getPosicaoJogador(String nomeJogador) {
        for (int i = 0; i < jogadores.size(); i++) {
            if (jogadores.get(i).getNome().equals(nomeJogador)) {
                return i + 1;
            }
        }
        return -1;
    }

    private void carregarRanking() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_RANKING))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                jogadores.add(new Jogador(dados[0], Integer.parseInt(dados[1])));
            }
        } catch (IOException e) {
            System.out.println("Arquivo de ranking nao encontrado. Sera criado um novo.");
        }
    }

    private void salvarRanking() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_RANKING))) {
            for (Jogador jogador : jogadores) {
                writer.write(jogador.getNome() + "," + jogador.getPontuacao());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar ranking: " + e.getMessage());
        }
    }

    public static void atualizarRanking(String nome, int novaPontuacao) {
        List<Jogador> jogadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_RANKING))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String nomeJogador = dados[0];
                int pontuacao = Integer.parseInt(dados[1]);
                if (!nomeJogador.equals(nome)) {
                    jogadores.add(new Jogador(nomeJogador, pontuacao));
                }
            }
        } catch (IOException e) {
            // Ignora, cria novo se necessário
        }

        jogadores.add(new Jogador(nome, novaPontuacao));
        jogadores.sort((j1, j2) -> Integer.compare(j2.getPontuacao(), j1.getPontuacao()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_RANKING))) {
            for (Jogador jogador : jogadores) {
                writer.write(jogador.getNome() + "," + jogador.getPontuacao());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar ranking.");
        }
    }
    
}