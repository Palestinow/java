
public class Bote extends EmbarcacaoGeral {
    private static final long serialVersionUID = 1L;

    @Override
    public int getTamanho() {
        return 2;
    }

    @Override
    public char getSimbolo() {
        return 'b';
    }
}