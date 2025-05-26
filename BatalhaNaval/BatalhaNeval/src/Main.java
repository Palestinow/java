import java.util.Scanner;

public class Main {
    private static final int OPCAO_JOGO_LOCAL = 1;
    private static final int OPCAO_JOGO_REDE_SERVIDOR = 2;
    private static final int OPCAO_JOGO_REDE_CLIENTE = 3;
    private final GameLauncher gameLauncher;

    public Main() {
        this.gameLauncher = new GameLauncher();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.iniciarAplicacao();
    }

    private void iniciarAplicacao() {
        exibirMenu();
        int opcaoSelecionada = gameLauncher.obterOpcaoUsuario();
        executarModoJogo(opcaoSelecionada);
    }

    private void exibirMenu() {
        System.out.println("=== REGRAS DO JOGO ===");
        System.out.println("1. O jogo e disputado em um tabuleiro 20x20");
        System.out.println("2. Cada jogador possui os seguintes navios:");
        System.out.println("   - 2 Porta-Avioes (8 casas)");
        System.out.println("   - 3 Destroyers (5 casas)");
        System.out.println("   - 4 Submarinos (4 casas)");
        System.out.println("   - 5 Fragatas (3 casas)");
        System.out.println("   - 6 Botes (2 casas)");
        System.out.println("3. Os navios podem ser posicionados na horizontal, vertical ou diagonal");
        System.out.println("4. Para atirar, digite o numero da linha (0-19) e a letra da coluna (A-T)");
        System.out.println("5. O primeiro jogador a destruir 5 embarcacoes do oponente vence");
        System.out.println();

        System.out.println("=== LEGENDA ===");
        System.out.println(". - Agua (nao atingida)");
        System.out.println("O - Agua (atingida)");
        System.out.println("a - Porta-Avioes");
        System.out.println("d - Destroyer");
        System.out.println("s - Submarino");
        System.out.println("f - Fragata");
        System.out.println("b - Bote");
        System.out.println("X - Navio atingido");
        System.out.println("Y - Seu navio atingido");
        System.out.println("M - Tiro na agua");
        System.out.println();

        System.out.println("=== BATALHA NAVAL ===");
        System.out.println(OPCAO_JOGO_LOCAL + ". Jogo Local");
        System.out.println(OPCAO_JOGO_REDE_SERVIDOR + ". Jogo em Rede (Servidor)");
        System.out.println(OPCAO_JOGO_REDE_CLIENTE + ". Jogo em Rede (Cliente)");
        System.out.print("Escolha uma opcao: ");
    }

    private void executarModoJogo(int opcao) {
        switch (opcao) {
            case OPCAO_JOGO_LOCAL -> gameLauncher.iniciarJogoLocal();
            case OPCAO_JOGO_REDE_SERVIDOR -> gameLauncher.iniciarServidor();
            case OPCAO_JOGO_REDE_CLIENTE -> gameLauncher.iniciarCliente();
            default -> System.out.println("Opcao invalida!");
        }
    }
}

class GameLauncher {
    private final Scanner scanner;

    public GameLauncher() {
        this.scanner = new Scanner(System.in);
    }

    public int obterOpcaoUsuario() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public void iniciarJogoLocal() {
        Jogo jogoLocal = new Jogo();
        jogoLocal.iniciarJogo();
        encerrarScanner();
    }

    public void iniciarServidor() {
        new Servidor().iniciar();
        encerrarScanner();
    }

    public void iniciarCliente() {
        new Cliente().conectar();
        encerrarScanner();
    }

    private void encerrarScanner() {
        scanner.close();
    }
}