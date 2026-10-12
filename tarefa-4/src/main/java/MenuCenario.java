import java.util.Scanner;

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

            int opcao = LeitorDeOpcoes.lerOpcao(scanner);
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
