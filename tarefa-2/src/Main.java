import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            int opcao = scanner.nextInt();
             
            switch (opcao) {
                case 1:
                    break;
                case 3:
                    executando = false;
                    break;
                default:
                    System.out.println("\nEncerrando o sistema da planta industrial Farmacêutica. Até logo!");
                    executando = false;
                    break;
            }
        }
    }
}