import java.util.Scanner;

/**
 * Classe utilitária para leitura das opções dos menus.
 * <p>
 * Captura números inteiros, informa entradas inválidas e identifica o fim da entrada.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class LeitorDeOpcoes {
    private LeitorDeOpcoes(){}

    /**
     * Lê uma opção numérica inteira fornecida pelo usuário, tratando entradas não numéricas.
     *
     * @param scanner Leitor do console.
     * @return O número digitado, -1 se a entrada não for int ou 0 se não houver dados.
     */
    public static int opcao(Scanner scanner){
        if (!scanner.hasNext()){
            return 0;
        }
        if (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida! Digite apenas números inteiros.");
            scanner.next();
            return -1;
        }
        return scanner.nextInt();
    }

}
