import java.util.Scanner;

public class MenuEstrategias {
    private MenuEstrategias(){
    }

    /**
    * Exibe o submenu para seleção e troca dinâmica da estratégia de produção ativa.
    *
    * @param scanner     Leitor de entrada padrão do console.
    * @param gerenciador Instância ativa do gerenciador da fábrica.
    */
    public static void exibir(Scanner scanner, GerenciadorProducao gerenciador){

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

            int opcao = LeitorDeOpcoes.lerOpcao(scanner);
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
}

