
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Cenario cenario = Cenario.FABRICA_NORMAL;
        Random random = new Random(cenario.getSementeAleatoria());

        // 1. Instanciar Matéria-Prima e Gerenciador de Produção
        MateriaPrima materiaPrima = new MateriaPrima(1, "Princípio Ativo Farmacêutico (Insumo)", cenario.getEstoqueInicialMateriaPrima(), "kg", 10.0);
        GerenciadorProducao gerenciador = new GerenciadorProducao(materiaPrima, cenario.getBudgetInicial());

        // 2. Instanciar e registrar as Máquinas da linha de produção
        MaquinaProcessamento maquinaProcessamento = new MaquinaProcessamento("Reator Farmacêutico / Mistura", 500, 0.15, 1.50, cenario, random);
        MaquinaEmbalagem maquinaEmbalagem = new MaquinaEmbalagem("Embaladora Blister / Selagem", 500, 0.10, 1.00, cenario, random);
        MaquinaInspecao maquinaInspecao = new MaquinaInspecao("Espectrômetro de Inspeção / Controle Anvisa", 500, 0.05, 2.00, cenario, random);

        gerenciador.adicionarMaquina(maquinaProcessamento);
        gerenciador.adicionarMaquina(maquinaEmbalagem);
        gerenciador.adicionarMaquina(maquinaInspecao);

        // 3. Registrar Demandas Iniciais de Medicamentos
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.CONTROLADO, "Medicamento Controlado", 50, 4.50));
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.CONTINUO, "Medicamento Contínuo", 50, 4.50));
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.GENERICO, "Medicamento Genérico", 50, 4.50));

        boolean executando = true;

        while (executando) {
            // Exibição do Menu
            System.out.println("\n==============================================================");
            System.out.println("       FÁBRICA DE MEDICAMENTOS - Crescendo para te ajudar!!!");
            System.out.printf(" ESTRATÉGIA ATUAL: [%s]\n", gerenciador.getEstrategiaAtual().getNomeEstrategia());
            System.out.printf(" CENÁRIO ATIVO:    [%s]\n", cenario.getNome());
            System.out.printf(" BUDGET ATUAL:     R$ %.2f\n", gerenciador.getBudget());
            System.out.println("==============================================================");

            System.out.println("--------------------------------------------------------------");
            System.out.println("[ATUALIZAR DEMANDAS]");
            System.out.println("1 - Atualizar demanda de Medicamento Controlado");
            System.out.println("2 - Atualizar demanda de Medicamento Contínuo");
            System.out.println("3 - Atualizar demanda de Medicamento Genérico");

            System.out.println("--------------------------------------------------------------");
            System.out.println("[FABRICAR]");
            System.out.println("4 - Processar próxima demanda (usa estratégia ativa)");
            System.out.println("5 - Fabricar Medicamento Controlado");
            System.out.println("6 - Fabricar Medicamento Contínuo");
            System.out.println("7 - Fabricar Medicamento Genérico");

            System.out.println("--------------------------------------------------------------");
            System.out.println("[CONSULTAR]");
            System.out.println("8 - Ver armazém (produtos acabados)");
            System.out.println("9 - Ver estoque de matéria-prima");
            System.out.println("10 - Ver demandas");

            System.out.println("--------------------------------------------------------------");
            System.out.println("[COMPRAR MATÉRIA-PRIMA]");
            System.out.println("11 - Comprar matéria-prima");

            System.out.println("--------------------------------------------------------------");
            System.out.println("[GERENCIAMENTO DE ESTRATÉGIA]");
            System.out.println("12 - Alterar Estratégia de Produção\n  (1: Ordem de Chegada | 2: Maior Demanda | 3: Maximizar Producao)");
            System.out.println("--------------------------------------------------------------");
            System.out.println("[AUDITORIA]");
            System.out.println("13 - Executar Relatório de Auditoria (Interface Auditável)");

            System.out.println("--------------------------------------------------------------");
            System.out.println("0 - SAIR");
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
                    System.out.print("Digite a nova quantidade para a demanda de Medicamento Controlado: ");
                    if (scanner.hasNextInt()) {
                        int qtd = scanner.nextInt();
                        gerenciador.atualizarDemanda(0, qtd);
                    } else {
                        System.out.println("Entrada inválida! Digite apenas números inteiros.");
                        scanner.next();
                    }
                    break;
                case 2:
                    System.out.print("Digite a nova quantidade para a demanda de Medicamento Contínuo: ");
                    if (scanner.hasNextInt()) {
                        int qtd = scanner.nextInt();
                        gerenciador.atualizarDemanda(1, qtd);
                    } else {
                        System.out.println("Entrada inválida! Digite apenas números inteiros.");
                        scanner.next();
                    }
                    break;
                case 3:
                    System.out.print("Digite a nova quantidade para a demanda de Medicamento Genérico: ");
                    if (scanner.hasNextInt()) {
                        int qtd = scanner.nextInt();
                        gerenciador.atualizarDemanda(2, qtd);
                    } else {
                        System.out.println("Entrada inválida! Digite apenas números inteiros.");
                        scanner.next();
                    }
                    break;
                case 4:
                    System.out.println("\nProcessando próxima demanda com a estratégia ativa: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    gerenciador.executarProximaProducao();
                    break;
                case 5:
                    System.out.println("\nIniciando fabricação de Medicamento Controlado...");
                    gerenciador.fabricarDemanda(0);
                    break;
                case 6:
                    System.out.println("\nIniciando fabricação de Medicamento Contínuo...");
                    gerenciador.fabricarDemanda(1);
                    break;
                case 7:
                    System.out.println("\nIniciando fabricação de Medicamento Genérico...");
                    gerenciador.fabricarDemanda(2);
                    break;
                case 8:
                    gerenciador.exibirArmazem();
                    break;
                case 9:
                    gerenciador.exibirEstoqueMateriaPrima();
                    break;
                case 10:
                    gerenciador.exibirDemandas();
                    break;
                case 11:
                    System.out.print("Digite a quantidade de matéria-prima a ser comprada (kg): ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Entrada inválida! Digite apenas números.");
                        scanner.next();
                    } else {
                        double quantidade = scanner.nextDouble();
                        gerenciador.comprarMateriaPrima(quantidade);
                    }
                    break;
                case 12:
                    System.out.println("\nSelecione a nova Estratégia de Produção:");
                    System.out.println("1 - Ordem de Chegada");
                    System.out.println("2 - Maior Demanda");
                    System.out.println("3 - Maximizar Produção no Orçamento");
                    System.out.print("Escolha: ");
                    if (scanner.hasNextInt()) {
                        int escolhaEstrategia = scanner.nextInt();
                        if (escolhaEstrategia == 1) {
                            gerenciador.setEstrategia(new EstrategiaOrdemDeReceitas());
                        } else if (escolhaEstrategia == 2) {
                            gerenciador.setEstrategia(new EstrategiaMaiorDemanda());
                        } else if (escolhaEstrategia == 3) {
                            gerenciador.setEstrategia(new EstrategiaMaximoProdutos());
                        } else {
                            System.out.println("Opção de estratégia inválida.");
                        }
                    } else {
                        System.out.println("Entrada inválida!");
                        scanner.next();
                    }
                    break;
                case 13:
                    gerenciador.gerarAuditoriaGeral();
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema da fábrica farmacêutica. Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu.");
                    break;
            }
        }

        scanner.close();
    }
}
