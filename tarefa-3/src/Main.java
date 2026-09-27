
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

            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Demandas");
            System.out.println("2. Fabricacao");
            System.out.println("3. Consultar");
            System.out.println("4. Comprar materia-prima");
            System.out.println("5. Gerenciar estrategia");
            System.out.println("6. Auditoria");
            System.out.println("0. Sair");
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
                    menuDemandas(scanner, gerenciador);
                    break;
                case 2:
                    menuFabricacao(scanner, gerenciador);
                    break;
                case 3:
                    menuConsultar(scanner, gerenciador);
                    break;
                case 4:
                    System.out.print("Digite a quantidade de matéria-prima a ser comprada (kg): ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Entrada inválida! Digite apenas números.");
                        scanner.next();
                    } else {
                        double quantidade = scanner.nextDouble();
                        gerenciador.comprarMateriaPrima(quantidade);
                    }
                    break;
                case 5:
                    menuEstrategia(scanner, gerenciador);
                    break;
                case 6:
                    menuAuditoria(scanner, gerenciador);
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

    private static void menuDemandas(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("1. Demandas");
            System.out.println("1. Atualizar demanda");
            System.out.println("2. Listar demandas (status via enum)");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.next();
                continue;
            }

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\nSelecione a demanda para atualizar:");
                    for (int i = 0; i < gerenciador.getDemandas().size(); i++) {
                        System.out.println((i + 1) + " - " + gerenciador.getDemandas().get(i).getMedicamento());
                    }
                    System.out.print("Escolha: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Entrada inválida! Digite apenas números inteiros.");
                        scanner.next();
                    } else {
                        int escolha = scanner.nextInt();
                        if (escolha >= 1 && escolha <= gerenciador.getDemandas().size()) {
                            Demanda d = gerenciador.getDemandas().get(escolha - 1);
                            System.out.print("Digite a nova quantidade para a demanda de " + d.getMedicamento() + ": ");
                            if (scanner.hasNextInt()) {
                                int qtd = scanner.nextInt();
                                gerenciador.atualizarDemanda(escolha - 1, qtd);
                            } else {
                                System.out.println("Entrada inválida! Digite apenas números inteiros.");
                                scanner.next();
                            }
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }
                    break;
                case 2:
                    gerenciador.exibirDemandas();
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu.");
                    break;
            }
        }
    }

    private static void menuFabricacao(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("2. Fabricacao");
            System.out.println("1. Processar proxima demanda (usa estrategia ativa)");
            System.out.println("2. Fabricar item especifico");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.next();
                continue;
            }

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\nProcessando próxima demanda com a estratégia ativa: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    gerenciador.executarProximaProducao();
                    break;
                case 2:
                    System.out.println("\nSelecione o medicamento a ser fabricado:");
                    for (int i = 0; i < gerenciador.getDemandas().size(); i++) {
                        System.out.println((i + 1) + " - " + gerenciador.getDemandas().get(i).getMedicamento());
                    }
                    System.out.print("Escolha: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Entrada inválida! Digite apenas números inteiros.");
                        scanner.next();
                    } else {
                        int escolha = scanner.nextInt();
                        if (escolha >= 1 && escolha <= gerenciador.getDemandas().size()) {
                            Demanda d = gerenciador.getDemandas().get(escolha - 1);
                            System.out.println("\nIniciando fabricação de " + d.getMedicamento() + "...");
                            gerenciador.fabricarDemanda(escolha - 1);
                        } else {
                            System.out.println("Opção inválida!");
                        }
                    }
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu.");
                    break;
            }
        }
    }

    private static void menuConsultar(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("3. Consultar");
            System.out.println("1. Ver armazem (produtos acabados)");
            System.out.println("2. Ver estoque de materia-prima");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.next();
                continue;
            }

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    gerenciador.exibirArmazem();
                    break;
                case 2:
                    gerenciador.exibirEstoqueMateriaPrima();
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu.");
                    break;
            }
        }
    }

    private static void menuEstrategia(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("5. Gerenciar estrategia");
            System.out.printf("Estratégia atual: [%s]\n", gerenciador.getEstrategiaAtual().getNomeEstrategia());
            System.out.println("1. Ordem de Chegada");
            System.out.println("2. Maior Demanda");
            System.out.println("3. Maximizar Producao");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.next();
                continue;
            }

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    gerenciador.setEstrategia(new EstrategiaOrdemDeReceitas());
                    System.out.println("Estratégia alterada para: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    break;
                case 2:
                    gerenciador.setEstrategia(new EstrategiaMaiorDemanda());
                    System.out.println("Estratégia alterada para: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    break;
                case 3:
                    gerenciador.setEstrategia(new EstrategiaMaximoProdutos());
                    System.out.println("Estratégia alterada para: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção de estratégia inválida.");
                    break;
            }
        }
    }

    private static void menuAuditoria(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("6. Auditoria");
            System.out.println("1. Relatorio geral");
            System.out.println("2. Detalhar maquinas");
            System.out.println("3. Detalhar produtos");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.next();
                continue;
            }

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    gerenciador.gerarAuditoriaGeral();
                    break;
                case 2:
                    System.out.println("\n==========================================");
                    System.out.println("       RELATÓRIO: DETALHAR MÁQUINAS");
                    System.out.println("==========================================");
                    for (Maquina maquina : gerenciador.getMaquinas()) {
                        System.out.println(maquina.gerarRelatorioDiagnostico());
                    }
                    System.out.println("==========================================\n");
                    break;
                case 3:
                    System.out.println("\n==========================================");
                    System.out.println("       RELATÓRIO: DETALHAR PRODUTOS");
                    System.out.println("==========================================");
                    if (gerenciador.getProdutosFabricados().isEmpty()) {
                        System.out.println("Nenhum produto fabricado no momento.");
                    } else {
                        for (Produto produto : gerenciador.getProdutosFabricados()) {
                            System.out.println(produto.gerarRelatorioDiagnostico());
                        }
                    }
                    System.out.println("==========================================\n");
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu.");
                    break;
            }
        }
    }
}
