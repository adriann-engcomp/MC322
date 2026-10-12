import java.util.Scanner;

/**
 * Interface de seleção do cenário operacional.
 * <p>
 * Apresenta os cenários disponíveis e retorna a escolha válida do usuário.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MenuCenario {

    private MenuCenario(){
    }

    /**
    * Exibe o menu interativo para escolha do cenário operacional inicial da fábrica.
    *
    * @param scanner Leitor de entrada padrão do console.
    * @return O {@link Cenario} escolhido pelo usuário.
    */
    public static Cenario exibir(Scanner scanner){
        while (true) {
            System.out.println("\nEscolha o cenário da fábrica:");
            System.out.println("1 - " + Cenario.FABRICA_NORMAL.getNome());
            System.out.println("2 - " + Cenario.FABRICA_PANDEMIA.getNome());
            System.out.print("Escolha: ");

            int opcao = LeitorDeOpcoes.opcao(scanner);
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
}
