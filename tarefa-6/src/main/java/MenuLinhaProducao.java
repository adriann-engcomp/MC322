import java.util.List;
import java.util.Scanner;

/**
 * Interface interativa para visualização e gerenciamento do Agrupador {@link LinhaDeProducao}.
 * <p>
 * Permite ao operador inspecionar as máquinas agregadas na esteira produtiva,
 * verificar a saúde e auditoria estrutural em cascata da linha, e consultar o estado
 * de integridade dos componentes internos de cada posto de trabalho respeitando
 * o desacoplamento e a Lei de Deméter.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MenuLinhaProducao {

    private MenuLinhaProducao() {
    }

    /**
     * Exibe o submenu do Agrupador Linha de Produção.
     *
     * @param scanner     Leitor de entrada padrão do console.
     * @param gerenciador Instância ativa do gerenciador da fábrica.
     */
    public static void exibir(Scanner scanner, GerenciadorProducao gerenciador) {
        LinhaDeProducao linha = gerenciador.getLinhaDeProducao();
        boolean voltar = false;

        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("7. Linha de Produção");
            System.out.println("1. Visão geral da linha e postos agregados");
            System.out.println("2. Auditoria estrutural em cascata (Linha -> Máquinas -> Partes)");
            System.out.println("3. Checar status de subcomponentes via métodos de delegação");
            System.out.println("4. Ligar esteira de produção");
            System.out.println("5. Desligar esteira de produção");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = LeitorDeOpcoes.opcao(scanner);
            if (opcao == -1) {
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n========================================================================================");
                    System.out.printf("LINHA DE PRODUÇÃO: [%s] %s\n", linha.getIdLinha(), linha.getNome());
                    System.out.println("========================================================================================");
                    List<Maquina> maquinas = linha.getMaquinas();
                    if (maquinas.isEmpty()) {
                        System.out.println("Nenhuma máquina alocada nesta linha.");
                    } else {
                        for (int i = 0; i < maquinas.size(); i++) {
                            Maquina m = maquinas.get(i);
                            System.out.printf("Posto %d: %-38s | Tipo: %-38s | Status: %s | Saúde: %.1f\n",
                                    i + 1, m.getNome(), m.getTipo(), m.getStatus().getDescricao(), m.getSaude());
                        }
                    }
                    System.out.println("========================================================================================\n");
                    break;

                case 2:
                    System.out.println("\n" + linha.gerarRelatorioDiagnostico() + "\n");
                    break;

                case 3:
                    System.out.println("\n========================================================================================");
                    System.out.println("CONSULTA DE SUBCOMPONENTES");
                    System.out.println("========================================================================================");
                    for (Maquina m : linha.getMaquinas()) {
                        System.out.printf("Máquina: %s\n", m.getNome());
                        System.out.printf("  - Motor operacional:             %s\n", m.verificaMotorOperacional() ? "SIM (Normal)" : "NÃO (Atenção/Sobreaquecido)");
                        System.out.printf("  - Sensor calibrado (Anvisa):     %s\n", m.verificaSensorCalibrado() ? "SIM (Conforme)" : "NÃO (Descalibrado)");
                        System.out.printf("  - Todos atuadores operacionais:  %s\n", m.verificaAtuadoresOperacionais() ? "SIM" : "NÃO (Travamento/Vazamento)");
                        System.out.printf("  - Máquina precisa manutenção:    %s\n", m.precisaManutencao() ? "SIM" : "NÃO");
                        System.out.println("----------------------------------------------------------------------------------------");
                    }
                    System.out.println("========================================================================================\n");
                    break;

                case 4:
                    linha.ligarTodas();
                    System.out.println("\nTodas as máquinas da linha de produção foram energizadas e ligadas.");
                    break;

                case 5:
                    linha.desligarTodas();
                    System.out.println("\nTodas as máquinas da linha de produção foram desligadas em segurança.");
                    break;

                case 0:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu da Linha de Produção (0 a 5).");
                    break;
            }
        }
    }
}
