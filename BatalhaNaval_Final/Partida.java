import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Partida {
    private Tabuleiro mapa1;
    private Tabuleiro mapa2;
    private Jogador jogador1;
    private Jogador jogador2;
    private final Random random;
    private final Scanner scanner;
    private final Ranking ranking;
    private boolean vezJogador1;

    public Partida() {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.ranking = new Ranking();
    }

    public void iniciarJogo() {
        System.out.println("1. Novo Partida");
        System.out.println("2. Continuar Partida");
        System.out.print("Escolha uma opcao: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 2 && StatusJogo.existeJogoSalvo()) {
            carregarJogo();
        } else {
            novoJogo();
        }

        executarJogo();
    }

    private void novoJogo() {
        this.mapa1 = new Tabuleiro();
        this.mapa2 = new Tabuleiro();

        System.out.println("Insira o nome do jogador 1: ");
        jogador1 = new Jogador(scanner.nextLine(), 0);
        System.out.println("Insira o nome do jogador 2: ");
        jogador2 = new Jogador(scanner.nextLine(), 0);

        posicionarEmbarcacoes(mapa1);
        posicionarEmbarcacoes(mapa2);

        vezJogador1 = true;
    }

    private void carregarJogo() {
        StatusJogo estado = StatusJogo.carregarEstado();
        if (estado != null) {
            this.mapa1 = estado.getMapa1();
            this.mapa2 = estado.getMapa2();
            this.jogador1 = estado.getJogador1();
            this.jogador2 = estado.getJogador2();
            this.vezJogador1 = estado.isVezJogador1();
            System.out.println("Partida carregada com sucesso!");
        }
    }

    private void salvarJogo() {
        StatusJogo estado = new StatusJogo(mapa1, mapa2, jogador1, jogador2, vezJogador1);
        StatusJogo.salvarEstado(estado);
    }

    private void executarJogo() {
        boolean jogoAtivo = true;

        while (jogoAtivo) {
            Jogador jogadorAtual = vezJogador1 ? jogador1 : jogador2;
            Tabuleiro mapaAlvo = vezJogador1 ? mapa2 : mapa1;

            salvarJogo();

            System.out.println("\nVez de: " + jogadorAtual.getNome());
            realizarJogada(jogadorAtual, mapaAlvo);

            if (jogadorAtual.getPontuacao() >= 5) {
                System.out.println("\nParabens " + jogadorAtual.getNome() + "! Voce venceu!");
                ranking.adicionarJogador(jogadorAtual);
                int posicao = ranking.getPosicaoJogador(jogadorAtual.getNome());
                System.out.println("Sua posicao no ranking: " + posicao + " lugar");
                StatusJogo.deletarJogoSalvo();
                jogoAtivo = false;
            } else {
                vezJogador1 = !vezJogador1;
            }
        }

        scanner.close();
    }

    private void posicionarEmbarcacoes(Tabuleiro mapa) {
        List<NavioBase> embarcacoes = Docas.criarTodasEmbarcacoes();

        for (NavioBase embarcacao : embarcacoes) {
            boolean posicionado = false;
            while (!posicionado) {
                int linha = random.nextInt(Tabuleiro.getTamanho());
                int coluna = random.nextInt(Tabuleiro.getTamanho());
                int direcao = random.nextInt(8);
                posicionado = mapa.posicionarEmbarcacao(embarcacao, linha, coluna, direcao);
            }
        }
    }

    private void realizarJogada(Jogador jogador, Tabuleiro mapaAlvo) {
        Tabuleiro mapaProprio = mapaAlvo == mapa2 ? mapa1 : mapa2;

        mapaProprio.mostrarMinhaFrota();
        mapaAlvo.mostrarFrotaInimigo();

        System.out.println("\nDigite a linha (0-19): ");
        int linha = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite a coluna (A-T): ");
        String colunaStr = scanner.nextLine().toUpperCase();
        int coluna = colunaStr.charAt(0) - 'A';

        if (linha < 0 || linha >= Tabuleiro.getTamanho() ||
            coluna < 0 || coluna >= Tabuleiro.getTamanho()) {
            System.out.println("Posicao invalida! Tente novamente.");
            realizarJogada(jogador, mapaAlvo);
            return;
        }

        if (mapaAlvo.atirar(linha, coluna)) {
            System.out.println("Acertou uma embarcacao!");
            jogador.adicionarPontos();
            System.out.println("Pontuacao: " + jogador.getPontuacao());
        } else {
            System.out.println("Agua!");
        }
    }
}