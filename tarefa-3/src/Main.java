
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Instanciar Matéria-Prima e Gerenciador de Produção
        MateriaPrima materiaPrima = new MateriaPrima(1, "Princípio Ativo Farmacêutico (Insumo)", 500.0, "kg", 10.0);
        GerenciadorProducao gerenciador = new GerenciadorProducao(materiaPrima, 1000.0);

        // 2. Instanciar e registrar as Máquinas da linha de produção
        MaquinaProcessamento maquinaProcessamento = new MaquinaProcessamento("Reator Farmacêutico / Mistura", 500, 0.0, 1.50, 0.15);
        MaquinaEmbalagem maquinaEmbalagem = new MaquinaEmbalagem("Embaladora Blister / Selagem", 500, 0.0, 1.00, 0.10);
        MaquinaInspecao maquinaInspecao = new MaquinaInspecao("Espectrômetro de Inspeção / Controle Anvisa", 500, 0.05, 2.00);

        gerenciador.adicionarMaquina(maquinaProcessamento);
        gerenciador.adicionarMaquina(maquinaEmbalagem);
        gerenciador.adicionarMaquina(maquinaInspecao);

        // 3. Registrar Demandas Iniciais de Medicamentos
        gerenciador.registrarDemanda(new Demanda("Medicamento de Alta Qualidade", 50));
        gerenciador.registrarDemanda(new Demanda("Medicamento de Média Qualidade", 50));
        gerenciador.registrarDemanda(new Demanda("Medicamento de Baixa Qualidade", 50));

        boolean executando = true;

        while (executando) {
            // Exibição do Menu
            System.out.println("\n==========================================");
            System.out.println("       FÁBRICA DE MEDICAMENTOS - Crescendo para te ajuadar!!!");
            System.out.println("        A vida é uma caixinha de surpresas, a nossa tem remédio barato hehe. ");
            System.out.println("            Desenvolvido por: Adriann e Alex");
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
            System.out.println("7 - Ver demandas");
            System.out.println("8 - Ver armazém");
            System.out.println("9 - Ver estoque de matéria-prima");

            System.out.println("\nCOMPRAR MATÉRIA-PRIMA");
            System.out.println("10 - Comprar matéria-prima");

            System.out.println("\n0 - SAIR");
            System.out.print("ESCOLHA: ");

            // Validação de entrada numérica
            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.next();
                continue;
            }

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite a nova quantidade para a demanda de Alta Qualidade: ");
                    if (scanner.hasNextInt()) {
                        int qtd = scanner.nextInt();
                        gerenciador.atualizarDemanda(0, qtd);
                    } else {
                        System.out.println("Entrada inválida! Digite apenas números inteiros.");
                        scanner.next();
                    }
                    break;
                case 2:
                    System.out.print("Digite a nova quantidade para a demanda de Média Qualidade: ");
                    if (scanner.hasNextInt()) {
                        int qtd = scanner.nextInt();
                        gerenciador.atualizarDemanda(1, qtd);
                    } else {
                        System.out.println("Entrada inválida! Digite apenas números inteiros.");
                        scanner.next();
                    }
                    break;
                case 3:
                    System.out.print("Digite a nova quantidade para a demanda de Baixa Qualidade: ");
                    if (scanner.hasNextInt()) {
                        int qtd = scanner.nextInt();
                        gerenciador.atualizarDemanda(2, qtd);
                    } else {
                        System.out.println("Entrada inválida! Digite apenas números inteiros.");
                        scanner.next();
                    }
                    break;
                case 4:
                    System.out.println("\nIniciando fabricação de Medicamento de Alta Qualidade...");
                    gerenciador.fabricarDemanda(0);
                    break;
                case 5:
                    System.out.println("\nIniciando fabricação de Medicamento de Média Qualidade...");
                    gerenciador.fabricarDemanda(1);
                    break;
                case 6:
                    System.out.println("\nIniciando fabricação de Medicamento de Baixa Qualidade...");
                    gerenciador.fabricarDemanda(2);
                    break;
                case 7:
                    gerenciador.exibirArmazem();
                    break;
                case 8:
                    gerenciador.exibirDemandas();
                    break;
                case 9:
                    gerenciador.exibirEstoqueMateriaPrima();
                    break;
                case 10:
                    System.out.print("Digite a quantidade de matéria-prima a ser comprada (kg): ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Entrada inválida! Digite apenas números.");
                        scanner.next();
                    } else {
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
