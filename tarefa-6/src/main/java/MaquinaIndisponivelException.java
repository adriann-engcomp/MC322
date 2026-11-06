/**
 * Exceção lançada quando uma máquina da linha de produção está quebrada ou impossibilitada de operar.
 * <p>
 * Garante que a linha de fabricação não inicie a passagem de produtos com equipamentos avariados,
 * prevenindo o consumo indevido de matéria-prima e custos de operação sem que o lote seja aprovado.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class MaquinaIndisponivelException extends PlantaException {

    /**
     * Constrói a exceção de máquina indisponível com a mensagem descritiva.
     *
     * @param mensagem Descrição do equipamento indisponível e a causa da parada.
     */
    public MaquinaIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
