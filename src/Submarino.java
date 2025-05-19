
public class Submarino extends EmbarcacaoGeral {
    private static final long serialVersionUID = 1L;

    @Override
    public int getTamanho() {
        return 4;
    }

    @Override
    public char getSimbolo() {
        return 's';
    }
}