import java.util.Scanner;

/**
 * Interface de auditoria da fábrica farmacêutica.
 * <p>
 * Exibe relatórios gerais, auditoria estrutural em cascata (Agrupador -> Todo -> Partes),
 * diagnósticos detalhados de máquinas/subcomponentes e produtos do armazém,
 * além de permitir solicitar a manutenção preventiva e corretiva completa.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MenuAuditoria {

    private MenuAuditoria() {
    }

    /**
     * Exibe o submenu para emissão de relatórios de auditoria e diagnóstico em cascata.
     *
     * @param scanner     Leitor de entrada padrão do console.
     * @param gerenciador Instância ativa do gerenciador da fábrica.
     */
    public static void exibir(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("6. Auditoria Farmacêutica e Diagnóstico Estrutural");
            System.out.println("1. Relatorio geral (Linha + Produtos)");
            System.out.println("2. Auditoria estrutural em cascata da Linha de Produção");
            System.out.println("3. Detalhar máquinas e subcomponentes internos");
            System.out.println("4. Detalhar produtos no armazém");
            System.out.println("5. Reparar máquinas e calibrar componentes (manutenção completa)");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = LeitorDeOpcoes.opcao(scanner);
            if (opcao == -1) {
                continue;
            }

            switch (opcao) {
                case 1:
                    gerenciador.gerarAuditoriaGeral();
                    break;
                case 2:
                    System.out.println("\n" + gerenciador.getLinhaDeProducao().gerarRelatorioDiagnostico() + "\n");
                    break;
                case 3:
                    System.out.println("\n========================================================================================");
                    System.out.println("       RELATÓRIO: DETALHAMENTO DE MÁQUINAS E SEUS SUBCOMPONENTES INTERNOS");
                    System.out.println("========================================================================================");
                    for (Maquina maquina : gerenciador.getMaquinas()) {
                        System.out.println(maquina.gerarRelatorioDiagnosticoComIndentacao("    "));
                        System.out.println("----------------------------------------------------------------------------------------");
                    }
                    System.out.println("========================================================================================\n");
                    break;
                case 4:
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
                case 5:
                    int reparadas = gerenciador.repararMaquinas();
                    System.out.println("\n========================================================================================");
                    System.out.println("               MANUTENÇÃO E CALIBRAÇÃO DE COMPONENTES CONCLUÍDA");
                    System.out.println("========================================================================================");
                    System.out.println("Máquinas reparadas/revisadas com sucesso: " + reparadas);
                    System.out.println("Todos os motores foram resfriados/revisados, sensores calibrados (RDC 658)");
                    System.out.println("e válvulas/atuadores tiveram vedações sanitárias limpas e destravadas.");
                    System.out.println("========================================================================================\n");
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida! Escolha um número válido do menu de auditoria (0 a 5).");
                    break;
            }
        }
    }
}
