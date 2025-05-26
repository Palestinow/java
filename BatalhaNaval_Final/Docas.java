import java.util.ArrayList;
import java.util.List;

public class Docas {
    public static List<NavioBase> criarTodasEmbarcacoes() {
        List<NavioBase> embarcacoes = new ArrayList<>();

        for (int i = 0; i < 2; i++) embarcacoes.add(new PortaAvioes());
        for (int i = 0; i < 3; i++) embarcacoes.add(new Destroyer());
        for (int i = 0; i < 4; i++) embarcacoes.add(new Submarino());
        for (int i = 0; i < 5; i++) embarcacoes.add(new Fragata());
        for (int i = 0; i < 6; i++) embarcacoes.add(new Bote());

        return embarcacoes;
    }
}