import java.util.Scanner;
import java.io.IOException;
import java.util.Formatter;

public class Main {
    public static void main(String[] args) throws IOException {
       
        Scanner scanner = new Scanner(System.in);
        
        Calculas calculas = new Calculas();

        System.out.println("Selecione uma operação:");
        System.out.println("1. Soma");
        System.out.println("2. Subtração");
        System.out.println("3. Multiplicação");
        System.out.println("4. Divisão");

    
        int operacao = scanner.nextInt();

        System.out.print("Digite o primeiro número: ");
        double num1 = scanner.nextDouble();
        System.out.print("Digite o segundo número: ");
        double num2 = scanner.nextDouble();

        double resultado = 0;
        switch (operacao) {
            case 1:
                resultado = calculas.somar(num1, num2);
                System.out.println("Resultado: " + resultado);
                break;
            case 2:
                resultado = calculas.subtrair(num1, num2);
                System.out.println("Resultado: " + resultado);
                break;
            case 3:
                resultado = calculas.multiplicar(num1, num2);
                System.out.println("Resultado: " + resultado);
                break;
            case 4:
                resultado = calculas.dividir(num1, num2);
                if (!Double.isNaN(resultado)) {
                    System.out.println("Resultado: " + resultado);
                }
                break;
            default:
                System.out.println("Opção inválida.");

                

        }
        Formatter f = new Formatter("C:\\tcp\\java\\salva\\results.txt");
                f.format("Operacao: " + operacao + resultado);
                f.close();

        scanner.close();
    }
}

