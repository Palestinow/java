import java.io.Serializable;

public class Mapa implements Serializable {
    private static final int TAMANHO = 20;
    private static final char OCULTO = '.';
    private static final char VAZIO = '.';
    private static final char AGUA = 'O';
    private static final char ACERTO = 'X';
    private static final char ACERTO_PROPRIO = 'Y';
    private static final char ERRO = 'M';

    private final char[][] mapaJogador;
    private final char[][] mapaControle;
    private final boolean[][] mapaHits;

    public Mapa() {
        mapaJogador = new char[TAMANHO][TAMANHO];
        mapaControle = new char[TAMANHO][TAMANHO];
        mapaHits = new boolean[TAMANHO][TAMANHO];
        inicializarMapas();
    }

    public static int getTamanho() {
        return TAMANHO;
    }

    public char[][] getMapaControle() {
        return mapaControle;
    }

    private void inicializarMapas() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                mapaJogador[i][j] = OCULTO;
                mapaControle[i][j] = VAZIO;
                mapaHits[i][j] = false;
            }
        }
    }

    public boolean posicionarEmbarcacao(Embarcacao embarcacao, int linha, int coluna, int direcao) {
        int tamanho = embarcacao.getTamanho();
        int[] dLinha = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dColuna = {1, 1, 0, -1, -1, -1, 0, 1};

        if (!posicaoValida(linha, coluna, tamanho, direcao, dLinha, dColuna)) {
            return false;
        }

        if (temAdjacencias(linha, coluna, tamanho, direcao, dLinha, dColuna)) {
            return false;
        }

        for (int i = 0; i < tamanho; i++) {
            int novaLinha = linha + (dLinha[direcao] * i);
            int novaColuna = coluna + (dColuna[direcao] * i);
            mapaControle[novaLinha][novaColuna] = embarcacao.getSimbolo();
        }
        return true;
    }

    private boolean posicaoValida(int linha, int coluna, int tamanho, int direcao, int[] dLinha, int[] dColuna) {
        for (int i = 0; i < tamanho; i++) {
            int novaLinha = linha + (dLinha[direcao] * i);
            int novaColuna = coluna + (dColuna[direcao] * i);

            if (novaLinha < 0 || novaLinha >= TAMANHO ||
                novaColuna < 0 || novaColuna >= TAMANHO ||
                mapaControle[novaLinha][novaColuna] != VAZIO) {
                return false;
            }
        }
        return true;
    }

    private boolean temAdjacencias(int linha, int coluna, int tamanho, int direcao, int[] dLinha, int[] dColuna) {
        for (int i = 0; i < tamanho; i++) {
            int novaLinha = linha + (dLinha[direcao] * i);
            int novaColuna = coluna + (dColuna[direcao] * i);

            for (int d = 0; d < 8; d++) {
                int adjLinha = novaLinha + dLinha[d];
                int adjColuna = novaColuna + dColuna[d];

                if (adjLinha >= 0 && adjLinha < TAMANHO &&
                    adjColuna >= 0 && adjColuna < TAMANHO &&
                    mapaControle[adjLinha][adjColuna] != VAZIO) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean atirar(int linha, int coluna) {
        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            return false;
        }

        if (mapaControle[linha][coluna] != VAZIO && mapaControle[linha][coluna] != AGUA) {
            mapaHits[linha][coluna] = true;
            mapaJogador[linha][coluna] = ACERTO;
            char simboloEmbarcacao = mapaControle[linha][coluna];
            mapaControle[linha][coluna] = ACERTO_PROPRIO;

            revelarEmbarcacaoAtingida(linha, coluna, simboloEmbarcacao);
            return true;
        } else {
            mapaJogador[linha][coluna] = ERRO;
            mapaControle[linha][coluna] = AGUA;
            return false;
        }
    }

    public void registrarTiro(int linha, int coluna, boolean acerto) {
        if (acerto) {
            mapaJogador[linha][coluna] = ACERTO;
            int[] dLinha = {0, 1, 1, 1, 0, -1, -1, -1};
            int[] dColuna = {1, 1, 0, -1, -1, -1, 0, 1};

            for (int dir = 0; dir < 8; dir++) {
                int novaLinha = linha;
                int novaColuna = coluna;

                while (true) {
                    novaLinha += dLinha[dir];
                    novaColuna += dColuna[dir];

                    if (novaLinha < 0 || novaLinha >= TAMANHO ||
                        novaColuna < 0 || novaColuna >= TAMANHO) {
                        break;
                    }

                    if (mapaJogador[novaLinha][novaColuna] == ERRO ||
                        mapaJogador[novaLinha][novaColuna] == OCULTO) {
                        break;
                    }

                    if (mapaJogador[novaLinha][novaColuna] == ACERTO) {
                        continue;
                    }

                    mapaJogador[novaLinha][novaColuna] = ACERTO;
                }

                novaLinha = linha;
                novaColuna = coluna;
                while (true) {
                    novaLinha -= dLinha[dir];
                    novaColuna -= dColuna[dir];

                    if (novaLinha < 0 || novaLinha >= TAMANHO ||
                        novaColuna < 0 || novaColuna >= TAMANHO) {
                        break;
                    }

                    if (mapaJogador[novaLinha][novaColuna] == ERRO ||
                        mapaJogador[novaLinha][novaColuna] == OCULTO) {
                        break;
                    }

                    if (mapaJogador[novaLinha][novaColuna] == ACERTO) {
                        continue;
                    }

                    mapaJogador[novaLinha][novaColuna] = ACERTO;
                }
            }
        } else {
            mapaJogador[linha][coluna] = ERRO;
        }
    }

    private void revelarEmbarcacaoAtingida(int linhaInicial, int colunaInicial, char simboloEmbarcacao) {
        int[] dLinha = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dColuna = {1, 1, 0, -1, -1, -1, 0, 1};

        mapaJogador[linhaInicial][colunaInicial] = ACERTO;
        mapaControle[linhaInicial][colunaInicial] = ACERTO_PROPRIO;
        mapaHits[linhaInicial][colunaInicial] = true;

        for (int dir = 0; dir < 8; dir++) {
            int novaLinha = linhaInicial;
            int novaColuna = colunaInicial;

            while (true) {
                novaLinha += dLinha[dir];
                novaColuna += dColuna[dir];

                if (novaLinha < 0 || novaLinha >= TAMANHO ||
                    novaColuna < 0 || novaColuna >= TAMANHO ||
                    mapaControle[novaLinha][novaColuna] != simboloEmbarcacao) {
                    break;
                }

                mapaJogador[novaLinha][novaColuna] = ACERTO;
                mapaControle[novaLinha][novaColuna] = ACERTO_PROPRIO;
                mapaHits[novaLinha][novaColuna] = true;
            }

            novaLinha = linhaInicial;
            novaColuna = colunaInicial;
            while (true) {
                novaLinha -= dLinha[dir];
                novaColuna -= dColuna[dir];

                if (novaLinha < 0 || novaLinha >= TAMANHO ||
                    novaColuna < 0 || novaColuna >= TAMANHO ||
                    mapaControle[novaLinha][novaColuna] != simboloEmbarcacao) {
                    break;
                }

                mapaJogador[novaLinha][novaColuna] = ACERTO;
                mapaControle[novaLinha][novaColuna] = ACERTO_PROPRIO;
                mapaHits[novaLinha][novaColuna] = true;
            }
        }
    }

    public void mostrarFrotaInimigo() {
        System.out.println("\n=== ATAQUE AO INIMIGO ===");
        System.out.print("   ");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%2c ", (char)('A' + i));
        }
        System.out.println();

        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < TAMANHO; j++) {
                System.out.printf("%2c ", mapaJogador[i][j]);
            }
            System.out.println();
        }
    }

    public void mostrarMinhaFrota() {
        System.out.println("\n=== SUA FROTA ===");
        System.out.print("   ");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%2c ", (char)('A' + i));
        }
        System.out.println();

        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < TAMANHO; j++) {
                char simbolo = mapaControle[i][j];
                if (mapaHits[i][j]) {
                    simbolo = ACERTO_PROPRIO;
                }
                System.out.printf("%2c ", simbolo);
            }
            System.out.println();
        }
    }
}