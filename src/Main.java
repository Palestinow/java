import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== BATALHA NAVAL ===");
        System.out.println("Tabuleiro: 15 x 15 (linhas 0 a 14, colunas A a O)");
        System.out.print("Digite o nome do Jogador 1: ");
        String nomeJogador1 = scanner.nextLine();

        System.out.print("Digite o nome do Jogador 2: ");
        String nomeJogador2 = scanner.nextLine();

        System.out.println("\n1 - Iniciar Jogo");
        System.out.println("2 - Sair");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        if (opcao == 1) {
            Jogo jogo = new Jogo(nomeJogador1, nomeJogador2);
            jogo.iniciar();
        } else {
            System.out.println("Saindo...");
        }

        scanner.close();
    }
}