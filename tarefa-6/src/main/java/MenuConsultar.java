import java.util.Scanner;

/**
 * Interface de consulta dos estoques da fábrica.
 * <p>
 * Exibe os produtos do armazém e a matéria-prima disponível.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public final class MenuConsultar{
    private MenuConsultar(){
    }

    /**
    * Exibe o submenu para consulta do armazém e do estoque atual de matéria-prima.
    *
    * @param scanner     Leitor de entrada padrão do console.
    * @param gerenciador Instância ativa do gerenciador da fábrica.
    */
    public static void exibir(Scanner scanner, GerenciadorProducao gerenciador) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--------------------------------------------------------------");
            System.out.println("3. Consultar");
            System.out.println("1. Ver armazem (produtos acabados)");
            System.out.println("2. Ver estoque de materia-prima");
            System.out.println("0. Voltar");
            System.out.print("ESCOLHA: ");

            int opcao = LeitorDeOpcoes.opcao(scanner);
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
}