/**
 * Exceção lançada quando ocorre uma operação inválida ou incompatível relacionada a uma demanda.
 * <p>
 * Exemplos de situações incluem: tentativa de fabricar demanda inexistente, transição de status
 * incompatível com o ciclo de vida (como produzir demanda já concluída ou cancelada), ou
 * especificação de quantidade nula ou negativa.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class DemandaInvalidaException extends PlantaException {

    /**
     * Constrói a exceção de demanda inválida com a mensagem explicativa.
     *
     * @param mensagem Descrição do motivo da invalidade da demanda ou da operação.
     */
    public DemandaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
