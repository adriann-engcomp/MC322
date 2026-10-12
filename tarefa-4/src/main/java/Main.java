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

        Cenario cenario = MenuCenario.exibir(scanner);
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

            int opcao = LeitorDeOpcoes.lerOpcao(scanner);
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
                            ExibidorDeMensagens.exibirPainelErro("Recurso Insuficiente", e.getMessage(), "Nenhuma alteração foi realizada no estoque ou no orçamento.");
                        } catch (DemandaInvalidaException e) {
                            ExibidorDeMensagens.exibirPainelErro("Quantidade Inválida", e.getMessage(), "A compra não foi efetuada.");
                        } catch (PlantaException e) {
                            ExibidorDeMensagens.exibirPainelErro("Falha na Operação", e.getMessage(), "O orçamento permaneceu inalterado.");
                        }
                    }
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
