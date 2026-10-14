import java.util.Scanner;

/**
 * Ponto de entrada da fábrica de medicamentos.
 * <p>
 * Solicita o cenário, inicializa a fábrica e coordena o menu principal.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class Main {

    /**
     * Construtor privado para prevenir instanciação direta da classe utilitária de entrada.
     */
    private Main() {
    }

    /**
     * Método principal que pede o cenário, inicializa a fábrica e controla o loop do menu interativo.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Cenario cenario = MenuCenario.exibir(scanner);
        GerenciadorProducao gerenciador = InicializoFabrica.inicializar(cenario);
        boolean executando = true;

        while (executando) {
            System.out.println("\n==============================================================");
            System.out.println("       FÁBRICA DE MEDICAMENTOS - Crescendo para te ajudar!!!");
            System.out.printf(" ESTRATÉGIA ATUAL: [%s]\n", gerenciador.getEstrategiaAtual().getNomeEstrategia());
            System.out.printf(" CENÁRIO ATIVO:    [%s]\n", cenario.getNome());
            System.out.printf(" BUDGET ATUAL:     R$ %.2f\n", gerenciador.getBudget());
            System.out.println("==============================================================");

            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Demandas");
            System.out.println("2. Fabricacao");
            System.out.println("3. Consultar");
            System.out.println("4. Comprar materia-prima");
            System.out.println("5. Gerenciar estrategia");
            System.out.println("6. Auditoria");
            System.out.println("0. Sair");
            System.out.print("ESCOLHA: ");

            int opcao = LeitorDeOpcoes.opcao(scanner);
            if (opcao == -1) {
                continue;
            }

            switch (opcao) {
                case 1:
                    MenuDemandas.exibir(scanner, gerenciador);
                    break;
                case 2:
                    MenuFabricacao.exibir(scanner, gerenciador);
                    break;
                case 3:
                    MenuConsultar.exibir(scanner, gerenciador);
                    break;
                case 4:
                    MenuMateriaPrima.exibir(scanner, gerenciador);
                    break;
                case 5:
                    MenuEstrategias.exibir(scanner, gerenciador);
                    break;
                case 6:
                    MenuAuditoria.exibir(scanner, gerenciador);
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema da fábrica farmacêutica. Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu principal (0 a 6).");
                    break;
            }
        }
        scanner.close();
    }
}
