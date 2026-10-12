/**
 * Classe base para todas as exceções de domínio da planta farmacêutica.
 * <p>
 * Representa situações excepcionais e violações das regras de negócio operacionais
 * da fábrica, tais como escassez de recursos, estados inconsistentes de pedidos ou falhas de maquinário.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class PlantaException extends RuntimeException {

    /**
     * Constrói uma nova exceção da planta com uma mensagem detalhada de erro.
     *
     * @param mensagem Descrição compreensível do erro ocorrido.
     */
    public PlantaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Constrói uma nova exceção da planta com uma mensagem explicativa e a causa raiz.
     *
     * @param mensagem Descrição do erro.
     * @param causa    Exceção original causadora do problema.
     */
    public PlantaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
