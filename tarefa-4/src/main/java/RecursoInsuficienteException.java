/**
 * Exceção lançada quando um recurso essencial da planta (matéria-prima ou orçamento financeiro)
 * é insuficiente para realizar uma operação solicitada.
 * <p>
 * O lançamento desta exceção interrompe a operação antes que qualquer alteração de estado seja
 * efetuada, garantindo que o saldo orçamentário e os estoques permaneçam inalterados.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class RecursoInsuficienteException extends PlantaException {

    /**
     * Constrói a exceção de recurso insuficiente com uma mensagem descritiva.
     *
     * @param mensagem Descrição do recurso deficitário, valores necessários e disponíveis.
     */
    public RecursoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
