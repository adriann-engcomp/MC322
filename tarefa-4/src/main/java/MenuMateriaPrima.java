import java.util.Scanner;

public class MenuMateriaPrima {
    private MenuMateriaPrima(){
    }

    /**
    * Exibe a interface para compra de matéria-prima, tratando entradas inválidas e falhas na operação.
    *
    * @param scanner     Leitor de entrada padrão do console.
    * @param gerenciador Instância ativa do gerenciador da fábrica.
    */
    public static void exibir(Scanner scanner, GerenciadorProducao gerenciador){
        System.out.print("Digite a quantidade de matéria-prima a ser comprada (kg): ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Entrada inválida! Digite apenas números.");
                        if (scanner.hasNext()) {
                            scanner.next();
                        }
                    } else {
                        double quantidade = scanner.nextDouble();
                        try {
                            gerenciador.comprarMateriaPrima(quantidade);
                        } catch (RecursoInsuficienteException e) {
                            ExibidorDeMensagens.exibirPainelErro("Recurso Insuficiente", e.getMessage(), "Nenhuma alteração foi realizada no estoque ou no orçamento.");
                        } catch (DemandaInvalidaException e) {
                            ExibidorDeMensagens.exibirPainelErro("Quantidade Inválida", e.getMessage(), "A compra não foi efetuada.");
                        } catch (PlantaException e) {
                            ExibidorDeMensagens.exibirPainelErro("Falha na Operação", e.getMessage(), "O orçamento permaneceu inalterado.");
                        }
                    }
    }
}
