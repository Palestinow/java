import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
    private Socket socket;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private Mapa mapaCliente;
    private Jogador jogadorCliente;
    private static final String HOST = "192.168.15.117";
    private static final int PORTA = 12345;

    public void conectar() {
        try {
            socket = new Socket(HOST, PORTA);
            System.out.println("Conectado ao servidor!");

            saida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            configurarJogo();

            executarJogo();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        } finally {
            encerrarConexao();
        }
    }

    private void configurarJogo() throws IOException, ClassNotFoundException {
        mapaCliente = new Mapa();
        List<Embarcacao> embarcacoes = EmbarcacaoFabrica.criarTodasEmbarcacoes();
        Random random = new Random();

        for (Embarcacao embarcacao : embarcacoes) {
            boolean posicionado = false;
            while (!posicionado) {
                int linha = random.nextInt(Mapa.getTamanho());
                int coluna = random.nextInt(Mapa.getTamanho());
                int direcao = random.nextInt(8);
                posicionado = mapaCliente.posicionarEmbarcacao(embarcacao, linha, coluna, direcao);
            }
        }

        String nomeServidor = (String) entrada.readObject();
        System.out.println("Jogando contra: " + nomeServidor);

        System.out.println("Digite seu nome: ");
        Scanner scanner = new Scanner(System.in);
        jogadorCliente = new Jogador(scanner.nextLine(), 0);
        saida.writeObject(jogadorCliente.getNome());
    }

    private void executarJogo() throws IOException, ClassNotFoundException {
        boolean jogoAtivo = true;
        boolean minhaVez = false;
        Mapa mapaOponente = new Mapa();
        mapaCliente.mostrarMinhaFrota();
        mapaOponente.mostrarFrotaInimigo();

        while (jogoAtivo) {
            if (minhaVez) {
                System.out.println("\n=== ESTADO ATUAL DO JOGO ===");
                mapaCliente.mostrarMinhaFrota();
                mapaOponente.mostrarFrotaInimigo();

                System.out.println("Sua vez! Digite linha (0-19) e coluna (A-T):");
                Scanner scanner = new Scanner(System.in);
                int linha = scanner.nextInt();
                String colunaStr = scanner.next().toUpperCase();
                int coluna = colunaStr.charAt(0) - 'A';

                saida.writeObject(new int[]{linha, coluna});

                Boolean acerto = (Boolean) entrada.readObject();
                if (acerto) {
                    System.out.println("Acertou uma embarcacao!");
                    jogadorCliente.adicionarPontos();

                    List<int[]> posicoesEmbarcacao = (List<int[]>) entrada.readObject();

                    for (int[] posicoes : posicoesEmbarcacao) {
                        mapaOponente.registrarTiro(posicoes[0], posicoes[1], true);
                    }

                    if (jogadorCliente.getPontuacao() >= 5) {
                        System.out.println("Voce venceu!");
                        jogoAtivo = false;
                    }
                } else {
                    System.out.println("Agua!");
                    mapaOponente.registrarTiro(linha, coluna, false);
                }

                System.out.println("\n=== RESULTADO DA SUA JOGADA ===");
                mapaCliente.mostrarMinhaFrota();
                mapaOponente.mostrarFrotaInimigo();
            } else {
                System.out.println("Aguardando jogada do oponente...");
                int[] jogada = (int[]) entrada.readObject();

                boolean acerto = mapaCliente.atirar(jogada[0], jogada[1]);
                saida.writeObject(acerto);

                if (acerto) {
                    System.out.println("Oponente acertou uma embarcacao!");

                    List<int[]> posicoesEmbarcacao = new ArrayList<>();

                    for (int i = 0; i < Mapa.getTamanho(); i++) {
                        for (int j = 0; j < Mapa.getTamanho(); j++) {
                            if (mapaCliente.getMapaControle()[i][j] == 'Y') {
                                posicoesEmbarcacao.add(new int[]{i, j});
                            }
                        }
                    }

                    saida.writeObject(posicoesEmbarcacao);
                } else {
                    System.out.println("Oponente errou o tiro!");
                }

                System.out.println("\n=== RESULTADO DA JOGADA DO OPONENTE ===");
                mapaCliente.mostrarMinhaFrota();
                mapaOponente.mostrarFrotaInimigo();
            }

            minhaVez = !minhaVez;
        }
    }

    private void encerrarConexao() {
        try {
            if (saida != null) saida.close();
            if (entrada != null) entrada.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao encerrar conexões: " + e.getMessage());
        }
    }
}