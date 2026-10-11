/*
EstrategiaOrdemDeRecitas: FIFO
Lógica: Atendo quem chegou primeiro
*/

import java.util.List;

public class EstrategiaFilaDePedidos implements EstrategiaProducao {
    @Override 
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel){
        Demanda pedidoEscolhido = null;
        for (Demanda demanda : separoElegiveis(demandas)){
            // Busco quem chegou logo (menor numero)
            if (pedidoEscolhido == null || demanda.getNumero() < pedidoEscolhido.getNumero()){
                pedidoEscolhido = demanda;
            }
        }
        return pedidoEscolhido;
    }

    @Override
    public String getNomeEstrategia() {
        return "Fila de Pedidos";
    }
}

