import java.util.Scanner;

public class MenuDemandas {
    private MenuDemandas(){
    }

    /**
    * Exibe o submenu para gerenciamento de demandas (atualização, listagem e cadastro de novos pedidos).
    *
    * @param scanner     Leitor de entrada padrão do console.
    * @param gerenciador Instância ativa do gerenciador da fábrica.
    */
    public static void exibir(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("1. Demandas");
            System.out.println("1. Atualizar demanda");
            System.out.println("2. Listar demandas (status via enum)");
            System.out.println("3. Registrar demanda");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = LeitorDeOpcoes.lerOpcao(scanner);
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
                    int escolha = LeitorDeOpcoes.lerOpcao(scanner);
                    if (escolha == -1) {
                        break;
                    }

                    System.out.print("Digite a nova quantidade: ");
                    int qtd = LeitorDeOpcoes.lerOpcao(scanner);
                    if (qtd == -1) {
                        break;
                    }

                    try {
                        gerenciador.atualizarDemanda(escolha - 1, qtd);
                    } catch (DemandaInvalidaException e) {
                        ExibidorDeMensagens.exibirPainelErro("Demanda Inválida", e.getMessage(), "O pedido permaneceu inalterado.");
                    } catch (PlantaException e) {
                        ExibidorDeMensagens.exibirPainelErro("Operação da Planta", e.getMessage(), "Nenhuma alteração foi realizada.");
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
                        ExibidorDeMensagens.exibirPainelErro("Entrada Inválida", "O nome do medicamento não pode ser vazio.", "Demanda não registrada.");
                        break;
                    }

                    System.out.print("Digite a quantidade da demanda: ");
                    int quantidade = LeitorDeOpcoes.lerOpcao(scanner);
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
                    int tipoEscolha = LeitorDeOpcoes.lerOpcao(scanner);
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
                            ExibidorDeMensagens.exibirPainelErro("Tipo Inválido", "Opção de tipo não reconhecida (escolha 1, 2 ou 3).", "Demanda não registrada.");
                            continue;
                    }

                    try {
                        gerenciador.registrarDemanda(new Demanda(tipo, nomeMedicamento, quantidade, preco));
                        System.out.println("Demanda registrada com sucesso!");
                    } catch (DemandaInvalidaException e) {
                        ExibidorDeMensagens.exibirPainelErro("Demanda Inválida", e.getMessage(), "Demanda não registrada.");
                    } catch (PlantaException e) {
                        ExibidorDeMensagens.exibirPainelErro("Operação da Planta", e.getMessage(), "Demanda não registrada.");
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
}
