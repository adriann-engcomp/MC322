/**
 * Lê uma opção numérica inteira fornecida pelo usuário, tratando entradas não numéricas.
 *
 * @param scanner Leitor do console.
 * @return O número digitado ou -1 se a entrada for inválida ou não houver dados.
 */

import java.util.Scanner;

public class LeitorDeOpcoes {
    private LeitorDeOpcoes(){}

    public static int lerOpcao(Scanner scanner){
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
