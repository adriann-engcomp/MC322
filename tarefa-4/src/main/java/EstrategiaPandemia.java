import java.util.List;

/**
 * Estratégia de produção voltada para situações de emergência sanitária / pandemia.
 * <p>
 * Em cenários críticos de alta demanda coletiva, essa estratégia busca priorizar o pedido
 * de maior volume de unidades, visando abastecer os estoques hospitalares com a máxima rapidez.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class EstrategiaPandemia implements EstrategiaProducao {

    /**
     * Construtor padrão da estratégia de pandemia.
     */
    public EstrategiaPandemia() {
    }

    /**
     * Seleciona a demanda elegível que possuir a maior quantidade solicitada de unidades.
     *
     * @param demandas            Lista de demandas cadastradas.
     * @param orcamentoDisponivel Orçamento financeiro disponível.
     * @return A demanda elegível de maior volume, ou {@code null} se não houver demanda elegível.
     */
    @Override
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        Demanda maiorDemanda = null;
        for (Demanda demanda : separoElegiveis(demandas)) {
            if (maiorDemanda == null || demanda.getQuantidade() > maiorDemanda.getQuantidade()) {
                maiorDemanda = demanda;
            }
        }
        return maiorDemanda;
    }

    /**
     * Retorna o nome amigável da estratégia.
     *
     * @return Nome da estratégia.
     */
    @Override
    public String getNomeEstrategia() {
        return "Pandemia";
    }
}
