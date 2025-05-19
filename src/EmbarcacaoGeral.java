import java.io.Serializable;

public abstract class EmbarcacaoGeral implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract int getTamanho();
    public abstract char getSimbolo();
}