import java.util.Scanner;

public class MenuAuditoria {
    private MenuAuditoria(){
    }
    
    /**
     * Exibe o submenu para emissão de relatórios de auditoria e diagnóstico de máquinas e produtos.
     *
     * @param scanner     Leitor de entrada padrão do console.
     * @param gerenciador Instância ativa do gerenciador da fábrica.
     */
    public static void exibir(Scanner scanner, GerenciadorProducao gerenciador) {
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

            int opcao = LeitorDeOpcoes.lerOpcao(scanner);
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
