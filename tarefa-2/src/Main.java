
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // todo: conteudo que preciso fazer (instanciar matéria-prima, máquinas, demandas e o gerenciador de produção)
        MateriaPrima materiaPrima = new MateriaPrima(1, "Princípio Ativo Farmacêutico (Insumo)", 500.0, "kg", 10.0);
        GerenciadorProducao gerenciador = new GerenciadorProducao(materiaPrima, 1000.0);

        boolean executando = true;

        while (executando) {
            // Exibição do Menu
            System.out.println("\n==========================================");
            System.out.println("       FÁBRICA DE MEDICAMENTOS");
            System.out.println("==========================================");
            System.out.printf("BUDGET ATUAL: R$ %.2f\n\n", gerenciador.getBudget());

            System.out.println("ATUALIZAR DEMANDAS");
            System.out.println("1 - Atualizar demanda de Medicamento de Alta Qualidade");
            System.out.println("2 - Atualizar demanda de Medicamento de Média Qualidade");
            System.out.println("3 - Atualizar demanda de Medicamento de Baixa Qualidade");

            System.out.println("\nFABRICAR");
            System.out.println("4 - Fabricar Medicamento de Alta Qualidade");
            System.out.println("5 - Fabricar Medicamento de Média Qualidade");
            System.out.println("6 - Fabricar Medicamento de Baixa Qualidade");

            System.out.println("\nCONSULTAR");
            System.out.println("7 - Ver armazém");
            System.out.println("8 - Ver estoque de matéria-prima");

            System.out.println("\nCOMPRAR MATÉRIA-PRIMA");
            System.out.println("9 - Comprar matéria-prima");

            System.out.println("\n0 - SAIR");
            System.out.print("ESCOLHA: ");

            // Validação de entrada numérica
            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.nextLine();
                continue;
            }

            int opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1:
                    if (gerenciador.getDemandas().size() < 1) {
                        System.out.println("Demanda de Alta Qualidade não encontrada. Registrando nova demanda.");
                        gerenciador.registrarDemanda(new Demanda("Medicamento de Alta Qualidade", 100));
                    } else {
                        System.out.println("Atualizando demanda de Medicamento de Alta Qualidade para 100 unidades.");
                        gerenciador.atualizarDemanda(0, 100);
                    }

                    gerenciador.atualizarDemanda(0, 50);
                    break;
                case 2:
                    System.out.println("Atualizando demanda de Medicamento de Média Qualidade para 50 unidades.");
                    gerenciador.atualizarDemanda(1, 50);
                    break;
                case 3:
                    System.out.println("Atualizando demanda de Medicamento de Baixa Qualidade para 50 unidades.");
                    gerenciador.atualizarDemanda(2, 50);
                    break;
                case 4:
                    System.out.println("Fabricando demanda de Medicamento de Alta Qualidade.");
                    gerenciador.fabricarDemanda(0);
                    break;
                case 5:
                    System.out.println("Fabricando demanda de Medicamento de Média Qualidade.");
                    gerenciador.fabricarDemanda(1);
                    break;
                case 6:
                    System.out.println("Fabricando demanda de Medicamento de Baixa Qualidade.");
                    gerenciador.fabricarDemanda(2);
                    break;
                case 7:
                    gerenciador.exibirArmazem();
                    break;
                case 8:
                    gerenciador.exibirEstoqueMateriaPrima();
                    break;
                case 9:
                    System.out.print("Digite a quantidade de matéria-prima a ser comprada: ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Entrada inválida! Digite apenas números.");
                        scanner.nextLine();
                    }
                    else {
                        double quantidade = scanner.nextDouble();
                        gerenciador.comprarMateriaPrima(quantidade);
                    }
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema da fábrica farmacêutica. Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número entre 0 e 9.");
                    break;
            }
        }

        scanner.close();
    }
}
