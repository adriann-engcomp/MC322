import java.util.Random;
import java.util.Scanner;

/**
 * Ponto de entrada (CLI) e interface com o usuário da fábrica de medicamentos.
 * <p>
 * Inicializa os módulos de insumo, gerenciador de produção, máquinas especializadas
 * e orquestra a navegação interativa através de menus textuais no console.
 * É responsável por capturar as exceções de domínio disparadas pelas regras de negócio
 * ({@link RecursoInsuficienteException}, {@link DemandaInvalidaException}, {@link MaquinaIndisponivelException}),
 * apresentar mensagens explicativas e amigáveis ao usuário, garantir a recuperação após falhas e
 * manter o estado da aplicação rigorosamente consistente.
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
     * Método principal que inicializa o sistema da fábrica e controla o loop do menu interativo.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Cenario cenario = menuCenario(scanner);
        Random random = new Random(cenario.getSementeAleatoria());

        // 1. Instanciar Matéria-Prima e Gerenciador de Produção
        MateriaPrima materiaPrima = new MateriaPrima(1, "Princípio Ativo Farmacêutico (Insumo)", cenario.getEstoqueInicialMateriaPrima(), "kg", 10.0);
        GerenciadorProducao gerenciador = new GerenciadorProducao(materiaPrima, cenario.getBudgetInicial(), new EstrategiaFilaDePedidos());

        // 2. Instanciar e registrar as Máquinas da linha de produção
        MaquinaProcessamento maquinaProcessamento = new MaquinaProcessamento("Reator Farmacêutico / Mistura", 500, 0.15, 1.50, cenario, random);
        MaquinaEmbalagem maquinaEmbalagem = new MaquinaEmbalagem("Embaladora Blister / Selagem", 500, 0.10, 1.00, cenario, random);
        MaquinaInspecao maquinaInspecao = new MaquinaInspecao("Espectrômetro de Inspeção / Controle Anvisa", 500, 0.05, 2.00, cenario, random);

        gerenciador.adicionarMaquina(maquinaProcessamento);
        gerenciador.adicionarMaquina(maquinaEmbalagem);
        gerenciador.adicionarMaquina(maquinaInspecao);

        // 3. Registrar Demandas Iniciais de Medicamentos
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.CONTROLADO, "Ritalina 40mg", 10, 4.50));
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.CONTINUO, "Losartana 50mg", 20, 4.50));
        gerenciador.registrarDemanda(new Demanda(TipoMedicamento.GENERICO, "Dipirona 500mg", 30, 4.50));

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

            int opcao = lerOpcao(scanner);
            if (opcao == -1) {
                continue;
            }

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
                        if (scanner.hasNext()) {
                            scanner.next();
                        }
                    } else {
                        double quantidade = scanner.nextDouble();
                        try {
                            gerenciador.comprarMateriaPrima(quantidade);
                        } catch (RecursoInsuficienteException e) {
                            exibirPainelErro("Recurso Insuficiente", e.getMessage(), "Nenhuma alteração foi realizada no estoque ou no orçamento.");
                        } catch (DemandaInvalidaException e) {
                            exibirPainelErro("Quantidade Inválida", e.getMessage(), "A compra não foi efetuada.");
                        } catch (PlantaException e) {
                            exibirPainelErro("Falha na Operação", e.getMessage(), "O orçamento permaneceu inalterado.");
                        }
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
                    System.out.println("Opção inválida! Escolha um número válido do menu principal (0 a 6).");
                    break;
            }
        }

        scanner.close();
    }

    /**
     * Exibe o painel visual padronizado de erro com o diagnóstico e a garantia de consistência do estado.
     *
     * @param titulo           Título descritivo da falha ou categoria do erro.
     * @param mensagem         Mensagem explicativa sobre a causa do problema.
     * @param estadoPreservado Descrição das garantias de integridade do estado da aplicação.
     */
    private static void exibirPainelErro(String titulo, String mensagem, String estadoPreservado) {
        System.out.println("\n========================================");
        System.out.println("ERRO: " + titulo.toUpperCase());
        System.out.println("========================================");
        System.out.println(mensagem);
        if (estadoPreservado != null && !estadoPreservado.isBlank()) {
            System.out.println(estadoPreservado);
        }
        System.out.println("========================================\n");
    }

    /**
     * Lê uma opção numérica inteira fornecida pelo usuário, tratando entradas não numéricas.
     *
     * @param scanner Leitor do console.
     * @return O número digitado ou -1 se a entrada for inválida ou não houver dados.
     */
    private static int lerOpcao(Scanner scanner) {
        if (!scanner.hasNext()) {
            return 0;
        }
        if (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida! Digite apenas números inteiros.");
            scanner.next();
            return -1;
        }
        return scanner.nextInt();
    }

    /**
     * Exibe o menu interativo para escolha do cenário operacional inicial da fábrica.
     *
     * @param scanner Leitor de entrada padrão do console.
     * @return O {@link Cenario} escolhido pelo usuário.
     */
    private static Cenario menuCenario(Scanner scanner) {
        while (true) {
            System.out.println("\nEscolha o cenário da fábrica:");
            System.out.println("1 - " + Cenario.FABRICA_NORMAL.getNome());
            System.out.println("2 - " + Cenario.FABRICA_PANDEMIA.getNome());
            System.out.print("Escolha: ");

            int opcao = lerOpcao(scanner);
            if (opcao == 1) {
                return Cenario.FABRICA_NORMAL;
            }
            if (opcao == 2) {
                return Cenario.FABRICA_PANDEMIA;
            }
            if (opcao != -1) {
                System.out.println("Opção inválida! Digite 1 ou 2.");
            }
        }
    }

    /**
     * Exibe o submenu para gerenciamento de demandas (atualização, listagem e cadastro de novos pedidos).
     *
     * @param scanner     Leitor de entrada padrão do console.
     * @param gerenciador Instância ativa do gerenciador da fábrica.
     */
    private static void menuDemandas(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("1. Demandas");
            System.out.println("1. Atualizar demanda");
            System.out.println("2. Listar demandas (status via enum)");
            System.out.println("3. Registrar demanda");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = lerOpcao(scanner);
            if (opcao == -1) {
                continue;
            }

            switch (opcao) {
                case 1:
                    if (gerenciador.getDemandas().isEmpty()) {
                        System.out.println("Nenhuma demanda cadastrada para atualizar.");
                        break;
                    }
                    System.out.println("\nSelecione a demanda para atualizar:");
                    for (int i = 0; i < gerenciador.getDemandas().size(); i++) {
                        System.out.println((i + 1) + " - " + gerenciador.getDemandas().get(i).getMedicamento());
                    }
                    System.out.print("Escolha: ");
                    int escolha = lerOpcao(scanner);
                    if (escolha == -1) {
                        break;
                    }

                    System.out.print("Digite a nova quantidade: ");
                    int qtd = lerOpcao(scanner);
                    if (qtd == -1) {
                        break;
                    }

                    try {
                        gerenciador.atualizarDemanda(escolha - 1, qtd);
                    } catch (DemandaInvalidaException e) {
                        exibirPainelErro("Demanda Inválida", e.getMessage(), "O pedido permaneceu inalterado.");
                    } catch (PlantaException e) {
                        exibirPainelErro("Operação da Planta", e.getMessage(), "Nenhuma alteração foi realizada.");
                    }
                    break;

                case 2:
                    gerenciador.exibirDemandas();
                    break;

                case 3:
                    System.out.print("Digite o nome do medicamento: ");
                    scanner.nextLine();
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                    String nomeMedicamento = scanner.nextLine().trim();
                    if (nomeMedicamento.isEmpty()) {
                        exibirPainelErro("Entrada Inválida", "O nome do medicamento não pode ser vazio.", "Demanda não registrada.");
                        break;
                    }

                    System.out.print("Digite a quantidade da demanda: ");
                    int quantidade = lerOpcao(scanner);
                    if (quantidade == -1) {
                        break;
                    }

                    System.out.print("Digite o preço/custo operacional do medicamento: ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Entrada inválida! Digite apenas números.");
                        if (scanner.hasNext()) {
                            scanner.next();
                        }
                        break;
                    }
                    double preco = scanner.nextDouble();

                    System.out.println("Selecione o tipo de medicamento:");
                    System.out.println("1 - CONTROLADO");
                    System.out.println("2 - CONTINUO");
                    System.out.println("3 - GENERICO");
                    System.out.print("Escolha: ");
                    int tipoEscolha = lerOpcao(scanner);
                    if (tipoEscolha == -1) {
                        break;
                    }

                    TipoMedicamento tipo;
                    switch (tipoEscolha) {
                        case 1:
                            tipo = TipoMedicamento.CONTROLADO;
                            break;
                        case 2:
                            tipo = TipoMedicamento.CONTINUO;
                            break;
                        case 3:
                            tipo = TipoMedicamento.GENERICO;
                            break;
                        default:
                            exibirPainelErro("Tipo Inválido", "Opção de tipo não reconhecida (escolha 1, 2 ou 3).", "Demanda não registrada.");
                            continue;
                    }

                    try {
                        gerenciador.registrarDemanda(new Demanda(tipo, nomeMedicamento, quantidade, preco));
                        System.out.println("Demanda registrada com sucesso!");
                    } catch (DemandaInvalidaException e) {
                        exibirPainelErro("Demanda Inválida", e.getMessage(), "Demanda não registrada.");
                    } catch (PlantaException e) {
                        exibirPainelErro("Operação da Planta", e.getMessage(), "Demanda não registrada.");
                    }
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu de demandas (0 a 3).");
                    break;
            }
        }
    }

    /**
     * Exibe o submenu para execução de ordens de fabricação (estratégica ou manual de item específico).
     *
     * @param scanner     Leitor de entrada padrão do console.
     * @param gerenciador Instância ativa do gerenciador da fábrica.
     */
    private static void menuFabricacao(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("2. Fabricacao");
            System.out.println("1. Processar proxima demanda (usa estrategia ativa)");
            System.out.println("2. Fabricar item especifico");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = lerOpcao(scanner);
            if (opcao == -1) {
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\nProcessando próxima demanda com a estratégia ativa: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    try {
                        gerenciador.executarProximaProducao();
                    } catch (RecursoInsuficienteException e) {
                        exibirPainelErro("Recurso Insuficiente", e.getMessage(), "Nenhuma alteração foi realizada no estoque ou no orçamento.");
                    } catch (MaquinaIndisponivelException e) {
                        exibirPainelErro("Máquina Indisponível", e.getMessage(), "Nenhuma alteração foi realizada na esteira ou no lote.");
                    } catch (DemandaInvalidaException e) {
                        exibirPainelErro("Demanda Inválida", e.getMessage(), "Nenhuma demanda foi modificada.");
                    } catch (PlantaException e) {
                        exibirPainelErro("Falha Operacional", e.getMessage(), "O estado da fábrica foi mantido consistente.");
                    }
                    break;

                case 2:
                    if (gerenciador.getDemandas().isEmpty()) {
                        System.out.println("Não há demandas cadastradas para fabricar.");
                        break;
                    }
                    System.out.println("\nSelecione o medicamento a ser fabricado:");
                    for (int i = 0; i < gerenciador.getDemandas().size(); i++) {
                        System.out.println((i + 1) + " - " + gerenciador.getDemandas().get(i).getMedicamento());
                    }
                    System.out.print("Escolha: ");
                    int escolha = lerOpcao(scanner);
                    if (escolha == -1) {
                        break;
                    }

                    try {
                        gerenciador.fabricarDemanda(escolha - 1);
                    } catch (RecursoInsuficienteException e) {
                        exibirPainelErro("Recurso Insuficiente", e.getMessage(), "Nenhuma alteração foi realizada no estoque ou no orçamento.");
                    } catch (MaquinaIndisponivelException e) {
                        exibirPainelErro("Máquina Indisponível", e.getMessage(), "Nenhuma alteração foi realizada na esteira ou no lote.");
                    } catch (DemandaInvalidaException e) {
                        exibirPainelErro("Demanda Inválida", e.getMessage(), "Nenhuma alteração foi realizada na demanda.");
                    } catch (PlantaException e) {
                        exibirPainelErro("Falha Operacional", e.getMessage(), "O estado da fábrica foi mantido consistente.");
                    }
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu de fabricação (0 a 2).");
                    break;
            }
        }
    }

    /**
     * Exibe o submenu para consulta do armazém e do estoque atual de matéria-prima.
     *
     * @param scanner     Leitor de entrada padrão do console.
     * @param gerenciador Instância ativa do gerenciador da fábrica.
     */
    private static void menuConsultar(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("3. Consultar");
            System.out.println("1. Ver armazem (produtos acabados)");
            System.out.println("2. Ver estoque de materia-prima");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = lerOpcao(scanner);
            if (opcao == -1) {
                continue;
            }

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
                    System.out.println("Opção inválida! Escolha um número válido do menu de consulta (0 a 2).");
                    break;
            }
        }
    }

    /**
     * Exibe o submenu para seleção e troca dinâmica da estratégia de produção ativa.
     *
     * @param scanner     Leitor de entrada padrão do console.
     * @param gerenciador Instância ativa do gerenciador da fábrica.
     */
    private static void menuEstrategia(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("5. Gerenciar estrategia");
            System.out.printf("Estratégia atual: [%s]\n", gerenciador.getEstrategiaAtual().getNomeEstrategia());
            System.out.println("1. Fila de pedidos");
            System.out.println("2. Maior Demanda");
            System.out.println("3. Maximizar Producao");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = lerOpcao(scanner);
            if (opcao == -1) {
                continue;
            }

            switch (opcao) {
                case 1:
                    gerenciador.setEstrategia(new EstrategiaFilaDePedidos());
                    System.out.println("Estratégia alterada para: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    break;
                case 2:
                    gerenciador.setEstrategia(new EstrategiaPandemia());
                    System.out.println("Estratégia alterada para: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    break;
                case 3:
                    gerenciador.setEstrategia(new EstrategiaMedicamentosSus());
                    System.out.println("Estratégia alterada para: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção de estratégia inválida! Escolha entre 0 e 3.");
                    break;
            }
        }
    }

    /**
     * Exibe o submenu para emissão de relatórios de auditoria e diagnóstico de máquinas e produtos.
     *
     * @param scanner     Leitor de entrada padrão do console.
     * @param gerenciador Instância ativa do gerenciador da fábrica.
     */
    private static void menuAuditoria(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("6. Auditoria");
            System.out.println("1. Relatorio geral");
            System.out.println("2. Detalhar maquinas");
            System.out.println("3. Detalhar produtos");
            System.out.println("4. Reparar maquinas (manutencao preventiva/corretiva)");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = lerOpcao(scanner);
            if (opcao == -1) {
                continue;
            }

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
                case 4:
                    int reparadas = gerenciador.repararMaquinas();
                    System.out.println("\n==========================================");
                    System.out.println("MANUTENÇÃO DE MÁQUINAS CONCLUÍDA");
                    System.out.println("==========================================");
                    System.out.println("Máquinas reparadas/revisadas: " + reparadas);
                    System.out.println("Todas as máquinas operacionais estão prontas para a produção.");
                    System.out.println("==========================================\n");
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu de auditoria (0 a 4).");
                    break;
            }
        }
    }
}
