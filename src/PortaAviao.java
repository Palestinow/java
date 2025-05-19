public class PortaAviao extends EmbarcacaoGeral {

    private static final long serialVersionUID = 1L;

    @Override
    public int getTamanho() {
        return 8;
    }

    @Override
    public char getSimbolo() {
        return 'a';
    }
}