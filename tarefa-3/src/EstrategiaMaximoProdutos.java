import java.util.List;

public class EstrategiaMaximoProdutos implements EstrategiaProducao {

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

    @Override
    public String getNomeEstrategia() {
        return "Maximizar Produção no Orçamento";
    }
}
