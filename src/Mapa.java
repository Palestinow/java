import java.io.Serializable;
import java.util.Arrays;

public class Mapa implements Serializable {

    public static final int TAMANHO = 20;
    private static final char VAZIO = 'V';
    private static final char OCULTO = 'O';
    private static final char ACERTO = 'X';
    private static final char ERRO = '-';

    private char[][] mapaVisivel;
    private char[][] mapaInterno;

    public Mapa() {
        mapaVisivel = new char[TAMANHO][TAMANHO];
        mapaInterno = new char[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            Arrays.fill(mapaVisivel[i], OCULTO);
            Arrays.fill(mapaInterno[i], VAZIO);
        }
    }

    public boolean posicionarEmbarcacao(EmbarcacaoGeral emb, int linha, int coluna, char direcao) {
        int tamanho = emb.getTamanho();
        // Validar posição e espaços livres, considerando adjacências

        int dLinha = 0, dColuna = 0;
        switch (direcao) {
            case 'H': dColuna = 1; break;
            case 'V': dLinha = 1; break;
            case 'D': dLinha = 1; dColuna = 1; break;
            default: return false;
        }

        // Verificar limites
        int fimLinha = linha + dLinha * (tamanho - 1);
        int fimColuna = coluna + dColuna * (tamanho - 1);

        if (fimLinha >= TAMANHO || fimColuna >= TAMANHO) return false;

        // Verificar espaço vazio e adjacência
        for (int i = 0; i < tamanho; i++) {
            int l = linha + i * dLinha;
            int c = coluna + i * dColuna;
            if (mapaInterno[l][c] != VAZIO) return false;
            // Verifica se não tem embarcação adjacente
            if (temEmbarcacaoAdjacente(l, c)) return false;
        }

        // Colocar embarcação
        for (int i = 0; i < tamanho; i++) {
            int l = linha + i * dLinha;
            int c = coluna + i * dColuna;
            mapaInterno[l][c] = emb.getSimbolo();
        }

        return true;
    }

    private boolean temEmbarcacaoAdjacente(int linha, int coluna) {
        for (int i = Math.max(0, linha - 1); i <= Math.min(TAMANHO - 1, linha + 1); i++) {
            for (int j = Math.max(0, coluna - 1); j <= Math.min(TAMANHO - 1, coluna + 1); j++) {
                if (mapaInterno[i][j] != VAZIO) return true;
            }
        }
        return false;
    }

    public boolean atacar(int linha, int coluna) {
        if (mapaInterno[linha][coluna] != VAZIO && mapaVisivel[linha][coluna] == OCULTO) {
            mapaVisivel[linha][coluna] = ACERTO;
            return true;
        } else if (mapaVisivel[linha][coluna] == OCULTO) {
            mapaVisivel[linha][coluna] = ERRO;
        }
        return false;
    }

    public void atualizarEstadoAposAtaque(int linha, int coluna) {
        // Marca 'Y' no mapaInterno para embarcação atingida
        if (mapaVisivel[linha][coluna] == ACERTO) {
            mapaInterno[linha][coluna] = 'Y';
        }
    }

    public void mostrarMapaVisivel() {
        System.out.print("   ");
        for (int c = 0; c < TAMANHO; c++) {
            System.out.printf("%2d ", c);
        }
        System.out.println();
        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(" " + mapaVisivel[i][j] + " ");
            }
            System.out.println();
        }
    }
}