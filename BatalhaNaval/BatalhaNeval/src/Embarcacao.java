import java.io.Serializable;

public abstract class Embarcacao implements Serializable {
    protected int tamanho;
    protected char simbolo;
    protected int quantidade;

    public Embarcacao(int tamanho, char simbolo, int quantidade) {
        this.tamanho = tamanho;
        this.simbolo = simbolo;
        this.quantidade = quantidade;
    }

    public int getTamanho() {
        return tamanho;
    }

    public char getSimbolo() {
        return simbolo;
    }
}