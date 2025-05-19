import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

public class Jogo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Mapa mapa;
    private Jogador jogador1;
    private Jogador jogador2;
    private transient Scanner scanner;

    public Jogo(String nomeJogador1, String nomeJogador2) {
        this.mapa = new Mapa();
        this.jogador1 = new Jogador(nomeJogador1);
        this.jogador2 = new Jogador(nomeJogador2);
        this.scanner = new Scanner(System.in);
        posicionarEmbarcacoes();
    }

    private void posicionarEmbarcacoes() {
        posicionarPorSimbolo('a', 2); // Porta Aviões - 8 posições
        posicionarPorSimbolo('d', 3); // Destroyers - 5 posições
        posicionarPorSimbolo('s', 4); // Submarinos - 4 posições
        posicionarPorSimbolo('f', 5); // Fragatas - 3 posições
        posicionarPorSimbolo('b', 6); // Botes - 2 posições
    }

    private void posicionarPorSimbolo(char simbolo, int quantidade) {
        Random rand = new Random();
        int tentativas, linha, coluna;
        char direcao;
        for (int i = 0; i < quantidade; i++) {
            tentativas = 0;
            boolean posicionado = false;
            while (!posicionado && tentativas < 1000) {
                linha = rand.nextInt(Mapa.TAMANHO);
                coluna = rand.nextInt(Mapa.TAMANHO);
                direcao = "HVD".charAt(rand.nextInt(3)); // H, V, D
                EmbarcacaoGeral nova = criarNovaEmbarcacao(simbolo);
                if (nova == null) break;
                posicionado = mapa.posicionarEmbarcacao(nova, linha, coluna, direcao);
                tentativas++;
            }
            if (!posicionado) {
                System.out.println("Erro ao posicionar embarcação: " + simbolo);
            }
        }
    }

    private EmbarcacaoGeral criarNovaEmbarcacao(char simbolo) {
        return switch (simbolo) {
            case 'a' -> new PortaAviao();
            case 'd' -> new Destroyer();
            case 's' -> new Submarino();
            case 'f' -> new Fragata();
            case 'b' -> new Bote();
            default -> null;
        };
    }

    public void iniciar() {
        if (scanner == null) scanner = new Scanner(System.in);
        Jogador jogadorAtual = jogador1;
        Jogador jogadorOponente = jogador2;

        while (true) {
            System.out.println("\nMapa visível para " + jogadorAtual.getNome() + ":");
            mapa.mostrarMapaVisivel();

            System.out.print(jogadorAtual.getNome() + ", digite linha (0 a 19) e coluna (0 a 19) para atacar (ex: 5 10): ");
            int linha = scanner.nextInt();
            int coluna = scanner.nextInt();

            if (linha < 0 || linha >= Mapa.TAMANHO || coluna < 0 || coluna >= Mapa.TAMANHO) {
                System.out.println("Coordenadas inválidas. Tente novamente.");
                continue;
            }

            boolean acertou = mapa.atacar(linha, coluna);
            if (acertou) {
                System.out.println("Acertou uma embarcação!");
                jogadorAtual.addPontuacao(10);
            } else {
                System.out.println("Errou.");
            }

            mapa.atualizarEstadoAposAtaque(linha, coluna);

            try {
                SalvadorDePartida.salvar(this);
            } catch (Exception e) {
                System.out.println("Erro ao salvar partida: " + e.getMessage());
            }

            if (jogadorAtual.getPontuacao() >= 50) {
                System.out.println("Fim de jogo! " + jogadorAtual.getNome() + " venceu!");
                System.out.println("Pontuação final: " + jogadorAtual.getPontuacao());

                RegistroDeRanking.salvarPontuacao(jogadorAtual.getNome(), jogadorAtual.getPontuacao());
                int posicao = RegistroDeRanking.posicaoNoRanking(jogadorAtual.getNome());
                System.out.println("Posição no ranking: " + posicao);
                System.out.println("\nRanking completo:");
                RegistroDeRanking.mostrarRanking();

                break;
            }

            Jogador temp = jogadorAtual;
            jogadorAtual = jogadorOponente;
            jogadorOponente = temp;
        }

        scanner.close();
    }
}