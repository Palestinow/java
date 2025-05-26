import java.util.Scanner;

public class Main {
    private static final int OPCAO_JOGO_LOCAL = 1;
    private static final int OPCAO_JOGO_REDE_SERVIDOR = 2;
    private static final int OPCAO_JOGO_REDE_CLIENTE = 3;
    private final GameLauncher gameLauncher;

    public Main() {
        this.gameLauncher = new GameLauncher();
    }

    public static void executarJogo(String[] args) {
        Main executarJogo = new Main();
        executarJogo.iniciarAplicacao();
    }
    public static void main(String[] args) {
        executarJogo(args);
    }
    private void iniciarAplicacao() {
        exibirMenu();
        int opcaoSelecionada = gameLauncher.obterOpcaoUsuario();
        executarModoJogo(opcaoSelecionada);
        
    }

    private void exibirMenu() {
        System.out.println(". - Agua (nao atingida)");
        System.out.println("O - Agua (atingida)");
        System.out.println("a - Porta-Avioes");
        System.out.println("d - Destruidor");
        System.out.println("s - NaveSubmersa");
        System.out.println("f - Fregata");
        System.out.println("b - Barquinho");
        System.out.println("X - Navio atingido");
        System.out.println("Y - Seu navio atingido");
        System.out.println("M - Tiro na agua");
        System.out.println();

        System.out.println("=== BATALHA NAVAL ===");
        System.out.println(OPCAO_JOGO_LOCAL + ". Partida Local");
        System.out.println(OPCAO_JOGO_REDE_SERVIDOR + ". Partida em Rede (Servidor)");
        System.out.println(OPCAO_JOGO_REDE_CLIENTE + ". Partida em Rede (Cliente)");
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
        Partida jogoLocal = new Partida();
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
