import java.io.*;
import java.util.*;
import java.net.*;

public class Servidor {
    private ServerSocket serverSocket;
    private Socket clienteSocket;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private Tabuleiro mapaServidor;
    private Jogador jogadorServidor;
    private static final int PORTA = 12345;

    public void iniciar() {
        try {
            serverSocket = new ServerSocket(PORTA);
            System.out.println("ServidorHost iniciado na porta " + PORTA);
            System.out.println("Aguardando conexao do cliente...");

            clienteSocket = serverSocket.accept();
            System.out.println("ConexaoJogador conectado!");

            saida = new ObjectOutputStream(clienteSocket.getOutputStream());
            entrada = new ObjectInputStream(clienteSocket.getInputStream());

            configurarJogo();

            try {
                executarJogo();
            } catch (ClassNotFoundException e) {
                System.out.println("Erro ao executar o jogo: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        } finally {
            encerrarConexao();
        }
    }

    private void configurarJogo() throws IOException {
        mapaServidor = new Tabuleiro();
        List<NavioBase> embarcacoes = Docas.criarTodasEmbarcacoes();
        Random random = new Random();

        for (NavioBase embarcacao : embarcacoes) {
            boolean posicionado = false;
            while (!posicionado) {
                int linha = random.nextInt(Tabuleiro.getTamanho());
                int coluna = random.nextInt(Tabuleiro.getTamanho());
                int direcao = random.nextInt(8);
                posicionado = mapaServidor.posicionarEmbarcacao(embarcacao, linha, coluna, direcao);
            }
        }

        System.out.println("Digite seu nome: ");
        Scanner scanner = new Scanner(System.in);
        jogadorServidor = new Jogador(scanner.nextLine(), 0);

        saida.writeObject(jogadorServidor.getNome());
        String nomeCliente = null;
        try {
            nomeCliente = (String) entrada.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao ler nome do cliente: " + e.getMessage());
        }
        System.out.println("Jogando contra: " + nomeCliente);
    }

    private void executarJogo() throws IOException, ClassNotFoundException {
        boolean jogoAtivo = true;
        boolean minhaVez = true;
        Tabuleiro mapaOponente = new Tabuleiro();

        while (jogoAtivo) {
            if (minhaVez) {
                System.out.println("\n=== ESTADO ATUAL DO JOGO ===");
                mapaServidor.mostrarMinhaFrota();
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
                    jogadorServidor.adicionarPontos();

                    List<int[]> posicoesEmbarcacao = (List<int[]>) entrada.readObject();

                    for (int[] posicoes : posicoesEmbarcacao) {
                        mapaOponente.registrarTiro(posicoes[0], posicoes[1], true);
                    }

                    if (jogadorServidor.getPontuacao() >= 5) {
                        System.out.println("Voce venceu!");
                        jogoAtivo = false;
                    }
                } else {
                    System.out.println("Agua!");
                    mapaOponente.registrarTiro(linha, coluna, false);
                }

                System.out.println("\n=== RESULTADO DA SUA JOGADA ===");
                mapaServidor.mostrarMinhaFrota();
                mapaOponente.mostrarFrotaInimigo();
            } else {
                System.out.println("Aguardando jogada do oponente...");
                int[] jogada = (int[]) entrada.readObject();

                boolean acerto = mapaServidor.atirar(jogada[0], jogada[1]);
                saida.writeObject(acerto);

                if (acerto) {
                    System.out.println("Oponente acertou uma embarcacao!");

                    List<int[]> posicoesEmbarcacao = new ArrayList<>();

                    for (int i = 0; i < Tabuleiro.getTamanho(); i++) {
                        for (int j = 0; j < Tabuleiro.getTamanho(); j++) {
                            if (mapaServidor.getMapaControle()[i][j] == 'Y') {
                                posicoesEmbarcacao.add(new int[]{i, j});
                            }
                        }
                    }

                    saida.writeObject(posicoesEmbarcacao);
                } else {
                    System.out.println("Oponente errou o tiro!");
                }

                System.out.println("\n=== RESULTADO DA JOGADA DO OPONENTE ===");
                mapaServidor.mostrarMinhaFrota();
                mapaOponente.mostrarFrotaInimigo();
            }

            minhaVez = !minhaVez;
        }
    }

    private void encerrarConexao() {
        try {
            if (saida != null) saida.close();
            if (entrada != null) entrada.close();
            if (clienteSocket != null) clienteSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            System.out.println("Erro ao encerrar conexoes: " + e.getMessage());
        }
    }
}