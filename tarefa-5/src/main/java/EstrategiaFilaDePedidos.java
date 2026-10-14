import java.util.List;

/**
 * Estratégia de produção baseada em fila de atendimento por ordem de chegada (FIFO - First-In, First-Out).
 * <p>
 * Prioriza a demanda elegível mais antiga (ou seja, de menor número de identificador),
 * garantindo equidade temporal no atendimento dos pedidos registrados pelos clientes.
 * </p>
 *
 * @author Adriann e Alexsandro
 */
public class EstrategiaFilaDePedidos implements EstrategiaProducao {
    /**
     * Seleciona a demanda elegível com o menor identificador sequencial (pedido mais antigo).
     *
     * @param demandas            Lista de demandas registradas no sistema.
     * @param orcamentoDisponivel Orçamento financeiro disponível.
     * @return Demanda mais antiga elegível, ou {@code null} se não houver demanda apta.
     */
    @Override 
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        Demanda pedidoEscolhido = null;
        for (Demanda demanda : separoElegiveis(demandas)) {
            if (pedidoEscolhido == null || demanda.getNumero() < pedidoEscolhido.getNumero()) {
                pedidoEscolhido = demanda;
            }
        }
        return pedidoEscolhido;
    }

    /**
     * Retorna o nome amigável da estratégia.
     *
     * @return Nome representativo da estratégia.
     */
    @Override
    public String getNomeEstrategia() {
        return "Fila de Pedidos";
    }
}
