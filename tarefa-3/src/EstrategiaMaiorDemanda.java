import java.util.List;

public class EstrategiaMaiorDemanda implements EstrategiaProducao {

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

    @Override
    public String getNomeEstrategia() {
        return "Maior Demanda";
    }
}
