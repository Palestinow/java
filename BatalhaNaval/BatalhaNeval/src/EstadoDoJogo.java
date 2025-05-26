import java.io.*;

public class EstadoDoJogo implements Serializable {
    private static final String ARQUIVO_SALVO = "estadodojogo.dat";
    private final Mapa mapa1;
    private final Mapa mapa2;
    private final Jogador jogador1;
    private final Jogador jogador2;
    private final boolean vezJogador1;

    public EstadoDoJogo(Mapa mapa1, Mapa mapa2, Jogador jogador1, Jogador jogador2, boolean vezJogador1) {
        this.mapa1 = mapa1;
        this.mapa2 = mapa2;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.vezJogador1 = vezJogador1;
    }

    public static void salvarEstado(EstadoDoJogo estado) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(ARQUIVO_SALVO))) {
            objectOutputStream.writeObject(estado);
        } catch (IOException e) {
            System.out.println("Erro ao salvar estado do jogo: " + e.getMessage());
        }
    }

    public static EstadoDoJogo carregarEstado() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(ARQUIVO_SALVO))) {
            return (EstadoDoJogo) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static boolean existeJogoSalvo() {
        File arquivo = new File(ARQUIVO_SALVO);
        return arquivo.exists();
    }

    public static void deletarJogoSalvo() {
        File arquivo = new File(ARQUIVO_SALVO);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    public Mapa getMapa1() { return mapa1; }
    public Mapa getMapa2() { return mapa2; }
    public Jogador getJogador1() { return jogador1; }
    public Jogador getJogador2() { return jogador2; }
    public boolean isVezJogador1() { return vezJogador1; }
}