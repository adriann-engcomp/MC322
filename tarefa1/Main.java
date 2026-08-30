
import java.util.Scanner;
import model.MateriaPrima;
import model.Produto;

public class Main {

    // inicializando remedios
    static Produto[] remedios = new Produto[3];
    static MateriaPrima[] materiaPrima = new MateriaPrima[0];

    public static void main(String[] args) {
        MateriaPrima materiaPrima = new MateriaPrima(1, "Composto Ativo XR-7", 100, "g", 20);

        remedios[0] = new Produto(1, "Paracetamol", 5);
        remedios[1] = new Produto(2, "Ibuprofeno", 3);
        remedios[2] = new Produto(3, "Dipirona", 7);

        System.out.println("""
        ========================================
        Fabrica de remédios
        ========================================

        Bem-vindos à nossa fábrica de remédios! Aqui transformamos e cuidamos de vidas

        Desenvolvido por:
        ========================================
        """);

        System.out.println("========================================");
        System.out.println("PLANTA INDUSTRIAL");
        System.out.println("========================================");
        System.out.println("\nMatéria-Prima: MP00" + materiaPrima.getId() + " - " + materiaPrima.getNome());
        System.out.println("Quantidade: " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade());
        System.out.println("Unidade: " + materiaPrima.getUnidade());
        System.out.println("\nRemédios disponíveis:");
        for (int i = 0; i < remedios.length; i++) {
            System.out.println("Remédio " + remedios[i].getId() + " - " + remedios[i].getNome() + " (demanda: " + remedios[i].getQuantidadeMateriaPrimaNecessaria() + " " + materiaPrima.getUnidade() + ")");
        }
        System.out.println("========================================");

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        System.out.println("\n========================================");
        System.out.println("MENU PRINCIPAL");
        System.out.println("========================================");
        System.out.println("1 - Iniciar produção");
        System.out.println("2 - Consultar estoque");
        System.out.println("3 - Sair");
        System.out.print("Escolha uma opção: ");

        opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Você escolheu: Iniciar produção.");
                break;
            case 2:
                System.out.println("Você escolheu: Consultar estoque.");
                break;
            case 3:
                System.out.println("Saindo do sistema");
                break;
            default:
                System.out.println("Opção inválida! Escolha entre 1 e 3.");
        }
    }
}
