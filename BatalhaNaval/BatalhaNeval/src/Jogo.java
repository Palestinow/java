import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Jogo {
    private Mapa mapa1;
    private Mapa mapa2;
    private Jogador jogador1;
    private Jogador jogador2;
    private final Random random;
    private final Scanner scanner;
    private final Ranking ranking;
    private boolean vezJogador1;

    public Jogo() {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.ranking = new Ranking();
    }

    public void iniciarJogo() {
        System.out.println("1. Novo Jogo");
        System.out.println("2. Continuar Jogo");
        System.out.print("Escolha uma opcao: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 2 && EstadoDoJogo.existeJogoSalvo()) {
            carregarJogo();
        } else {
            novoJogo();
        }

        executarJogo();
    }

    private void novoJogo() {
        this.mapa1 = new Mapa();
        this.mapa2 = new Mapa();

        System.out.println("Insira o nome do jogador 1: ");
        jogador1 = new Jogador(scanner.nextLine(), 0);
        System.out.println("Insira o nome do jogador 2: ");
        jogador2 = new Jogador(scanner.nextLine(), 0);

        posicionarEmbarcacoes(mapa1);
        posicionarEmbarcacoes(mapa2);

        vezJogador1 = true;
    }

    private void carregarJogo() {
        EstadoDoJogo estado = EstadoDoJogo.carregarEstado();
        if (estado != null) {
            this.mapa1 = estado.getMapa1();
            this.mapa2 = estado.getMapa2();
            this.jogador1 = estado.getJogador1();
            this.jogador2 = estado.getJogador2();
            this.vezJogador1 = estado.isVezJogador1();
            System.out.println("Jogo carregado com sucesso!");
        }
    }

    private void salvarJogo() {
        EstadoDoJogo estado = new EstadoDoJogo(mapa1, mapa2, jogador1, jogador2, vezJogador1);
        EstadoDoJogo.salvarEstado(estado);
    }

    private void executarJogo() {
        boolean jogoAtivo = true;

        while (jogoAtivo) {
            Jogador jogadorAtual = vezJogador1 ? jogador1 : jogador2;
            Mapa mapaAlvo = vezJogador1 ? mapa2 : mapa1;

            salvarJogo();

            System.out.println("\nVez de: " + jogadorAtual.getNome());
            realizarJogada(jogadorAtual, mapaAlvo);

            if (jogadorAtual.getPontuacao() >= 5) {
                System.out.println("\nParabens " + jogadorAtual.getNome() + "! Voce venceu!");
                ranking.adicionarJogador(jogadorAtual);
                int posicao = ranking.getPosicaoJogador(jogadorAtual.getNome());
                System.out.println("Sua posicao no ranking: " + posicao + " lugar");
                EstadoDoJogo.deletarJogoSalvo();
                jogoAtivo = false;
            } else {
                vezJogador1 = !vezJogador1;
            }
        }

        scanner.close();
    }

    private void posicionarEmbarcacoes(Mapa mapa) {
        List<Embarcacao> embarcacoes = EmbarcacaoFabrica.criarTodasEmbarcacoes();

        for (Embarcacao embarcacao : embarcacoes) {
            boolean posicionado = false;
            while (!posicionado) {
                int linha = random.nextInt(Mapa.getTamanho());
                int coluna = random.nextInt(Mapa.getTamanho());
                int direcao = random.nextInt(8);
                posicionado = mapa.posicionarEmbarcacao(embarcacao, linha, coluna, direcao);
            }
        }
    }

    private void realizarJogada(Jogador jogador, Mapa mapaAlvo) {
        Mapa mapaProprio = mapaAlvo == mapa2 ? mapa1 : mapa2;

        mapaProprio.mostrarMinhaFrota();
        mapaAlvo.mostrarFrotaInimigo();

        System.out.println("\nDigite a linha (0-19): ");
        int linha = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite a coluna (A-T): ");
        String colunaStr = scanner.nextLine().toUpperCase();
        int coluna = colunaStr.charAt(0) - 'A';

        if (linha < 0 || linha >= Mapa.getTamanho() ||
            coluna < 0 || coluna >= Mapa.getTamanho()) {
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