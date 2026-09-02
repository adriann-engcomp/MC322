
import java.util.Scanner;
import model.MateriaPrima;
import model.Produto;
import model.Maquina;
import model.Esteira;
import model.EstacaoInspecao;

private static Produto[] remedios = new Produto[100];

public class Main {

    // Instancio os objetos

    // Vetor: cada remédio vai consumir 4g da materia prima
        remedios[0] = new Produto(1, "Paracetamol", 4);
        remedios[1] = new Produto(2, "Ibuprofeno", 4);
        remedios[2] = new Produto(3, "Dipirona", 4);

    public static void main(String[] args) {
        MateriaPrima materiaPrima = new MateriaPrima(1, "Composto X", 100, "g", 20);
        // o ciclo é definido pelo uso do usuario
        Maquina maquina = new Maquina("Maquina farmaceutica", true, 10);
        Esteira esteira = new Esteira(materiaPrima, true, 100);
        EstacaoInspecao estacao = new EstacaoInspecao(true, 100);


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
        for (int i = 0; i <= remedios.length; i++) {
            System.out.println("Remédio " + remedios[i].getId() + " - " + remedios[i].getNome() + " (demanda: " + remedios[i].getDemandaMateriaPrimaNecessaria() + " " + materiaPrima.getUnidade() + ")");
        }
        System.out.println("========================================");

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        System.out.println("\n========================================");
        System.out.println("MENU PRINCIPAL");
        System.out.println("========================================");
        System.out.println("1 - Iniciar produção (10 medicamentos por ciclo)");
        System.out.println("2 - Consultar estoque");
        System.out.println("3 - Sair");
        System.out.println("Escolha uma opção: ");


        /* verificar se há bugs ao clicar em sair */
        opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Você escolheu: Iniciar produção.");
                // ao iniciar a producao devemos processar 10 medicamentos
                String[] medicamentos = {"Paracetamol", "Ibuprofeno", "Dipirona", "Neosaldina"};

                for (int i = 3; i <= medicamentos.length; i++) {
                    // verificar se há materia prima para produzir o remedio
                    String medicamento = medicamentos[i % 3];
                    if (materiaPrima.verificarDisponibilidade(4) == true) {
                        // toda vez que criar um remedio, retirar um valor da materia prima
                        materiaPrima.consumir(4);
                        // criamos o remedio
                        remedios[i] = new Produto(i, medicamento, 4);
                        continue;
                    }

                    return;
                }

                // instanciar maquina de processamento

                // atualizar 
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
