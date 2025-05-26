import java.io.Serializable;

public abstract class NavioBase implements Serializable {
    protected int tamanho;
    protected char simbolo;
    protected int quantidade;

    public NavioBase(int tamanho, char simbolo, int quantidade) {
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