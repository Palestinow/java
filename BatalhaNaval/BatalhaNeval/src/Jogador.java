import java.io.Serializable;

public class Jogador implements Serializable {
    private final String nome;
    private int pontuacao;

    public Jogador(String nome, int pontuacao) {
        this.nome = nome;
        this.pontuacao = pontuacao;
    }

    public void adicionarPontos() {
        pontuacao++;
    }

    public String getNome() {
        return nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }
}