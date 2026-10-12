import java.util.List;

/**
 * Estratégia de produção voltada para atendimento de editais e lotes do Sistema Único de Saúde (SUS).
 * <p>
 * Prioriza a demanda elegível de maior volume que caiba integralmente dentro do orçamento
 * financeiro atualmente disponível na fábrica, garantindo o cumprimento de licitações
 * sem riscos de insolvência ou paralisação por falta de recursos monetários.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class EstrategiaMedicamentosSus implements EstrategiaProducao {

    /**
     * Construtor padrão da estratégia de medicamentos para o SUS.
     */
    public EstrategiaMedicamentosSus() {
    }

    /**
     * Seleciona a maior demanda elegível cujo custo de operação não exceda o orçamento disponível.
     *
     * @param demandas            Lista de demandas registradas.
     * @param orcamentoDisponivel Orçamento financeiro disponível para custear a operação.
     * @return A maior demanda viável financeiramente, ou {@code null} se nenhuma demanda couber no orçamento.
     */
    @Override
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        Demanda melhorDemanda = null;
        for (Demanda demanda : separoElegiveis(demandas)) {
            if (demanda.cabeNoOrcamento(orcamentoDisponivel)) {
                if (melhorDemanda == null || demanda.getQuantidade() > melhorDemanda.getQuantidade()) {
                    melhorDemanda = demanda;
                }
            }
        }
        return melhorDemanda;
    }

    /**
     * Retorna o nome amigável da estratégia.
     *
     * @return Nome da estratégia.
     */
    @Override
    public String getNomeEstrategia() {
        return "Muitos remédios para fabricar para o SUS";
    }
}
