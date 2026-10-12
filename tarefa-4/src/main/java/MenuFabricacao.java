import  java.util.Scanner;

public class MenuFabricacao {
    private MenuFabricacao(){
    }

    /**
    * Exibe o submenu para execução de ordens de fabricação (estratégica ou manual de item específico).
    *
    * @param scanner     Leitor de entrada padrão do console.
    * @param gerenciador Instância ativa do gerenciador da fábrica.
    */
    public static void exibir(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("2. Fabricacao");
            System.out.println("1. Processar proxima demanda (usa estrategia ativa)");
            System.out.println("2. Fabricar item especifico");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = LeitorDeOpcoes.lerOpcao(scanner);
            if (opcao == -1) {
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\nProcessando próxima demanda com a estratégia ativa: " + gerenciador.getEstrategiaAtual().getNomeEstrategia());
                    try {
                        gerenciador.executarProximaProducao();
                    } catch (RecursoInsuficienteException e) {
                        ExibidorDeMensagens.exibirPainelErro("Recurso Insuficiente", e.getMessage(), "Nenhuma alteração foi realizada no estoque ou no orçamento.");
                    } catch (MaquinaIndisponivelException e) {
                        ExibidorDeMensagens.exibirPainelErro("Máquina Indisponível", e.getMessage(), "Nenhuma alteração foi realizada na esteira ou no lote.");
                    } catch (DemandaInvalidaException e) {
                        ExibidorDeMensagens.exibirPainelErro("Demanda Inválida", e.getMessage(), "Nenhuma demanda foi modificada.");
                    } catch (PlantaException e) {
                        ExibidorDeMensagens.exibirPainelErro("Falha Operacional", e.getMessage(), "O estado da fábrica foi mantido consistente.");
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
                    int escolha = LeitorDeOpcoes.lerOpcao(scanner);
                    if (escolha == -1) {
                        break;
                    }

                    try {
                        gerenciador.fabricarDemanda(escolha - 1);
                    } catch (RecursoInsuficienteException e) {
                        ExibidorDeMensagens.exibirPainelErro("Recurso Insuficiente", e.getMessage(), "Nenhuma alteração foi realizada no estoque ou no orçamento.");
                    } catch (MaquinaIndisponivelException e) {
                        ExibidorDeMensagens.exibirPainelErro("Máquina Indisponível", e.getMessage(), "Nenhuma alteração foi realizada na esteira ou no lote.");
                    } catch (DemandaInvalidaException e) {
                        ExibidorDeMensagens.exibirPainelErro("Demanda Inválida", e.getMessage(), "Nenhuma alteração foi realizada na demanda.");
                    } catch (PlantaException e) {
                        ExibidorDeMensagens.exibirPainelErro("Falha Operacional", e.getMessage(), "O estado da fábrica foi mantido consistente.");
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
}
