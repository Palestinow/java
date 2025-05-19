
public class Fragata extends EmbarcacaoGeral {
    private static final long serialVersionUID = 1L;

    @Override
    public int getTamanho() {
        return 3;
    }

    @Override
    public char getSimbolo() {
        return 'f';
    }
}